package fi.opendemocracy.web;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import org.vaadin.navigator.Navigator;
import org.vaadin.openid.OpenIdHandler;
import org.vaadin.openid.OpenIdHandler.UserAttribute;

import com.vaadin.Application;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class LoginView extends CustomComponent implements org.vaadin.navigator.Navigator.View {
	Application application;
	Navigator navigator;

	private void addLoginWindow() {

        VerticalLayout container = new VerticalLayout();
        container.setSpacing(true);
        container.setMargin(true);

        Window loginWindow = application.getWindow("OpenId login");
        loginWindow.setContent(container);

        final OpenIdHandler openIdHandler = new OpenIdHandler(application);
        openIdHandler.setRequestedAttributes(UserAttribute.values());

        final HorizontalLayout linkHolder = new HorizontalLayout();
        linkHolder.setSpacing(true);
        linkHolder
                .addComponent(createLoginLink(openIdHandler,
                        "https://www.google.com/accounts/o8/id",
                        "Log in using Google"));
        linkHolder.addComponent(createLoginLink(openIdHandler,
                "https://me.yahoo.com", "Log in using Yahoo"));

        container.addComponent(linkHolder);

        openIdHandler.addListener(new OpenIdHandler.OpenIdLoginListener() {
            public void onLogin(String id, Map<UserAttribute, String> userInfo) {
                Window window = application.getMainWindow();
                window.removeComponent(linkHolder);
                window.addComponent(new Label("Logged in identity: " + id));
                Set<UserAttribute> missingFields = EnumSet
                        .allOf(UserAttribute.class);
                for (UserAttribute field : userInfo.keySet()) {
                    window.addComponent(new Label(field + ": "
                            + userInfo.get(field)));
                    missingFields.remove(field);
                }
                for (UserAttribute registrationFields : missingFields) {
                    window.addComponent(new Label(registrationFields
                            + " not provided"));
                }

                openIdHandler.close();
            }

            public void onCancel() {
                application.getMainWindow().removeComponent(linkHolder);
                application.getMainWindow().addComponent(
                        new Label("Too sad you didn't want to log in."));

                openIdHandler.close();
            }
        });
	}

    private static Link createLoginLink(OpenIdHandler openIdHandler, String id,
            String caption) {
        return new Link(caption, openIdHandler.getLoginResource(id),
                "openidLogin", 600, 400, Window.BORDER_NONE);
    }

	@Override
	public void init(Navigator navigator, Application application) {
		this.application = application;
		this.navigator = navigator;
		addLoginWindow();
	}

	@Override
	public void navigateTo(String requestedDataId) {
	}

	@Override
	public String getWarningForNavigatingFrom() {
		return null;
	}
}
