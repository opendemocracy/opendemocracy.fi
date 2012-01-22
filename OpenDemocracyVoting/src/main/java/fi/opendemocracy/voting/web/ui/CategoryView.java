package fi.opendemocracy.voting.web.ui;

import fi.opendemocracy.voting.web.AbstractEntityView;
import fi.opendemocracy.voting.web.EntityEditor;
import fi.opendemocracy.voting.web.ThemeConstants;

import com.vaadin.event.Action;
import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.voting.domain.Category.class)
public class CategoryView extends AbstractEntityView<fi.opendemocracy.voting.domain.Category> {
	
    // right-click actions
    static final Action ACTION_NEW_CATEGORY = new Action("Create new");
    static final Action ACTION_OPEN_CATEGORY = new Action("Open");
    static final Action ACTION_SUBSCRIBE = new Action("Subscribe");
    static final Action ACTION_VIEW_PROPOSITIONS = new Action("View Propositions");
    static final Action ACTION_VIEW_EXPERTS = new Action("View Experts");
    static final Action ACTION_CLAIM_EXPERTISE = new Action("Claim Expertise");
    static final Action[] ACTIONS_MENU = new Action[] { ACTION_NEW_CATEGORY, ACTION_OPEN_CATEGORY, ACTION_SUBSCRIBE, ACTION_VIEW_PROPOSITIONS,
            ACTION_VIEW_EXPERTS, ACTION_CLAIM_EXPERTISE };
    
	public CategoryView(){
		super();
		setCaption(ThemeConstants.TAB_CAPTION_CATEGORIES);
		setIcon(ThemeConstants.TAB_ICON_CATEGORIES);
		constructTable();
	}
	
	// add view-specific properties to table
	private void constructTable(){
        getTable().setNullSelectionAllowed(false);
        
		// create custom columns
        ColumnCountGenerator columnPropositionCount = new ColumnCountGenerator("propositions");
        ColumnCountGenerator columnExpertCount = new ColumnCountGenerator("experts");
        ColumnCountGenerator columnSubscriberCount = new ColumnCountGenerator("subscribers");
        
        getTable().addGeneratedColumn("Propositions", columnPropositionCount);
        getTable().addGeneratedColumn("Subscribers", columnSubscriberCount);
        getTable().addGeneratedColumn("Experts", columnExpertCount);
        
        // add right-click menu
        getTable().addActionHandler(new Action.Handler() {
	        public Action[] getActions(Object target, Object sender) {
	            return ACTIONS_MENU;
	        }

	        public void handleAction(Action action, Object sender, Object target) {
	            if (ACTION_OPEN_CATEGORY == action) {
	            	getWindow().showNotification("TODO: Open");
	            } else if (ACTION_NEW_CATEGORY == action){
	            	navigateTo("new");
	            	getWindow().showNotification("TODO: Create");
	            }else if (ACTION_SUBSCRIBE == action){
	            	//TODO
	            	getWindow().showNotification("TODO: Subscribe");
	            } else if (ACTION_VIEW_PROPOSITIONS == action) {
	            	//TODO
	            	getWindow().showNotification("TODO: Propositions");
	            } else if (ACTION_VIEW_EXPERTS == action) {
	            	//TODO
	            	getWindow().showNotification("TODO: Experts");
	            } else if (ACTION_CLAIM_EXPERTISE == action) {
	            	//TODO: Set expert on claim
	            	getWindow().showNotification("TODO: Claim");
	            }

	        }
        });
	}
	
    @Override
    protected EntityEditor createForm() {
        return new CategoryForm();
    }

    @Override
    protected void configureTable(Table table) {
        // set default visible columns 
		table.setVisibleColumns(getTableColumns());
        table.setContainerDataSource(getTableContainer());
        setupGeneratedColumns(table);
    }
    
    // column generator for data linked to category
    private class ColumnCountGenerator implements Table.ColumnGenerator {
        private String sumType;

        public ColumnCountGenerator(String type) {
            sumType = type;
        }

        public Component generateCell(Table source, Object itemId, Object columnId) {
        	//TODO: Count associated items and return sum
        	if(sumType.equals("propositions")){
                return new Label("0");
        	}else if(sumType.equals("experts")){
                return new Label("0");
        	}else if(sumType.equals("subscribers")){
                return new Label("0");
        	}
        	return null;
        }
    }
}
