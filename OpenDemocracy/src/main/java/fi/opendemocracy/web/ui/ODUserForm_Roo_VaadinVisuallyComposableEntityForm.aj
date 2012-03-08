// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.web.ui;

import com.vaadin.addon.beanvalidation.BeanValidationValidator;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;
import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.UserRole;
import java.lang.Class;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.vaadin.addon.customfield.ConvertingValidator;
import org.vaadin.addon.customfield.PropertyConverter;

privileged aspect ODUserForm_Roo_VaadinVisuallyComposableEntityForm {
    
    private Map<Object, Field> ODUserForm.fieldMap = new LinkedHashMap<Object, Field>();
    
    private Map<Object, PropertyConverter> ODUserForm.converterMap = new LinkedHashMap<Object, PropertyConverter>();
    
    private BeanItemContainer<UserRole> ODUserForm.containerForUserRoles;
    
    public Collection<Object> ODUserForm.getBeanPropertyIds() {
        return Arrays.asList(new Object[] { "userRole", "openIdIdentifier", "username", "emailAddress", "description", "firstName", "lastName", "ts" });
    }
    
    public Field ODUserForm.getField(Object propertyId) {
        return fieldMap.get(propertyId);
    }
    
    public void ODUserForm.configure() {
        configureFieldMap();
        configureFields();
        configureContainersForFields();
        configureConverters();
        configureValidators();
    }
    
    public void ODUserForm.refresh() {
        configureContainersForFields();
        configureConverters();
        configureValidators();
    }
    
    public boolean ODUserForm.isModified() {
        if (getItemDataSource() != null) {
            for (Object propertyId : getItemDataSource().getItemPropertyIds()) {
                Field field = getField(propertyId);
                if (field != null && field.isModified()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void ODUserForm.configureFieldMap() {
        fieldMap.put("username", usernameField);
        fieldMap.put("emailAddress", emailAddressField);
        fieldMap.put("description", descriptionField);
        fieldMap.put("firstName", firstNameField);
        fieldMap.put("lastName", lastNameField);
    }
    
    public void ODUserForm.configureFields() {
        for (Object propertyId : getBeanPropertyIds()) {
            Field field = getField(propertyId);
            if (field == null) {
                continue;
            }
            if (field instanceof TextField) {
                ((TextField) field).setNullRepresentation("");
            }
            field.setWriteThrough(false);
            field.setInvalidAllowed(true);
        }
    }
    
    public void ODUserForm.configureContainersForFields() {
        Field field;
        
        field = getField("userRole");
        if (field instanceof AbstractSelect) {
            ((AbstractSelect) field).setContainerDataSource(getContainerForUserRoles());
            Object captionId = getUserRoleCaptionPropertyId();
            if (captionId != null) {
                ((AbstractSelect) field).setItemCaptionPropertyId(captionId);
            } else {
                ((AbstractSelect) field).setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);
            }
        }
    }
    
    public void ODUserForm.configureConverters() {
        // cannot parametrize PropertyConverter here due to an AJDT bug
        PropertyConverter converter;
        Container container;
        Field field;
        
    }
    
    public void ODUserForm.configureValidators() {
        for (Object propertyId : getBeanPropertyIds()) {
            Field field = getField(propertyId);
            if (field != null) {
                Collection<Validator> validators = field.getValidators();
                if (validators != null) {
                    for (Validator validator : new ArrayList<Validator>(field.getValidators())) {
                        if (validator instanceof BeanValidationValidator || validator instanceof ConvertingValidator) {
                            field.removeValidator(validator);
                        }
                    }
                }
                BeanValidationValidator validator = new BeanValidationValidator(getEntityClass(), String.valueOf(propertyId));
                if (validator.isRequired()) {
                    field.setRequired(true);
                    field.setRequiredError(validator.getRequiredMessage());
                }
                PropertyConverter converter = getConverter(propertyId);
                if (converter == null) {
                    field.addValidator(validator);
                } else {
                    field.addValidator(new ConvertingValidator(validator, converter));
                }
            }
        }
    }
    
    public PropertyConverter ODUserForm.getConverter(Object propertyId) {
        return converterMap.get(propertyId);
    }
    
    public BeanItemContainer<UserRole> ODUserForm.getContainerForUserRoles() {
        if (containerForUserRoles == null) {
            Collection<UserRole> items = Arrays.asList(UserRole.class.getEnumConstants());
            BeanItemContainer<UserRole> container = new BeanItemContainer<UserRole>(items);
            containerForUserRoles = container;
        }
        return containerForUserRoles;
    }
    
    public Object ODUserForm.getUserRoleCaptionPropertyId() {
        return null;
    }
    
    public String ODUserForm.getIdProperty() {
        return "id";
    }
    
    public String ODUserForm.getVersionProperty() {
        return "version";
    }
    
    public void ODUserForm.validateFields() {
        if (getItemDataSource() != null) {
            for (Object propertyId : getItemDataSource().getItemPropertyIds()) {
                Field field = getField(propertyId);
                if (field != null && !field.isReadOnly()) {
                    field.validate();
                }
            }
        }
    }
    
    public void ODUserForm.commitFields() {
        if (getItemDataSource() != null) {
            for (Object propertyId : getItemDataSource().getItemPropertyIds()) {
                Field field = getField(propertyId);
                if (field != null && !field.isReadOnly()) {
                    field.commit();
                }
            }
        }
    }
    
    public void ODUserForm.setFieldPropertyDataSource(Object propertyId, Property property) {
        Field field = getField(propertyId);
        if (field == null) {
            return;
        }
        if (property == null) {
            field.setPropertyDataSource(null);
        } else {
            PropertyConverter converter = getConverter(propertyId);
            if (converter != null) {
                converter.setPropertyDataSource(property);
                field.setPropertyDataSource(converter);
            } else {
                if (field instanceof CheckBox && property.getValue() == null) {
                    property.setValue(Boolean.FALSE);
                }
                field.setPropertyDataSource(property);
            }
        }
    }
    
    public void ODUserForm.setFieldValues(Item item) {
        if (item != null) {
            // set values for fields in item
            for (Object propertyId : item.getItemPropertyIds()) {
                setFieldPropertyDataSource(propertyId, item.getItemProperty(propertyId));
            }
            // other fields are not touched by default
        } else {
            // reset all fields
            for (Object propertyId : getBeanPropertyIds()) {
                setFieldPropertyDataSource(propertyId, null);
            }
        }
    }
    
    public Field ODUserForm.getFirstField() {
        Iterator<Object> it = getBeanPropertyIds().iterator();
        if (it.hasNext()) {
            return getField(it.next());
        }
        return null;
    }
    
    public Class<ODUser> ODUserForm.getEntityClass() {
        return ODUser.class;
    }
    
}
