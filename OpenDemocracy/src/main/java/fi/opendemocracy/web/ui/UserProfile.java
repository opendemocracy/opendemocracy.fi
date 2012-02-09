package fi.opendemocracy.web.ui;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.web.ThemeConstants;

public class UserProfile extends CustomComponent {

	private AbsoluteLayout mainLayout;
	
	private ODUser user;

	private Panel scrollPanel;

	private VerticalLayout scrollContent;

	public UserProfile(ODUser odUser) {
		user = odUser;
		buildMainLayout();
		setCompositionRoot(mainLayout);
		String name = user.getUsername();
		setCaption("User: " + (name == null || name.isEmpty() ? user.getId() : name));
		setIcon(ThemeConstants.TAB_ICON_USER);
	}
	
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();

		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

		// scrollPanel
		scrollPanel = buildScrollPanel();
		mainLayout.addComponent(scrollPanel);

		return mainLayout;
	}


	private Panel buildScrollPanel() {
		// common part: create layout
		scrollPanel = new Panel();
		scrollPanel.setWidth("100.0%");
		scrollPanel.setHeight("100.0%");
		scrollPanel.setImmediate(false);

		// scrollContent
		scrollContent = buildScrollContent();
		scrollPanel.setContent(scrollContent);

		return scrollPanel;
	}

	private VerticalLayout buildScrollContent() {
		VerticalLayout scrollContent = new VerticalLayout();
		Label label = new Label(user.toString());
		scrollContent.addComponent(label);
		return scrollContent;
	}

}
