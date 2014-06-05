package id.co.gpsc.common.client.security;

import id.co.gpsc.common.client.app.ApplicationMenu;
import id.co.gpsc.jquery.client.menu.JQBranchMenuWidget;
import id.co.gpsc.jquery.client.menu.JQLeafMenuWidget;
import id.co.gpsc.jquery.client.menu.MenuClickHandler;



/**
 * branch menu dedicated dengan menerima {@link ApplicationMenu}
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class ExtendedBranchMenu<KEY> extends JQBranchMenuWidget<ApplicationMenu<KEY>>{

	public ExtendedBranchMenu(final MenuClickHandler<ApplicationMenu<KEY>> clickHandler,  ApplicationMenu<KEY> menuData) {
		super(menuData.getLabel());
		this.setMenuData(menuData);
		if ( menuData.getChildren()==null||menuData.getChildren().length==0)
			return ; 
		for ( ApplicationMenu<KEY> mnu : menuData.getChildren()){
			if(mnu.isHaveChildren()){
				ExtendedBranchMenu<KEY> anotherMenu = new ExtendedBranchMenu<KEY>(clickHandler, mnu);
				appendChildMenu(anotherMenu);
			}else{
				JQLeafMenuWidget<ApplicationMenu<KEY>> daun = new JQLeafMenuWidget<ApplicationMenu<KEY>>(mnu.getLabel());
				daun.assignClickHandler(clickHandler);
				
				appendChildMenu(daun);
			}
		}
	}

}
