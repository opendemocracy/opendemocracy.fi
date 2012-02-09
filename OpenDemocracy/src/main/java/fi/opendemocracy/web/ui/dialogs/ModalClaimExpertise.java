package fi.opendemocracy.web.ui.dialogs;

import java.util.Date;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import fi.opendemocracy.domain.Category;
import fi.opendemocracy.domain.Expert;
import fi.opendemocracy.domain.ODUser;

public class ModalClaimExpertise extends Window{
	
	private VerticalLayout expertForm;
	private final Button add = new Button("Claim");
	private final Button cancel = new Button("Cancel");
	private ODUser currentUser;
	private final RichTextArea description = new RichTextArea("Please describe your expertise in a few words:");
	private Category sourceCategory;
	private Button.ClickListener addButtonClickListener;
	private Expert newExpert;
	public ModalClaimExpertise(Category c){
		setWidth("400px");
		
		sourceCategory = c;
		expertForm = new VerticalLayout();
		expertForm.setWidth("100%");

		addComponent(expertForm);
		setModal(true);
		
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(cancel);
		buttons.addComponent(add);
		buttons.setSpacing(true);
		
		description.setWidth("100%");
		
		expertForm.addComponent(description);
		expertForm.addComponent(buttons);
		expertForm.setComponentAlignment(buttons, Alignment.TOP_RIGHT);
	
	}
	
	private void addListeners(){
		cancel.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ModalClaimExpertise.this.close();
			}
		});
		add.addListener(addButtonClickListener);		
	}
	
	public void setCategory(Category c){
		sourceCategory = c;
		setCaption("Claim expertise in \"" + sourceCategory.getName() + "\"");
		addButtonClickListener = new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				//TODO: Update if expertise exists
				newExpert.setCategory(sourceCategory);
				newExpert.setOdUser(currentUser);
				newExpert.setExpertise(description.getValue().toString());
				newExpert.setTs(new Date());
				
				if(newExpert.getId() == null){
					newExpert.persist();	
				}else{
					newExpert.merge();
				}
				description.setValue("");
				ModalClaimExpertise.this.close();
			}
		};
	}
	
	@Override
	public void attach() {
		super.attach();
		Object o = getApplication().getUser();
		if (o == null || !(o instanceof ODUser)) {
			this.close();
			return;
		}
		currentUser = (ODUser) o;

		if((newExpert = Expert.findExpertByOdUserAndCategory(currentUser, sourceCategory)) != null){
			description.setValue(newExpert.getExpertise());
		}else{
			newExpert = new Expert();
		}
		setCategory(sourceCategory);
		addListeners();
	}
	
	
}
