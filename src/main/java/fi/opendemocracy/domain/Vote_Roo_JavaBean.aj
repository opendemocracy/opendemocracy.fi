// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.domain;

import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.PropositionOption;
import java.math.BigDecimal;

privileged aspect Vote_Roo_JavaBean {
    
    public ODUser Vote.getOdUser() {
        return this.odUser;
    }
    
    public void Vote.setOdUser(ODUser odUser) {
        this.odUser = odUser;
    }
    
    public PropositionOption Vote.getPropositionOption() {
        return this.propositionOption;
    }
    
    public void Vote.setPropositionOption(PropositionOption propositionOption) {
        this.propositionOption = propositionOption;
    }
    
    public BigDecimal Vote.getSupport() {
        return this.support;
    }
    
    public void Vote.setSupport(BigDecimal support) {
        this.support = support;
    }
    
}
