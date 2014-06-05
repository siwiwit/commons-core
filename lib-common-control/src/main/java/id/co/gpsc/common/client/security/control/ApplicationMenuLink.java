package id.co.gpsc.common.client.security.control;

import id.co.gpsc.common.client.security.BaseRootSecurityPanel;
import id.co.gpsc.common.client.security.RootWigetGenerator;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Anchor;

/**
 * Anchor untuk menu security
 * @author Gede Sutarsa
 * @author I Gede Mahendra
 * @since Jan 3, 2013, 10:44:42 AM
 * @version $Id
 */
public class ApplicationMenuLink extends Anchor{
		
	private BaseRootSecurityPanel panel ;	
	private RootWigetGenerator widgetGenerator ;
	private boolean cacheable = true;
	
	public ApplicationMenuLink(){		
		super(); 		
		addClickHandler(new ClickHandler() {		
			@Override
			public void onClick(ClickEvent arg0) {				
				if ( panel==null){
					panel = widgetGenerator.generatePanel();					
				} else {
					if (!cacheable)
						panel = widgetGenerator.generatePanel();
				}
								
				new Timer() {					
					@Override
					public void run() {
						RootPanelManager.getInstance().setRootWidget(panel);						
						setStyleLink();
					}
				}.schedule(5);							
			}
		});
	}
	
	public ApplicationMenuLink(String menu){
		super();
		this.setHTML(menu);
		addClickHandler(new ClickHandler() {		
			@Override
			public void onClick(ClickEvent arg0) {				
				//if ( panel==null){
					panel = widgetGenerator.generatePanel();					
				//}	
								
				new Timer() {					
					@Override
					public void run() {						
						RootPanelManager.getInstance().setRootWidget(panel);
						clearStyleSelection();
						setStyleLink();
					}
				}.schedule(5);							
			}
		});
	}
	
	/**
	 * Clear style selection
	 */
	private void clearStyleSelection(){
		List<ApplicationMenuLink> menusLink = RootPanelManager.getInstance().getListOfApplicationLink();
		for (ApplicationMenuLink menu : menusLink) {
			menu.removeStyleName("selected-menu");
		}
	}
	
	/**
	 * Set style
	 */
	private void setStyleLink(){
		this.setStyleName("selected-menu");		
	}
	
	/**
	 * Set widget generator
	 */
	public RootWigetGenerator getWidgetGenerator() {
		return widgetGenerator;
	}

	/**
	 * Get widget generator
	 */
	public void setWidgetGenerator(RootWigetGenerator widgetGenerator) {
		this.widgetGenerator = widgetGenerator;
	}
	
	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}
	
	public boolean isCacheable() {
		return cacheable;
	}

	public void setPanel(BaseRootSecurityPanel panel) {
		this.panel = panel;
	}	
}