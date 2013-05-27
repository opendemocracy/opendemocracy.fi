// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.domain;

import fi.opendemocracy.domain.Representation;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Representation_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Representation.entityManager;
    
    public static final EntityManager Representation.entityManager() {
        EntityManager em = new Representation().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Representation.countRepresentations() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Representation o", Long.class).getSingleResult();
    }
    
    public static List<Representation> Representation.findAllRepresentations() {
        return entityManager().createQuery("SELECT o FROM Representation o", Representation.class).getResultList();
    }
    
    public static Representation Representation.findRepresentation(Long id) {
        if (id == null) return null;
        return entityManager().find(Representation.class, id);
    }
    
    public static List<Representation> Representation.findRepresentationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Representation o", Representation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Representation.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Representation.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Representation attached = Representation.findRepresentation(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Representation.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Representation.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Representation Representation.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Representation merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}