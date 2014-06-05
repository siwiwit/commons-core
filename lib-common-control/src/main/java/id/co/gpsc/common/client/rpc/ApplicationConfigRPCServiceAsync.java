package id.co.gpsc.common.client.rpc;



import java.util.List;

import id.co.gpsc.common.client.rpc.impl.ApplicationConfigRPCServiceAsyncImpl;
import id.co.gpsc.common.data.CommonGlobalVariableHolder;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.app.AppFormConfiguration;
import id.co.gpsc.common.data.app.I18FormMasterConfigurationNotDefinedException;
import id.co.gpsc.common.data.app.LabelDataWrapper;
import id.co.gpsc.common.data.app.NoConfigurationChangeException;
import id.co.gpsc.common.data.entity.FormElementConfiguration;
import id.co.gpsc.common.data.entity.I18Code;
import id.co.gpsc.common.data.entity.I18NTextGroup;
import id.co.gpsc.common.data.entity.I18Text;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;




public interface ApplicationConfigRPCServiceAsync {
	
	
	
	/**
	 * pls use this for direct access
	 **/
	public static class Util{
		private static ApplicationConfigRPCServiceAsync instance ;
		
		
		/**
		 * singleton instance
		 **/
		public static ApplicationConfigRPCServiceAsync getInstance() {
			if ( instance==null){
				instance= GWT.create(ApplicationConfigRPCServiceAsyncImpl.class);
//				CommonGlobalVariableHolder.getInstance().fixTargetUrl((ServiceDefTarget)instance);
			}
			return instance;
		}
		
	}

	
	/**
	 * membaca konfigurasi form
	 * @param formId id form yang i18 friendly
	 * @param localeCode localization code
	 * @param currentVersioOnClientCache versi data dalam client cache. ini untuk mengecek update atau tidak data di bandingkan dengan versi server
	 * @exception I18FormMasterConfigurationNotDefinedException ini kalau master konfigrasi i18 untuk form yang di minta tidak di temukan. jadinya form harus mengirimkan detil label-label yang i18 ready untuk di daftarkan ke server. ini untuk efisiensi transfer data
	 * @exception NoConfigurationChangeException ini kalau tidak ada perubahan versi data. jadinya tidak ada yang perlu di kirim kembali ke client 
	 **/
	void getFormConfiguration(String formId , String localeCode , Integer currentVersioOnClientCache ,
			AsyncCallback<AppFormConfiguration> callback);


	/**
	 * simpan perubahan pada label(form related label).
	 * @param localeCode kode lokalisasi. internalization
	 * @param key key text
	 * @param label label baru untuk konfigurasi yang di minta 
	 **/
	void saveLabel(String localeCode, String key, String label,
			AsyncCallback<Void> callback);


	/**
	 * labels, dalam semua localization
	 * @param key key dari locale
	 **/
	void getLabels(String key,
			AsyncCallback<LabelDataWrapper[]> callback);


	/**
	 * simpan sekalian labels. ini akan memaksa semua localization di save sekalian
	 * @param textKey key dari localized text
	 * @param labels labels. di kirim semua dan di kirim dengan blind, update semua
	 **/
	void saveLabels(String textKey, LabelDataWrapper[] labels,AsyncCallback<Void> callback);
	
	/**
	 * Get i18N text group Id
	 * @param groupId
	 * @param callback
	 */
	void getI18NGroupId(I18Text groupId, int pageSize, int rowPosition, AsyncCallback<PagedResultHolder<LabelDataWrapper>> callback);
	
	/**
	 * Save new label
	 * @param data
	 * @param callback
	 */
	void saveLabel(I18Text data, AsyncCallback<Void> callback);
	
	
	/**
	 * Save new label(dengan array)
	 * @param 	 texts array of text yang akan d i simpan
	 */
	public void saveLabels(I18Text[] texts, AsyncCallback<Void> callback)  ;
	
	/**
	 * Update label
	 * @param data
	 * @param callback
	 */
	void updateLabel(I18Text data, AsyncCallback<Void> callback);

	/**
	 * languages yang ada dalam database
	 **/
	void getAvaliableLanguages(AsyncCallback<List<I18Code>> callback);
	
	/**
	 * Menyimpan konfigurasi field control
	 * @param data
	 * @param callback
	 */
	void saveControlConfiguration(FormElementConfiguration data, AsyncCallback<Void> callback);
	
	/**
	 * Membaca konfigurasi field control
	 * @param formId
	 * @param elementId
	 * @param callback
	 */
	void readControlConfiguration(String formId, String elementId, AsyncCallback<FormElementConfiguration> callback);
	
	/**
	 * membaca data i18N text . ini untuk browse text box
	 * @param pageSize ukuran page per pembacaan 
	 * @param pagePosition posisi page yang hendak di baca
	 *  
	 **/
	public void getI18NTexts(int pageSize , int pagePosition , SigmaSimpleQueryFilter [] filters  , AsyncCallback<PagedResultHolder<I18Text>> callback );
	
	
	
	
	/**
	 * membaca group i18 N yang ada dalam database
	 **/
	public void getTextGroups (AsyncCallback<List<I18NTextGroup>> callback);
	
	
	/**
	 * membaca semua languages 
	 **/
	public void getAllLanguagesTextById(String i18Nkey , AsyncCallback< List<I18Text>> callback) ; 
}