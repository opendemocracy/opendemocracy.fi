// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.domain;

import fi.opendemocracy.domain.Category;
import fi.opendemocracy.domain.Proposition;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect Proposition_Roo_Finder {
    
    public static TypedQuery<Proposition> Proposition.findPropositionsByCategories(Set<Category> categories) {
        if (categories == null) throw new IllegalArgumentException("The categories argument is required");
        EntityManager em = Proposition.entityManager();
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Proposition AS o WHERE");
        for (int i = 0; i < categories.size(); i++) {
            if (i > 0) queryBuilder.append(" AND");
            queryBuilder.append(" :categories_item").append(i).append(" MEMBER OF o.categories");
        }
        TypedQuery<Proposition> q = em.createQuery(queryBuilder.toString(), Proposition.class);
        int categoriesIndex = 0;
        for (Category _category: categories) {
            q.setParameter("categories_item" + categoriesIndex++, _category);
        }
        return q;
    }
    
}
