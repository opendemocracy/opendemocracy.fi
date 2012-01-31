package fi.opendemocracy.web.ui;

import java.util.Date;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.event.Action;
import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import fi.opendemocracy.domain.Category;
import fi.opendemocracy.domain.Expert;
import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.PropositionOption;
import fi.opendemocracy.web.AbstractEntityView;
import fi.opendemocracy.web.EntityEditor;
import fi.opendemocracy.web.ThemeConstants;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.domain.Category.class)
public class CategoryView extends
		AbstractEntityView<fi.opendemocracy.domain.Category> {

    // right-click actions
    static final Action ACTION_NEW_CATEGORY = new Action("Create new");
    static final Action ACTION_OPEN_CATEGORY = new Action("Open");
    static final Action ACTION_SUBSCRIBE = new Action("Subscribe");
    static final Action ACTION_VIEW_PROPOSITIONS = new Action("View Propositions");
    static final Action ACTION_VIEW_EXPERTS = new Action("View Experts");
    static final Action ACTION_CLAIM_EXPERTISE = new Action("Claim Expertise");
    static final Action[] ACTIONS_MENU = new Action[] { ACTION_NEW_CATEGORY, ACTION_OPEN_CATEGORY, ACTION_SUBSCRIBE, ACTION_VIEW_PROPOSITIONS,
            ACTION_VIEW_EXPERTS, ACTION_CLAIM_EXPERTISE };
	
	//Claim expertise modal
	private VerticalLayout expertForm;
	private Window expertModal; 
    
	public CategoryView(){
		setCaption(ThemeConstants.TAB_CAPTION_CATEGORIES);
		setIcon(ThemeConstants.TAB_ICON_CATEGORIES);
		constructTable();
	}
	
	//TODO: This does not work yet for some reason
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
	            	claimExpertise();
	            }

	        }
        });
	}
	
	private void claimExpertise(){
		final ODUser currentUser = (ODUser)getApplication().getUser();
		final Window main = getWindow();
		final RichTextArea description = new RichTextArea("Please describe your expertise in a few words:");
		
		if(currentUser == null){
			main.showNotification("Not logged in.");
			return;
		}		
		
		if(expertForm == null){
			expertForm = new VerticalLayout();
				
			expertForm.setMargin(true);
			expertForm.setSpacing(true);
			expertForm.setWidth("400px");
						
			expertModal = new Window(null, expertForm);
			expertModal.setModal(true);
			
			Button cancel = new Button("Cancel", new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					main.removeWindow(expertModal);
				}
			});
			Button add = new Button("Claim", new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					Category category = (Category) getEntityForItem(getTable().getItem(getTable().getValue()));
					Expert newExpert = new Expert();
					newExpert.setCategory(category);
					newExpert.setOdUser(currentUser);
					newExpert.setExpertise(description.getValue().toString());
					newExpert.setTs(new Date());
					newExpert.persist();
					description.setValue("");
					main.removeWindow(expertModal);
					main.showNotification("You are now an expert in \"" + category.getName() + "\"");
				}
			});
			HorizontalLayout buttons = new HorizontalLayout();
			buttons.addComponent(cancel);
			buttons.addComponent(add);
			buttons.setSpacing(true);
			
			expertForm.addComponent(description);
			expertForm.addComponent(buttons);
			expertForm.setComponentAlignment(buttons, Alignment.TOP_RIGHT);
		}
		
		Category category = (Category) getEntityForItem(getTable().getItem(getTable().getValue()));
		expertModal.setCaption("Claim expertise in \"" + category.getName() + "\"");
		main.addWindow(expertModal);
	}
	
	@Override
	protected EntityEditor createForm() {
		return new CategoryForm();
	}

	@Override
	protected void configureTable(Table table) {
		table.setContainerDataSource(getTableContainer());
		table.setVisibleColumns(getTableColumns());
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
