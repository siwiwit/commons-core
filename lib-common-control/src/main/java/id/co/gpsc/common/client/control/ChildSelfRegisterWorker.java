package id.co.gpsc.common.client.control;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;



/**
 * worker self register type dari child ke parent
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public abstract class ChildSelfRegisterWorker<CHILD, PARENT> {
	
	/**
	 * instance yg di register ke parent
	 **/
	private CHILD widgetToRegister ;
	
	private boolean registered = false ; 
	

	private boolean readyToRegister =false ;
	
	
	private PARENT  currentParent ; 
	
	
	public void setWidgetToRegister(CHILD widgetToRegister) {
		this.widgetToRegister = widgetToRegister;
	}
	
	/**
	 * mencoba untuk meregister. return true kalau match. 
	 * @param w widget yang menjadi suspct parent /container node
	 **/
	@SuppressWarnings("unchecked")
	public boolean attempRegister (Widget w){
		if ( !checkIsExpectedParent(w))
			return false ; 
		actualRegisterWorker((PARENT) w);
		this.registered=true ;
		if ( !GWT.isProdMode()){
			GWT.log("found parent class : "   +w.getClass().getName());
		}
		return true ; 
	}
	
	
	
	/**
	 * dettach dari parent
	 **/
	public void detachFromParent (){
		detachFromParentWorker(currentParent);
	}
	
	/**
	 * worker untuk detach dari parent
	 **/
	protected abstract void detachFromParentWorker (PARENT  currentParent) ; 
	
	
	
	/**
	 * compute ready untuk di register atau ndak
	 **/
	public  boolean computeIsReadyToRegister(){
		readyToRegister = computeIsReadyToRegisterWorker(widgetToRegister);
		return readyToRegister;
	}
	
	/**
	 * ready untuk di register atau tidak
	 **/
	protected abstract boolean computeIsReadyToRegisterWorker (CHILD widget); 
	
	/**
	 * mengecek widget merupakan tipe parent yang di minta atau bukan
	 **/
	protected abstract boolean checkIsExpectedParent(Widget w ) ;
	
	
	/**
	 * actual worker untuk meregister child ke parent node
	 * @param parentNode
	 *  
	 **/
	protected abstract void actualRegisterWorker (PARENT parentNode) ; 
	
	/**
	 * widget yang perlu di register kan ke parent
	 **/
	public CHILD getWidgetToRegister() {
		return widgetToRegister;
	}
	
	
	/**
	 * flag widget sudah ke register atau belum ke parent
	 **/
	public boolean isRegistered() {
		return registered;
	}
	
	
	/**
	 * flag ini item sudah siap di register ke parent atau tidak. Pls panggil {@link #computeIsReadyToRegister()} sebelum mempergunakan ini
	 **/
	public boolean isReadyToRegister() {
		return readyToRegister;
	}
}
