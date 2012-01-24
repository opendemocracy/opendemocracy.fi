package fi.opendemocracy.voting.web.ui;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import org.vaadin.openid.OpenIdHandler;
import org.vaadin.openid.OpenIdHandler.UserAttribute;

import com.vaadin.Application;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import fi.opendemocracy.voting.web.TabNavigator;
import fi.opendemocracy.voting.web.ThemeConstants;

public class LoginView extends CustomComponent implements TabNavigator.View {
	private VerticalLayout mainLayout;
	Application application;
	TabNavigator navigator;

	public LoginView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		setCaption(ThemeConstants.TAB_CAPTION_LOGIN);
		setIcon(ThemeConstants.TAB_ICON_LOGIN);
	}
	

	private void addLogin() {
		final VerticalLayout container = new VerticalLayout();
	    container.setSpacing(true);
	    container.setMargin(true);
	
	    mainLayout.addComponent(container);
	
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
	    
	    final TextField openIdentifier = new TextField("OpenID identifier");
	    Button submit = new Button("Submit");
	    submit.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	String id = (String)openIdentifier.getValue();
            	Resource res = openIdHandler.getLoginResource(id);
            	getApplication().getMainWindow().open(res, "openidLogin");
            }
        });
	    Form openId = new Form();
	    openId.addField("openid_identifier", openIdentifier);
	    openId.addField("submit", submit);
	    container.addComponent(openId);
	    container.addComponent(linkHolder);
	    
	    openIdHandler.addListener(new OpenIdHandler.OpenIdLoginListener() {
	        public void onLogin(String id, Map<UserAttribute, String> userInfo) {
	            container.removeComponent(linkHolder);
	            container.addComponent(new Label("Logged in identity: " + id));
	            
	    		Button logout = new Button("logout");
	    		logout.addListener(new Button.ClickListener()
	    		{
	                private static final long serialVersionUID = 1L;

	    			@Override
	    			public void buttonClick(ClickEvent event)
	    			{
	    				openIdHandler.close();
	    				getWindow().getApplication().close();
	    			}
	    		});
	    		container.addComponent(logout);
	            
	            Set<UserAttribute> missingFields = EnumSet
	                    .allOf(UserAttribute.class);
	            for (UserAttribute field : userInfo.keySet()) {
	            	container.addComponent(new Label(field + ": "
	                        + userInfo.get(field)));
	                missingFields.remove(field);
	            }
	            for (UserAttribute registrationFields : missingFields) {
	            	container.addComponent(new Label(registrationFields
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
	public void init(TabNavigator navigator, Application application) {
		this.application = application;
		this.navigator = navigator;
		addLogin();
	}

	@Override
	public void navigateTo(String requestedDataId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getWarningForNavigatingFrom() {
		// TODO Auto-generated method stub
		return null;
	}

	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		return mainLayout;
	}

}
