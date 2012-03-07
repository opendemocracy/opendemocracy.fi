// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect ODUser_Roo_Finder {
    
    public static TypedQuery<ODUser> ODUser.findODUsersByOpenIdIdentifier(String openIdIdentifier) {
        if (openIdIdentifier == null || openIdIdentifier.length() == 0) throw new IllegalArgumentException("The openIdIdentifier argument is required");
        EntityManager em = ODUser.entityManager();
        TypedQuery<ODUser> q = em.createQuery("SELECT o FROM ODUser AS o WHERE o.openIdIdentifier = :openIdIdentifier", ODUser.class);
        q.setParameter("openIdIdentifier", openIdIdentifier);
        return q;
    }
    
}
