package id.co.gpsc.common.client.form;

import id.co.gpsc.common.control.ResourceBundleConfigurableControl;

import com.google.gwt.user.client.ui.HasText;



/**
 * expanded ResourceBundleConfigurableLabel
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public interface SelfRegisterEnableResourceBundleConfigurableLabel extends ResourceBundleConfigurableControl, HasText{

	/**
	 * scan otomatis {@link ResourceBundleEnableContainer}. proses ini bisa panjang. pls pergunakan dengan hati hati. untuk masalah performence pls pergunakan method
	 **/
	public void registerToContainer () ; 
	/**
	 * register ke contaoiner(manual)
	 **/
	public void registerToContainer (ResourceBundleEnableContainer targetContainer) ;
	
	/**
	 * boolean flag. widget di register ke parent pada saat node di attach atau tidak. demi optimasi pls register manual saja. register on attach bisa membuat operasi di client menjadi lamban. Default = false
	 * @param registerOnAttach true -> widget di register on attach
	 **/
	public void  setRegisterOnAttach(boolean registerOnAttach); 
	/**
	 * getter. register on attach atau tidak
	 **/
	public boolean isRegisterOnAttach();
	
	
}
