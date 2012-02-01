// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.domain;

import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Vote;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect Vote_Roo_Finder {
    
    public static TypedQuery<Vote> Vote.findVotesByOdUser(ODUser odUser) {
        if (odUser == null) throw new IllegalArgumentException("The odUser argument is required");
        EntityManager em = Vote.entityManager();
        TypedQuery<Vote> q = em.createQuery("SELECT o FROM Vote AS o WHERE o.odUser = :odUser", Vote.class);
        q.setParameter("odUser", odUser);
        return q;
    }
    
}