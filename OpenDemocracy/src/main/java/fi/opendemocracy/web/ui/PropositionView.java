package fi.opendemocracy.web.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Property;
import com.vaadin.data.util.PropertyFormatter;
import com.vaadin.event.Action;
import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import fi.opendemocracy.domain.Category;
import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Proposition;
import fi.opendemocracy.web.AbstractEntityView;
import fi.opendemocracy.web.EntityEditor;
import fi.opendemocracy.web.EntityProviderUtil;
import fi.opendemocracy.web.ThemeConstants;

@RooVaadinEntityView(formBackingObject = fi.opendemocracy.domain.Proposition.class)
public class PropositionView extends
		AbstractEntityView<fi.opendemocracy.domain.Proposition> {
    // right-click actions
    static final Action ACTION_NEW_PROPOSITION = new Action("Create new");
    static final Action ACTION_OPEN_PROPOSITION = new Action("Open");
    static final Action[] ACTIONS_MENU = new Action[] { ACTION_NEW_PROPOSITION };
	
    public PropositionView(){
		setCaption(ThemeConstants.TAB_CAPTION_PROPOSITION);
		setIcon(ThemeConstants.TAB_ICON_PROPOSITION);
        // add right-click menu
        getTable().addActionHandler(new Action.Handler() {
	        public Action[] getActions(Object target, Object sender) {
	            return ACTIONS_MENU;
	        }

	        public void handleAction(Action action, Object sender, Object target) {
	            if (ACTION_OPEN_PROPOSITION == action) {
	            	getWindow().showNotification("TODO: Open");
	            } else if (ACTION_NEW_PROPOSITION == action){
	            	navigateTo("new");
	            }
	        }
        });
    }

	@Override
	protected EntityEditor createForm() {
		return new PropositionForm();
	}


	@Override
	protected CustomComponent createView() {
		return new PropositionEntityView((Proposition) getEntityForItem(getTable().getItem(getTable().getValue())));
	}
	
	@Override
	protected void configureTable(Table table) {
		table.setContainerDataSource(getTableContainer());
		table.setVisibleColumns(new Object[]{"name","description","categories","propositionOptions","author","ts"});
		table.setColumnHeader("propositionOptions", "Options");
		table.setColumnHeader("ts", "Created");
		setupGeneratedColumns(table);
	}
}
