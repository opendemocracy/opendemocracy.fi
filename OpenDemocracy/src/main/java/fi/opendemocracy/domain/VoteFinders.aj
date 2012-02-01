package fi.opendemocracy.domain;

import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Vote;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect VoteFinders {
    
    public static TypedQuery<Vote> Vote.findVotesByOdUserAndProposition(ODUser odUser, Proposition proposition) {
        if (odUser == null) throw new IllegalArgumentException("The odUser argument is required");
        if (proposition == null) throw new IllegalArgumentException("The proposition argument is required");
        EntityManager em = Vote.entityManager();
        TypedQuery<Vote> q = em.createQuery("SELECT o FROM Vote AS o WHERE o.odUser = :odUser and o.proposition = :proposition ORDER BY o.ts DESC", Vote.class);
        q.setParameter("odUser", odUser);
        q.setParameter("proposition", proposition);
        return q;
    }

}

