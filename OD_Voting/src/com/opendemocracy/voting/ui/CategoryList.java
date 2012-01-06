package com.opendemocracy.voting.ui;

import com.vaadin.data.util.BeanItem;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.opendemocracy.voting.VotingApplication;
import com.opendemocracy.voting.data.Category;

@SuppressWarnings("serial")
public class CategoryList extends Table {

    //Controller
    private final VotingApplication vApp;
    
    //Right-click actions
    static final Action ACTION_OPEN_CATEGORY = new Action("Open");
    static final Action ACTION_SUBSCRIBE = new Action("Subscribe");
    static final Action ACTION_VIEW_PROPOSITIONS = new Action("View Propositions");
    static final Action ACTION_VIEW_EXPERTS = new Action("View Experts");
    static final Action ACTION_CLAIM_EXPERTISE = new Action("Claim Expertise");
    
    static final Action[] ACTIONS_MENU = new Action[] { ACTION_OPEN_CATEGORY, ACTION_SUBSCRIBE, ACTION_VIEW_PROPOSITIONS,
            ACTION_VIEW_EXPERTS, ACTION_CLAIM_EXPERTISE };
	
    public final static Object[] NATURAL_COL_ORDER = new Object[] { "id",
		"name"};

    public final static String[] COL_HEADERS_ENGLISH = new String[] { "Id",
		"Name"};

    public CategoryList(VotingApplication app) {
		this.vApp = app;

		//Table properties
		setSizeFull();
		setSelectable(true);
        setMultiSelect(false);
        setNullSelectionAllowed(false);
		setRowHeaderMode(Table.ROW_HEADER_MODE_ICON_ONLY);
        
        // connect data source
        setContainerDataSource(vApp.getCategoryData());

        setVisibleColumns(NATURAL_COL_ORDER);
        setColumnHeaders(COL_HEADERS_ENGLISH);
		
		//Generated columns
        ColumnCountGenerator columnPropositionCount = new ColumnCountGenerator("propositions");
        ColumnCountGenerator columnExpertCount = new ColumnCountGenerator("experts");
        ColumnCountGenerator columnSubscriberCount = new ColumnCountGenerator("subscribers");
        
        addGeneratedColumn("Propositions", columnPropositionCount);
		addGeneratedColumn("Subscribers", columnSubscriberCount);
		addGeneratedColumn("Experts", columnExpertCount);
        
        // Menu actions
        addActionHandler(new Action.Handler() {
            public Action[] getActions(Object target, Object sender) {
                return ACTIONS_MENU;
            }

            public void handleAction(Action action, Object sender, Object target) {
                if (ACTION_OPEN_CATEGORY == action) {
                	//Display category description modal
    				Category c = (Category) target;
    				vApp.getViewManager().getCategoryView().openCategoryTab(c);
                } else if (ACTION_SUBSCRIBE == action) {
                	//TODO
                	getWindow().showNotification("Subscribe");
                } else if (ACTION_VIEW_PROPOSITIONS == action) {
                	//TODO
                	getWindow().showNotification("Propositions");
                } else if (ACTION_VIEW_EXPERTS == action) {
                	//TODO
                	getWindow().showNotification("Experts");
                } else if (ACTION_CLAIM_EXPERTISE == action) {
                	//TODO: Set expert icon on claim
                	setItemIcon(getItem(target), new ThemeResource("icons/16/experts.png"));
                	getWindow().showNotification("Claim");
                }

            }

        });

        addListener(new ItemClickEvent.ItemClickListener() {
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
    				Category c = (Category) ((BeanItem) getItem(event.getItemId())).getBean();
    				vApp.getViewManager().getCategoryView().openCategoryTab(c);
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
        	//Property prop = source.getItem(itemId).getItemProperty(columnId);
        	
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