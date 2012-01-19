package fi.opendemocracy.voting.web;

import com.vaadin.ui.Window;

public class OpenDemocracyVotingWindow extends Window {

    public OpenDemocracyVotingWindow() {

        setCaption(ThemeConstants.APPLICATION_NAME);
        // entity manager
        OpenDemocracyVotingEntityManagerView entityManagerView = new OpenDemocracyVotingEntityManagerView();
        setContent(entityManagerView);

        // select window theme
        setTheme("OpenDemocracyTheme");
    }
}
