package id.co.gpsc.common.client.security;

import com.google.gwt.user.client.ui.Widget;

/**
 * interface untuk generate widget
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public interface IMenuHandlerPanelGenerator<WIDGET extends Widget> {
	
	
	
	/**
	 * ID dari menu yang di populate oleh panel ini
	 **/
	public String getMenuCode  () ; 
	
	
	/**
	 * method ini untuk generate widget
	 **/
	public WIDGET instantiateWidget () ; 
	
	
	
	
	/**
	 * ini memindah state dari widget ke state normal. ini di pergunakan kalau menu yang sama di click beberapa kali
	 **/
	public void restoreWidgetToDefaultState (WIDGET widgetToRestore) ; 
	
	
	/**
	 * label deskripsi. ini dipergunakan untuk render data. jadinya ada keterangan , ini menu untuk apa. ini akan berguna kalau data di automated insert ke dalam database dengan definisi dari client
	 **/
	public String getDescriptionLabel () ; 

}
