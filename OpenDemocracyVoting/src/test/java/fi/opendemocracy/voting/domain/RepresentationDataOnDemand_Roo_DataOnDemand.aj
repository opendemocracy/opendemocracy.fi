// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.voting.domain;

import fi.opendemocracy.voting.domain.Category;
import fi.opendemocracy.voting.domain.CategoryDataOnDemand;
import fi.opendemocracy.voting.domain.Expert;
import fi.opendemocracy.voting.domain.ExpertDataOnDemand;
import fi.opendemocracy.voting.domain.ODUser;
import fi.opendemocracy.voting.domain.ODUserDataOnDemand;
import fi.opendemocracy.voting.domain.Representation;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect RepresentationDataOnDemand_Roo_DataOnDemand {
    
    declare @type: RepresentationDataOnDemand: @Component;
    
    private Random RepresentationDataOnDemand.rnd = new SecureRandom();
    
    private List<Representation> RepresentationDataOnDemand.data;
    
    @Autowired
    private CategoryDataOnDemand RepresentationDataOnDemand.categoryDataOnDemand;
    
    @Autowired
    private ExpertDataOnDemand RepresentationDataOnDemand.expertDataOnDemand;
    
    @Autowired
    private ODUserDataOnDemand RepresentationDataOnDemand.oDUserDataOnDemand;
    
    public Representation RepresentationDataOnDemand.getNewTransientRepresentation(int index) {
        Representation obj = new Representation();
        setCategory(obj, index);
        setExpert(obj, index);
        setOdUser(obj, index);
        setTrust(obj, index);
        return obj;
    }
    
    public void RepresentationDataOnDemand.setCategory(Representation obj, int index) {
        Category category = categoryDataOnDemand.getRandomCategory();
        obj.setCategory(category);
    }
    
    public void RepresentationDataOnDemand.setExpert(Representation obj, int index) {
        Expert expert = expertDataOnDemand.getRandomExpert();
        obj.setExpert(expert);
    }
    
    public void RepresentationDataOnDemand.setOdUser(Representation obj, int index) {
        ODUser odUser = oDUserDataOnDemand.getRandomODUser();
        obj.setOdUser(odUser);
    }
    
    public void RepresentationDataOnDemand.setTrust(Representation obj, int index) {
        BigDecimal trust = BigDecimal.valueOf(index);
        obj.setTrust(trust);
    }
    
    public Representation RepresentationDataOnDemand.getSpecificRepresentation(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Representation obj = data.get(index);
        return Representation.findRepresentation(obj.getId());
    }
    
    public Representation RepresentationDataOnDemand.getRandomRepresentation() {
        init();
        Representation obj = data.get(rnd.nextInt(data.size()));
        return Representation.findRepresentation(obj.getId());
    }
    
    public boolean RepresentationDataOnDemand.modifyRepresentation(Representation obj) {
        return false;
    }
    
    public void RepresentationDataOnDemand.init() {
        data = Representation.findRepresentationEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Representation' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<fi.opendemocracy.voting.domain.Representation>();
        for (int i = 0; i < 10; i++) {
            Representation obj = getNewTransientRepresentation(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator(); it.hasNext();) {
                    ConstraintViolation<?> cv = it.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
