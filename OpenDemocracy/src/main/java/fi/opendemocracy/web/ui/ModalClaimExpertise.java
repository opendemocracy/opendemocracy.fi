package fi.opendemocracy.web.ui;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Slider.ValueOutOfBoundsException;

import fi.opendemocracy.domain.Category;
import fi.opendemocracy.domain.Expert;
import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.PropositionOption;
import fi.opendemocracy.domain.Vote;


//TODO: Link to application ??!
public class ModalClaimExpertise extends Window{
	
	private VerticalLayout expertForm;
	private final Button add = new Button("Claim");
	private final Button cancel = new Button("Cancel");
	private Window main;
	private ODUser currentUser;
	private final RichTextArea description = new RichTextArea("Please describe your expertise in a few words:");
	private Category sourceCategory;
	private Button.ClickListener addButtonClickListener;
	
	public ModalClaimExpertise(Category c){
		sourceCategory = c;
		expertForm = new VerticalLayout();
			
		expertForm.setMargin(true);
		expertForm.setSpacing(true);
		expertForm.setWidth("400px");
					
		addComponent(expertForm);
		setModal(true);
		
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(cancel);
		buttons.addComponent(add);
		buttons.setSpacing(true);
		
		expertForm.addComponent(description);
		expertForm.addComponent(buttons);
		expertForm.setComponentAlignment(buttons, Alignment.TOP_RIGHT);
	}
	
	private void addListeners(){
		cancel.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				main.removeWindow(ModalClaimExpertise.this);
			}
		});
		add.addListener(addButtonClickListener);		
	}
	
	private void setCategory(Category c){
		sourceCategory = c;
		setCaption("Claim expertise in \"" + sourceCategory.getName() + "\"");
		addButtonClickListener = new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				//TODO: Update if expertise exists
				Expert newExpert = new Expert();
				newExpert.setCategory(sourceCategory);
				newExpert.setOdUser(currentUser);
				newExpert.setExpertise(description.getValue().toString());
				newExpert.setTs(new Date());
				newExpert.persist();
				description.setValue("");
				main.removeWindow(ModalClaimExpertise.this);
				main.showNotification("You have claimed expertise in \"" + sourceCategory.getName() + "\"");
			}
		};
	}
	
	@Override
	public void attach() {
		super.attach();
		main = getWindow();
		currentUser = (ODUser) getApplication().getUser();
		setCategory(sourceCategory);
		addListeners();
		if(currentUser == null){
			this.close();
			//TODO: Login msg
		}
	}
	
	
}
