package fi.opendemocracy.domain;

import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Vote;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect VoteFinders {
    
    public static TypedQuery<Vote> Vote.findVotesByOdUserAndPropositionAll(ODUser odUser, Proposition proposition) {
        if (odUser == null) throw new IllegalArgumentException("The odUser argument is required");
        if (proposition == null) throw new IllegalArgumentException("The proposition argument is required");
        EntityManager em = Vote.entityManager();
        TypedQuery<Vote> q = em.createQuery("SELECT o FROM Vote AS o WHERE o.odUser = :odUser AND o.proposition = :proposition ORDER BY o.ts DESC", Vote.class);
        q.setParameter("odUser", odUser);
        q.setParameter("proposition", proposition);
        return q;
    }
    public static TypedQuery<Vote> Vote.findVotesByOdUserAndPropositionLatest(ODUser odUser, Proposition proposition) {
        if (odUser == null) throw new IllegalArgumentException("The odUser argument is required");
        if (proposition == null) throw new IllegalArgumentException("The proposition argument is required");
        EntityManager em = Vote.entityManager();
        final String query = "SELECT o FROM Vote AS o WHERE o.odUser = :odUser AND o.proposition = :proposition AND o.ts = " +
			        			"(SELECT MAX(mostRecent.ts) FROM Vote AS mostRecent WHERE " +
			        			"mostRecent.proposition = :proposition AND mostRecent.odUser = o.odUser)";
        TypedQuery<Vote> q = em.createQuery(query, Vote.class);
        q.setParameter("odUser", odUser);
        q.setParameter("proposition", proposition);
        return q;
    }
    public static TypedQuery<Vote> Vote.findVotesByPropositionAndPropositionOptionLatest(Proposition proposition, PropositionOption propositionOption) {
        if (proposition == null) throw new IllegalArgumentException("The proposition argument is required");
        if (propositionOption == null) throw new IllegalArgumentException("The propositionOption argument is required");
        EntityManager em = Vote.entityManager();
        TypedQuery<Vote> q = em.createQuery("SELECT o FROM Vote AS o WHERE o.proposition = :proposition AND o.propositionOption = :propositionOption AND o.ts = " +
			        			"(SELECT MAX(mostRecent.ts) FROM Vote AS mostRecent WHERE " +
			        			"mostRecent.proposition = :proposition AND mostRecent.propositionOption = :propositionOption AND mostRecent.odUser = o.odUser)", Vote.class);
        q.setParameter("proposition", proposition);
        q.setParameter("propositionOption", propositionOption);
        return q;
    }
    public static TypedQuery<Vote> Vote.findVotesByPropositionAndPropositionOptionLatestBefore(Proposition proposition, PropositionOption propositionOption, Date timelimit) {
        if (proposition == null) throw new IllegalArgumentException("The proposition argument is required");
        if (propositionOption == null) throw new IllegalArgumentException("The propositionOption argument is required");
        if (timelimit == null) throw new IllegalArgumentException("The timelimit argument is required");
        EntityManager em = Vote.entityManager();
        TypedQuery<Vote> q = em.createQuery("SELECT o FROM Vote AS o WHERE o.proposition = :proposition AND o.propositionOption = :propositionOption AND o.ts = " +
			        			"(SELECT MAX(mostRecent.ts) FROM Vote AS mostRecent WHERE " +
			        			"mostRecent.proposition = :proposition AND mostRecent.propositionOption = :propositionOption AND mostRecent.odUser = o.odUser AND mostRecent.ts <= :timelimit)", Vote.class);

        q.setParameter("proposition", proposition);
        q.setParameter("propositionOption", propositionOption);
        q.setParameter("timelimit", timelimit);
        return q;
    }
    public static TypedQuery<Vote> Vote.findVotesByPropositionLatestBefore(Proposition proposition, Date timelimit) {
        if (proposition == null) throw new IllegalArgumentException("The proposition argument is required");
        if (timelimit == null) throw new IllegalArgumentException("The timelimit argument is required");
        EntityManager em = Vote.entityManager();
        TypedQuery<Vote> q = em.createQuery("SELECT o FROM Vote AS o WHERE o.proposition = :proposition AND o.ts = " +
			        			"(SELECT MAX(mostRecent.ts) FROM Vote AS mostRecent WHERE " +
			        			"mostRecent.proposition = :proposition AND mostRecent.odUser = o.odUser AND mostRecent.ts <= :timelimit)", Vote.class);

        q.setParameter("proposition", proposition);
        q.setParameter("timelimit", timelimit);
        return q;
    }

}

