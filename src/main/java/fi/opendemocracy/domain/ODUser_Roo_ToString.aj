// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.domain;

import java.lang.String;

privileged aspect ODUser_Roo_ToString {
    
    public String ODUser.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Password: ").append(getPassword()).append(", ");
        sb.append("Username: ").append(getUsername()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Admin: ").append(isAdmin());
        return sb.toString();
    }
    
}
