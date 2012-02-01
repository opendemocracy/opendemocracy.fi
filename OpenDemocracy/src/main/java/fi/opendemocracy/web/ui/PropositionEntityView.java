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
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider.ValueOutOfBoundsException;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import fi.opendemocracy.domain.Category;
import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Proposition;
import fi.opendemocracy.domain.PropositionOption;
import fi.opendemocracy.domain.Vote;
import fi.opendemocracy.web.ThemeConstants;


public class PropositionEntityView extends VerticalLayout implements ValueChangeListener {
	final HashMap<PropositionOption, VoteOptionSlider> votes = new HashMap<PropositionOption, VoteOptionSlider>();
	Proposition p;
	
	public PropositionEntityView(final Proposition p){
		this.p = p;
		setCaption(p.getName());
		setIcon(ThemeConstants.TAB_ICON_CATEGORIES);
		
		Label propositionName = new Label("<h1>" + p.getName() + "</h1>");
		Label propositionDescription = new Label("<p>" + p.getDescription() + "</p>");
		
		propositionName.setContentMode(Label.CONTENT_XHTML);
		propositionDescription.setContentMode(Label.CONTENT_XHTML);

		addComponent(propositionName);
		addComponent(propositionDescription);
		
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
			addComponent(new Label("<b>"+o.getName()+"</b>"+o.getDescription(), Label.CONTENT_XHTML));
			VoteOptionSlider oS = new VoteOptionSlider();
			votes.put(o, oS);
			addComponent(oS);
			oS.addListener(this);
		}
		
		setSizeFull();
		setMargin(true);
		setSpacing(false);
		
		HorizontalLayout footer = new HorizontalLayout();
		footer.addComponent(vote);
		footer.addComponent(cancel);
		addComponent(footer);
		setComponentAlignment(footer, Alignment.BOTTOM_LEFT);
		
		setWidth("440px");
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
