// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.web.ui;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Item;
import com.vaadin.ui.Table;
import fi.opendemocracy.domain.Proposition;
import fi.opendemocracy.web.EntityProviderUtil;
import fi.opendemocracy.web.EntityTableColumnGenerator;
import java.lang.Class;
import java.lang.Object;
import java.lang.String;
import java.util.List;

privileged aspect PropositionView_Roo_VaadinEntityView {
    
    private JPAContainer<Proposition> PropositionView.tableContainer;
    
    public String PropositionView.getEntityName() {
        return "Proposition";
    }
    
    public Class<Proposition> PropositionView.getEntityClass() {
        return Proposition.class;
    }
    
    public boolean PropositionView.isCreateAllowed() {
        return true;
    }
    
    public boolean PropositionView.isUpdateAllowed() {
        return true;
    }
    
    public boolean PropositionView.isDeleteAllowed() {
        return true;
    }
    
    public List<Proposition> PropositionView.listEntities() {
        List<Proposition> list = Proposition.findAllPropositions();
        return list;
    }
    
    public Proposition PropositionView.saveEntity(Proposition entity) {
        if (entity == null) {
            return null;
        }
        return (Proposition) entity.merge();
    }
    
    public void PropositionView.deleteEntity(Proposition entity) {
        if (entity != null) {
            entity.remove();
        }
    }
    
    public boolean PropositionView.isNewEntity(Proposition entity) {
        return (entity != null && getIdForEntity(entity) == null);
    }
    
    public String PropositionView.getIdProperty() {
        return "id";
    }
    
    public String PropositionView.getVersionProperty() {
        return "version";
    }
    
    public Proposition PropositionView.createEntityInstance() {
        return new Proposition();
    }
    
    public JPAContainer<Proposition> PropositionView.getTableContainer() {
        if (tableContainer == null) {
            JPAContainer<Proposition> container = new JPAContainer<Proposition>(Proposition.class);
            container.setEntityProvider(EntityProviderUtil.get().getEntityProvider(Proposition.class));
            tableContainer = container;
        }
        return tableContainer;
    }
    
    public Item PropositionView.getItemForEntity(Proposition entity) {
        Item item = getTable().getItem(entity.getId());
        if (item == null) {
            item = tableContainer.createEntityItem(entity);
        }
        return item;
    }
    
    public Proposition PropositionView.getEntityForItem(Item item) {
        if (item instanceof EntityItem && ((EntityItem) item).getEntity() instanceof Proposition) {
            return (Proposition) ((EntityItem) item).getEntity();
        } else {
            return null;
        }
    }
    
    public Object PropositionView.getIdForEntity(Proposition entity) {
        return entity != null ? entity.getId() : null;
    }
    
    public void PropositionView.setupGeneratedColumns(Table table) {
        table.removeGeneratedColumn("author");
        table.addGeneratedColumn("author", new EntityTableColumnGenerator((String) getODUserCaptionPropertyId()));
        table.removeGeneratedColumn("categories");
        table.addGeneratedColumn("categories", new EntityTableColumnGenerator((String) getCategoryCaptionPropertyId()));
        table.removeGeneratedColumn("propositionOptions");
        table.addGeneratedColumn("propositionOptions", new EntityTableColumnGenerator((String) getPropositionOptionCaptionPropertyId()));
    }
    
    public Object PropositionView.getODUserCaptionPropertyId() {
        return null;
    }
    
    public Object PropositionView.getCategoryCaptionPropertyId() {
        return "name";
    }
    
    public Object PropositionView.getPropositionOptionCaptionPropertyId() {
        return "name";
    }
    
}
