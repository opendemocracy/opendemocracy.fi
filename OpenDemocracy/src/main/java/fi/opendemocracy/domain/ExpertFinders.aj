package fi.opendemocracy.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;

privileged aspect ExpertFinders {
	//TODO: Check exception handling
    public static Expert Expert.findExpertByOdUserAndCategory(ODUser u, Category c){
    	if (u == null) throw new IllegalArgumentException("The User argument is required");
    	if (c == null) throw new IllegalArgumentException("The Category argument is required");
        EntityManager em = Expert.entityManager();
        final String query = "SELECT o FROM Expert AS o WHERE " +
			          		 "o.odUser = :u AND o.category = :c";
        TypedQuery<Expert> q = em.createQuery(query, Expert.class);
        q.setParameter("u", u);
        q.setParameter("c", c);
        Expert e;
        try {
        	e = q.getSingleResult();
        }catch (org.springframework.dao.EmptyResultDataAccessException ex){
        	return null;
        }
    	return e;
    }
}

