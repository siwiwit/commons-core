package id.co.gpsc.common.client.control.i18;

import com.google.gwt.user.client.rpc.AsyncCallback;

import id.co.gpsc.common.client.cache.ClientCacheDataExpiredException;
import id.co.gpsc.common.data.app.AppFormConfiguration;



/**
 * 
 * interface cache manager resource configuration 
 *  @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public interface AppPanelResourceCacheManager {
	
	
	public static class Util {
		private static  AppPanelResourceCacheManager instance ; 
		
		
		
		
		
		/**
		 * instance . set pada saat start aplikasi
		 **/
		public static void setInstance(AppPanelResourceCacheManager instance) {
			Util.instance = instance;
		}

		
		
		/**
		 * current instance. asumsi ini sudah di set pada saat startup
		 **/
		public static AppPanelResourceCacheManager getInstance() {
			
			return instance;
		}
	}
	
	
	/**
	 * durasi cache expired(dalam menit)
	 **/
	public static int CACHE_EXPIRY_DURATION_IN_SECONDS =7200/*60*120-> 2 jam di cache*/; 
	
	
	
	/**
	 * submit form configuration ke dalam cache(tipikal nya : Local Storage) 
	 * @param formConfiguration form configuration untuk di cache
	 **/
	public void submitToCache (AppFormConfiguration formConfiguration);
	
	
	
	/**
	 * facade. request form configuration by id + locale.kalau misal nya ada dalam cache, kembalikan langsung object dalam cache. kalau tidak maka request ke server
	 * @param panelId id dari panel yang akan di baca konfigurasi nya
	 * @param localizationCode kode localization 
	 **/
	public void requestForPanelConfiguration (String panelId , String localizationCode ,  AsyncCallback<AppFormConfiguration> callback) ; 
	
	
	/**
	 * membaca application configuration dari client cache(Local storage atau session storage)
	 * @param panelId id dari panel
	 * @param localizationCode localication code yang di pergunakan 
	 * @return form configuration 
	 * @exception ClientCacheDataExpiredException kalau client cache sudah expired. dengan begini app musti request ulang kembali ke server
	 **/
	public AppFormConfiguration getCachedConfiguration (String panelId , String localizationCode) throws ClientCacheDataExpiredException;
	
	
	
	/**
	 * generate key
	 * @param panelId id panel
	 * @param localeCode localization code
	 **/
	public String generateKey (String panelId , String localeCode ) ; 
	
	

}
