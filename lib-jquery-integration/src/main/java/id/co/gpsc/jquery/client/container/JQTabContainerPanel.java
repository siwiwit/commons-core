package id.co.gpsc.jquery.client.container;



import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.DOM;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import id.co.gpsc.jquery.client.BaseJqueryWidget;
import id.co.gpsc.jquery.client.util.NativeJsUtilities;



/**
 * wrapper tab container
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class JQTabContainerPanel extends BaseJqueryWidget{
	
	
	
	/**
	 * untuk kemudahan debug. id(prefix) dari content tab
	 **/
	public final String CONTENT_ID_PREFIX="tab-content-";
	
	/**
	 * class untuk manipulasi tab content
	 **/
	public class TabContentPanel  {
		
		private String currentTabElementId ; 
		
		
		
		
		protected TabContentPanel(String elementId){
			this.currentTabElementId = elementId ; 
		}
		
		
		/**
		 * replace widget
		 **/
		public void addWidget(Widget w) {
			tabHolderSwap.add(w);
			DOM.getElementById(currentTabElementId).appendChild(w.getElement());
		}
		
		
		/**
		 * hapus widget dari tab content
		 **/
		public void removeWidget(Widget w) {
			tabHolderSwap.remove(w);
		}
		
		
		
		
		/**
		 * remove current tab dari tab container
		 **/
		public void removeFromTab () {
			int index = findIndexOnTab();
			JQTabContainerPanel.this.removeFromTab(index);
		}
		
		
		/**
		 * enable tab
		 **/
		public void enable(){
			int idx = findIndexOnTab();
			if (idx>0)
				enableTab(idx);
		}
		
		
		public void disable () {
			int idx = findIndexOnTab();
			if (idx>0)
				disableTab( idx);
		}
		
		
		 
		
		private int findIndexOnTab () {
			int index = -1 ; 
			int maxy = headerULElement.getChildCount();
			for ( int i=0;i<maxy ;i++){
				Node ttl = headerULElement.getChild(i);
				for (int y=0;y<ttl.getChildCount();y++){
					if ( "A".equalsIgnoreCase(  ttl.getChild(y).getNodeName())){
						LinkElement node =  (LinkElement)ttl.getChild(y) ;
						if ( ("#" +  currentTabElementId).equals(   node.getHref()))
							return i;	
						break ;
					}
				}
			}
			return index  ; 
		}
		
		
		
		
		
	}
	
	
	/**
	 * div yang paling luar. 
	 **/
	private Element outmostDivElement ;  
	
	private final FlowPanel tabHolderSwap = new FlowPanel(); 
	
	
	
	
	
	private Element headerULElement ; 
	
	
	public JQTabContainerPanel(){
		super();
		tabHolderSwap.setVisible(false);
		tabHolderSwap.setWidth("1px");
		tabHolderSwap.setHeight("1px");
		RootPanel.get().add(tabHolderSwap);
		
	}

	@Override
	protected String getJQueryClassName() {
		return "tabs";
	}

	@Override
	protected Element generateUnderlyingElement() {
		outmostDivElement = DOM.createDiv(); 
		
		outmostDivElement.setId( DOM.createUniqueId());
		headerULElement = DOM.createElement("ul");
		
		outmostDivElement.appendChild(headerULElement);
		
		
		return outmostDivElement;
	}
	
	
	/**
	 * append widget sekaligus dengan widget pada content
	 * 
	 **/
	public TabContentPanel appendTab (String titleForPanel ,final Widget tabContentWidget ){
		final TabContentPanel swap = appendTab(titleForPanel);
		
		new Timer() {
			
			@Override
			public void run() {
				swap.addWidget(tabContentWidget);
				
			}
		}.schedule(1);
		
		
		
		
		return swap ; 
		
	}
	
	/**
	 * append tab to panel
	 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
	 * @param titleForPanel title label for new tabs
	 **/
	public TabContentPanel appendTab (String titleForPanel){
		if ( jqueryRendered){
			destroy();
		}
		String tabId = CONTENT_ID_PREFIX + DOM.createUniqueId();
		TabContentPanel tb = new TabContentPanel(tabId);
		Element aElement = DOM.createAnchor(); 
		aElement.setAttribute("href", "#" + tabId);
		
		Element liElement = DOM.createElement("li");
		aElement.setInnerHTML(titleForPanel);
		liElement.appendChild(aElement);
		headerULElement.appendChild(liElement);
		Element containerDiv = DOM.createDiv(); 
		containerDiv.setAttribute("id", tabId);
		outmostDivElement.appendChild(containerDiv);
		if(jqueryRendered){
			//new Timer() {
				
				//@Override
				//public void run() {
					renderJQueryWidget();
				//}
			//}.schedule(10);
			
		}
		return tb;
	}
	
	/**
	 * flag collapsible
	 **/
	public void setCollapsible(boolean collapsible) {
		NativeJsUtilities.getInstance().putObject(widgetConstructArgument, "collapsible", collapsible);
		if ( jqueryRendered){
			triggerOption("collapsible", collapsible);
		}
		
	}
	
	/**
	 * flag collapsible
	 **/
	public boolean isCollapsible() {
		return NativeJsUtilities.getInstance().getBoolean(widgetConstructArgument, "collapsible");
	}
	
	/**
	 * remove tab dengan index. index berbasis 0, sesuai dengan urutan current(kalau di sort, tetap dari kiri ke kanan index)
	 * @param index index tab dari kiri
	 **/
	public native void removeFromTab (int index) /*-{
		var idSrc = "#" + this.@id.co.gpsc.jquery.client.container.JQTabContainerPanel::outmostDivElement;
		$wnd.$(idSrc).tabs("remove" , index);
	}-*/;
	
	/**
	 * worker, enable tab. dengan index dari tab(reverse {@link #disableTab(int)})<br/>
	 * @see #disableTab(int)
	 **/
	public native void enableTab (int index) /*-{
		var idSrc = "#" + this.@id.co.gpsc.jquery.client.container.JQTabContainerPanel::outmostDivElement;
		$wnd.$(idSrc).tabs("enable" , index);
	}-*/;
	
	/**
	 * worker, disable tab. dengan index dari tab
	 * @see  #enableTab(int)
	 **/
	public native void disableTab (int index) /*-{
		var idSrc = "#" + this.@id.co.gpsc.jquery.client.container.JQTabContainerPanel::outmostDivElement;
		$wnd.$(idSrc).tabs("disable" , index);
	}-*/;
}
