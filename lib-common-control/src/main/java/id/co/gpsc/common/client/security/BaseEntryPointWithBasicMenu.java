package id.co.gpsc.common.client.security;

import id.co.gpsc.common.client.BaseSimpleEntryPoint;
import id.co.gpsc.common.client.common.CommonRunAsynCallback;
import id.co.gpsc.common.client.rpc.SimpleAsyncCallback;
import id.co.gpsc.common.client.security.rpc.ApplicationUserRPCServiceAsync;
import id.co.gpsc.common.security.domain.Signon;
import id.co.gpsc.common.security.dto.UserDetailDTO;
import id.co.gpsc.common.security.menu.ApplicationMenuSecurity;
import id.co.gpsc.jquery.client.menu.MenuClickHandler;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Window;

/**
 * base class entry point untuk entry point dengan menu
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public abstract class BaseEntryPointWithBasicMenu extends BaseSimpleEntryPoint{

	/**
	 * cache menu, di index dengan id dari menu(big integer)
	 **/
	private Map<BigInteger,	 ApplicationMenuSecurity> cachedMenuData = new HashMap<BigInteger,	 ApplicationMenuSecurity>();
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * pekerjaan di sini : 
	 * <ol>
	 * <li>request menu via RPC</li>
	 * <li>render dom untuk menu</li>
	 * <li>render menu</li>
	 * </ol>
	 **/
	protected void requestMenuData () {
		
		Signon parameter = new Signon();
		parameter.setUserId(this.getUserDetailData().getUserId());
		parameter.setApplicationId(this.getUserDetailData().getApplicationId());
		parameter.setUuid(this.getUserDetailData().getUuid());
		//cachedMenuGenerator = getMenuGenerators() ; 
		
		registerMenuGenerators();
		
		final MenuClickHandler<ApplicationMenuSecurity> clickHandler = new MenuClickHandler<ApplicationMenuSecurity>() {
			
			@Override
			public void execute(ApplicationMenuSecurity data) {
				// karena berantai makai kirim saja ke chain 1
				cachedMenuGenerator.get(0).handleMenuRequest(data); 
				//Window.alert("Hore, berhasil");
			}
		};
		
		
		ApplicationUserRPCServiceAsync.Util.getInstance().getApplicationMenu(parameter, new SimpleAsyncCallback<List<ApplicationMenuSecurity>>() {
			@Override
			protected void customFailurehandler(Throwable caught) {
				//FIXME : masukan message yang lebih bersahabat
				Window.alert("gagal membaca data user login. error  : " + caught.getMessage() ); 
				
			}
			@Override
			public void onSuccess(final List<ApplicationMenuSecurity> menuData) {
				renderMenuDomNode(menuData, clickHandler); 
				taskAfterRenderMenuDOMDone();
			}
			
		}) ; 
	}
	
	
	
	
	
	
	/**
	 * ini bertugas membangun DOM node yang merepresentasikan tree
	 **/
	protected abstract void renderMenuDomNode (List<ApplicationMenuSecurity> menuData, MenuClickHandler<ApplicationMenuSecurity > menuClickHandler) ; 
	
	
	
	/**
	 * pekerjaan setelah taruh dom selesai
	 **/
	protected abstract void taskAfterRenderMenuDOMDone () ;
	
	
	/**
	 * data user detail data. di load pada saat awal load
	 **/
	public UserDetailDTO getUserDetailData() {
		return userDetailData;
	}
	
	
	/**
	 * register menu handlers
	 **/
	protected void registerMenuGenerators (){
		
		 GWT.runAsync(new CommonRunAsynCallback() {
			
			@Override
			public void onSuccess() {
				registerMenuGenerator(new id.co.gpsc.common.client.CommonControlMenuHandler());
				
			}
		});
		
	}
	
	
	
	/**
	 * method ini di pergunakan untuk register method ke dalam chain. kalau anda punya generator baru, masukan di sini
	 **/
	protected void registerMenuGenerator ( MenuHandlerPanelGeneratorGroup  menuHandler){
		
		
		
		if ( !cachedMenuGenerator.isEmpty()){
			cachedMenuGenerator.get(cachedMenuGenerator.size()-1).setNextChain(menuHandler);
		}
		
		
		
		if ( !GWT.isProdMode()){
			menuHandler.setNextChain(new MenuHandlerPanelGeneratorGroup() {
				@Override
				public void handleMenuRequest(ApplicationMenuSecurity menuData) {
				
					String message ="handler utnuk code : " + menuData.getMenuCode() + ", belum tersedia.";  
					GWT.log(message);
					Window.alert(message); 
					return ; 
				}
				@Override
				protected IMenuHandlerPanelGenerator<?>[] getGenerators() {
					// karena ini lates, nothing TODO then
					return null;
				}
			});
		}
		
		
		cachedMenuGenerator.add(menuHandler);
	
	}
}
