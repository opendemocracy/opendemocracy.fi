// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.domain;

import fi.opendemocracy.domain.Vote;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Vote_Roo_Jpa_Entity {
    
    declare @type: Vote: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Vote.id;
    
    @Version
    @Column(name = "version")
    private Integer Vote.version;
    
    public Long Vote.getId() {
        return this.id;
    }
    
    public void Vote.setId(Long id) {
        this.id = id;
    }
    
    public Integer Vote.getVersion() {
        return this.version;
    }
    
    public void Vote.setVersion(Integer version) {
        this.version = version;
    }
    
}
