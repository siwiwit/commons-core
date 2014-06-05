package id.co.gpsc.common.client.security;


import id.co.gpsc.common.client.control.MainPanelStackControl;
import id.co.gpsc.common.client.control.ViewScreenMode;
import id.co.gpsc.common.security.menu.ApplicationMenuSecurity;
import id.co.gpsc.jquery.client.menu.MenuClickHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

/**
 * group of menu generator. 1 group = 1 splitter. jadinya kalau berencana menempatkan data dalam splitter ke pisah pergunakan menu yang berbeda
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public abstract class MenuHandlerPanelGeneratorGroup {
	
	
	
	
	private IMenuHandlerPanelGenerator<?>[] generators ; 
	
	
	private boolean generatorInstantiated = false ; 
	
	
	
	
	/**
	 * command kalau sudah done render menu
	 **/
	private Command afterMenuResponseDoneHandler ;
	
	
	/**
	 * chain lanjuatan dari generator. kalau ndak nemu, setelah ini panggil yang mana
	 **/
	private MenuHandlerPanelGeneratorGroup nextChain ; 
	
	
	
	
	
	private MenuClickHandler<ApplicationMenuSecurity>  currentHandler = new MenuClickHandler<ApplicationMenuSecurity>() {
		
		@SuppressWarnings("unchecked")
		@Override
		public void execute(ApplicationMenuSecurity data) {
			if ( generators!= null ){
				 for ( @SuppressWarnings("rawtypes") IMenuHandlerPanelGenerator scn : generators ){
					 if (   data.getMenuCode().equals(  scn.getMenuCode())){
						 
						 //FIXME : cek , kalau kode sama, tidak perlu melakukan apapun
						 //FIXME : put loading image here
						 MainPanelStackControl.getInstance().clearStack() ; 
						 
						 Widget w =  scn.instantiateWidget() ; 
						 MainPanelStackControl.getInstance().putPanel(w, ViewScreenMode.normal); 
						 if ( afterMenuResponseDoneHandler!= null)
							 afterMenuResponseDoneHandler.execute() ;
						 
						 scn.restoreWidgetToDefaultState(w); 
						 return ; 
					 }
				 }
			}
			// ndak ada yang cocok, next kalau begitu
			if ( nextChain!= null){
				nextChain.handleMenuRequest(data);
			}
			 
		}
	};
	
	
	/**
	 * generators group. Apa saja yang tersedia dalam 1 group
	 **/
	protected abstract IMenuHandlerPanelGenerator<?>[] getGenerators () ;
	
	
	
	
	/**
	 * ini merupakan worker untuk merespon pada menu. kalau misalnya ada yang cocok maka panel di render dan commandOnMenuRenderDone. kalau misalnya tidak ada yang cocok, maka di teruskan ke next chain
	 **/
	public void handleMenuRequest (final ApplicationMenuSecurity menuData ) {
		 if ( generatorInstantiated){
			 currentHandler.execute(menuData); 
			 return ; 
		 }
		 GWT.runAsync(new RunAsyncCallback() {
			@Override
			public void onSuccess() {
				generators = getGenerators(); 
				generatorInstantiated = true ; 
				currentHandler.execute(menuData); 
			}
			
			@Override
			public void onFailure(Throwable exception) {
				Window.alert("gagal render aktivasi menu : " + menuData.toString() + ",error :" + exception.getMessage() ); 
				
			}
		}); 
		
		
	}
	
	/**
	 * command kalau sudah done render menu
	 **/
	public Command getAfterMenuResponseDoneHandler() {
		return afterMenuResponseDoneHandler;
	}
	/**
	 * command kalau sudah done render menu
	 **/
	public void setAfterMenuResponseDoneHandler(
			Command afterMenuResponseDoneHandler) {
		this.afterMenuResponseDoneHandler = afterMenuResponseDoneHandler;
	}
	
	

	/**
	 * chain lanjuatan dari generator. kalau ndak nemu, setelah ini panggil yang mana
	 **/
	public MenuHandlerPanelGeneratorGroup getNextChain() {
		return nextChain;
	}
	

	/**
	 * chain lanjuatan dari generator. kalau ndak nemu, setelah ini panggil yang mana
	 **/
	public void setNextChain(MenuHandlerPanelGeneratorGroup nextChain) {
		this.nextChain = nextChain;
	}


}
