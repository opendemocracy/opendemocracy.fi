// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.voting.domain;

import fi.opendemocracy.voting.domain.PropositionOption;
import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.springframework.transaction.annotation.Transactional;

privileged aspect PropositionOption_Roo_Entity {
    
    declare @type: PropositionOption: @Entity;
    
    @PersistenceContext
    transient EntityManager PropositionOption.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long PropositionOption.id;
    
    @Version
    @Column(name = "version")
    private Integer PropositionOption.version;
    
    public Long PropositionOption.getId() {
        return this.id;
    }
    
    public void PropositionOption.setId(Long id) {
        this.id = id;
    }
    
    public Integer PropositionOption.getVersion() {
        return this.version;
    }
    
    public void PropositionOption.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void PropositionOption.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void PropositionOption.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            PropositionOption attached = PropositionOption.findPropositionOption(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void PropositionOption.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void PropositionOption.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public PropositionOption PropositionOption.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PropositionOption merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager PropositionOption.entityManager() {
        EntityManager em = new PropositionOption().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long PropositionOption.countPropositionOptions() {
        return entityManager().createQuery("SELECT COUNT(o) FROM PropositionOption o", Long.class).getSingleResult();
    }
    
    public static List<PropositionOption> PropositionOption.findAllPropositionOptions() {
        return entityManager().createQuery("SELECT o FROM PropositionOption o", PropositionOption.class).getResultList();
    }
    
    public static PropositionOption PropositionOption.findPropositionOption(Long id) {
        if (id == null) return null;
        return entityManager().find(PropositionOption.class, id);
    }
    
    public static List<PropositionOption> PropositionOption.findPropositionOptionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PropositionOption o", PropositionOption.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
