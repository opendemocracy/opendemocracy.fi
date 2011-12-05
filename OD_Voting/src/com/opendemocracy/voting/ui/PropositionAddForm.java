package com.opendemocracy.voting.ui;

import java.util.Collection;
import java.util.Locale;

import com.opendemocracy.voting.VotingApplication;
import com.opendemocracy.voting.data.Option;
import com.opendemocracy.voting.data.Proposition;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.data.Property.ValueChangeListener;

/*Add proposition form, will replace PropositionForm when finished*/
public class PropositionAddForm extends Form implements
ValueChangeListener, ClickListener{
	private static final long serialVersionUID = 1L;
	
	//for unfinished propositions?
	//private boolean draft = true;
	
	private Proposition newProposition = new Proposition();
	private Collection<Option> options;
	
	private final RichTextArea description = new RichTextArea();
	
	private final TextField newOption = new TextField();
	private final Button addOption = new Button("Add", (ClickListener) this);
	private final ListSelect selectOption = new ListSelect();
	private final RichTextArea optionDescription = new RichTextArea();
	
	
	PropositionAddForm(VotingApplication app){ 
						
		// Default form overrides
		setFormFieldFactory(new DefaultFieldFactory() {
			private static final long serialVersionUID = 1L;

			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				
				//Owner not yet implemented
				if (propertyId.equals("owner") || propertyId.equals("id")) {
					return null;
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
		getLayout().addComponent(createOptionsWindow());
		
	}

	private HorizontalLayout createOptionsWindow(){
		/*Options*/
		HorizontalLayout optionsWindow = new HorizontalLayout();
		VerticalLayout optionSelector = new VerticalLayout();
		
		selectOption.setRows(11);
		selectOption.setColumns(15);
		selectOption.setNullSelectionAllowed(false);
		selectOption.addListener((ValueChangeListener)this);
		
		HorizontalLayout addOptionLayout = new HorizontalLayout();
		addOptionLayout.addComponent(newOption);
		addOptionLayout.addComponent(addOption);
		
		optionSelector.addComponent(addOptionLayout);
		optionSelector.addComponent(selectOption);
		
		optionsWindow.addComponent(optionSelector);
		optionsWindow.addComponent(new RichTextArea());
		
		return optionsWindow;
	}
	
	public void valueChange(ValueChangeEvent event) {
		Property source = event.getProperty();
		getWindow().showNotification("Change");
		if (source == selectOption){
			// TODO Hantering av options	
		}
	}

	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();
		if(source == addOption){
			String option = newOption.getValue().toString();
			getWindow().showNotification("Click" + option);
			selectOption.addItem(option);
			// TODO Add option to collection
			// options.add(new Option(0, option, null));
		}
	}
	
}
