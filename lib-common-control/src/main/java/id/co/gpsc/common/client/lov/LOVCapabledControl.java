package id.co.gpsc.common.client.lov;

import id.co.gpsc.common.data.lov.CommonLOV;
import id.co.gpsc.common.data.lov.CommonLOVHeader;
import id.co.gpsc.common.data.lov.LOVSource;



/**
 * control yang bisa menerima LOV(list of value kecenderungannya dari ->lendor_app.us_extendedparameter atau table lainnya )
 **/
public interface LOVCapabledControl  extends LOVChangeHandler {
	/**
	 * ID parameter 
	 **/
	public String getParameterId(); 
	
	/**
	 * id parameter 
	 **/
	public void setParameterId(String parameterId);
	
	
	
	/**
	 * sumber dari LOV mana
	 **/
	public LOVSource getLOVSource ();
	
	
	
	/**
	 * assign LOV ke dalam kontrol
	 **/
	public void setLOVData(CommonLOVHeader lovData);
	
	
	
	
	/**
	 * membaca data LOV dari data ini
	 **/
	public CommonLOVHeader getLOVData() ; 
	
	
	
	
	/**
	 * assign LOV type source untuk kontrol ini
	 **/
	public void setLovSource(LOVSource lovSource) ;
	
	
	
	/**
	 * register LOV pada saat lov di attach ke container
	 **/
	public boolean isLOVRegisteredOnAttach();
	
	
	
	/**
	 * lov di register on attach atau tidak
	 * @param registerOnAttach 
	 **/
	public void setLOVRegisteredOnAttach(boolean registerOnAttach);
	
	
	
	/**
	 * register otomatis. prefer tidak mempergunakan ini. ini akan memproses scan ke atas sampai nemu.
	 * pls pergunakan {@link LovPoolPanel#registerLOVCapableControl(LOVCapabledControl)} 
	 **/
	public void registerLOVToContainer () ; 
	
	
	
	
	
	
	/**
	 * memuali animasi loading content
	 */
	public void startLoadingAnimation() ; 
	
	
	
	/**
	 * stop loading animation 
	 */
	public void stopLoadingAnimation() ; 

}
