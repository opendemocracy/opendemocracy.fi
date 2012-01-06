package com.opendemocracy.voting.ui;

import com.vaadin.data.Property;
import com.vaadin.event.Action;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;

import com.opendemocracy.voting.VotingApplication;

@SuppressWarnings("serial")
public class CategoryList extends VerticalLayout {

    //Controller
    private final VotingApplication vApp;
    
    Table categoryTable = new Table();

    //Right-click actions
    static final Action ACTION_VIEW_DESCRIPTION = new Action("View Description");
    static final Action ACTION_SUBSCRIBE = new Action("Subscribe");
    static final Action ACTION_VIEW_PROPOSITIONS = new Action("View Propositions");
    static final Action ACTION_VIEW_EXPERTS = new Action("View Experts");
    static final Action ACTION_CLAIM_EXPERTISE = new Action("Claim Expertise");
    
    static final Action[] ACTIONS_MENU = new Action[] { ACTION_VIEW_DESCRIPTION, ACTION_SUBSCRIBE, ACTION_VIEW_PROPOSITIONS,
            ACTION_VIEW_EXPERTS, ACTION_CLAIM_EXPERTISE };
	

    public CategoryList(VotingApplication app) {
		this.vApp = app;

		setSizeFull();
        
		//Table properties
		categoryTable.setSizeFull();
        categoryTable.setSelectable(true);
		
        addComponent(categoryTable);
        
        // connect data source
        categoryTable.setContainerDataSource(vApp.getCategoryData());


		//Generated columns
        ColumnCountGenerator columnPropositionCount = new ColumnCountGenerator("propositions");
        ColumnCountGenerator columnExpertCount = new ColumnCountGenerator("experts");
        ColumnCountGenerator columnSubscriberCount = new ColumnCountGenerator("subscribers");
        
        categoryTable.addGeneratedColumn("Propositions", columnPropositionCount);
		categoryTable.addGeneratedColumn("Subscribers", columnSubscriberCount);
		categoryTable.addGeneratedColumn("Experts", columnExpertCount);
        
        // set column headers
        //categoryTable.setColumnHeaders(new String[] { "Name", "Propositions", "Subscribers", "Experts" });

        // Menu actions
        categoryTable.addActionHandler(new Action.Handler() {
            public Action[] getActions(Object target, Object sender) {
                return ACTIONS_MENU;
            }

            public void handleAction(Action action, Object sender, Object target) {
                if (ACTION_VIEW_DESCRIPTION == action) {
                	getWindow().showNotification("Propositions");
                } else if (ACTION_SUBSCRIBE == action) {
                	getWindow().showNotification("Subscribe");
                } else if (ACTION_VIEW_PROPOSITIONS == action) {
                	getWindow().showNotification("Propositions");
                } else if (ACTION_VIEW_EXPERTS == action) {
                	getWindow().showNotification("Experts");
                } else if (ACTION_CLAIM_EXPERTISE == action) {
                	getWindow().showNotification("Claim");
                }

            }

        });

    }
    
    /** Formats the value in a column containing Double objects. */
    private class ColumnCountGenerator implements Table.ColumnGenerator {
        String sumType; /* Format string for the Double values. */

        /**
         * Creates double value column formatter with the given
         * format string.
         */
        public ColumnCountGenerator(String type) {
            this.sumType = type;
        }

        /**
         * Generates the cell containing the Double value.
         * The column is irrelevant in this use case.
         */
        public Component generateCell(Table source, Object itemId,
                                      Object columnId) {
            
        	// Get the object stored in the cell as a property  	
        	Property prop = source.getItem(itemId).getItemProperty(columnId);
        	
        	//TODO: Count associated items and return sum
        	if(sumType.equals("propositions")){
                return new Label(sumType);
        	}else if(sumType.equals("experts")){
                return new Label(sumType);
        	}else if(sumType.equals("subscribers")){
                return new Label(sumType);
        	}
        	return null;
        }
    }
    
    
}