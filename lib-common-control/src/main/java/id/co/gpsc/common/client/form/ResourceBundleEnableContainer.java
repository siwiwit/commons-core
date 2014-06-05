package id.co.gpsc.common.client.form;





import id.co.gpsc.common.control.ResourceBundleConfigurableControl;

import java.util.Collection;
import java.util.Set;

import com.google.gwt.user.client.Command;



/**
 * container yang enable resource bundle jadinya container ini yang memerintahkan configure /timpa text dengan info dari db/file konfigurasi
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * 
 * @version $Id
 * @since 6 August 2012
 * @see id.co.gpsc.common.client.CommonClientControlConstant#TAG_KEY_SEL_REF untuk cara mencari reference ke object dalam dom
 * @see id.co.gpsc.common.client.widget.BaseSigmaComposite#initWidget
 **/
public interface ResourceBundleEnableContainer {
	
	
	
	/**
	 * register widget ke dalam resource bundle
	 * <strong>ini hanya boleh di panggil kalau semua node child sudah siap di daftarkan</strong>
	 * 
	 **/
	public void registerResourceBundleEnabledNode (ResourceBundleConfigurableControl widget) ; 
	
	
	
	/**
	 * versi bulk
	 **/
	public void registerResourceBundleEnabledNodes (ResourceBundleConfigurableControl[] widgets) ;
	
	/**
	 * versi bulk
	 **/
	public void registerResourceBundleEnabledNodes (Collection<ResourceBundleConfigurableControl> widgets) ;
	
	/**
	 * unreg dari parent. remove dari item yang musti di customize label nya
	 **/
	public void unregisterResourceBundleEnabledNode (ResourceBundleConfigurableControl widget) ;
	
	

	
	
	
	/**
	 * set. jadinya ke internalization di distinct dari semua yang di register dalam kontrol
	 **/
	public Set<String> getI18Keys();
	/**
	 * kode pendek form. ini untuk key ke database. column maksimal bisa memuat 128 char. 
	 * key ini di pergunakan untuk menyimpan internalization key
	 **/
	public abstract String getPanelShortCode () ; 
	
	
	/**
	 * kode group dari panel
	 **/
	public abstract String getPanelGroupId(); 
	
	
	/**
	 * kode group dari panel
	 **/
	public abstract void setPanelGroupId(String groupId); 

}
