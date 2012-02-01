package fi.opendemocracy.web.ui;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.TypedQuery;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Slider.ValueOutOfBoundsException;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import fi.opendemocracy.domain.Category;
import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Proposition;
import fi.opendemocracy.domain.PropositionOption;
import fi.opendemocracy.domain.Vote;
import fi.opendemocracy.web.ThemeConstants;


public class PropositionEntityView extends CustomComponent implements ValueChangeListener {
	final HashMap<PropositionOption, VoteOptionSlider> votes = new HashMap<PropositionOption, VoteOptionSlider>();
	Proposition p;
	private AbsoluteLayout mainLayout;
	private Panel scrollPanel;
	private VerticalLayout scrollContent;
	
	public PropositionEntityView(final Proposition p){
		this.p = p;
		setCaption(p.getName());
		setIcon(ThemeConstants.TAB_ICON_CATEGORIES);
		buildMainLayout();
		setCompositionRoot(mainLayout);
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
		VerticalLayout scrollContent = new VerticalLayout();
		
		Label propositionName = new Label("<h1>" + p.getName() + "</h1>");
		Label propositionDescription = new Label("<p>" + p.getDescription() + "</p>");
		
		propositionName.setContentMode(Label.CONTENT_XHTML);
		propositionDescription.setContentMode(Label.CONTENT_XHTML);

		scrollContent.addComponent(propositionName);
		scrollContent.addComponent(propositionDescription);
		
		Button vote = new Button("Vote", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Object o = getApplication().getUser();
				if (o == null || !(o instanceof ODUser)) {
	            	getWindow().showNotification("You need to login to vote");
					return;
				}
				
				for (Entry<PropositionOption, VoteOptionSlider> e : votes.entrySet()) {
					Vote v = new Vote();
					v.setOdUser((ODUser) o);
					v.setProposition(p);
					v.setPropositionOption(e.getKey());
					v.setSupport(BigDecimal.valueOf((Double)e.getValue().getValue()));
					v.setTs(new Date());
					v.persist();
				}
			}
		});
		
		Button cancel = new Button("Close", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
			}
		});
		
		for (PropositionOption o : p.getPropositionOptions()) {
			scrollContent.addComponent(new Label("<b>"+o.getName(), Label.CONTENT_XHTML));
			scrollContent.addComponent(new Label(o.getDescription(), Label.CONTENT_XHTML));
			VoteOptionSlider oS = new VoteOptionSlider();
			votes.put(o, oS);
			scrollContent.addComponent(oS);
			oS.addListener(this);
		}
		
		HorizontalLayout footer = new HorizontalLayout();
		footer.addComponent(vote);
		footer.addComponent(cancel);
		scrollContent.addComponent(footer);
		
		return scrollContent;
	}
	
	@Override
	public void attach() {
		super.attach();

		List<Vote> currentVotes = null;
			
		Object user = getApplication().getUser();
		if (user != null && user instanceof ODUser) {
			TypedQuery<Vote> voteQuery = Vote.findVotesByOdUserAndProposition((ODUser) user, p);
			currentVotes = voteQuery.getResultList();
		}
		if (currentVotes != null) {
			for (PropositionOption o : votes.keySet()) {
				for (Vote v : currentVotes) {
					if (v.getPropositionOption().getId() == o.getId()) {
						try {
							votes.get(o).setValue(v.getSupport().doubleValue());
						} catch (ValueOutOfBoundsException e) {
							// TODO Use the experts trusted by the user as representatives
							e.printStackTrace();
						}
						break;
					}
				}
			}
		}
	}

	public void valueChange(ValueChangeEvent event) {
		Property property = event.getProperty();
		VoteOptionSlider oS = (VoteOptionSlider) property;
		oS.setMaxAllowed((Double)oS.getValue());
		// TODO Auto-generated method stub

	}
}
