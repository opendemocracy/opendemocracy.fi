package fi.opendemocracy.web.ui.dialogs;

import java.math.BigDecimal;
import java.util.Date;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import fi.opendemocracy.domain.Expert;
import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Representation;
import fi.opendemocracy.web.ui.VoteOptionSlider;

public class ModalTrustExpert extends Window {
	private VerticalLayout trustExpertForm;
	private final Button add = new Button("Assign trust");
	private final Button cancel = new Button("Cancel");
	private Window main;
	private ODUser currentUser;
	private final RichTextArea description = new RichTextArea("Please describe your expertise in a few words:");
	private Expert sourceExpert;
	private Button.ClickListener addButtonClickListener;
	private final VoteOptionSlider trust = new VoteOptionSlider("Trust");
	private Label expertise;
	
	public ModalTrustExpert(final Expert e){
		setWidth("400px");
		sourceExpert = e;
		trustExpertForm = new VerticalLayout();
		trustExpertForm.setMargin(true);
		trustExpertForm.setSpacing(true);
		trustExpertForm.setWidth("100%");
			

		expertise = new Label();
		expertise.setContentMode(Label.CONTENT_XHTML);

		trustExpertForm.addComponent(expertise);
		trustExpertForm.addComponent(trust);
		
		addComponent(trustExpertForm);
		setModal(true);
		
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(cancel);
		buttons.addComponent(add);
		buttons.setSpacing(true);
		trustExpertForm.addComponent(buttons);
		trustExpertForm.setComponentAlignment(buttons, Alignment.TOP_RIGHT);
	}
	
	public void setExpert(Expert e){
		sourceExpert = e;
		setCaption(sourceExpert.getCategory().getName() + " > Experts > " + sourceExpert.getOdUser().getUsername());
		trustExpertForm.removeComponent(expertise);
		expertise = new Label(sourceExpert.getExpertise(), Label.CONTENT_XHTML);
		trustExpertForm.addComponent(expertise, 0);
		addButtonClickListener = new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Representation r = new Representation();
				r.setExpert(sourceExpert);
				r.setOdUser(currentUser);
				r.setTrust(BigDecimal.valueOf((Double)trust.getValue()));
				r.setTs(new Date());
				r.persist();
				ModalTrustExpert.this.close();
			}

		};
	}
	
	private void addListeners(){
		cancel.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ModalTrustExpert.this.close();
			}
		});
		add.addListener(addButtonClickListener);		
	}
	
	@Override
	public void attach() {
		super.attach();
		Object o = getApplication().getUser();
		if (o == null || !(o instanceof ODUser)) {
			this.close();
			getApplication().getMainWindow().showNotification("You need to login");
			return;
		}
		main = getWindow();
		setExpert(sourceExpert);
		addListeners();
	}
}
