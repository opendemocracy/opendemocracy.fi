package fi.opendemocracy.web.ui;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Item;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.spring.roo.addon.annotations.RooVaadinVisuallyComposableEntityForm;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Proposition;
import fi.opendemocracy.domain.PropositionOption;
import fi.opendemocracy.web.EntityEditor;

/**
 * Form for editing an entity. The layout of this form can be edited with the
 * Vaadin Visual Editor.
 * 
 * Fields are automatically bound to container data sources and item properties
 * based on their names (propertyId + "Field") in the aspect. Implementing
 * methods with the same name as used in the aspect allows "overriding"
 * functionality as such methods replace those from the aspect.
 */
@RooVaadinVisuallyComposableEntityForm(formBackingObject = fi.opendemocracy.domain.Proposition.class)
public class PropositionForm extends CustomComponent implements EntityEditor {

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Panel scrollPanel;
	@AutoGenerated
	private VerticalLayout scrollContent;
	@AutoGenerated
	private HorizontalLayout buttonLayout;
	@AutoGenerated
	private Button deleteButton;
	@AutoGenerated
	private Button cancelButton;
	@AutoGenerated
	private Button saveButton;
	@AutoGenerated
	private Label errorMessageLabel;
	@AutoGenerated
	private VerticalLayout fieldLayout;
	@AutoGenerated
	private Button addOptionButton;
	@AutoGenerated
	private VerticalLayout optionsLayout;
	@AutoGenerated
	private TwinColSelect categoriesField;
	@AutoGenerated
	private RichTextArea descriptionField;
	@AutoGenerated
	private TextField nameField;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	//Add option modal
	private VerticalLayout optionForm;
	private Window wDialog; 
	
	//Container for temporary options
	private HashSet<PropositionOption> propositionOptions = new HashSet<PropositionOption>();
	
	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	// data item being edited
	private Item item;

	public PropositionForm() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		configure();

		// make saving the form the default action on Enter keypress
		saveButton.setClickShortcut(KeyCode.ENTER);

		
		// TODO add user code here
		addOptionButton.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				addNewOptionForm();
			}
		});
	}

	@Override
	public void attach() {
		// TODO Auto-generated method stub
		super.attach();

		if (getApplication().getUser() == null) {

			Label loginMsg = new Label();
			loginMsg.setStyleName("errormessage");
			loginMsg.setImmediate(false);
			loginMsg.setValue("You need to be logged in to make propositions");
			scrollContent.addComponent(loginMsg, 0);
		}
	}
	
	private void addNewOptionForm() {
		final Window main = getWindow();
		if(optionForm == null){
			optionForm = new VerticalLayout();
			optionForm.setMargin(true);
			optionForm.setSpacing(true);
			optionForm.setWidth("400px");
			
			final TextField name = new TextField("Name");
			final RichTextArea description = new RichTextArea("Description");
			optionForm.addComponent(new Label("New Option"));
			optionForm.addComponent(name);
			optionForm.addComponent(description);
			
			wDialog = new Window("Add Option", optionForm);
			wDialog.setModal(true);
			
			Button cancel = new Button("Cancel", new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					main.removeWindow(wDialog);
				}
			});
			Button add = new Button("Continue", new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					final PropositionOption newOption = new PropositionOption();
					newOption.setDescription(description.getValue().toString());
					newOption.setName(name.getValue().toString());
					newOption.setTs(new Date());
					propositionOptions.add(newOption);
					
					final VerticalLayout optionContainer = new VerticalLayout();
					optionsLayout.addComponent(optionContainer);
					HorizontalLayout titleContainer = new HorizontalLayout();
					Label labelName = new Label("<h2>" + name.getValue().toString() + "</h2>");
					titleContainer.addComponent(labelName);
					
					labelName.setContentMode(Label.CONTENT_XHTML);
					Label labelDescription = new Label(description.getValue().toString());
					labelDescription.setContentMode(Label.CONTENT_XHTML);		


					Button removeOption = new Button("Remove", new Button.ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							propositionOptions.remove(newOption);
							optionsLayout.removeComponent(optionContainer);
						}
					});
					
					titleContainer.addComponent(removeOption);
					
					optionContainer.addComponent(titleContainer);
					optionContainer.addComponent(labelDescription);
					

					
					name.setValue(null);
					description.setValue(null);
					main.removeWindow(wDialog);
				}

			});
			HorizontalLayout buttons = new HorizontalLayout();
			buttons.addComponent(cancel);
			buttons.addComponent(add);
			buttons.setSpacing(true);
			optionForm.addComponent(buttons);
			optionForm.setComponentAlignment(buttons, Alignment.TOP_RIGHT);
		}
		main.addWindow(wDialog);
	}
	
	
	@Override
	public void addSaveActionListener(ClickListener listener) {
		saveButton.addListener(listener);
	}

	@Override
	public void addCancelActionListener(ClickListener listener) {
		cancelButton.addListener(listener);
	}

	@Override
	public void addDeleteActionListener(ClickListener listener) {
		deleteButton.addListener(listener);
	}

	@Override
	public void setSaveAllowed(boolean canSave) {
		saveButton.setVisible(canSave);
		cancelButton.setVisible(canSave);
		saveButton.setEnabled(canSave);
		cancelButton.setEnabled(canSave);

		// do not change the enabled state of the delete button
		fieldLayout.setEnabled(canSave);
	}

	@Override
	public void setDeleteAllowed(boolean canDelete) {
		deleteButton.setVisible(canDelete);
		deleteButton.setEnabled(canDelete);
	}

	@Override
	public void setCommitErrorMessage(String message) {
		errorMessageLabel.setVisible(message != null);
		errorMessageLabel.setValue(message);
	}

	@Override
	public void commit() {
		if (getItemDataSource() != null) {
			validateFields();
			Object user = getApplication().getUser();
			if (user == null || !(user instanceof ODUser)) {
				setCommitErrorMessage("You need to be logged in to make propositions");
				return;
			}
			setCommitErrorMessage(null);
			Proposition p = getEntityForItem(item);
			if (p != null) {
				Set<PropositionOption> set = p.getPropositionOptions();
				set.addAll(propositionOptions);
				p.setPropositionOptions(set);
				p.setTs(new Date());
				p.setAuthor((ODUser) getApplication().getUser());
			}
			commitFields();
			propositionOptions.clear();
		}
	}
    
    public Proposition getEntityForItem(Item item) {
        if (item instanceof EntityItem && ((EntityItem) item).getEntity() instanceof Proposition) {
            return (Proposition) ((EntityItem) item).getEntity();
        } else {
            return null;
        }
    }

	@Override
	public void setItemDataSource(Item item) {
		// TODO implement

		this.item = item;

		setFieldValues(item);
		setCommitErrorMessage(null);
	}

	@Override
	public Item getItemDataSource() {
		return item;
	}

	@Override
	public void focus() {
		Field field = getFirstField();
		if (field != null) {
			field.focus();
		}
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// scrollPanel
		scrollPanel = buildScrollPanel();
		mainLayout.addComponent(scrollPanel);
		
		return mainLayout;
	}

	@AutoGenerated
	private Panel buildScrollPanel() {
		// common part: create layout
		scrollPanel = new Panel();
		scrollPanel.setImmediate(false);
		scrollPanel.setWidth("100.0%");
		scrollPanel.setHeight("100.0%");
		
		// scrollContent
		scrollContent = buildScrollContent();
		scrollPanel.setContent(scrollContent);
		
		return scrollPanel;
	}

	@AutoGenerated
	private VerticalLayout buildScrollContent() {
		// common part: create layout
		scrollContent = new VerticalLayout();
		scrollContent.setImmediate(false);
		scrollContent.setWidth("100.0%");
		scrollContent.setHeight("-1px");
		scrollContent.setMargin(true);
		scrollContent.setSpacing(true);
		
		// fieldLayout
		fieldLayout = buildFieldLayout();
		scrollContent.addComponent(fieldLayout);
		
		// errorMessageLabel
		errorMessageLabel = new Label();
		errorMessageLabel.setStyleName("errormessage");
		errorMessageLabel.setImmediate(false);
		errorMessageLabel.setWidth("-1px");
		errorMessageLabel.setHeight("-1px");
		errorMessageLabel.setValue("Label");
		scrollContent.addComponent(errorMessageLabel);
		
		// buttonLayout
		buttonLayout = buildButtonLayout();
		scrollContent.addComponent(buttonLayout);
		
		return scrollContent;
	}

	@AutoGenerated
	private VerticalLayout buildFieldLayout() {
		// common part: create layout
		fieldLayout = new VerticalLayout();
		fieldLayout.setImmediate(false);
		fieldLayout.setWidth("100.0%");
		fieldLayout.setHeight("-1px");
		fieldLayout.setMargin(false);
		fieldLayout.setSpacing(true);
		
		// nameField
		nameField = new TextField();
		nameField.setCaption("Name");
		nameField.setImmediate(true);
		nameField.setWidth("-1px");
		nameField.setHeight("-1px");
		nameField.setSecret(false);
		fieldLayout.addComponent(nameField);
		
		// descriptionField
		descriptionField = new RichTextArea();
		descriptionField.setCaption("Description");
		descriptionField.setImmediate(true);
		descriptionField.setDescription("Describe your proposition");
		descriptionField.setWidth("-1px");
		descriptionField.setHeight("-1px");
		fieldLayout.addComponent(descriptionField);
		
		// categoriesField
		categoriesField = new TwinColSelect();
		categoriesField.setCaption("Categories");
		categoriesField.setImmediate(true);
		categoriesField.setWidth("-1px");
		categoriesField.setHeight("-1px");
		fieldLayout.addComponent(categoriesField);
		
		// optionsLayout
		optionsLayout = new VerticalLayout();
		optionsLayout.setImmediate(false);
		optionsLayout.setWidth("100.0%");
		optionsLayout.setHeight("-1px");
		optionsLayout.setMargin(false);
		fieldLayout.addComponent(optionsLayout);
		
		// addOptionButton
		addOptionButton = new Button();
		addOptionButton.setCaption("Add new option");
		addOptionButton.setImmediate(true);
		addOptionButton.setWidth("-1px");
		addOptionButton.setHeight("-1px");
		fieldLayout.addComponent(addOptionButton);
		
		return fieldLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildButtonLayout() {
		// common part: create layout
		buttonLayout = new HorizontalLayout();
		buttonLayout.setImmediate(false);
		buttonLayout.setWidth("-1px");
		buttonLayout.setHeight("-1px");
		buttonLayout.setMargin(false);
		buttonLayout.setSpacing(true);
		
		// saveButton
		saveButton = new Button();
		saveButton.setStyleName("primary");
		saveButton.setCaption("Save");
		saveButton.setImmediate(true);
		saveButton.setWidth("-1px");
		saveButton.setHeight("-1px");
		buttonLayout.addComponent(saveButton);
		
		// cancelButton
		cancelButton = new Button();
		cancelButton.setCaption("Cancel");
		cancelButton.setImmediate(true);
		cancelButton.setWidth("-1px");
		cancelButton.setHeight("-1px");
		buttonLayout.addComponent(cancelButton);
		
		// deleteButton
		deleteButton = new Button();
		deleteButton.setStyleName("link");
		deleteButton.setCaption("Delete");
		deleteButton.setImmediate(true);
		deleteButton.setWidth("-1px");
		deleteButton.setHeight("-1px");
		buttonLayout.addComponent(deleteButton);
		buttonLayout.setComponentAlignment(deleteButton, new Alignment(48));
		
		return buttonLayout;
	}

}
