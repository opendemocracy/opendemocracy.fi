package com.opendemocracy.voting.ui;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import com.opendemocracy.voting.VotingApplication;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
public class ExpertView extends VerticalLayout implements
		TabSheet.SelectedTabChangeListener {

	private TabSheet expertMenu;
	private VotingApplication app;
	private TrustExpertModalView trust;
	private VerticalLayout list;
	private VerticalLayout add;
	private VerticalLayout view;
	private VerticalLayout help;

	public ExpertView(VotingApplication app) {
		this.app = app;
		this.setSizeFull();
		// List propositions
		list = new VerticalLayout();
		list.setMargin(false);
		list.addComponent(app.getViewManager().getExpertList());
		list.setSizeFull();

		// Add proposition
		add = new VerticalLayout();
		add.setMargin(true);
		//trust = app.getViewManager().getCategoryAddExpertForm();
		//add.addComponent(trust);
		add.setSizeFull();

		// Propositions tab
		view = new VerticalLayout();
		view.setMargin(true);
		view.addComponent(new Label("View expert"));
		view.setSizeFull();
		expertMenu = new TabSheet();

		// Help
		help = new VerticalLayout();
		help.setMargin(true);
		help.addComponent(new Label("Help"));
		help.setSizeFull();

		// Add main tabs
		expertMenu.setSizeFull();
		expertMenu.addTab(list, "List experts", new ThemeResource(
				"icons/16/propositions.png"));
		expertMenu.addTab(add, "Distrust/Trust expert", new ThemeResource(
				"icons/16/document-add.png"));
		expertMenu.addTab(help, "Help",
				new ThemeResource("icons/16/help.png"));

		expertMenu.addListener(this);
		addComponent(expertMenu);

		//categoryMenu.addTab(viewTabs);
	}

	public void selectedTabChange(SelectedTabChangeEvent event) {
		TabSheet tabsheet = event.getTabSheet();
		Tab tab = tabsheet.getTab(tabsheet.getSelectedTab());
		if (tab != null) {
			getWindow().showNotification("Selected tab: " + tab.getCaption());
		}
	}

	/**
	 * @return the list
	 */
	public VerticalLayout showExpertList() {
		expertMenu.setSelectedTab(list);
		return list;
	}
}