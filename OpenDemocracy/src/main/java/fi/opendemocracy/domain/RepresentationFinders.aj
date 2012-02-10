package fi.opendemocracy.domain;

import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Vote;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect RepresentationFinders {

    public static TypedQuery<Representation> Representation.findRepresentationsByExpertAndTrustGreaterThanLatestBefore(Expert expert, BigDecimal trust, Date timelimit) {
        if (expert == null) throw new IllegalArgumentException("The expert argument is required");
        if (trust == null) throw new IllegalArgumentException("The trust argument is required");
        if (timelimit == null) throw new IllegalArgumentException("The timelimit argument is required");
        EntityManager em = Representation.entityManager();
        TypedQuery<Representation> q = em.createQuery("SELECT o FROM Representation AS o WHERE o.expert = :expert AND o.trust > :trust AND o.ts = " +
			        			"(SELECT MAX(mostRecent.ts) FROM Representation AS mostRecent WHERE " +
			        			"mostRecent.expert = :expert AND mostRecent.trust  > :trust AND mostRecent.odUser = o.odUser AND mostRecent.ts <= :timelimit)", Representation.class);
        q.setParameter("expert", expert);
        q.setParameter("trust", trust);
        q.setParameter("timelimit", timelimit);
        return q;
    }
    
    public static TypedQuery<Representation> Representation.findRepresentationsByOdUserAndTrustGreaterThanLatestBefore(ODUser odUser, BigDecimal trust, Date timelimit) {
        if (odUser == null) throw new IllegalArgumentException("The odUser argument is required");
        if (trust == null) throw new IllegalArgumentException("The trust argument is required");
        if (timelimit == null) throw new IllegalArgumentException("The timelimit argument is required");
        EntityManager em = Representation.entityManager();
        TypedQuery<Representation> q = em.createQuery("SELECT o FROM Representation AS o WHERE o.odUser = :odUser AND o.trust > :trust AND o.ts = " +
			        			"(SELECT MAX(mostRecent.ts) FROM Representation AS mostRecent WHERE " +
			        			"mostRecent.odUser = :odUser AND mostRecent.trust > :trust AND mostRecent.ts <= :timelimit)", Representation.class);
        q.setParameter("odUser", odUser);
        q.setParameter("trust", trust);
        q.setParameter("timelimit", timelimit);
        return q;
    }
}

