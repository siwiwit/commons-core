package id.co.gpsc.jquery.client.menu;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;


/**
 * widget menu yang punya child
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class JQBranchMenuWidget<MENUDATA> extends JQBaseMenuWidget<MENUDATA>{

	
	private Element ulElement ; 
	/**
	 * @param menuLabel label untuk menu
	 **/
	public JQBranchMenuWidget(String menuLabel) {
		super(menuLabel);
		configureChildContainer();
	}
	
	
	/**
	 * @param menuLabel label untuk menu
	 * @param cssForIcon nama css untuk menampilkan icon
	 * 
	 **/
	public JQBranchMenuWidget(String menuLabel, String cssForIcon) {
		super(menuLabel, cssForIcon);
		configureChildContainer();
	}
	
	
	/**
	 * append sub menu ke dalam current node
	 * @param childMenu child menu untuk di append ke menu
	 **/
	public void appendChildMenu (JQBaseMenuWidget<MENUDATA> childMenu){
		ulElement.appendChild(childMenu.getUnderlyingElement());
	}
	
	
	/**
	 * worker untuk config
	 **/
	private void configureChildContainer (){
		if (ulElement!=null)
			return ;
		ulElement = DOM.createElement("ul");
		ulElement.setId(DOM.createUniqueId());
		underlyingElement.appendChild(ulElement);
	}
	
	
	 

}
