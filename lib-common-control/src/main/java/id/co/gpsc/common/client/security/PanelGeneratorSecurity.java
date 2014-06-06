package id.co.gpsc.common.client.security;

import id.co.gpsc.common.client.rpc.SimpleAsyncCallback;
import id.co.gpsc.common.client.security.function.ApplicationMenuEditorPanel;
import id.co.gpsc.common.client.security.group.GroupListPanel;
import id.co.gpsc.common.client.security.rpc.ApplicationRPCServiceAsync;
import id.co.gpsc.common.client.security.user.UserListPanel;
import id.co.gpsc.common.security.dto.ApplicationDTO;

import com.google.gwt.user.client.Window;

/**
 * menu security
 * @author <a href="mailto:agus.adiparth@gmail.com">Dode</a>
 **/
public   class PanelGeneratorSecurity extends MenuHandlerPanelGeneratorGroup {

	@Override
	protected IMenuHandlerPanelGenerator<?>[] getGenerators() {
		IMenuHandlerPanelGenerator<?>[] retval = new IMenuHandlerPanelGenerator<?>[]{
				new IMenuHandlerPanelGenerator<GroupListPanel>(){
					@Override
					public String getMenuCode() {
						return "SEC_GROUP_LIST";
					}
					@Override
					public GroupListPanel instantiateWidget() {
						return new GroupListPanel(){
							@Override
							public void getDataGroup() {
								
								
								
								getCurrentUserSecurityData(new SimpleAsyncCallback<ApplicationDTO>() {
									@Override
									protected void customFailurehandler(
											Throwable caught) {
										Window.alert("gagal membaca data security user. error di laporkan : " + caught.getMessage());
									}
									@Override
									public void onSuccess(ApplicationDTO securityData) {
										setApplicationDTO(securityData); 
										getDataGroupParent();
									}
								}); 
								
								
							}
							
							
							/**
							 * ini jembatan untuk membaca data group ,versi parent, karena method getDataGroup di override
							 **/
							protected void getDataGroupParent () {
								super.getDataGroup();
							}
							
						};
						
						
						
						
					}
					@Override
					public void restoreWidgetToDefaultState(
							GroupListPanel widgetToRestore) {
						
					}
					@Override
					public String getDescriptionLabel() {
						return "Daftar group user";
					}
				},
				
				new IMenuHandlerPanelGenerator<UserListPanel>(){
					@Override
					public String getMenuCode() {
						return "SEC_USER_LIST";
					}
					@Override
					public UserListPanel instantiateWidget() {
						return new UserListPanel();
					}
					@Override
					public void restoreWidgetToDefaultState(
							UserListPanel widgetToRestore) {
						
					}
					@Override
					public String getDescriptionLabel() {
						
						return "Panel User List - Daftar user + menu editor user";
					}
				},
				
				new IMenuHandlerPanelGenerator<ApplicationMenuEditorPanel>(){
					@Override
					public String getMenuCode() {
						return "SEC_FUNCTION_EDITOR";
					}
					@Override
					public ApplicationMenuEditorPanel instantiateWidget() {
						return new ApplicationMenuEditorPanel();
					}
					@Override
					public void restoreWidgetToDefaultState(
							ApplicationMenuEditorPanel widgetToRestore) {
						
					}
					@Override
					public String getDescriptionLabel() {
						
						return "Panel Application Menu - Daftar function";
					}
				}
		};
		return retval;
	}
	
	
	private ApplicationDTO applicationDTO ; 
	
	/**
	 * aktivitas ini untuk membaca current security data. di bikin async, kalau di perlukan akses ke RPC, taruh di sini
	 **/
	protected    void getCurrentUserSecurityData (final SimpleAsyncCallback<ApplicationDTO> callback)  {
		if ( applicationDTO!= null){
			callback.onSuccess(applicationDTO); 
			return ; 
		}
		
		
		// kirim request ke server
		ApplicationRPCServiceAsync.Util.getInstance().getCurrentAppApplicationInfo(new SimpleAsyncCallback<ApplicationDTO>() {
			@Override
			public void onSuccess(ApplicationDTO innerResult) {
				
				applicationDTO = innerResult ; 
				callback.onSuccess(innerResult); 
			}
			
			@Override
			protected void customFailurehandler(Throwable caught) {
				callback.onFailure(caught); 
				
			}
		}); 
		
		
		
	}

}
