// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.domain;

import fi.opendemocracy.domain.ODUser;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect ODUser_Roo_Jpa_Entity {
    
    declare @type: ODUser: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ODUser.id;
    
    @Version
    @Column(name = "version")
    private Integer ODUser.version;
    
    public Long ODUser.getId() {
        return this.id;
    }
    
    public void ODUser.setId(Long id) {
        this.id = id;
    }
    
    public Integer ODUser.getVersion() {
        return this.version;
    }
    
    public void ODUser.setVersion(Integer version) {
        this.version = version;
    }
    
}