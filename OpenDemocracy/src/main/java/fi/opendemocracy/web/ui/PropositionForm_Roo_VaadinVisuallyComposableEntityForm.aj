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
import fi.opendemocracy.domain.Category;
import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Proposition;
import fi.opendemocracy.domain.PropositionOption;
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
import org.vaadin.addon.customfield.beanfield.BeanSetFieldPropertyConverter;

privileged aspect PropositionForm_Roo_VaadinVisuallyComposableEntityForm {
    
    private Map<Object, Field> PropositionForm.fieldMap = new LinkedHashMap<Object, Field>();
    
    private Map<Object, PropertyConverter> PropositionForm.converterMap = new LinkedHashMap<Object, PropertyConverter>();
    
    private JPAContainer<ODUser> PropositionForm.containerForODUsers;
    
    private JPAContainer<Category> PropositionForm.containerForCategorys;
    
    private JPAContainer<PropositionOption> PropositionForm.containerForPropositionOptions;
    
    public Collection<Object> PropositionForm.getBeanPropertyIds() {
        return Arrays.asList(new Object[] { "author", "name", "description", "categories", "propositionOptions", "ts" });
    }
    
    public Field PropositionForm.getField(Object propertyId) {
        return fieldMap.get(propertyId);
    }
    
    public void PropositionForm.configure() {
        configureFieldMap();
        configureFields();
        configureContainersForFields();
        configureConverters();
        configureValidators();
    }
    
    public void PropositionForm.refresh() {
        configureContainersForFields();
        configureConverters();
        configureValidators();
    }
    
    public boolean PropositionForm.isModified() {
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
    
    public void PropositionForm.configureFieldMap() {
        fieldMap.put("name", nameField);
        fieldMap.put("description", descriptionField);
        fieldMap.put("categories", categoriesField);
    }
    
    public void PropositionForm.configureFields() {
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
    
    public void PropositionForm.configureContainersForFields() {
        Field field;
        
        field = getField("author");
        if (field instanceof AbstractSelect) {
            ((AbstractSelect) field).setContainerDataSource(getContainerForODUsers());
            Object captionId = getODUserCaptionPropertyId();
            if (captionId != null) {
                ((AbstractSelect) field).setItemCaptionPropertyId(captionId);
            } else {
                ((AbstractSelect) field).setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ITEM);
            }
        }
        
        field = getField("categories");
        if (field instanceof AbstractSelect) {
            ((AbstractSelect) field).setContainerDataSource(getContainerForCategorys());
            Object captionId = getCategoryCaptionPropertyId();
            if (captionId != null) {
                ((AbstractSelect) field).setItemCaptionPropertyId(captionId);
            } else {
                ((AbstractSelect) field).setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ITEM);
            }
        }
        
        field = getField("propositionOptions");
        if (field instanceof AbstractSelect) {
            ((AbstractSelect) field).setContainerDataSource(getContainerForPropositionOptions());
            Object captionId = getPropositionOptionCaptionPropertyId();
            if (captionId != null) {
                ((AbstractSelect) field).setItemCaptionPropertyId(captionId);
            } else {
                ((AbstractSelect) field).setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ITEM);
            }
        }
    }
    
    public void PropositionForm.configureConverters() {
        // cannot parametrize PropertyConverter here due to an AJDT bug
        PropertyConverter converter;
        Container container;
        Field field;
        
        field = getField("author");
        if (field instanceof AbstractSelect) {
            container = ((AbstractSelect) field).getContainerDataSource();
            converter = new BeanFieldPropertyConverter<ODUser, Long>(ODUser.class, container, "id");
            converterMap.put("author", converter);
        }
        
        field = getField("categories");
        if (field instanceof AbstractSelect) {
            container = ((AbstractSelect) field).getContainerDataSource();
            converter = new BeanSetFieldPropertyConverter<Category, Long>(Category.class, container, "id");
            converterMap.put("categories", converter);
        }
        
        field = getField("propositionOptions");
        if (field instanceof AbstractSelect) {
            container = ((AbstractSelect) field).getContainerDataSource();
            converter = new BeanSetFieldPropertyConverter<PropositionOption, Long>(PropositionOption.class, container, "id");
            converterMap.put("propositionOptions", converter);
        }
    }
    
    public void PropositionForm.configureValidators() {
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
    
    public PropertyConverter PropositionForm.getConverter(Object propertyId) {
        return converterMap.get(propertyId);
    }
    
    public JPAContainer<ODUser> PropositionForm.getContainerForODUsers() {
        if (containerForODUsers == null) {
            JPAContainer<ODUser> container = new JPAContainer<ODUser>(ODUser.class);
            container.setEntityProvider(EntityProviderUtil.get().getEntityProvider(ODUser.class));
            containerForODUsers = container;
        }
        return containerForODUsers;
    }
    
    public JPAContainer<Category> PropositionForm.getContainerForCategorys() {
        if (containerForCategorys == null) {
            JPAContainer<Category> container = new JPAContainer<Category>(Category.class);
            container.setEntityProvider(EntityProviderUtil.get().getEntityProvider(Category.class));
            containerForCategorys = container;
        }
        return containerForCategorys;
    }
    
    public JPAContainer<PropositionOption> PropositionForm.getContainerForPropositionOptions() {
        if (containerForPropositionOptions == null) {
            JPAContainer<PropositionOption> container = new JPAContainer<PropositionOption>(PropositionOption.class);
            container.setEntityProvider(EntityProviderUtil.get().getEntityProvider(PropositionOption.class));
            containerForPropositionOptions = container;
        }
        return containerForPropositionOptions;
    }
    
    public Object PropositionForm.getODUserCaptionPropertyId() {
        return null;
    }
    
    public Object PropositionForm.getCategoryCaptionPropertyId() {
        return "name";
    }
    
    public Object PropositionForm.getPropositionOptionCaptionPropertyId() {
        return "name";
    }
    
    public String PropositionForm.getIdProperty() {
        return "id";
    }
    
    public String PropositionForm.getVersionProperty() {
        return "version";
    }
    
    public void PropositionForm.validateFields() {
        if (getItemDataSource() != null) {
            for (Object propertyId : getItemDataSource().getItemPropertyIds()) {
                Field field = getField(propertyId);
                if (field != null && !field.isReadOnly()) {
                    field.validate();
                }
            }
        }
    }
    
    public void PropositionForm.commitFields() {
        if (getItemDataSource() != null) {
            for (Object propertyId : getItemDataSource().getItemPropertyIds()) {
                Field field = getField(propertyId);
                if (field != null && !field.isReadOnly()) {
                    field.commit();
                }
            }
        }
    }
    
    public void PropositionForm.setFieldPropertyDataSource(Object propertyId, Property property) {
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
    
    public void PropositionForm.setFieldValues(Item item) {
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
    
    public Field PropositionForm.getFirstField() {
        Iterator<Object> it = getBeanPropertyIds().iterator();
        if (it.hasNext()) {
            return getField(it.next());
        }
        return null;
    }
    
    public Class<Proposition> PropositionForm.getEntityClass() {
        return Proposition.class;
    }
    
}
