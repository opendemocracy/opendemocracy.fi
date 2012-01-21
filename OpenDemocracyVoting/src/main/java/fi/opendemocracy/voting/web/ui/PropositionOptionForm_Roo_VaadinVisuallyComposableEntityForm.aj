// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.voting.web.ui;

import com.vaadin.addon.beanvalidation.BeanValidationValidator;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;
import fi.opendemocracy.voting.domain.Proposition;
import fi.opendemocracy.voting.domain.PropositionOption;
import fi.opendemocracy.voting.web.EntityProviderUtil;
import java.lang.Class;
import java.lang.Long;
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
import org.vaadin.addon.customfield.beanfield.BeanFieldPropertyConverter;

privileged aspect PropositionOptionForm_Roo_VaadinVisuallyComposableEntityForm {
    
    private Map<Object, Field> PropositionOptionForm.fieldMap = new LinkedHashMap<Object, Field>();
    
    private Map<Object, PropertyConverter> PropositionOptionForm.converterMap = new LinkedHashMap<Object, PropertyConverter>();
    
    private JPAContainer<Proposition> PropositionOptionForm.containerForPropositions;
    
    public Collection<Object> PropositionOptionForm.getBeanPropertyIds() {
        return Arrays.asList(new Object[] { "name", "description", "proposition" });
    }
    
    public Field PropositionOptionForm.getField(Object propertyId) {
        return fieldMap.get(propertyId);
    }
    
    public void PropositionOptionForm.configure() {
        configureFieldMap();
        configureFields();
        configureContainersForFields();
        configureConverters();
        configureValidators();
    }
    
    public void PropositionOptionForm.refresh() {
        configureContainersForFields();
        configureConverters();
        configureValidators();
    }
    
    public boolean PropositionOptionForm.isModified() {
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
    
    public void PropositionOptionForm.configureFieldMap() {
        fieldMap.put("name", nameField);
        fieldMap.put("description", descriptionField);
        fieldMap.put("proposition", propositionField);
    }
    
    public void PropositionOptionForm.configureFields() {
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
    
    public void PropositionOptionForm.configureContainersForFields() {
        Field field;
        
        field = getField("proposition");
        if (field instanceof AbstractSelect) {
            ((AbstractSelect) field).setContainerDataSource(getContainerForPropositions());
            Object captionId = getPropositionCaptionPropertyId();
            if (captionId != null) {
                ((AbstractSelect) field).setItemCaptionPropertyId(captionId);
            } else {
                ((AbstractSelect) field).setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ITEM);
            }
        }
    }
    
    public void PropositionOptionForm.configureConverters() {
        // cannot parametrize PropertyConverter here due to an AJDT bug
        PropertyConverter converter;
        Container container;
        Field field;
        
        field = getField("proposition");
        if (field instanceof AbstractSelect) {
            container = ((AbstractSelect) field).getContainerDataSource();
            converter = new BeanFieldPropertyConverter<Proposition, Long>(Proposition.class, container, "id");
            converterMap.put("proposition", converter);
        }
    }
    
    public void PropositionOptionForm.configureValidators() {
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
    
    public PropertyConverter PropositionOptionForm.getConverter(Object propertyId) {
        return converterMap.get(propertyId);
    }
    
    public JPAContainer<Proposition> PropositionOptionForm.getContainerForPropositions() {
        if (containerForPropositions == null) {
            JPAContainer<Proposition> container = new JPAContainer<Proposition>(Proposition.class);
            container.setEntityProvider(EntityProviderUtil.get().getEntityProvider(Proposition.class));
            containerForPropositions = container;
        }
        return containerForPropositions;
    }
    
    public Object PropositionOptionForm.getPropositionCaptionPropertyId() {
        return "name";
    }
    
    public String PropositionOptionForm.getIdProperty() {
        return "id";
    }
    
    public String PropositionOptionForm.getVersionProperty() {
        return "version";
    }
    
    public void PropositionOptionForm.validateFields() {
        if (getItemDataSource() != null) {
            for (Object propertyId : getItemDataSource().getItemPropertyIds()) {
                Field field = getField(propertyId);
                if (field != null && !field.isReadOnly()) {
                    field.validate();
                }
            }
        }
    }
    
    public void PropositionOptionForm.commitFields() {
        if (getItemDataSource() != null) {
            for (Object propertyId : getItemDataSource().getItemPropertyIds()) {
                Field field = getField(propertyId);
                if (field != null && !field.isReadOnly()) {
                    field.commit();
                }
            }
        }
    }
    
    public void PropositionOptionForm.setFieldPropertyDataSource(Object propertyId, Property property) {
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
    
    public void PropositionOptionForm.setFieldValues(Item item) {
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
    
    public Field PropositionOptionForm.getFirstField() {
        Iterator<Object> it = getBeanPropertyIds().iterator();
        if (it.hasNext()) {
            return getField(it.next());
        }
        return null;
    }
    
    public Class<PropositionOption> PropositionOptionForm.getEntityClass() {
        return PropositionOption.class;
    }
    
}