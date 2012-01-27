package fi.opendemocracy.web.ui;

import fi.opendemocracy.web.EntityEditor;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Item;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

import com.vaadin.spring.roo.addon.annotations.RooVaadinVisuallyComposableEntityForm;

/**
 * Form for editing an entity. The layout of this form can be edited with the
 * Vaadin Visual Editor.
 * 
 * Fields are automatically bound to container data sources and item properties
 * based on their names (propertyId + "Field") in the aspect. Implementing
 * methods with the same name as used in the aspect allows "overriding"
 * functionality as such methods replace those from the aspect.
 */
@RooVaadinVisuallyComposableEntityForm(formBackingObject = fi.opendemocracy.domain.Representation.class)
public class RepresentationForm extends CustomComponent implements EntityEditor {

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
    private ComboBox expertField;
    @AutoGenerated
    private ComboBox odUserField;
    @AutoGenerated
    private TextField trustField;
    @AutoGenerated
    private DateField tsField;


    // data item being edited
    private Item item;

    public RepresentationForm() {
        buildMainLayout();
        setCompositionRoot(mainLayout);

        configure();

        // make saving the form the default action on Enter keypress
        saveButton.setClickShortcut(KeyCode.ENTER);

        // TODO add user code here
    }

    public void addSaveActionListener(ClickListener listener) {
        saveButton.addListener(listener);
    }

    public void addCancelActionListener(ClickListener listener) {
        cancelButton.addListener(listener);
    }

    public void addDeleteActionListener(ClickListener listener) {
        deleteButton.addListener(listener);
    }

    public void setSaveAllowed(boolean canSave) {
        saveButton.setVisible(canSave);
        cancelButton.setVisible(canSave);
        saveButton.setEnabled(canSave);
        cancelButton.setEnabled(canSave);

        // do not change the enabled state of the delete button
        fieldLayout.setEnabled(canSave);
    }

    public void setDeleteAllowed(boolean canDelete) {
        deleteButton.setVisible(canDelete);
        deleteButton.setEnabled(canDelete);
    }

    public void setCommitErrorMessage(String message) {
        errorMessageLabel.setVisible(message != null);
   	    errorMessageLabel.setValue(message);
    }

    public void commit() {
        if (getItemDataSource() != null) {
            validateFields();
            setCommitErrorMessage(null);
            commitFields();
        }
    }

    public void setItemDataSource(Item item) {
        // TODO implement

        this.item = item;

        setFieldValues(item);
        setCommitErrorMessage(null);
    }

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
        scrollPanel.setWidth("100.0%");
        scrollPanel.setHeight("100.0%");
        scrollPanel.setImmediate(false);

        // scrollContent
        scrollContent = buildScrollContent();
        scrollPanel.setContent(scrollContent);

        return scrollPanel;
    }

    @AutoGenerated
    private VerticalLayout buildScrollContent() {
        // common part: create layout
        scrollContent = new VerticalLayout();
        scrollContent.setWidth("100.0%");
        scrollContent.setHeight("-1px");
        scrollContent.setImmediate(false);
        scrollContent.setMargin(true);
        scrollContent.setSpacing(true);

        // fieldLayout
        fieldLayout = buildFieldLayout();
        scrollContent.addComponent(fieldLayout);

		// errorMessageLabel
		errorMessageLabel = new Label();
		errorMessageLabel.setWidth("-1px");
		errorMessageLabel.setHeight("-1px");
		errorMessageLabel.setStyleName("errormessage");
		errorMessageLabel.setValue("");
		errorMessageLabel.setImmediate(false);
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
        fieldLayout.setWidth("100.0%");
        fieldLayout.setHeight("-1px");
        fieldLayout.setImmediate(false);
        fieldLayout.setMargin(false);
        fieldLayout.setSpacing(true);

        // expertField
        expertField = new ComboBox();
        expertField.setWidth("-1px");
        expertField.setHeight("-1px");
        expertField.setCaption("Expert");
        expertField.setImmediate(true);
        fieldLayout.addComponent(expertField);

        // odUserField
        odUserField = new ComboBox();
        odUserField.setWidth("-1px");
        odUserField.setHeight("-1px");
        odUserField.setCaption("Od User");
        odUserField.setImmediate(true);
        fieldLayout.addComponent(odUserField);

        // trustField
        trustField = new TextField();
        trustField.setWidth("-1px");
        trustField.setHeight("-1px");
        trustField.setCaption("Trust");
        trustField.setImmediate(true);
        fieldLayout.addComponent(trustField);

        // tsField
        tsField = new DateField();
        tsField.setWidth("-1px");
        tsField.setHeight("-1px");
        tsField.setCaption("Ts");
        tsField.setImmediate(true);
        fieldLayout.addComponent(tsField);


        return fieldLayout;
    }

    @AutoGenerated
    private HorizontalLayout buildButtonLayout() {
        // common part: create layout
        buttonLayout = new HorizontalLayout();
        buttonLayout.setWidth("-1px");
        buttonLayout.setHeight("-1px");
        buttonLayout.setImmediate(false);
        buttonLayout.setMargin(false);
        buttonLayout.setSpacing(true);

        // saveButton
        saveButton = new Button();
        saveButton.setWidth("-1px");
        saveButton.setHeight("-1px");
        saveButton.setCaption("Save");
        saveButton.setStyleName("primary");
        saveButton.setImmediate(true);
        buttonLayout.addComponent(saveButton);

        // cancelButton
        cancelButton = new Button();
        cancelButton.setWidth("-1px");
        cancelButton.setHeight("-1px");
        cancelButton.setCaption("Cancel");
        cancelButton.setImmediate(true);
        buttonLayout.addComponent(cancelButton);

        // deleteButton
        deleteButton = new Button();
        deleteButton.setWidth("-1px");
        deleteButton.setHeight("-1px");
        deleteButton.setCaption("Delete");
        deleteButton.setStyleName("link");
        deleteButton.setImmediate(true);
        buttonLayout.addComponent(deleteButton);
        buttonLayout.setComponentAlignment(deleteButton, new Alignment(48));

        return buttonLayout;
    }

}
