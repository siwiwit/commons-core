package id.co.gpsc.common.client;

public class CommonClientControlConstant {

	/**
	 * 
	 **/
	public static final String TAG_KEY_SEL_REF ="selfReference";
	
	
	
	
	
	/**
	 * key untuk menaruh marker i18 dalam DOM element
	 **/
	public static final String I18_KEY_ON_DOM="i18key";
	
	
	/**
	 * Key untuk menaruh business name dari kontrol dalam DOM
	 **/
	public static final String CONTROL_BUSINESS_NAME_DOM_KEY="controlbusinessname";
	
	
	/**
	 * key DOM-> mandatory atau tidak elemen
	 **/
	public static final String CONTROL_MANDATORY_DOM_KEY="mandatory_flag";
	
	
	
	
	
	
	/**
	 * kode lokalisasi yang bisa di handle oleh aplikasi
	 **/
	public static String[] I18_ACCEPTED_LOCALIZATION_CODES ;
	
	
	
	
	/**
	 * enable on screen admin mode atau tidak. ini menentukan on screen editor aktiv atau tidak untuk <i>seluruh</> applikasi. berguna selama masa setup/development
	 **/
	public static boolean ENABLE_ON_SCREEN_ADMIN = true ; 
	
	
	/**
	 * key ini di taruh di url. kalau di taruh di url, maka info debuger akan di tampilkan. misal : nama class
	 */
	public static String SHOW_DEBUGER_THING_KEY="show_debuger_info"; 
	
	
}
