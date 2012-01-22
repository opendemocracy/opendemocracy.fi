package fi.opendemocracy.voting.web;

import java.util.HashMap;
import java.util.LinkedList;

import com.vaadin.Application;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.gwt.client.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.UriFragmentUtility;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedEvent;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedListener;
@SuppressWarnings("serial")
public class TabNavigator extends CustomComponent {

	private HashMap<String, Class<?extends Component>> uriToClass = new HashMap<String, Class<?extends Component>>();
	private HashMap<Class<?extends Component>, String> classToUri = new HashMap<Class<?extends Component>, String>();
	private HashMap<Class<?extends Component>, View> classToView = new HashMap<Class<?extends Component>, View>();
	private HashMap<Component, String> tabToUri = new HashMap<Component, String>();
	private String mainViewUri = null;
	private HorizontalLayout container;
	private TabSheet tabSheet = new TabSheet();
	private UriFragmentUtility uriFragmentUtil = new UriFragmentUtility();
	private String currentFragment = "";
	private View previousView = null;
	private View currentView = null;
	private LinkedList<ViewChangeListener> listeners = new LinkedList<ViewChangeListener>();

    private Panel helpPanel;
    private Button helpToggle;
    
	public TabNavigator() {
		tabSheet.setSizeFull();
		setSizeFull();
		container = new HorizontalLayout();
		container.addComponent(uriFragmentUtil);
		container.addComponent(tabSheet);
		container.setSizeFull();
		container.setExpandRatio(tabSheet, 1.0f);
		createHelpPanel();
		setCompositionRoot(container);
		addListeners();
		addHandlers();
	}

	private void createHelpPanel(){
        // Help panel
        helpPanel = new Panel("No help available in this section.");
        
        //Toggle bar
        helpToggle = new Button();        
        helpToggle.setStyleName("toggle-help");
        helpToggle.setHeight("100.0%");
        helpToggle.setIcon(new ThemeResource("icons/16/help.png"));
        helpToggle.setImmediate(true);
        container.addComponent(helpToggle);
        
        //Help panel properties
        helpPanel.setHeight("100.0%");
        helpPanel.setWidth("200px");
        helpPanel.setStyleName("panel-help");
        helpPanel.setVisible(false);
        container.addComponent(helpPanel);
	}
	
	public void setHelpText(Component c) {
        helpPanel.replaceComponent(helpPanel.getComponentIterator().next(), c);
	}
	
	// uri handling methods
	private void fragmentChanged() {
		String newFragment = uriFragmentUtil.getFragment();
		if ("".equals(newFragment)) {
			newFragment = mainViewUri;
		}
		int i = newFragment.indexOf('/');
		String uri = i < 0 ? newFragment : newFragment.substring(0, i);
		final String requestedDataId = i < 0 || i + 1 == newFragment.length() ? null : newFragment.substring(i + 1);
		if (uriToClass.containsKey(uri)) {
			final View newView = getOrCreateView(uri);
			String warn = currentView == null ? null : currentView.getWarningForNavigatingFrom();
			if (warn != null && warn.length() > 0) {
				confirmedMoveToNewView(requestedDataId, newView, warn);
			} else {
				moveTo(newView, requestedDataId, false);
			}

		} else {
			uriFragmentUtil.setFragment(currentFragment, false);
		}
	}
	private void confirmedMoveToNewView(final String requestedDataId,
			final View newView, String warn) {
		VerticalLayout lo = new VerticalLayout();
		lo.setMargin(true);
		lo.setSpacing(true);
		lo.setWidth("400px");
		final Window wDialog = new Window("Warning", lo);
		wDialog.setModal(true);
		final Window main = getWindow();
		main.addWindow(wDialog);
		lo.addComponent(new Label(warn));
		lo.addComponent(new Label("If you do not want to navigate away from the current screen, press Cancel."));
		Button cancel = new Button("Cancel", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				uriFragmentUtil.setFragment(currentFragment, false);
				main.removeWindow(wDialog);
			}
		});
		Button cont = new Button("Continue", new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {
				main.removeWindow(wDialog);
				moveTo(newView, requestedDataId, false);
			}

		});
		HorizontalLayout h = new HorizontalLayout();
		h.addComponent(cancel);
		h.addComponent(cont);
		h.setSpacing(true);
		lo.addComponent(h);
		lo.setComponentAlignment(h, new Alignment(Bits.ALIGNMENT_RIGHT));
	}

	private View getOrCreateView(String uri) {
		Class<? extends Component> newViewClass = uriToClass.get(uri);
		if (!classToView.containsKey(newViewClass)) {
			try {
				View view = (View) newViewClass.newInstance();
				view.init(this, getApplication());
				classToView.put(newViewClass, view);
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		final View v = classToView.get(newViewClass);
		return v;
	}
	
	private void moveTo(View v, String requestedDataId, boolean noFragmentSetting) {
		currentFragment = classToUri.get(v.getClass());
		if (requestedDataId != null) {
			currentFragment += "/" + requestedDataId;
		}
		if (!noFragmentSetting && !currentFragment.equals(uriFragmentUtil.getFragment())) {
			uriFragmentUtil.setFragment(currentFragment, false);
		}
		
		previousView = currentView;
		currentView = v;
		openTab(v, requestedDataId);
		
		for (ViewChangeListener l : listeners) {
			l.navigatorViewChange(previousView, currentView);
		}
	}

	public void openTab(Component v, String uri) {
		Tab t = tabSheet.getTab(v);
		if(t == null){
			t = tabSheet.addTab(v, v.getCaption(), v.getIcon());
			t.setClosable(!v.isReadOnly());
			tabToUri.put(v, uri);
		}
		tabSheet.setSelectedTab(v);
		currentView.navigateTo(uri);
	}
	
	
	private void addListeners(){
		uriFragmentUtil.addListener(new FragmentChangedListener() {
			public void fragmentChanged(FragmentChangedEvent source) {
				TabNavigator.this.fragmentChanged();
			}
		});
		tabSheet.addListener(new SelectedTabChangeListener(){
			public void selectedTabChange(SelectedTabChangeEvent event) {
				//TODO: Update uri navigateTo
			}
		});
    	helpToggle.addListener(new ClickListener() {
        	  public void buttonClick(ClickEvent event) {
        	    helpPanel.setVisible(!helpPanel.isVisible());
        	  }
        });
	}
	private void addHandlers(){
		tabSheet.setCloseHandler(new CloseHandler() {
			public void onTabClose(TabSheet tabsheet, Component tabContent) {
				// TODO: Clear view on close
				tabsheet.removeComponent(tabContent);
			}
		});
	}
	
	/**
	 * Get the main view.
	 * 
	 * Main view is the default view shown to user when he opens application
	 * without specifying view uri.
	 * 
	 * @return Uri of the main view.
	 */
	public String getMainView() {
		return mainViewUri;
	}

	/**
	 * Set the main view.
	 * 
	 * Main view is the default view shown to user when he opens application
	 * without specifying view uri. If main view has not been set, the first
	 * view registered with addView() is used as main view. Note that the view
	 * must be registered with addView() before calling this method.
	 * 
	 * @param mainViewUri
	 *            Uri of the main view.
	 */
	public void setMainView(String mainViewUri) {
		if (uriToClass.containsKey(mainViewUri)) {
			this.mainViewUri = mainViewUri;
			if (currentView == null) {
				moveTo(getOrCreateView(mainViewUri), null, true);
			}
		} else {
			throw new IllegalArgumentException(
					"No view with given uri can be found in the navigator");
		}
	}

	/**
	 * Add a new view to navigator.
	 * 
	 * Register a view to navigator.
	 * 
	 * @param uri
	 *            String that identifies a view. This is the string that is
	 *            shown in URL after #
	 * @param viewClass
	 *            Component class that implements Navigator.View interface
	 */
	public void addView(String uri, Class<?extends Component> viewClass, boolean locked) {

		// Check parameters
		if (!View.class.isAssignableFrom(viewClass)) {
			throw new IllegalArgumentException(
					"viewClass must implement TabNavigator.View");
		}

		if (uri == null || viewClass == null || uri.length() == 0) {
			throw new IllegalArgumentException(
					"viewClass and uri must be non-null and not empty");
		}

		if (uriToClass.containsKey(uri)) {
			if (uriToClass.get(uri) == viewClass) {
				return;
			}

			throw new IllegalArgumentException(uriToClass.get(uri).getName()
					+ " is already mapped to '" + uri + "'");
		}

		if (classToUri.containsKey(viewClass)) {
			throw new IllegalArgumentException(
					"Each view class can only be added to TabNavigator with one uri");
		}

		if (uri.indexOf('/') >= 0 || uri.indexOf('#') >= 0) {
			throw new IllegalArgumentException(
					"Uri can not contain # or / characters");
		}

		uriToClass.put(uri, viewClass);
		classToUri.put(viewClass, uri);

		if (getMainView() == null) {
			setMainView(uri);
		}
	}
	public void addView(String uri, Class<?extends Component> viewClass) {
		addView(uri, viewClass, false);
	}

	
	
	/**
	 * Remove view from navigator.
	 * 
	 * @param uri
	 *            Uri of the view to remove.
	 */
	public void removeView(String uri) {
		Class<?extends Component> c = uriToClass.get(uri);
		if (c != null) {
			uriToClass.remove(uri);
			classToUri.remove(c);
			if (getMainView() == null || getMainView().equals(getMainView())) {
				if (uriToClass.size() == 0) {
					mainViewUri = null;
				} else {
					setMainView(uriToClass.keySet().iterator().next());
				}
			}
		}
	}

	/**
	 * Get the uri for given view implementation class.
	 * 
	 * @param viewClass
	 *            Class that implements the view.
	 * @return Uri registered for the view class.
	 */
	public String getUri(Class<?extends Component> viewClass) {
		return classToUri.get(viewClass);
	}

	/**
	 * Get the view class for given uri.
	 * 
	 * @param uri
	 *            Uri to get view for
	 * @return View that corresponds to the uri
	 */
	public Class<?extends Component> getViewClass(String uri) {
		return uriToClass.get(uri);
	}

	/**
	 * Switch to view identified with uri.
	 * 
	 * Uri can be either the exact uri registered previously with addView() or
	 * it can also contain data id passed to the view. In case data id is
	 * included, the format is 'uri/freeFormedDataIdString'.
	 * 
	 * @param uri
	 *            Uri where to navigate.
	 */
	public void navigateTo(String uri) {
		uriFragmentUtil.setFragment(uri);
	}

	/**
	 * Switch to view implemented by given class.
	 * 
	 * Note that the view must be registered to navigator with addView() before
	 * calling this method.
	 * 
	 * @param viewClass
	 *            Class that implements the view.
	 */
	public void navigateTo(Class<? extends Component> viewClass) {
		String uri = getUri(viewClass);
		if (uri != null) {
			navigateTo(uri);
		}
	}

	/**
	 * Listen to the view changes.
	 * 
	 * The listener will get notified after the view has changed.
	 * 
	 * @param listener
	 *            Listener to invoke after view changes.
	 */
	public void addListener(ViewChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * Remove the view change listener.
	 * 
	 * @param listener
	 *            Listener to remove.
	 */
	public void removeListener(ViewChangeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Interface for listening to View changes.
	 */
	public interface ViewChangeListener {

		/**
		 * Invoked after the view has changed. Be careful for deadlocks if you
		 * decide to change the view again in the listener.
		 * 
		 * @param previous
		 *            Preview view before the change.
		 * @param current
		 *            New view after the change.
		 */
		public void navigatorViewChange(View previous, View current);

	}

	/**
	 * Interface for all views controlled by the navigator.
	 * 
	 * Each view added to the navigator must implement this interface.
	 */
	public interface View extends Component {

		/**
		 * Init view.
		 * 
		 * Convenience method which navigator call before slightly before the
		 * view is first time rendered. This is called only once in the lifetime
		 * of each view instance. In many cases it is better to construct UI
		 * within this method than in constructor as you are guaranteed to get
		 * references to application and navigator here.
		 * 
		 * @param navigator
		 *            Reference to navigator that controls the window where this
		 *            view is attached to.
		 * @param application
		 *            Application instance this view lives in.
		 */
		public void init(TabNavigator navigator, Application application);

		/**
		 * This view is navigated to.
		 * 
		 * This method is always called before the view is shown on screen. If
		 * there is any additional id to data what should be shown in the view,
		 * it is also optionally passed as parameter.
		 * 
		 * @param requestedDataId
		 *            Id of the data extracted from URI fragment or null if not
		 *            given. This is the string that appeards in URI after
		 *            #viewname/
		 */
		public void navigateTo(String requestedDataId);

		/**
		 * Get a warning that should be shown to user before navigating away
		 * from the view.
		 * 
		 * If the current view is in state where navigating away from it could
		 * lead to data loss, this method should return a message that will be
		 * shown to user before he confirms that he will leave the screen. If
		 * there is no need to ask questions from user, this should return null.
		 * 
		 * @return Message to be shown or null if no message should be shown.
		 */
		public String getWarningForNavigatingFrom();
	}

	/**
	 * Interface implemented by all applications that uses Navigator.
	 * 
	 */
	public interface NavigableApplication {

		/**
		 * Create a new browser window.
		 * 
		 * This method must construc a new window that could be used as a main
		 * window for the application. Each call to this method must create a
		 * new instance and your application should work when there are multiple
		 * instances of concurrently. Each window can contain anything you like,
		 * but at least they should contain a new Navigator instance for
		 * controlling navigation within the window. Typically one also adds
		 * somekind of menu for commanding navigator.
		 * 
		 * @return New window.
		 */
		public Window createNewWindow();
	}

	/**
	 * Helper for overriding Application.getWindow(String).
	 * 
	 * <p>
	 * This helper makes implementing support for multiple browser tabs or
	 * browser windows easy. Just override Application.getWindow(String) in your
	 * application like this:
	 * </p>
	 * 
	 * <pre>
	 * &#064;Override
	 * public Window getWindow(String name) {
	 * 	return Navigator.getWindow(this, name, super.getWindow(name));
	 * }
	 * </pre>
	 * 
	 * @param application
	 *            Application instance, which implements
	 *            Navigator.NavigableApplication interface.
	 * @param name
	 *            Name parameter from Application.getWindow(String name)
	 * @param superGetWindow
	 *            The window returned by super.getWindow(name)
	 * @return
	 */
	public static Window getWindow(NavigableApplication application,
			String name, Window superGetWindow) {
		if (superGetWindow != null) {
			return superGetWindow;
		}

		Window w = application.createNewWindow();
		w.setName(name);
		((Application) application).addWindow(w);
		w.open(new ExternalResource(w.getURL()));
		return w;
	}
}
