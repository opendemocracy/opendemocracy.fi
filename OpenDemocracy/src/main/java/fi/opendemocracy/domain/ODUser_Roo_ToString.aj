// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.domain;

import java.lang.String;

privileged aspect ODUser_Roo_ToString {
    
    public String ODUser.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Description: ").append(getDescription()).append(", ");
        sb.append("EmailAddress: ").append(getEmailAddress()).append(", ");
        sb.append("FirstName: ").append(getFirstName()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("LastName: ").append(getLastName()).append(", ");
        sb.append("OpenIdIdentifier: ").append(getOpenIdIdentifier()).append(", ");
        sb.append("Ts: ").append(getTs()).append(", ");
        sb.append("UserRole: ").append(getUserRole()).append(", ");
        sb.append("Username: ").append(getUsername()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}