// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.voting.domain;

import java.lang.String;

privileged aspect Proposition_Roo_ToString {
    
    public String Proposition.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Author: ").append(getAuthor()).append(", ");
        sb.append("Categories: ").append(getCategories() == null ? "null" : getCategories().size()).append(", ");
        sb.append("Description: ").append(getDescription()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Name: ").append(getName()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}