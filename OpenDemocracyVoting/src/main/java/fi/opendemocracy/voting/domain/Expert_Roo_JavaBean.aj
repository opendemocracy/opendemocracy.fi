// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.voting.domain;

import fi.opendemocracy.voting.domain.Category;
import fi.opendemocracy.voting.domain.ODUser;

privileged aspect Expert_Roo_JavaBean {
    
    public Category Expert.getCategory() {
        return this.category;
    }
    
    public void Expert.setCategory(Category category) {
        this.category = category;
    }
    
    public ODUser Expert.getOdUser() {
        return this.odUser;
    }
    
    public void Expert.setOdUser(ODUser odUser) {
        this.odUser = odUser;
    }
    
}
