package fi.opendemocracy.web;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;
import com.vaadin.ui.Window;

public class OpenDemocracyVotingApplicationServlet extends
		AbstractApplicationServlet {
	private WebApplicationContext applicationContext;
	private Class<? extends Application> applicationClass;
	private String applicationBean;

	@SuppressWarnings("unchecked")
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		applicationBean = servletConfig.getInitParameter("applicationBean");

		if (applicationBean == null) {
			throw new ServletException(
					"ApplicationBean not specified in servlet parameters");
		}

		applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(servletConfig.getServletContext());

		applicationClass = (Class<? extends Application>) applicationContext
				.getType(applicationBean);
	}

	@Override
	protected Class<? extends Application> getApplicationClass()
			throws ClassNotFoundException {
		return applicationClass;
	}

	@Override
	protected Application getNewApplication(HttpServletRequest request) {
		return (Application) applicationContext.getBean(applicationBean);
	}

	@Override
	protected void writeAjaxPageHtmlVaadinScripts(Window window,
			String themeName, Application application, BufferedWriter page,
			String appUrl, String themeUri, String appId,
			HttpServletRequest request) throws ServletException, IOException {
		page.write("<script type=\"text/javascript\">\n");
		page.write("//<![CDATA[\n");
		page.write("document.write(\"<script language='javascript' src='./VAADIN/jquery/jquery-1.4.4.min.js'><\\/script>\");\n");
		page.write("document.write(\"<script language='javascript' src='./VAADIN/js/highcharts.js'><\\/script>\");\n");
		page.write("document.write(\"<script language='javascript' src='./VAADIN/js/modules/exporting.js'><\\/script>\");\n");
		page.write("//]]>\n</script>\n");
		super.writeAjaxPageHtmlVaadinScripts(window, themeName, application,
				page, appUrl, themeUri, appId, request);
	}

}
