package fi.opendemocracy.voting.web;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class MainTab extends HorizontalLayout {
	
    private VerticalLayout tabContents;
    private Panel helpPanel;
    private Button toggle;
    
    public MainTab(Component c) {
        setSizeFull();
        c.setSizeFull();
        addComponent(c);
        setExpandRatio(c, 1.0f);

        // Help panel
        helpPanel = new Panel("No help available in this section.");
        //Toggle bar
        toggle = new Button();        
        toggle.setStyleName("toggle-help");
        toggle.setHeight("100.0%");
        toggle.setIcon(new ThemeResource("icons/16/help.png"));
        toggle.setImmediate(true);
        addComponent(toggle);
        
        //Help panel properties
        helpPanel.setHeight("100.0%");
        helpPanel.setWidth("200px");
        helpPanel.setStyleName("panel-help");
        helpPanel.setVisible(false);
        addComponent(helpPanel);
        
        // add toggle clicklistener
        addListeners();
    }

    private void addListeners() {
    	toggle.addListener(new ClickListener() {
      	  public void buttonClick(ClickEvent event) {
      	    helpPanel.setVisible(!helpPanel.isVisible());
      	  }
      	});
    }
     
	public void setHelpText(String h) {
        helpPanel.setCaption(h);
	}
}
