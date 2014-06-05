package id.co.gpsc.jquery.client.menu;

import com.google.gwt.core.client.GWT;





/**
 * menu dengan link
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 *  
 **/
public class JQLeafMenuWidget<MENUDATA> extends JQBaseMenuWidget<MENUDATA>{
	
	
	
	/**
	 * method counter. ini untuk menjamin method unik
	 **/
	private static int MENU_METHOD_COUNTER =361 ;

	/**
	 * prefix nama js menthod
	 **/
	private static final String METHOD_NAME_PREFIX="MENU_AUTOMATED_CLICK_HANDLER";
	private MenuClickHandler<MENUDATA> clickHandler ; 
	/**
	 * nama method js untuk handler clik pada menu
	 **/
	private String clickMethodHandlerName ; 
	
	public JQLeafMenuWidget(String menuLabel) {
		super(menuLabel);
		configureClickHandler();
	}

	
	/**
	 * @param menuLabel label untuk menu
	 * @param clickHandler method pada saat menu di click
	 **/
	public JQLeafMenuWidget(String menuLabel, MenuClickHandler<MENUDATA> clickHandler){
		this(menuLabel);
		this.clickHandler=clickHandler;
	}
	
	
	/**
	 * @param menuLabel label untuk menu
	 * @param cssForIcon css untuk icon menu
	 **/
	public JQLeafMenuWidget(String menuLabel, String cssForIcon){
		super(menuLabel,cssForIcon);
		configureClickHandler();
	}
	
	
	/**
	 * @param menuLabel label untuk menu
	 * @param cssForIcon css icon menu
	 * @param clickHandler method pada saat menu di click
	 *  
	 **/
	public JQLeafMenuWidget(String menuLabel, String cssForIcon, MenuClickHandler<MENUDATA> clickHandler){
		this(menuLabel , cssForIcon);
		this.clickHandler  = clickHandler ; 
	}
	/**
	 * menaruh click handler ke dalam menu
	 **/
	public void assignClickHandler(MenuClickHandler<MENUDATA> clickHandler) {
		this.clickHandler = clickHandler;
	}
	
	
	private void configureClickHandler(){
		this.clickMethodHandlerName = METHOD_NAME_PREFIX + MENU_METHOD_COUNTER;
		MENU_METHOD_COUNTER++;
		getLinkElement().setAttribute("href", "javascript:" + clickMethodHandlerName +"()"); 
		deployClickHandler(clickMethodHandlerName);
	}
	
	
	/**
	 * handler click menu
	 **/
	protected void propagateClickHandler () {
		if  ( clickHandler==null){
			GWT.log("click handler untuk jq leaf menu kosong, tidak ada yang akan di lakukan");
			return ; 
		}
		clickHandler.execute(getMenuData());
	}
	/**
	 * worker untuk menaruh handler method. method akan di taruh sebagai javascript method di window. sehingga href click bisa akses dengan javascript:method handler
	 * @param methodName nama method handler. ini auto generated method
	 **/
	protected native void deployClickHandler(String methodName )/*-{
		var thisSwap = this ; 
		$wnd[methodName]=function (){
			try{
				thisSwap.@id.co.gpsc.jquery.client.menu.JQLeafMenuWidget::propagateClickHandler()();
					
			}
			catch ( exc){
			}
		
		}
	
	}-*/;
}
