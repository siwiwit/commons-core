package id.co.gpsc.common.client.form;

import id.co.gpsc.common.util.I18Utilities;

import com.google.gwt.user.client.DOM;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;



/**
 * label read only, mirip dengan {@link SimpleReadonlyLabel} , cuma ini ndak ada set text. hanya default label + i18NKey
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class SimpleI18NDrivenReadonlyLabel extends Widget{

	public static final String i18_READONLY_LABEL_PREFIX ="i18n_driven_label_" ; 
	
	/**
	 * key internalization
	 **/
	private String i18NKey ; 
	
	
	/**
	 * label default untuk di munculkan kalau i18Nkey kosong
	 **/
	private String defaultLabel ="Mohon Set default label"; 
	
	
	public SimpleI18NDrivenReadonlyLabel(){
		Element w = DOM.createSpan(); 
		w.setId(DOM.createUniqueId());
		setElement(w);
	}
	
	/**
	 * key internalization
	 **/
	public String getI18NKey() {
		return i18NKey;
	}
	/**
	 * key internalization
	 **/
	public void setI18NKey(String i18nKey) {
		i18NKey = i18nKey;
		if ( i18nKey!=null&& i18nKey.length()>0){
			getElement().setInnerHTML(I18Utilities.getInstance().getInternalitionalizeText(i18nKey, defaultLabel));
		}
		else{
			getElement().setInnerHTML(defaultLabel);
		}
			
	}
	
	/**
	 * label default untuk di munculkan kalau i18Nkey kosong
	 **/
	public void setDefaultLabel(String defaultLabel) {
		this.defaultLabel = defaultLabel;
	}
	/**
	 * label default untuk di munculkan kalau i18Nkey kosong
	 **/
	public String getDefaultLabel() {
		return defaultLabel;
	}
}
