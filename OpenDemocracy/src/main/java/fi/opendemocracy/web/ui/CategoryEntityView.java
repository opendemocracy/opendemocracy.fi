package fi.opendemocracy.web.ui;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import fi.opendemocracy.domain.Category;
import fi.opendemocracy.web.ThemeConstants;


public class CategoryEntityView extends VerticalLayout {
	public CategoryEntityView(Category c){
		setCaption(c.getName());
		setIcon(ThemeConstants.TAB_ICON_CATEGORIES);
		
		Label title = new Label("<h1>" + c.getName() + "</h1>");
		Label description = new Label("<p>" + c.getDescription() + "</p>");

		title.setContentMode(Label.CONTENT_XHTML);
		description.setContentMode(Label.CONTENT_XHTML);

		addComponent(title);
		addComponent(description);
	}
}
