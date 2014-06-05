package id.co.gpsc.common.client.common;

import id.co.gpsc.jquery.client.util.JQueryUtils;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;

/**
 * base class untuk upload yang nyambung manggil RPC
 * 
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public abstract class LayeredLogicUploadSubmitHandler implements SubmitCompleteHandler{

	@Override
	public void onSubmitComplete(SubmitCompleteEvent event) {
		String rslt =  event.getResults();
		//Window.alert(rslt);
		JSONValue val =  JSONParser.parseLenient(rslt);
		if ( val== null || val.isObject()== null){
			Window.alert("gagal dalam upload data. ");
			return ; 
		}
		
		JSONObject obj  = val.isObject();
		
		JSONValue valStatus =  obj.get("status") ;
		if ( valStatus== null){
			Window.alert("upload file gagal");
			return ; 
		}
		JSONString strStatus =  obj.get("status").isString();
		if ( strStatus== null){
			Window.alert("gagal dalam upload data. ");
			JQueryUtils.getInstance().unblockEntirePage();
			return ; 
		}
		String sttsCOde =  strStatus.stringValue();
		if ("success".equalsIgnoreCase(sttsCOde)){
			JSONString key  =  obj.get("key").isString();
			handlerOnUploadSuccessHandler(key.stringValue());
		}else {
			JSONString msg  =  obj.get("message").isString();
			handlerOnUploadFailedHandler(msg.stringValue());
		}
		
	}
	
	
	
	/**
	 * ini handler kalau misalnya file sukses di lakukan. di sini anda harus mengirim RPC reqeust untuk memproses data yang di kirimkan
	 */
	protected abstract void handlerOnUploadSuccessHandler ( String uploadedFileKey ); 
	
	
	
	
	/**
	 * ini handler kalau kalau upload gagal
	 */
	protected abstract void handlerOnUploadFailedHandler ( String errorMessage );
	
	

}
