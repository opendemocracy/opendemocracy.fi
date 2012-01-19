package fi.opendemocracy.voting.web;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

public class MainContentView extends VerticalLayout implements
TabSheet.SelectedTabChangeListener{

	
	public void openTab(Tab t){
		if(false){
			//TODO: If exists, setselected
			//categoryMenu.setSelectedTab(newTab.getComponent());
		}else{
			/*
			PropositionTab tabContents = new PropositionTab(p, app);
			Tab newTab = propositionMenu.addTab(tabContents, p.getTitle());
		    newTab.setClosable(true);
		    newTab.setIcon(propositionIcon);
		    propositionMenu.setSelectedTab(newTab.getComponent());
		    */
		}
	}
	
	@Override
	public void selectedTabChange(SelectedTabChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
