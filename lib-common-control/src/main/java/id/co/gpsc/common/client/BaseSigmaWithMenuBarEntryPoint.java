package id.co.gpsc.common.client;

import id.co.gpsc.common.client.app.ApplicationMenu;
import id.co.gpsc.common.client.style.CommonResourceBundle;
import id.co.gpsc.common.client.util.ClientSecurityUtils;
import id.co.gpsc.common.data.app.security.ClientSecurityData;
import id.co.gpsc.jquery.client.menu.JQBaseMenuWidget;
import id.co.gpsc.jquery.client.menu.JQBranchMenuWidget;
import id.co.gpsc.jquery.client.menu.JQLeafMenuWidget;
import id.co.gpsc.jquery.client.menu.JQMenuBar;
import id.co.gpsc.jquery.client.menu.MenuClickHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;



/**
 * entry point dengan menubar
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public abstract class BaseSigmaWithMenuBarEntryPoint<KEY> extends BaseSigmaEntryPoint{
	
	
	
	
	private Image loadingImage ; 
	
	protected JQMenuBar<ApplicationMenu<KEY>> menubar  ; 
        
        /**
         * menu yang di load. semuanya di taruh dengan index string. data akan di string-kan
         */
        private HashMap<String , ApplicationMenu<KEY>> indexedLoadedMenus = new HashMap<String, ApplicationMenu<KEY>>();
	@Override
	protected void additionalOnLoadHandler() {
		super.additionalOnLoadHandler();
		requestCurrentUserCredentialData(new AsyncCallback<ClientSecurityData>() {
			@Override
			public void onSuccess(ClientSecurityData result) {
				GWT.log("uer credemtial result :" + result.getSecurityToken());
				ClientSecurityUtils.getInstance().setClientSecurityData(result);
				renderMenuBar(getMenuBarContainerId());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace(); 
			}
		});
		
	}
	
	
	/**
	 * method ini akan merender bar *ala jquery menubar*. menu akan di taruh pada HTML dengan ID tertentu
	 * @param menuContainerSpaceHolderId id dari html node di mana menu akan di taruh
	 **/
	protected void renderMenuBar (final String menuContainerSpaceHolderId){
		System.out.println("render menu di panel dng id:" +menuContainerSpaceHolderId );
		loadingImage = AbstractImagePrototype.create( CommonResourceBundle.getResources().iconLoadingWheel()).createImage();
		RootPanel.get(menuContainerSpaceHolderId).add(loadingImage);
		requestForMenus(ClientSecurityUtils.getInstance().getClientSecurityData(), new AsyncCallback<List<ApplicationMenu<KEY>>>(
				) {

					@Override
					public void onFailure(Throwable caught) {
						RootPanel.get(menuContainerSpaceHolderId).remove(loadingImage);
						RootPanel.get(menuContainerSpaceHolderId).add(new HTML("maaf, gagal load menu:" + caught.getMessage()));
						
					}

					@Override
					public void onSuccess(List<ApplicationMenu<KEY>> result) {
						renderMenuBarWorker(menuContainerSpaceHolderId , result);
						additionalDataRecieverHandler(result);
					}
		});
	}
	
	
	
	
	/**
	 * method ini di trigger pada saat data menu sampai di client. yang memerlukan akses ke menu perlu override method ini. yang memerlukan ini misal nya : 
	 * <ol>
	 * <li>Auto complete berisi menu list</li>
	 * 
	 * </ol>
	 **/
	protected void additionalDataRecieverHandler(List<ApplicationMenu<KEY>> menus) {
		
	}
	
	
	/**
	 * task tambahan setelah menu selesai di render. kalau dperlukan pekerjaan tambahan setelah menu selesai di render, masukan di sini
	 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
	 **/
	protected void afterMenuRenderedTask () {
		
	}
	
	/**
	 * ini ID pada host HTML di mana menubar harus di taruh
	 **/
	protected abstract  String getMenuBarContainerId ();
	
	/**
	 * worker untuk render menu
	 **/
	private void renderMenuBarWorker (final String menuContainerSpaceHolderId , List<ApplicationMenu<KEY>> menus) {
		if(menus==null||menus.isEmpty()){
			RootPanel.get(menuContainerSpaceHolderId).remove(loadingImage);
			return ; 
		}
		
		
		//Element ulSementara = DOM.createElement("ul");
		
		//SimplePanel pnlSementara = new SimplePanel();
		//pnlSementara.setVisible(false);
		//RootPanel.get().getElement().appendChild(ulSementara);
		//ulSementara.getStyle().setDisplay(Display.NONE);
		try{
			//#1 loop 1 buar menu  parent
			Map<KEY, JQBranchMenuWidget<ApplicationMenu<KEY>>> indexedParents = new HashMap<KEY, JQBranchMenuWidget<ApplicationMenu<KEY>>>();
			ArrayList<JQBaseMenuWidget< ApplicationMenu<KEY>>> allChild = new ArrayList<JQBaseMenuWidget<ApplicationMenu<KEY>>>();
			final ArrayList<JQBaseMenuWidget< ApplicationMenu<KEY>>> level0 = new ArrayList<JQBaseMenuWidget<ApplicationMenu<KEY>>>();
			
			for ( ApplicationMenu<KEY> scn : menus){
                            indexedLoadedMenus.put(scn.getMenuId() +"", scn);
				JQBaseMenuWidget< ApplicationMenu<KEY>> amenu =null; 
				if (! scn.isHaveChildren()){
					JQLeafMenuWidget<ApplicationMenu<KEY>> myMenu = new JQLeafMenuWidget<ApplicationMenu<KEY>>(scn.getLabel(), getMenuClickHandler());
					myMenu.setMenuData(scn);
					amenu = myMenu ; 
				}else{
					JQBranchMenuWidget< ApplicationMenu<KEY>> br = new JQBranchMenuWidget<ApplicationMenu<KEY>>(scn.getLabel());
					br.setMenuData(scn);
					amenu=br ;
					indexedParents.put(scn.getMenuId(), br);
					scn.setMiscObject(new ArrayList<ApplicationMenu<KEY>>());
				}
				
				if ( scn.getParentMenuId()==null)
					level0.add(amenu);
				else
					allChild.add(amenu);
				//ulSementara.appendChild(amenu.getUnderlyingElement());
				// taruh dulu di tmp sementara
			}
			//#2 loop 2 menyusun tree, untuk menu
			for ( JQBaseMenuWidget< ApplicationMenu<KEY>> scn : allChild){
				if ( !indexedParents.containsKey(scn.getMenuData().getParentMenuId() ))
					continue ; 
				JQBranchMenuWidget< ApplicationMenu<KEY>> bapak = indexedParents.get(scn.getMenuData().getParentMenuId() );
				
				bapak.appendChildMenu(scn);
				scn.getMenuData().setParentMenu(bapak.getMenuData());
				((ArrayList<ApplicationMenu<KEY>>)bapak.getMenuData().getMiscObject()).add(scn.getMenuData());
			}
			//#3  dari arraylist, musti di tranformasikan ke array, untuk variable miscObject, dan di nullkan kembali
			for ( JQBranchMenuWidget<ApplicationMenu<KEY> >scn : indexedParents.values()){
				ArrayList<ApplicationMenu<KEY>> swap = (ArrayList<ApplicationMenu<KEY>>)scn.getMenuData().getMiscObject();
				if (! swap.isEmpty()){
					scn.getMenuData().setChildren(new ApplicationMenu[swap.size()]);
					swap.toArray(scn.getMenuData().getChildren());
				}
				scn.getMenuData().setMiscObject(null);
			}
			// OK sudah siap. tinggal buat menu bar
			menubar = new JQMenuBar<ApplicationMenu<KEY>>();
			menubar.appendMenus(level0);
			
			
			RootPanel.get(menuContainerSpaceHolderId).add(menubar);
			new Timer() {
				
				@Override
				public void run() {
					
					RootPanel.get(menuContainerSpaceHolderId).remove(loadingImage);
					RootPanel.get(menuContainerSpaceHolderId).add(menubar);
					menubar.renderMenu(new Command() {
						@Override
						public void execute() {
							afterMenuRenderedTask();
						}
					});
				}
			}.schedule(10);
		}finally{
			
		}
		
		
	}
	
	
	
	/**
	 * menu click handler. pls provide bagaimana anda menangani click menu
	 **/
	protected abstract MenuClickHandler<ApplicationMenu<KEY>> getMenuClickHandler(); 
	
	
	/**
	 * request client security data. mohon di catat, sebaiknya, kalau mekanisme grab user credential adalah dengan RPC, jangan membuat RPC return {@link ClientSecurityData}, buat lah adapter class pada package shared untuk keperluan ini
	 * @param userSecurityDataCallback callback saat data di terima 
	 **/
	protected abstract void requestCurrentUserCredentialData (AsyncCallback<ClientSecurityData> userSecurityDataCallback) ; 
	
	/**
	 * worker untuk merequest menu ke server. anda harus menyediakan request menu ke server. hati-hati, karena RPC tidak bisa melewatkan generic dalam class nya, jadi anda perlu membuat RPC spesifik untuk keperluan anda
	 * untuk anda pertimbangkan. data tidak perlu di susun sebagai tree. data di request dengan posisi flat(bukan tree), cukup di order saja
	 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
	 * @param securityData data security user. tipikal nya anda hanya perlu mempergunakan : {@link ClientSecurityData#getSecurityToken()} untuk request ke server. tapi ini semua terserah anda 
	 **/
	protected abstract void requestForMenus (ClientSecurityData securityData ,  AsyncCallback<List<ApplicationMenu<KEY>>> callback) ; 
	
	
	
	public JQMenuBar<ApplicationMenu<KEY>> getMenubar() {
		return menubar;
	}
        
        /**
         * akses menu dengan 
         */
        public ApplicationMenu<KEY> getMenuById(String menuId){
            return indexedLoadedMenus.get(menuId);
        }
}
