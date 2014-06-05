package id.co.gpsc.common.client.security.menu;

import id.co.gpsc.common.client.security.rpc.ApplicationUserRPCServiceAsync;
import id.co.gpsc.common.security.domain.Signon;
import id.co.gpsc.common.security.menu.ApplicationMenuSecurity;
import id.co.gpsc.jquery.client.menu.MenuClickHandler;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Base yang men-handle tentang hal-hal yang berhubungan dg menu
 * @author I Gede Mahendra
 * @since Apr 12, 2013, 4:05:17 PM
 * @version $Id
 */
public abstract class BaseApplicationMenu<PARENT extends IContainerMenu<?>,CHILD extends ILinkMenu> extends ComplexPanel implements  IRootMenuContainer{
	
	private Map<BigInteger, ApplicationMenuSecurity> indexedLoadedMenus = new HashMap<BigInteger, ApplicationMenuSecurity>();
	
	/*public BaseApplicationMenu() {
		setElement(generateMenuUnderlyingElement());
	}*/
	
	
	private final  MenuClickHandler<ApplicationMenuSecurity> menuClickHandler = generateMenuClickHandler();
		
	public BaseApplicationMenu(Signon data){
		setElement(generateMenuUnderlyingElement());
		getApplicationMenu(data);		
	}	
	
	/**
	 * ini base element untuk menu. biasanya ini ul yang palingluar dari menu. Anda juga wajib mengeset css dari element ini
	 **/
	protected abstract Element generateMenuUnderlyingElement();
	
	
	/**
	 * mengosongkan menu
	 **/
	public abstract void clearMenu() ;
	
	
	private Map<BigInteger,	 ApplicationMenuSecurity> cachedMenuData = new HashMap<BigInteger,	 ApplicationMenuSecurity>();
		
	/**
	 * Get list menu yg available sesuai dg sigon data usernya
	 * @param data
	 * @return List of ApplicationSecurity
	 */
	protected void getApplicationMenu(Signon data){
		clearMenu();
		cachedMenuData.clear();
		ApplicationUserRPCServiceAsync.Util.getInstance().getApplicationMenu(data, new AsyncCallback<List<ApplicationMenuSecurity>>() {
			
			@Override
			public void onSuccess(List<ApplicationMenuSecurity> data) {				
				//RootPanel.get(getIdContainerMenu()).add(renderMenu(data));
				//FIXME : mauskan ke dalam #cachedMenuData
				renderRootMenu(data);
				readMenuFromList(data);
			}
			
			@Override
			public void onFailure(Throwable ex) {
				Window.alert("Gagal load menu");
				GWT.log(ex.getMessage());
			}
		});
	}	
	
	/**
	 * Generate Menu with link anchor
	 * @param menuData
	 * @return
	 */
	protected abstract CHILD generateLinkMenu(ApplicationMenuSecurity menuData);
	
	/**
	 * Generate container menu
	 * @param menuData
	 * @return
	 */
	protected abstract PARENT generateContainerMenu(ApplicationMenuSecurity menuData);
	
	/**
	 * Membuat UL pada root level menu tanpa menu
	 * @param menus
	 * @return
	 */
	protected abstract IRootMenuContainer renderRootMenu(List<ApplicationMenuSecurity> menus);
	
	/**
	 * Render menu
	 * @param menus
	 * @return
	 */
	/*private Widget renderMenu(List<ApplicationMenuSecurity> menus){
		ULWidget rootUL = new ULWidget();
		rootUL.getElement().addClassName("topnav");
		readMenuFromList(menus, rootUL);
		return rootUL;
	}*/
	
	/**
	 * Menyusun ApplicationMenuSecurity menjadi format HTML yg bersesuaian dg desain menu pada HTML utama
	 * @param result - List of ApplicationMenuSecurity
	 * @param rootUL - Root Widget UL
	 */
	@SuppressWarnings("unchecked")
	private void readMenuFromList(List<ApplicationMenuSecurity> result){
		Map<BigInteger, IContainerMenu<?>> indexParent = new HashMap<BigInteger, IContainerMenu<?>>();
		List<IBaseMenu> allChild = new ArrayList<IBaseMenu>();
		
		final List<IBaseMenu> level0 = new ArrayList<IBaseMenu>();
		
		//loop 1 buat menu  parent
		for (ApplicationMenuSecurity scn : result) {
			indexedLoadedMenus.put(scn.getMenuId(), scn);
			//LIWidget amenu = null;
			IBaseMenu amenu = null;
			if(!scn.isHaveChildren()){ //jika menu tidak punya child-nya	
				CHILD swap = generateLinkMenu(scn);
				amenu = swap ;
				swap.setMenuClickHandler(menuClickHandler);
				
			}else{ //jika menu punya child
				IContainerMenu<?> br = generateContainerMenu(scn);				
				amenu = br;				
				indexParent.put(scn.getMenuId(), br);						
			}					
			
			if(scn.getParentId() == null){
				level0.add(amenu);
			}else{
				allChild.add(amenu);
			}
		}	
		
		//loop 2 menyusun menu tree
		for (IBaseMenu scn : allChild) {			
			if(!indexParent.containsKey(scn.getMenuData().getParentId())){
				continue;
			}			
			IContainerMenu<IBaseMenu> bapak = (IContainerMenu<IBaseMenu>) indexParent.get(scn.getMenuData().getParentId());
			bapak.add(scn);
		}		
		this.addWidgets(level0);
	}	
	
	@Override
	public void add(Widget w) {
	    add(w, getElement());
	}	
	
	@Override
	public void addWidgets(List<?> childMenus) {
		for (Object obj : childMenus) {
			Widget widget =(Widget) obj;
			add(widget);
		}
		
	}
	
	public ApplicationMenuSecurity getMenuDataById(BigInteger menuId) {
		
		return cachedMenuData.get(menuId) ; 
	}
	
	
	
	
	/**
	 * worker untuk membuat handler click menu. default akan menaruh ke history token dengan key : IBaseMenu.MENU_ID_LINK_TOKEN_PREFIX + id menu. kalau perlu diganti, override method ini
	 **/
	protected  MenuClickHandler<ApplicationMenuSecurity> generateMenuClickHandler() {
		return new MenuClickHandler<ApplicationMenuSecurity>() {
			@Override
			public void execute(ApplicationMenuSecurity menuData) {
				History.newItem(IBaseMenu.MENU_ID_LINK_TOKEN_PREFIX + menuData.getMenuId());
				
			}
		};
	}
		
		
}