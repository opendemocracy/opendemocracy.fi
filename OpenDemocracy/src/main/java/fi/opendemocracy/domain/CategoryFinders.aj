package fi.opendemocracy.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;

privileged aspect CategoryFinders {
	//TODO: Check exception handling
    public static BigDecimal Category.getRepresentation(Expert e){
        if (e == null) throw new IllegalArgumentException("The Expert argument is required");
        EntityManager em = Category.entityManager();
        final String query = "SELECT (SUM(activeRep.trust) / NULLIF(COUNT(activeRep.trust),0)) FROM Representation activeRep WHERE " +
			          		 "activeRep.expert = :e AND activeRep.trust > 0  AND " +
			          		 "activeRep.ts = " +
			          			"(SELECT MAX(mostRecent.ts) FROM Representation mostRecent WHERE " +
			          			"mostRecent.expert = :e AND mostRecent.odUser = activeRep.odUser)";
        TypedQuery<BigDecimal> q = em.createQuery(query, BigDecimal.class);
        q.setParameter("e", e);
        BigDecimal bd;
        try {
        	bd = q.getSingleResult();
        }catch (NullPointerException ex){
        	bd = new BigDecimal(0L);
        }
    	return bd;
    }
    
	//TODO: Check exception handling
    public static BigDecimal Category.getDistrust(Expert e){
        if (e == null) throw new IllegalArgumentException("The Expert argument is required");
        EntityManager em = Category.entityManager();
        final String query = "SELECT (SUM(activeRep.trust) / NULLIF(COUNT(activeRep.trust),0)) FROM Representation activeRep WHERE " +
				       		 "activeRep.expert = :e AND activeRep.trust < 0  AND " +
				       		 "activeRep.ts = " +
				       			"(SELECT MAX(mostRecent.ts) FROM Representation mostRecent WHERE " +
				       			"mostRecent.expert = :e AND mostRecent.odUser = activeRep.odUser)";
        TypedQuery<BigDecimal> q = em.createQuery(query, BigDecimal.class);
        q.setParameter("e", e);
        BigDecimal bd;
        try {
        	bd = q.getSingleResult();
        }catch (NullPointerException ex){
        	bd = new BigDecimal(0L);
        }
    	return bd;
    }
    
    public static long Category.getSupporters(Expert e){
        if (e == null) throw new IllegalArgumentException("The Expert argument is required");
        EntityManager em = Category.entityManager();
        final String query = "SELECT COUNT(activeRep.trust) FROM Representation activeRep WHERE " +
				       		 "activeRep.expert = :e AND activeRep.trust > 0  AND " +
				       		 "activeRep.ts = " +
				       			"(SELECT MAX(mostRecent.ts) FROM Representation mostRecent WHERE " +
				       			"mostRecent.expert = :e AND mostRecent.odUser = activeRep.odUser)";
        TypedQuery<Long> q = em.createQuery(query, Long.class);
        q.setParameter("e", e);
        long l;
        try {
        	l = q.getSingleResult();
        }catch (NullPointerException ex){
        	l = 0L;
        }
    	return l;
    }
    //TODO: Check exception handling, use timestamp
    public static long Category.getOpposers(Expert e){
        if (e == null) throw new IllegalArgumentException("The Expert argument is required");
        EntityManager em = Category.entityManager();
        final String query = "SELECT COUNT(activeRep.trust) FROM Representation activeRep WHERE " +
			        		 "activeRep.expert = :e AND activeRep.trust < 0  AND " +
			        		 "activeRep.ts = " +
			        			"(SELECT MAX(mostRecent.ts) FROM Representation mostRecent WHERE " +
			        			"mostRecent.expert = :e AND mostRecent.odUser = activeRep.odUser)";
        TypedQuery<Long> q = em.createQuery(query, Long.class);
        q.setParameter("e", e);
        long l;
        try {
        	l = q.getSingleResult();
        }catch (NullPointerException ex){
        	l = 0L;
        }
    	return l;
    }
    //TODO: Check exception handling
    public static long Category.getExpertCount(Category c){
        if (c == null) throw new IllegalArgumentException("The Category argument is required");
        EntityManager em = Category.entityManager();        
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(e) FROM Expert e WHERE e.category = :c", Long.class);
        q.setParameter("c", c);
        long i;
        try {
        	i = q.getSingleResult();
        }catch (NullPointerException ex){
        	i = 0;
        }      
        return i;
    }

    //TODO
    public static long Category.getPropositionCount(Category c){
        if (c == null) throw new IllegalArgumentException("The Category argument is required");
        EntityManager em = Category.entityManager();
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(p) FROM Proposition p WHERE :c MEMBER OF p.categories", Long.class);
        q.setParameter("c", c);
        long i;
        try {
        	i = q.getSingleResult();
        }catch (NullPointerException ex){
        	i = 0;
        }      
        return i;
    }

}

