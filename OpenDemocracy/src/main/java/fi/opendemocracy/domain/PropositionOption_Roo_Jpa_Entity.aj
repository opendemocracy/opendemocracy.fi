// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.domain;

import fi.opendemocracy.domain.PropositionOption;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect PropositionOption_Roo_Jpa_Entity {
    
    declare @type: PropositionOption: @Entity;
    
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
    
}