// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.voting.domain;

import fi.opendemocracy.voting.domain.Proposition;
import java.lang.String;

privileged aspect PropositionOption_Roo_JavaBean {
    
    public String PropositionOption.getName() {
        return this.name;
    }
    
    public void PropositionOption.setName(String name) {
        this.name = name;
    }
    
    public String PropositionOption.getDescription() {
        return this.description;
    }
    
    public void PropositionOption.setDescription(String description) {
        this.description = description;
    }
    
    public Proposition PropositionOption.getProposition() {
        return this.proposition;
    }
    
    public void PropositionOption.setProposition(Proposition proposition) {
        this.proposition = proposition;
    }
    
}
