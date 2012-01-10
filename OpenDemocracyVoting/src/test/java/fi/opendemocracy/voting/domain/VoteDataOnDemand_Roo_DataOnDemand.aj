// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.voting.domain;

import fi.opendemocracy.voting.domain.ODUser;
import fi.opendemocracy.voting.domain.ODUserDataOnDemand;
import fi.opendemocracy.voting.domain.PropositionOption;
import fi.opendemocracy.voting.domain.PropositionOptionDataOnDemand;
import fi.opendemocracy.voting.domain.Vote;
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

privileged aspect VoteDataOnDemand_Roo_DataOnDemand {
    
    declare @type: VoteDataOnDemand: @Component;
    
    private Random VoteDataOnDemand.rnd = new SecureRandom();
    
    private List<Vote> VoteDataOnDemand.data;
    
    @Autowired
    private ODUserDataOnDemand VoteDataOnDemand.oDUserDataOnDemand;
    
    @Autowired
    private PropositionOptionDataOnDemand VoteDataOnDemand.propositionOptionDataOnDemand;
    
    public Vote VoteDataOnDemand.getNewTransientVote(int index) {
        Vote obj = new Vote();
        setOdUser(obj, index);
        setPropositionOption(obj, index);
        setSupport(obj, index);
        return obj;
    }
    
    public void VoteDataOnDemand.setOdUser(Vote obj, int index) {
        ODUser odUser = oDUserDataOnDemand.getRandomODUser();
        obj.setOdUser(odUser);
    }
    
    public void VoteDataOnDemand.setPropositionOption(Vote obj, int index) {
        PropositionOption propositionOption = propositionOptionDataOnDemand.getRandomPropositionOption();
        obj.setPropositionOption(propositionOption);
    }
    
    public void VoteDataOnDemand.setSupport(Vote obj, int index) {
        BigDecimal support = BigDecimal.valueOf(index);
        obj.setSupport(support);
    }
    
    public Vote VoteDataOnDemand.getSpecificVote(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Vote obj = data.get(index);
        return Vote.findVote(obj.getId());
    }
    
    public Vote VoteDataOnDemand.getRandomVote() {
        init();
        Vote obj = data.get(rnd.nextInt(data.size()));
        return Vote.findVote(obj.getId());
    }
    
    public boolean VoteDataOnDemand.modifyVote(Vote obj) {
        return false;
    }
    
    public void VoteDataOnDemand.init() {
        data = Vote.findVoteEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Vote' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<fi.opendemocracy.voting.domain.Vote>();
        for (int i = 0; i < 10; i++) {
            Vote obj = getNewTransientVote(i);
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
