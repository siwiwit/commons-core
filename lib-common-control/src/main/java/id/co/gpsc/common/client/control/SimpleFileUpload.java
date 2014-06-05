package id.co.gpsc.common.client.control;

import id.co.gpsc.common.client.util.CommonClientControlUtil;
import id.co.gpsc.common.data.CommonLibraryConstant;
import id.co.gpsc.jquery.client.util.JQueryUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel; 
import com.google.gwt.user.client.ui.NamedFrame;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

/**
 * 
 * komponen file upload MPN
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class SimpleFileUpload extends Composite{
	
	private FormPanel formPanel ; 
	private FileUpload fileUpload ; 
	
	private static   int NAMED_COUNTER =1 ; 
	
	private static final String NAME_PREFIX ="UPLOAD_FORM_CHAIN_"; 
	
	public static final String ON_UPLOAD_COMPLETE_SUCCESS_METHOD ="ON_UPLOAD_COMPLETE_SUCCESS_METHOD";
	
	
	public static final String ON_UPLOAD_COMPLETE_FAIL_METHOD ="ON_UPLOAD_COMPLETE_FAIL_METHOD";
	CommonChainedUploadHandler uploadHandler  ; 
	
	private NamedFrame targetFrame ; 
	
	
	private String invokeUploadSuccessParentMethod ; 
	private String invokeUploadFailedParentMethod ; 
	
	public SimpleFileUpload() {
		
		invokeUploadSuccessParentMethod = ON_UPLOAD_COMPLETE_SUCCESS_METHOD + NAMED_COUNTER ; 
		invokeUploadFailedParentMethod = ON_UPLOAD_COMPLETE_FAIL_METHOD + NAMED_COUNTER ; 
		
		NAMED_COUNTER = NAMED_COUNTER+1 ; 
		if ( CommonClientControlUtil.getInstance().isInternetExplorer()) {
			
			targetFrame = new NamedFrame(NAME_PREFIX +NAMED_COUNTER ); 
			formPanel = new FormPanel(targetFrame); 
			NAMED_COUNTER = NAMED_COUNTER + 1 ; 
			targetFrame.setWidth("100px");
			targetFrame.setHeight("100px");
			targetFrame.setVisible(false);
			
			RootPanel.get().add(targetFrame);
		}else {
			formPanel = GWT.create(FormPanel.class); 
		}
		
		initWidget(formPanel);
		fileUpload = GWT.create(FileUpload.class); 
		formPanel.add(fileUpload);
		fileUpload.setName("uploadedFile");
		configureUploadControl(formPanel, fileUpload);
		formPanel.addSubmitHandler(new SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				
				
				if ( fileUpload.getFilename()== null || fileUpload.getFilename().isEmpty()){
					Window.alert("Belum ada file yang di pilih. proses upload tidak bisa di lakukan");
					event.cancel(); 
					return ; 
				}
				JQueryUtils.getInstance().blockEntirePage("upload file sekarang. mohon menunggu");
				
			}
		}); 
		deployHandlerMethod(invokeUploadSuccessParentMethod, invokeUploadFailedParentMethod);
	}
	
	private native void deployHandlerMethod (String successMthdName , String failMthdName ) /*-{
		var ths = this ; 
		$wnd[successMthdName] = function (key ) {
			ths.@id.co.gpsc.common.client.control.SimpleFileUpload::handlerOnUploadSuccess(Ljava/lang/String;)(key); 
		}; 
		
		$wnd[failMthdName] = function (msg ) {
			ths.@id.co.gpsc.common.client.control.SimpleFileUpload::handlerOnUploadFail(Ljava/lang/String;)(msg);
		}
	
	}-*/;
	
	
	
	protected void handlerOnUploadSuccess (String key ){
		JQueryUtils.getInstance().unblockEntirePage();
		if ( this.uploadHandler== null)
			return ; 
		this.uploadHandler.onUploadFileSuccess(key);
	}
	
	protected void handlerOnUploadFail (String message ){
		JQueryUtils.getInstance().unblockEntirePage();
		if ( this.uploadHandler== null)
			return ; 
		this.uploadHandler.onUploadFileFailed(message , fileUpload.getFilename());
	}
	
	protected String getActualClassName () {
		return this.getClass().getName() ; 
	}
	
	
	
	
	
	public String getName () {
		return this.fileUpload.getName(); 
	}
	/**
	 * nama file yang di upload
	 */
	public void setName(String uploadFormName){
		this.fileUpload.setName(uploadFormName);
	}
	
	/**
	 * handler kalau file upload sukses
	 */
	protected void configureUploadControl (FormPanel formPanel ,final FileUpload fileUpload  ) {
		formPanel.setMethod(FormPanel.METHOD_POST);
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		
		if (! CommonClientControlUtil.getInstance().isInternetExplorer()){
			formPanel.setAction(CommonClientControlUtil.getInstance().getApplicationBaseUrl() + CommonLibraryConstant.COMMON_UPLOAD_CONTROLLER_URL);
			formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler() {
				
				@Override
				public void onSubmitComplete(SubmitCompleteEvent event) {
					JQueryUtils.getInstance().unblockEntirePage();
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
						uploadHandler.onUploadFileSuccess( key.stringValue());
					}else {
						JSONString msg  =  obj.get("message").isString();
						uploadHandler.onUploadFileFailed(   msg.stringValue() , fileUpload.getFilename());
					}
					
				}
			}) ; 
			
		} else {
			
			formPanel.setAction(CommonClientControlUtil.getInstance().getApplicationBaseUrl() + CommonLibraryConstant.COMMON_UPLOAD_CONTROLLER_URL_WITH_IFRAME +"?successHandlerMethod=" + invokeUploadSuccessParentMethod +"&failHandlerMethod=" + invokeUploadFailedParentMethod);
			
			
		}
		
		
	}
	
	
	
	
	
	/**
	 */
	public void uploadFile (CommonChainedUploadHandler uploadHandler){
		this.uploadHandler = uploadHandler ; 
		formPanel.submit();
		
	}
	
	
	/**
	 * nama file yang perlu di upload
	 */
	public String getFilename () {
		return fileUpload.getFilename(); 
	}
	
	
	

}
