// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.web.ui;

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
import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Proposition;
import fi.opendemocracy.domain.PropositionOption;
import fi.opendemocracy.domain.Vote;
import fi.opendemocracy.web.EntityProviderUtil;
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

privileged aspect VoteForm_Roo_VaadinVisuallyComposableEntityForm {
    
    private Map<Object, Field> VoteForm.fieldMap = new LinkedHashMap<Object, Field>();
    
    private Map<Object, PropertyConverter> VoteForm.converterMap = new LinkedHashMap<Object, PropertyConverter>();
    
    private JPAContainer<ODUser> VoteForm.containerForODUsers;
    
    private JPAContainer<Proposition> VoteForm.containerForPropositions;
    
    private JPAContainer<PropositionOption> VoteForm.containerForPropositionOptions;
    
    public Collection<Object> VoteForm.getBeanPropertyIds() {
        return Arrays.asList(new Object[] { "odUser", "proposition", "propositionOption", "support", "comment", "ts" });
    }
    
    public Field VoteForm.getField(Object propertyId) {
        return fieldMap.get(propertyId);
    }
    
    public void VoteForm.configure() {
        configureFieldMap();
        configureFields();
        configureContainersForFields();
        configureConverters();
        configureValidators();
    }
    
    public void VoteForm.refresh() {
        configureContainersForFields();
        configureConverters();
        configureValidators();
    }
    
    public boolean VoteForm.isModified() {
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
    
    public void VoteForm.configureFieldMap() {
        fieldMap.put("odUser", odUserField);
        fieldMap.put("proposition", propositionField);
        fieldMap.put("propositionOption", propositionOptionField);
        fieldMap.put("support", supportField);
        fieldMap.put("comment", commentField);
        fieldMap.put("ts", tsField);
    }
    
    public void VoteForm.configureFields() {
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
    
    public void VoteForm.configureContainersForFields() {
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
        
        field = getField("propositionOption");
        if (field instanceof AbstractSelect) {
            ((AbstractSelect) field).setContainerDataSource(getContainerForPropositionOptions());
            Object captionId = getPropositionOptionCaptionPropertyId();
            if (captionId != null) {
                ((AbstractSelect) field).setItemCaptionPropertyId(captionId);
            } else {
                ((AbstractSelect) field).setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ITEM);
            }
        }
        
        field = getField("odUser");
        if (field instanceof AbstractSelect) {
            ((AbstractSelect) field).setContainerDataSource(getContainerForODUsers());
            Object captionId = getODUserCaptionPropertyId();
            if (captionId != null) {
                ((AbstractSelect) field).setItemCaptionPropertyId(captionId);
            } else {
                ((AbstractSelect) field).setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ITEM);
            }
        }
    }
    
    public void VoteForm.configureConverters() {
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
        
        field = getField("propositionOption");
        if (field instanceof AbstractSelect) {
            container = ((AbstractSelect) field).getContainerDataSource();
            converter = new BeanFieldPropertyConverter<PropositionOption, Long>(PropositionOption.class, container, "id");
            converterMap.put("propositionOption", converter);
        }
        
        field = getField("odUser");
        if (field instanceof AbstractSelect) {
            container = ((AbstractSelect) field).getContainerDataSource();
            converter = new BeanFieldPropertyConverter<ODUser, Long>(ODUser.class, container, "id");
            converterMap.put("odUser", converter);
        }
    }
    
    public void VoteForm.configureValidators() {
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
    
    public PropertyConverter VoteForm.getConverter(Object propertyId) {
        return converterMap.get(propertyId);
    }
    
    public JPAContainer<ODUser> VoteForm.getContainerForODUsers() {
        if (containerForODUsers == null) {
            JPAContainer<ODUser> container = new JPAContainer<ODUser>(ODUser.class);
            container.setEntityProvider(EntityProviderUtil.get().getEntityProvider(ODUser.class));
            containerForODUsers = container;
        }
        return containerForODUsers;
    }
    
    public JPAContainer<Proposition> VoteForm.getContainerForPropositions() {
        if (containerForPropositions == null) {
            JPAContainer<Proposition> container = new JPAContainer<Proposition>(Proposition.class);
            container.setEntityProvider(EntityProviderUtil.get().getEntityProvider(Proposition.class));
            containerForPropositions = container;
        }
        return containerForPropositions;
    }
    
    public JPAContainer<PropositionOption> VoteForm.getContainerForPropositionOptions() {
        if (containerForPropositionOptions == null) {
            JPAContainer<PropositionOption> container = new JPAContainer<PropositionOption>(PropositionOption.class);
            container.setEntityProvider(EntityProviderUtil.get().getEntityProvider(PropositionOption.class));
            containerForPropositionOptions = container;
        }
        return containerForPropositionOptions;
    }
    
    public Object VoteForm.getODUserCaptionPropertyId() {
        return null;
    }
    
    public Object VoteForm.getPropositionCaptionPropertyId() {
        return "name";
    }
    
    public Object VoteForm.getPropositionOptionCaptionPropertyId() {
        return "name";
    }
    
    public String VoteForm.getIdProperty() {
        return "id";
    }
    
    public String VoteForm.getVersionProperty() {
        return "version";
    }
    
    public void VoteForm.validateFields() {
        if (getItemDataSource() != null) {
            for (Object propertyId : getItemDataSource().getItemPropertyIds()) {
                Field field = getField(propertyId);
                if (field != null && !field.isReadOnly()) {
                    field.validate();
                }
            }
        }
    }
    
    public void VoteForm.commitFields() {
        if (getItemDataSource() != null) {
            for (Object propertyId : getItemDataSource().getItemPropertyIds()) {
                Field field = getField(propertyId);
                if (field != null && !field.isReadOnly()) {
                    field.commit();
                }
            }
        }
    }
    
    public void VoteForm.setFieldPropertyDataSource(Object propertyId, Property property) {
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
    
    public void VoteForm.setFieldValues(Item item) {
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
    
    public Field VoteForm.getFirstField() {
        Iterator<Object> it = getBeanPropertyIds().iterator();
        if (it.hasNext()) {
            return getField(it.next());
        }
        return null;
    }
    
    public Class<Vote> VoteForm.getEntityClass() {
        return Vote.class;
    }
    
}
