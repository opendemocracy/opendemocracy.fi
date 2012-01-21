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
import fi.opendemocracy.voting.domain.Category;
import fi.opendemocracy.voting.domain.Expert;
import fi.opendemocracy.voting.domain.ODUser;
import fi.opendemocracy.voting.domain.Representation;
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

privileged aspect RepresentationForm_Roo_VaadinVisuallyComposableEntityForm {
    
    private Map<Object, Field> RepresentationForm.fieldMap = new LinkedHashMap<Object, Field>();
    
    private Map<Object, PropertyConverter> RepresentationForm.converterMap = new LinkedHashMap<Object, PropertyConverter>();
    
    private JPAContainer<Category> RepresentationForm.containerForCategorys;
    
    private JPAContainer<Expert> RepresentationForm.containerForExperts;
    
    private JPAContainer<ODUser> RepresentationForm.containerForODUsers;
    
    public Collection<Object> RepresentationForm.getBeanPropertyIds() {
        return Arrays.asList(new Object[] { "category", "expert", "odUser", "trust" });
    }
    
    public Field RepresentationForm.getField(Object propertyId) {
        return fieldMap.get(propertyId);
    }
    
    public void RepresentationForm.configure() {
        configureFieldMap();
        configureFields();
        configureContainersForFields();
        configureConverters();
        configureValidators();
    }
    
    public void RepresentationForm.refresh() {
        configureContainersForFields();
        configureConverters();
        configureValidators();
    }
    
    public boolean RepresentationForm.isModified() {
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
    
    public void RepresentationForm.configureFieldMap() {
        fieldMap.put("category", categoryField);
        fieldMap.put("expert", expertField);
        fieldMap.put("odUser", odUserField);
        fieldMap.put("trust", trustField);
    }
    
    public void RepresentationForm.configureFields() {
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
    
    public void RepresentationForm.configureContainersForFields() {
        Field field;
        
        field = getField("expert");
        if (field instanceof AbstractSelect) {
            ((AbstractSelect) field).setContainerDataSource(getContainerForExperts());
            Object captionId = getExpertCaptionPropertyId();
            if (captionId != null) {
                ((AbstractSelect) field).setItemCaptionPropertyId(captionId);
            } else {
                ((AbstractSelect) field).setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ITEM);
            }
        }
        
        field = getField("category");
        if (field instanceof AbstractSelect) {
            ((AbstractSelect) field).setContainerDataSource(getContainerForCategorys());
            Object captionId = getCategoryCaptionPropertyId();
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
    
    public void RepresentationForm.configureConverters() {
        // cannot parametrize PropertyConverter here due to an AJDT bug
        PropertyConverter converter;
        Container container;
        Field field;
        
        field = getField("expert");
        if (field instanceof AbstractSelect) {
            container = ((AbstractSelect) field).getContainerDataSource();
            converter = new BeanFieldPropertyConverter<Expert, Long>(Expert.class, container, "id");
            converterMap.put("expert", converter);
        }
        
        field = getField("category");
        if (field instanceof AbstractSelect) {
            container = ((AbstractSelect) field).getContainerDataSource();
            converter = new BeanFieldPropertyConverter<Category, Long>(Category.class, container, "id");
            converterMap.put("category", converter);
        }
        
        field = getField("odUser");
        if (field instanceof AbstractSelect) {
            container = ((AbstractSelect) field).getContainerDataSource();
            converter = new BeanFieldPropertyConverter<ODUser, Long>(ODUser.class, container, "id");
            converterMap.put("odUser", converter);
        }
    }
    
    public void RepresentationForm.configureValidators() {
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
    
    public PropertyConverter RepresentationForm.getConverter(Object propertyId) {
        return converterMap.get(propertyId);
    }
    
    public JPAContainer<Category> RepresentationForm.getContainerForCategorys() {
        if (containerForCategorys == null) {
            JPAContainer<Category> container = new JPAContainer<Category>(Category.class);
            container.setEntityProvider(EntityProviderUtil.get().getEntityProvider(Category.class));
            containerForCategorys = container;
        }
        return containerForCategorys;
    }
    
    public JPAContainer<Expert> RepresentationForm.getContainerForExperts() {
        if (containerForExperts == null) {
            JPAContainer<Expert> container = new JPAContainer<Expert>(Expert.class);
            container.setEntityProvider(EntityProviderUtil.get().getEntityProvider(Expert.class));
            containerForExperts = container;
        }
        return containerForExperts;
    }
    
    public JPAContainer<ODUser> RepresentationForm.getContainerForODUsers() {
        if (containerForODUsers == null) {
            JPAContainer<ODUser> container = new JPAContainer<ODUser>(ODUser.class);
            container.setEntityProvider(EntityProviderUtil.get().getEntityProvider(ODUser.class));
            containerForODUsers = container;
        }
        return containerForODUsers;
    }
    
    public Object RepresentationForm.getCategoryCaptionPropertyId() {
        return "name";
    }
    
    public Object RepresentationForm.getExpertCaptionPropertyId() {
        return null;
    }
    
    public Object RepresentationForm.getODUserCaptionPropertyId() {
        return "name";
    }
    
    public String RepresentationForm.getIdProperty() {
        return "id";
    }
    
    public String RepresentationForm.getVersionProperty() {
        return "version";
    }
    
    public void RepresentationForm.validateFields() {
        if (getItemDataSource() != null) {
            for (Object propertyId : getItemDataSource().getItemPropertyIds()) {
                Field field = getField(propertyId);
                if (field != null && !field.isReadOnly()) {
                    field.validate();
                }
            }
        }
    }
    
    public void RepresentationForm.commitFields() {
        if (getItemDataSource() != null) {
            for (Object propertyId : getItemDataSource().getItemPropertyIds()) {
                Field field = getField(propertyId);
                if (field != null && !field.isReadOnly()) {
                    field.commit();
                }
            }
        }
    }
    
    public void RepresentationForm.setFieldPropertyDataSource(Object propertyId, Property property) {
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
    
    public void RepresentationForm.setFieldValues(Item item) {
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
    
    public Field RepresentationForm.getFirstField() {
        Iterator<Object> it = getBeanPropertyIds().iterator();
        if (it.hasNext()) {
            return getField(it.next());
        }
        return null;
    }
    
    public Class<Representation> RepresentationForm.getEntityClass() {
        return Representation.class;
    }
    
}