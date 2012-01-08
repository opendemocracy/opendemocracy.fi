// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.domain;

import fi.opendemocracy.domain.PropositionOption;
import java.lang.String;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect PropositionOptionDataOnDemand_Roo_DataOnDemand {
    
    declare @type: PropositionOptionDataOnDemand: @Component;
    
    private Random PropositionOptionDataOnDemand.rnd = new SecureRandom();
    
    private List<PropositionOption> PropositionOptionDataOnDemand.data;
    
    public PropositionOption PropositionOptionDataOnDemand.getNewTransientPropositionOption(int index) {
        PropositionOption obj = new PropositionOption();
        setDescription(obj, index);
        setTitle(obj, index);
        return obj;
    }
    
    public void PropositionOptionDataOnDemand.setDescription(PropositionOption obj, int index) {
        String description = "description_" + index;
        if (description.length() > 4096) {
            description = description.substring(0, 4096);
        }
        obj.setDescription(description);
    }
    
    public void PropositionOptionDataOnDemand.setTitle(PropositionOption obj, int index) {
        String title = "title_" + index;
        if (title.length() > 255) {
            title = title.substring(0, 255);
        }
        obj.setTitle(title);
    }
    
    public PropositionOption PropositionOptionDataOnDemand.getSpecificPropositionOption(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        PropositionOption obj = data.get(index);
        return PropositionOption.findPropositionOption(obj.getId());
    }
    
    public PropositionOption PropositionOptionDataOnDemand.getRandomPropositionOption() {
        init();
        PropositionOption obj = data.get(rnd.nextInt(data.size()));
        return PropositionOption.findPropositionOption(obj.getId());
    }
    
    public boolean PropositionOptionDataOnDemand.modifyPropositionOption(PropositionOption obj) {
        return false;
    }
    
    public void PropositionOptionDataOnDemand.init() {
        data = PropositionOption.findPropositionOptionEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'PropositionOption' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<fi.opendemocracy.domain.PropositionOption>();
        for (int i = 0; i < 10; i++) {
            PropositionOption obj = getNewTransientPropositionOption(i);
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
