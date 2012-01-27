package fi.opendemocracy.web;

import com.vaadin.ui.Window;

public class OpenDemocracyVotingWindow extends Window {

    public OpenDemocracyVotingWindow() {

        // entity manager
        OpenDemocracyVotingEntityManagerView entityManagerView = new OpenDemocracyVotingEntityManagerView();
        setContent(entityManagerView);

        // select window theme
        setTheme("OpenDemocracyTheme");
    }
}
