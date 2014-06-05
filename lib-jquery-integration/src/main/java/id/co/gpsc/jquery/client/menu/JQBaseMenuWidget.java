package id.co.gpsc.jquery.client.menu;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;



/**
 * base class menu
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public abstract class JQBaseMenuWidget<MENUDATA> {
	
	protected static final String MENU_NODE_PREFIX ="gps_menu_";
	
	/**
	 * no operation JS method name
	 **/
	protected static final String NOP_JS_METHOD_NAME ="menuNOPMenuMethod";
	
	
	
	/**
	 * element yg menjadi base dari menu
	 **/
	protected Element underlyingElement ; 
	/**
	 * flag no operation sudah di define atau tidak
	 **/
	protected static boolean NOP_METHOD_INITIALIZED =false ;
	/**
	 * hyperlink menu
	 **/
	private Element linkElement ; 
	
	
	
	/**
	 * data untuk menu *if exist*
	 **/
	private MENUDATA menuData ; 
	


	/**
	 * constructor
	 * @param defaultLabel default menu label
	 *  
	 **/
	public JQBaseMenuWidget(String menuLabel){
		underlyingElement = DOM.createElement("li");
		underlyingElement.setPropertyString("id", MENU_NODE_PREFIX + DOM.createUniqueId());
		linkElement = DOM.createElement("a");
		linkElement.setInnerHTML(menuLabel);
		linkElement.setId(DOM.createUniqueId());
		underlyingElement.appendChild(linkElement);
		bindToNoneMenu(linkElement.getId(), NOP_JS_METHOD_NAME);
		if ( !NOP_METHOD_INITIALIZED){
			NOP_METHOD_INITIALIZED = true ;
			deployNOPHandler(NOP_JS_METHOD_NAME);
		}
		linkElement.setPropertyString("href", "javascript:" + NOP_JS_METHOD_NAME+"()");
	}
	
	
	/**
	 * @param menuLabel label untuk menu
	 * @param cssForIcon nama css untuk menampilkan icon
	 * 
	 **/
	public JQBaseMenuWidget(String menuLabel, String cssForIcon){
		this(menuLabel);
		setMenuIconCss(cssForIcon);
	}
	
	/**
	 * data untuk menu *if exist*
	 **/
	public MENUDATA getMenuData() {
		return menuData;
	}

	/**
	 * data untuk menu *if exist*
	 **/
	public void setMenuData(MENUDATA menuData) {
		this.menuData = menuData;
	}
	/**
	 * memasukan icon css. akan di tambahkan span ke dalam widget menu*sebelum link*, ini untuk di panggil di constructor
	 * @param menuCssName nama css untuk menu
	 **/
	protected void setMenuIconCss(String menuCssName){
		Element iconElement = DOM.createSpan();
		iconElement.setClassName(menuCssName);
		underlyingElement.insertBefore(iconElement, linkElement);
	}
	
	
	/**
	 * reference ke link dari menu
	 **/
	public Element getLinkElement() {
		return linkElement;
	}
	
	
	
	/**
	 * element yg paling luar dari menu
	 **/
	public Element getUnderlyingElement() {
		return underlyingElement;
	}
	/**
	 * set link kalau di klik menjadi none link
	 **/
	protected native void bindToNoneMenu (String menuId , String methodName)/*-{
		$wnd.$("#" +menuId ).click()
	
	}-*/;
	

	/**
	 * naruh Nop ke window
	 **/
	protected native void deployNOPHandler(String methodName)/*-{
		$wnd[methodName]=function(){}
	}-*/;
}
