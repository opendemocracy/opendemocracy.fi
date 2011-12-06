package com.opendemocracy.voting.ui;

import java.util.Collection;

import com.opendemocracy.voting.VotingApplication;
import com.opendemocracy.voting.data.Option;
import com.opendemocracy.voting.data.Proposition;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/*Add proposition form, will replace PropositionForm when finished*/
public class PropositionAddForm extends Form implements
Property.ValueChangeListener, ClickListener{
	private static final long serialVersionUID = 1L;
	
	//for unfinished propositions?
	//private boolean draft = true;
	
	private Proposition newProposition = new Proposition();
	
	private final RichTextArea description = new RichTextArea();
	private Collection<Option> options;
	
	private final TextField newOption = new TextField();
	private final TextField pTitle = new TextField();
	private final Button addOption = new Button("Add", (ClickListener) this);
	private ListSelect selectOption = new ListSelect("", options);
	private final TextArea optionDescription = new TextArea();
	
	PropositionAddForm(VotingApplication app){
		selectOption.addListener((ValueChangeListener)this);
		
		setWriteThrough(false);
		getLayout().addComponent(new Label("Add proposition"));
		
		pTitle.setInputPrompt("Title");
		description.setNullSettingAllowed(false);
		pTitle.setNullSettingAllowed(false);
		pTitle.setCaption("Title");
		
		// Default form overrides
		setFormFieldFactory(new DefaultFieldFactory() {
			private static final long serialVersionUID = 1L;

			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				//Hide unused fields
				if (propertyId.equals("owner") || propertyId.equals("id") || propertyId.equals("targetUsers") || propertyId.equals("targetCategories")) {
					return null;
				}
				//Set input prompt to title
				else if (propertyId.equals("title")) {
					return pTitle;
				}
				//Set description to rich text area
				else if (propertyId.equals("description")) {
					return description;
				}
				//Options are handled by createOptionsWindow()
				else if (propertyId.equals("options")){
					return null;
				}
				Field field = super.createField(item, propertyId, uiContext);
				field.setWidth("200px");
				
				return field;
			}
		});
		setItemDataSource(new BeanItem<Proposition>(newProposition));

		
		//Add optionwindow to form
		getLayout().addComponent(new Label("Manage options"));
		getLayout().addComponent(createOptionsEditor());
	}

	//Option editor
	private HorizontalLayout createOptionsEditor(){
		HorizontalLayout optionsWindow = new HorizontalLayout();
		VerticalLayout optionSelector = new VerticalLayout();
		
		newOption.setInputPrompt("New option");
		selectOption.setRows(11);
		selectOption.setColumns(15);
		selectOption.setNullSelectionAllowed(false);
		optionDescription.setInputPrompt("Description");
		optionDescription.setRows(12);
		optionDescription.setColumns(40);
		
		//new option + add button
		HorizontalLayout addOptionLayout = new HorizontalLayout();
		addOptionLayout.addComponent(newOption);
		addOptionLayout.addComponent(addOption);
		
		//selectlist
		optionSelector.addComponent(addOptionLayout);
		optionSelector.addComponent(selectOption);
		
		//textarea
		optionsWindow.addComponent(optionSelector);
		optionsWindow.addComponent(optionDescription);
		
		return optionsWindow;
	}
	
	public void valueChange(ValueChangeEvent event) {
		getWindow().showNotification("valueChange");
		Property source = event.getProperty();
		if (source == selectOption){
			// TODO Hantering av options	
		}
	}

	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();
		if(source == addOption){
			Option addMe = new Option(0,newOption.getValue().toString(),"",null);
			selectOption.addItem(addMe);
			//newProposition.addOption(addMe);
		}
	}
	
}
