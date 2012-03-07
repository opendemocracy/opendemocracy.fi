package fi.opendemocracy.web;

import org.vaadin.navigator.Navigator;

import com.vaadin.Application;
import com.vaadin.Application.UserChangeEvent;
import com.vaadin.Application.UserChangeListener;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityManagerView;
import com.vaadin.spring.roo.addon.annotations.RooVaadinEntityView;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.UserRole;
import fi.opendemocracy.web.ui.CategoryView;
import fi.opendemocracy.web.ui.ODUserView;
import fi.opendemocracy.web.ui.PropositionView;

/**
 * Entity manager view that by default automatically discovers all entity views
 * (classes with the {@link RooVaadinEntityView} annotation) and lets the user
 * choose one of them to display.
 * 
 * An entity view must implement the {@link Navigator.View} interface to be
 * automatically added to the view list. Other views can be registered by
 * calling <code>navigator.addView()</code> and added to the sidebar menu by
 * adding a corresponding {@link SidebarItem} to <code>menuItems</code> and
 * <code>viewList</code>.
 * 
 * This class is designed to be compatible with the Vaadin Visual Editor.
 * 
 * All methods and fields not marked with the {@link AutoGenerated} annotation
 * can be modified freely.
 * 
 * If you are planning to use the visual editor, do not touch the methods marked
 * with the {@link AutoGenerated} annotation - they will be removed and
 * re-generated when saving the class from the visual editor. Instead, add any
 * custom code to methods called from the constructor after the initial view
 * construction.
 * 
 * If you will not use the Vaadin Visual Editor to edit this class, all the
 * {@link AutoGenerated} annotations can be removed.
 */
@RooVaadinEntityManagerView
public class OpenDemocracyVotingEntityManagerView extends CustomComponent
		implements TabNavigator.ViewChangeListener {

	// UI objects
	private VerticalLayout mainLayout;
	private HorizontalLayout toolbar;
	private Button btnPropositions;
	private Button btnCategories;
	private Button btnLogout;
	private Button btnUser;
	private Button btnLogin;
	private TabNavigator navigator;

	// Main layout
	public OpenDemocracyVotingEntityManagerView() {
		setSizeFull();
		// build the layout
		buildMainLayout();
		setCompositionRoot(mainLayout);
		// layout and style adjustments
		mainLayout.addStyleName("main");
		mainLayout.addStyleName(Reindeer.TABSHEET_BORDERLESS);
		// add listeners
		addListeners();
	}

	private void buildMainLayout() {
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setWidth("100.0%");
		mainLayout.setHeight("100.0%");
		// add content
		mainLayout.addComponent(buildToolbar());
		mainLayout.addComponent(buildNavigator());
		// expand tabsheet
		mainLayout.setExpandRatio(navigator, 1.0f);
	}

	// Construct toolbar header
	private HorizontalLayout buildToolbar() {
		toolbar = new HorizontalLayout();
		// layout constants
		Embedded logo = new Embedded("", ThemeConstants.LOGO);
		// buttons
		btnPropositions = new Button("Propositions");
		btnCategories = new Button("Categories");
		btnLogout = new Button("Logout");
		btnLogin = new Button("Login");
		btnUser = new Button("Users");
		btnLogout.setVisible(false);
		btnUser.setVisible(false);
		btnPropositions.setIcon(ThemeConstants.TOOLBAR_ICON_PROPOSITION);
		btnCategories.setIcon(ThemeConstants.TOOLBAR_ICON_CATEGORIES);
		btnLogout.setIcon(ThemeConstants.TOOLBAR_ICON_LOGIN);
		btnLogin.setIcon(ThemeConstants.TOOLBAR_ICON_LOGIN);
		btnUser.setIcon(ThemeConstants.TOOLBAR_ICON_USER);
		// add components
		toolbar.addComponent(btnPropositions);
		toolbar.addComponent(btnCategories);
		toolbar.addComponent(btnUser);
		toolbar.addComponent(btnLogout);
		toolbar.addComponent(btnLogin);
		toolbar.addComponent(logo);
		// properties
		toolbar.setMargin(true);
		toolbar.setSpacing(true);
		toolbar.setStyleName("toolbar");
		toolbar.setWidth("100%");
		toolbar.setHeight(90, Sizeable.UNITS_PIXELS);
		toolbar.setComponentAlignment(logo, Alignment.MIDDLE_RIGHT);
		toolbar.setExpandRatio(logo, 1.0f);

		// scroll = buildScroll();
		// sidebar.addComponent(scroll);
		// sidebar.setExpandRatio(scroll, 1.0f);

		return toolbar;
	}

	// initialize navigator
	private TabNavigator buildNavigator() {
		navigator = new TabNavigator();
		navigator.setSizeFull();
		navigator.setImmediate(false);
		navigator.addView("home", HomeView.class);
		navigator.addView("category", CategoryView.class);
		navigator.addView("proposition", PropositionView.class);
		navigator.addView("user", ODUserView.class);

		return navigator;
	}

	// add listeners
	private void addListeners() {
		btnCategories.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("category");
			}
		});
		btnPropositions.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("proposition");
			}
		});
		btnUser.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Object user = getApplication().getUser();
				if (user != null && user instanceof ODUser) {
					navigator.navigateTo("user/edit/" + ((ODUser) user).getId());
				} else {
					System.err.println("Should not happen");
				}
			}
		});
		btnLogin.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Embedded jsp = new Embedded();
				jsp.setSizeFull();
				jsp.setType(Embedded.TYPE_BROWSER);
				jsp.setCaption(ThemeConstants.TAB_CAPTION_LOGIN);
				jsp.setIcon(ThemeConstants.TAB_ICON_LOGIN);
				jsp.setSource(new ExternalResource("/jsp/login"));
				navigator.openChildTab(jsp, "login");
			}
		});
		btnLogout.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getWindow().getApplication().close();
			}
		});
	}

	@Override
	public void navigatorViewChange(TabNavigator.View previous,
			TabNavigator.View current) {
		// TODO view change handling
	}

	@Override
	public void attach() {
		super.attach();

		Application application = getApplication();
		if (application != null) {
			application.addListener(new UserChangeListener() {

				@Override
				public void applicationUserChanged(UserChangeEvent event) {
					if (((OpenDemocracyVotingApplication) getWindow()
							.getApplication()).hasAnyRole(
							UserRole.ROLE_USER.name(),
							UserRole.ROLE_ADMIN.name(),
							UserRole.ROLE_VERIFIED_USER.name())) {
						btnLogout.setVisible(true);
						btnUser.setVisible(true);
						btnLogin.setVisible(false);
					}
				}
			});
			if (((OpenDemocracyVotingApplication) application).hasAnyRole(
					UserRole.ROLE_USER.name(), UserRole.ROLE_ADMIN.name(),
					UserRole.ROLE_VERIFIED_USER.name())) {
				btnLogout.setVisible(true);
				btnUser.setVisible(true);
				btnLogin.setVisible(false);
			}
		}
	}

	// Home view
	public static class HomeView extends VerticalLayout implements
			TabNavigator.View, UserChangeListener {
		Label loginMsg;
		String loginTxt = "<h1 class=\"v-label-h1\" style=\"text-align: center;\">Welcome to OpenDemocracy Alpha</h1> Select an option above to begin";

		public HomeView() {
			setReadOnly(true);
			setCaption(ThemeConstants.TAB_CAPTION_HOME);
			setIcon(ThemeConstants.TAB_ICON_HOME);
			setMargin(true);
			setSizeFull();
			addStyleName(Reindeer.LAYOUT_WHITE);
			loginMsg = new Label(loginTxt, Label.CONTENT_XHTML);
			loginMsg.setSizeUndefined();
			loginMsg.addStyleName(Reindeer.LABEL_SMALL);
			addComponent(loginMsg);
			setComponentAlignment(loginMsg, Alignment.MIDDLE_CENTER);
			setReadOnly(true);
		}

		// Unused interfaces
		@Override
		public void init(TabNavigator navigator, Application application) {
		}

		@Override
		public void navigateTo(String requestedDataId) {
		}

		@Override
		public String getWarningForNavigatingFrom() {
			return null;
		}

		@Override
		public void attach() {
			// TODO Auto-generated method stub
			super.attach();

			Application application = getApplication();
			if (application != null) {
				application.addListener(this);
			}
		}

		@Override
		public void applicationUserChanged(UserChangeEvent event) {
			Object user = event.getNewUser();
			if (user != null && user instanceof ODUser) {
				ODUser oduser = (ODUser) user;
				loginMsg.setValue(loginTxt + "<br /><br />Logged in as: "
						+ oduser.getOpenIdIdentifier() + "<br /><br />Mail: "
						+ oduser.getEmailAddress() + "<br /><br />Username: "
						+ oduser.getUsername());
			} else {
				loginMsg.setValue(loginTxt);
			}
		}
	}

}