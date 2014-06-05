package id.co.gpsc.common.client.control;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * 
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public final class RawJSFormElementConfiguration extends JavaScriptObject implements IFormElementConfiguration{

	
	protected RawJSFormElementConfiguration(){
		super();
	}

	
	
	
	@Override
	public  String getFormId(){
		return getStringById("formId");
	}

	@Override
	public String getElementId() {
		return getStringById("elementId");
	}

	@Override
	public String getHintI18NKey() {
		return getStringById("hintI18NKey");
	}

	@Override
	public String getPlaceHolderI18NKey() {
		return getStringById("placeHolderI18NKey");
	}

	@Override
	public Boolean getMandatory() {
		if ( !isKeyExist("mandatory"))
			return null;
		return getBooleanById("mandatory");
	}

	@Override
	public Integer getMaxLength() {
		if ( !isKeyExist("maxLength"))
			return null;
		return getIntById("maxLength");
	}

	@Override
	public String getMinValue() {
		// FIXME: men ama max blm di define
		return null;
	}

	@Override
	public String getMaxValue() {
		// FIXME: men ama max blm di define
		return null;
	}

	@Override
	public String getGroupId() {
		return getStringById("groupId");
	}
	
	
	
	
	/**
	 * akses string dengan id dari field
	 **/
	protected native String getStringById (String fieldKey) /*-{
		return this[fieldKey];
	}-*/;
	
	
	/**
	 * akses int dengan id dari field
	 **/
	protected native int getIntById (String fieldKey) /*-{
		return this[fieldKey]/1;
	}-*/;
	
	
	protected native boolean getBooleanById (String fieldKey) /*-{
	return this[fieldKey];
	}-*/;

	/**
	 * mengecek apakah field ada dalam js object
	 **/
	protected native boolean isKeyExist(String key) /*-{
		return !(this[key]==null||typeof this[key]== undefined); 
	}-*/;
	
	
	
	
	/**
	 * grad data dari js variables
	 **/
	public static native RawJSFormElementConfiguration getConfigurationById (String key)/*-{
		var arr = ["id","co","sigma","formConfigs"];
		var curr = $wnd ; 
		for ( i=0;i<arr.length;i++){
			curr=curr[arr[i]];
			if ( curr==null||typeof curr == undefined)
				return ; 
		}
		return $wnd.id.co.sigma.formConfigs[key];
	
	}-*/;
	
}
