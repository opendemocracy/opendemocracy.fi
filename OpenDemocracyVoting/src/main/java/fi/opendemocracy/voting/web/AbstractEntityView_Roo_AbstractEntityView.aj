// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.voting.web;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Validator.InvalidValueException;
import org.springframework.transaction.annotation.Transactional;

privileged aspect AbstractEntityView_Roo_AbstractEntityView {
    
    @Transactional
    public boolean AbstractEntityView.doCommit() {
        try {
            getForm().commit();
            E entity = getEntityForItem(getForm().getItemDataSource());
            if (!getTableContainer().containsId(getIdForEntity(entity))) {
                ((JPAContainer<E>) getTableContainer()).addEntity(entity);
            }
            return true;
        } catch (InvalidValueException e) {
            // show validation error also on the save button
            getForm().setCommitErrorMessage(e.getMessage());
            return false;
        }
    }
    
    @Transactional
    public void AbstractEntityView.doDelete() {
        Object id = getIdForEntity(getEntityForItem(getForm().getItemDataSource()));
        if (id != null) {
            getTable().removeItem(id);
        }
    }
    
}