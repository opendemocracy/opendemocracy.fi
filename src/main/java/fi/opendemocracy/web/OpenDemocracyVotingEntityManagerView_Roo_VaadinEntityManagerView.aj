// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.web;

import fi.opendemocracy.web.ui.CategoryView;
import fi.opendemocracy.web.ui.ExpertView;
import fi.opendemocracy.web.ui.ODUserView;
import fi.opendemocracy.web.ui.PropositionOptionView;
import fi.opendemocracy.web.ui.PropositionView;
import fi.opendemocracy.web.ui.RepresentationView;
import fi.opendemocracy.web.ui.VoteView;
import java.lang.Class;
import java.lang.String;
import java.util.Map;
import java.util.TreeMap;

privileged aspect OpenDemocracyVotingEntityManagerView_Roo_VaadinEntityManagerView {
    
    public Map<String, Class> OpenDemocracyVotingEntityManagerView.listEntityViews() {
        Map<String, Class> result = new TreeMap<String, Class>();
        result.put("Vote",VoteView.class);
        result.put("Representation",RepresentationView.class);
        result.put("Proposition",PropositionView.class);
        result.put("Proposition Option",PropositionOptionView.class);
        result.put("O D User",ODUserView.class);
        result.put("Expert",ExpertView.class);
        result.put("Category",CategoryView.class);
        return result;
    }
    
}
