package fi.opendemocracy.web.ui;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.TypedQuery;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Slider.ValueOutOfBoundsException;
import com.vaadin.ui.themes.Reindeer;

import fi.opendemocracy.domain.Category;
import fi.opendemocracy.domain.Expert;
import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Proposition;
import fi.opendemocracy.domain.PropositionOption;
import fi.opendemocracy.domain.Representation;
import fi.opendemocracy.domain.Vote;
import fi.opendemocracy.web.EntityProviderUtil;
import fi.opendemocracy.web.OpenDemocracyVotingApplication;
import fi.opendemocracy.web.ThemeConstants;
import fi.opendemocracy.web.ui.dialogs.ModalTrustExpert;

public class UserProfile extends CustomComponent {
	
	private AbsoluteLayout mainLayout;
	private ODUser user;
	private Panel scrollPanel;
	
	private VerticalLayout scrollContent;
	
	private Category currentCategory;
	private Expert currentExpert;
	private Equal categoryFilter;
	private Equal propositionFilter;
	private Label cTitle;
	private Label cExpertise;
	
	public UserProfile(ODUser odUser) {
		user = odUser;
		buildMainLayout();
		setCompositionRoot(mainLayout);
		String name = user.getUsername();
		setCaption("User: " + (name == null || name.isEmpty() ? user.getId() : name));
		setIcon(ThemeConstants.TAB_ICON_USER);
	}
	
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();

		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

		// scrollPanel
		scrollPanel = buildScrollPanel();
		mainLayout.addComponent(scrollPanel);

		return mainLayout;
	}


	private Panel buildScrollPanel() {
		// common part: create layout
		scrollPanel = new Panel();
		scrollPanel.setWidth("100.0%");
		scrollPanel.setHeight("100.0%");
		scrollPanel.setImmediate(false);

		// scrollContent
		scrollContent = buildScrollContent();
		scrollPanel.setContent(scrollContent);

		return scrollPanel;
	}

	private VerticalLayout buildScrollContent() {
		scrollContent = new VerticalLayout();
		scrollContent.setMargin(true);
		String title = user.getFirstName() + " " + user.getLastName();
		if(title.isEmpty() || title.equals("null null")){
			title = (user.getUsername() == null) ? "User #" + user.getId().toString() : user.getUsername();
		}

		scrollContent.addComponent(new Label("<h1>" + title + "</h1>", Label.CONTENT_XHTML));
		scrollContent.addComponent(new Label("<p>" + user.getDescription() + "</p>", Label.CONTENT_XHTML));
		
		//Container for categories user is expert in
		final JPAContainer<Expert> expertContainer = new JPAContainer<Expert>(Expert.class);
		expertContainer.setEntityProvider(EntityProviderUtil.get().getEntityProvider(Expert.class));
		expertContainer.addContainerFilter(new Equal("odUser", user));
		
		if(expertContainer != null && expertContainer.size() > 0){
			//Selector for categories
			final NativeSelect cSelect = new NativeSelect("Please select category");
			cSelect.setContainerDataSource(expertContainer);
			cSelect.setNullSelectionAllowed(false);
			cSelect.setImmediate(true);
			cSelect.setItemCaptionPropertyId("category");
			cSelect.setValue(expertContainer.getIdByIndex(0));
			scrollContent.addComponent(cSelect);
			
			//TODO: Valuechangelistener for table updates, default category
			currentExpert = expertContainer.getItem(cSelect.getValue()).getEntity();
			currentCategory = currentExpert.getCategory();
			
			cTitle = new Label("<h2>" + currentCategory.getName() + " - Expertise</h2>", Label.CONTENT_XHTML);
			cExpertise = new Label("<p>" + currentExpert.getExpertise() + "</p>", Label.CONTENT_XHTML);

			scrollContent.addComponent(cTitle);
			scrollContent.addComponent(cExpertise);
			
			//TODO: Support, Opposition
			
			//Container for propositions created by user in category
			final JPAContainer<Proposition> propositionContainer = new JPAContainer<Proposition>(Proposition.class);
			propositionContainer.setEntityProvider(EntityProviderUtil.get().getEntityProvider(Proposition.class));
	
			categoryFilter = new Equal("categories", currentCategory);
			propositionContainer.addContainerFilter(new Equal("author", user));
			propositionContainer.addContainerFilter(categoryFilter);
			
			final Table pTable = new Table();
			final Label noPropositions = new Label("<i>User has not created any propositions in this category</i>", Label.CONTENT_XHTML);
			pTable.setNullSelectionAllowed(false);
			pTable.setSelectable(false);
			pTable.setCaption("Created propositions");
			pTable.addStyleName(Reindeer.TABLE_STRONG);
			pTable.setContainerDataSource(propositionContainer);
			pTable.setVisibleColumns(new Object[] {"ts", "name", "description"});
						
			scrollContent.addComponent((propositionContainer.size() == 0) ? noPropositions : pTable);
				
			final BeanItemContainer<Proposition> voteContainer = new BeanItemContainer<Proposition>(Proposition.class);
			List<Proposition> hasVotedInPropositions = Proposition.findPropositionsByExpertAndExpertHasVoted(currentExpert).getResultList();
			
			for (Proposition p : hasVotedInPropositions) {
				voteContainer.addItem(p);
			}
						
			final Table vTable = new Table();
			final Label noVotes = new Label("<i>User has not voted in any propositions</i>", Label.CONTENT_XHTML);
			vTable.setNullSelectionAllowed(false);
			vTable.setCaption("Recent votes");
			vTable.addStyleName(Reindeer.TABLE_STRONG);
			vTable.setImmediate(true);
			vTable.setSelectable(true);
			vTable.setContainerDataSource(voteContainer);
			scrollContent.addComponent((voteContainer.size() == 0) ? noVotes : vTable);
			vTable.setVisibleColumns(new Object[] {"ts", "name", "description"});
		
			vTable.addListener(new ItemClickListener() {
				@Override
				public void itemClick(ItemClickEvent event) {
					Object value = event.getItemId();
					if(vTable.getValue() == value && event.getButton() == ItemClickEvent.BUTTON_LEFT){
						popup(currentExpert, voteContainer.getItem(value).getBean());
					}
				}
			});
			
			cSelect.addListener(new NativeSelect.ValueChangeListener(){
				@Override
				public void valueChange(ValueChangeEvent event) {
					currentExpert = expertContainer.getItem(cSelect.getValue()).getEntity();
					currentCategory = currentExpert.getCategory();
					
					//Update category info
					Label newTitle = new Label("<h2>" + currentCategory.getName() + " - Expertise</h2>", Label.CONTENT_XHTML);;
					Label newExpertise = new Label("<p>" + currentExpert.getExpertise() + "</p>", Label.CONTENT_XHTML);
					scrollContent.replaceComponent(cTitle, newTitle);
					scrollContent.replaceComponent(cExpertise, newExpertise);
					cTitle = newTitle;
					cExpertise = newExpertise;

					//Update created propositions table
					propositionContainer.removeContainerFilter(categoryFilter);
					categoryFilter = new Equal("categories", currentCategory);
					propositionContainer.addContainerFilter(categoryFilter);
					if(propositionContainer.size() == 0 && scrollContent.getComponentIndex(pTable) != -1){
						scrollContent.replaceComponent(pTable, noPropositions);
					}else if (propositionContainer.size() > 0 && scrollContent.getComponentIndex(noPropositions) != -1){
						scrollContent.replaceComponent(noPropositions, pTable);
					}
					
					//Update vote propositions table
					voteContainer.removeAllItems();
					List<Proposition> hasVotedInPropositions = Proposition.findPropositionsByExpertAndExpertHasVoted(currentExpert).getResultList();
					for (Proposition p : hasVotedInPropositions) {
						voteContainer.addItem(p);
					}
					if(voteContainer.size() == 0 && scrollContent.getComponentIndex(vTable) != -1){
						scrollContent.replaceComponent(vTable, noVotes);
					}else if (voteContainer.size() > 0 && scrollContent.getComponentIndex(noVotes) != -1){
						scrollContent.replaceComponent(noVotes, vTable);
					}
				}
			});
		}
		
		return scrollContent;
	}
	
	
	private void popup(Expert e, Proposition p){
		Window w = new Window();
		w.setModal(true);
		w.setWidth("400px");
				
		List<Vote> currentVotes = null;
		VerticalLayout content = new VerticalLayout();
		
		w.addComponent(content);

		boolean votesExist = false;
		if (user != null && user instanceof ODUser) {
			TypedQuery<Vote> voteQuery = Vote.findVotesByOdUserAndPropositionLatest(e.getOdUser(), p);
			currentVotes = voteQuery.getResultList();
		}
		
		if (currentVotes != null) {
			for (Vote vote : currentVotes) {
				votesExist |= true;
				content.addComponent(new Label("<b>" + vote.getPropositionOption().getName() + "</b>", Label.CONTENT_XHTML));
				content.addComponent(new Label(vote.getPropositionOption().getDescription(), Label.CONTENT_XHTML));
				content.addComponent(new Label("<i>Expert voted:</i>" + vote.getSupport().toPlainString(), Label.CONTENT_XHTML));
			}
		}
		
		if (!votesExist) {

			content.addComponent(new Label("<b>No votes</b>", Label.CONTENT_XHTML));
		}
		
		getWindow().addWindow(w);
	}	

}
