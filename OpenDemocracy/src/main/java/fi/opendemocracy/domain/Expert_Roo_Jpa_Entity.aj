// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.domain;

import fi.opendemocracy.domain.Expert;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Expert_Roo_Jpa_Entity {
    
    declare @type: Expert: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Expert.id;
    
    @Version
    @Column(name = "version")
    private Integer Expert.version;
    
    public Long Expert.getId() {
        return this.id;
    }
    
    public void Expert.setId(Long id) {
        this.id = id;
    }
    
    public Integer Expert.getVersion() {
        return this.version;
    }
    
    public void Expert.setVersion(Integer version) {
        this.version = version;
    }
    
}
