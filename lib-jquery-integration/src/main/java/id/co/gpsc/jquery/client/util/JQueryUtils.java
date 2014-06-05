package id.co.gpsc.jquery.client.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;



/**
 * jquery related utils
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * 
 **/
public final class JQueryUtils {
	
	
	
	private static JQueryUtils instance ; 
	
	
	
	
	/**
	 * tempat menaruh method "jembatan" trigger dari raw js
	 **/
	private Map<String, Command> globalBridgeMethodHolder  ; 
	
	
	
	private JQueryUtils(){
		globalBridgeMethodHolder = new HashMap<String, Command>(); 
	}
	
	
	public static JQueryUtils getInstance() {
		if(instance==null)
			instance=new JQueryUtils();
		return instance;
	}
	
	
	
	
	
	/**
	 * trigger method
	 **/
	public void triggerBridgeMethod (String methodName ) {
		if ( this.globalBridgeMethodHolder.containsKey(methodName))
			globalBridgeMethodHolder.get(methodName).execute(); 
	}	
	/**
	 * menaruh method dalam global handler
	 **/
	public void registerBridgeMethod (String methodName  , Command actualHandler ) {
		this.globalBridgeMethodHolder.put(methodName, actualHandler);
	}
	
	
	
	/**
	 * jsni worker. ini bertugas mengeset option pada jquery based widget
	 * @param controlId id dari jquery based widget
	 * @param jqueryObjectName nama jquery object.
	 * @param optionName nama option yang hendak di trigger
	 * @param optionValue nilai option yang hendak di set
	 * @category jsni
	 **/
	public native void triggerOption (String controlId , String jqueryObjectName , String optionName , Object optionValue)/*-{
		
		$wnd.$("#" + controlId)[jqueryObjectName]('option', optionName, optionValue);
	}-*/;

	/**
	 * baca boolean var dari option
	 * @param controlId id dari jquery node
	 * @param jqueryObjectName nama object jqeuery
	 * @return boolean value dari option
	 **/
	public native boolean triggerOptionReturnBoolean (String controlId , String jqueryObjectName , String optionName )/*-{
		
		return $wnd.$("#" + controlId)[jqueryObjectName]('option', optionName);
	}-*/;
	
	/**
	 * trigger jquery method dengan 1 argument
	 *
	 **/
	public native void triggerSingleArgmentJQueryMethod (String controlId , String jqueryObjectName , String methodName , Object argument)/*-{
		$wnd.$("#" + controlId)[jqueryObjectName](methodName , argument);
	}-*/;
	
	/**
	 * trigger void method. jquery native
	 * @param controlId id control
	 * @param jqueryObjectName nama object jquery
	 * @param methodName nama method
	 **/
	public native void triggerVoidMethod(String controlId , String jqueryObjectName , String methodName)/*-{
		$wnd.$("#" + controlId)[jqueryObjectName](methodName);
	}-*/;
	

	/**
	 * trigger void method. jquery native. ini dengan argument 1 biji
	 * @param controlId id control
	 * @param jqueryObjectName nama object jquery
	 * @param methodName nama method
	 * @param methodArgument argument method
	 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a> 
	 * @since 18 aug 2012
	 * 
	 **/
	public native void triggerVoidMethod(String controlId , String jqueryObjectName ,String methodName , Object methodArgument)/*-{
		$wnd.$("#" + controlId)[jqueryObjectName](methodName ,methodArgument );
	}-*/;
	
	/**
	 * trigger void method. jquery native. ini dengan argument 2 biji
	 * @param controlId id control
	 * @param jqueryObjectName nama object jquery
	 * @param methodName nama method
	 * @param methodArgument1 argument method(1)
	 * @param methodArgument2 argument method(2)
	 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a> 
	 * @since 18 aug 2012
	 * 
	 **/
	public native void triggerVoidMethod(String controlId , String jqueryObjectName ,String methodName ,  Object methodArgument1, Object methodArgument2)/*-{
		$wnd.$("#" + controlId)[jqueryObjectName](methodName , methodArgument1, methodArgument2);
	}-*/;

	/**
	 * baca option yang return boolean
	 **/
	public native boolean triggerBooleanReturnMethod (String controlId , String jqueryObjectName , String methodName)/*-{
		return $wnd.$("#" + controlId)[jqueryObjectName](methodName);
	}-*/;
	
	public native JsDate triggerDateReturnMethod (String controlId , String jqueryObjectName , String methodName)/*-{
		return $wnd.$("#" + controlId)[jqueryObjectName](methodName);
	}-*/;
	/**
	 * trigger method return integer
	 **/
	public native int triggerIntegerReturnMethod (String controlId , String jqueryObjectName , String methodName)/*-{
		return $wnd.$("#" + controlId)[jqueryObjectName](methodName);
	}-*/;
	
	/**
	 * common method. render widget
	 * @param widgetId id dari widget
	 * @param jqueryClassname nama class jquery yang perlu di render
	 **/
	public  native JavaScriptObject renderJQueryWidget(String widgetId, String jqueryClassname , JavaScriptObject widgetConstructArgument)/*-{
		try{
			var selector= "#" +widgetId;
			
			return $wnd.$(selector)[jqueryClassname](widgetConstructArgument);
		}catch(e){
			$wnd["LATEST_FAULT_ARGUMENT"] = widgetConstructArgument;
			alert("gagal render :" + jqueryClassname + ",id:" + widgetId + ",message:" +   e.message);
		} 
	}-*/;
	
	
	
	
	
	/**
	 * destroy Jquery widget
	 **/
	public  native void destroy(String widgetId, String jqueryClassname)/*-{
		try{
			var selector= "#" +widgetId;
			$wnd.$(selector)[jqueryClassname]("destroy");
		}catch(e){
			alert("gagal destroy :" + jqueryClassname + ",id:" + widgetId + ",message:" +   e.message);
		}
	
	}-*/; 
	
	
	/**
	 * bind click ke dalam element. ini mempergunakan mekanisme click Jquery
	 * @param clickTargetElement reference ke elemen yang di bind click
	 * @param clickHandler handler dari proses click ini. 
	 **/
	public native void appendClickHandler(Element clickTargetElement , Command clickHandler)/*-{
		var glueMethod =function (){
			clickHandler.@com.google.gwt.user.client.Command::execute();
		}
		$wnd.$(clickTargetElement).click(glueMethod);
	
	}-*/;
	
	/**
	 * bind click ke dalam element. ini mempergunakan mekanisme click Jquery
	 * @param clickTargetElementId id dari elemen yang di tambahi click handler
	 * @param clickHandler handler dari proses click ini. 
	 **/
	public native void appendClickHandler(String clickTargetElementId , Command clickHandler)/*-{
		var glueMethod =function (){
			
			clickHandler.@com.google.gwt.user.client.Command::execute()();
		}
		$wnd.$("#" + clickTargetElementId).click(glueMethod);
	
	}-*/;
	
	
	
	/**
	 * set value ke dalam text
	 **/
	public native void setValue(Element elementReference, String value)/*-{
		$wnd.$(elementReference).val(value);
	}-*/;
	
	
	

	
	/**
	 * remove key press event dari element
	 **/
	public native void clearKeyPressEvent(Element elementReference)/*-{
		$wnd.$(elementReference).unbind("keypress");
	
	}-*/;
	
	/**
	 * remove key press event dari element
	 **/
	public native void clearKeyPressEvent(String elementId)/*-{
		$wnd.$("#"+elementId).unbind("keypress");
	
	}-*/;
	
	
	/**
	 * worker untuk load javascript dengan url yang di kirimkan, ini untuk load dynamic scripts
	 **/
	public native void loadScript(String scriptUrl)/*-{
		$wnd.$.ajaxSetup({async:false});
		$wnd.$.getScript(scriptUrl , function (){
			$wnd.$.ajaxSetup({async:true});
		})
	
	}-*/;
	
	
	
	/**
	 * checkbox ke check atau tidak
	 **/
	public native boolean isChecked (String checkboxId) /*-{
		return $wnd.$("#" + checkboxId ).is(":checked"); 
	
	}-*/;
	
	
	/**
	 * unblock panel. ini dengan delay proses un-block 
	 **/
	public   void unblockUI (final String panelId   , int delayInMilist) {
		new Timer() {
			
			@Override
			public void run() {
				unblockUI(panelId); 
				
			}
		}.schedule(delayInMilist);
	}
	/**
	 * unblock panel
	 **/
	public native void unblockUI (String panelId   ) /*-{
		$wnd.$("#" + panelId ).unblock( );
	}-*/;
	
	/**
	 * block ui. versi ini dengan menampilkan message pada panel yang di block. kalau anda taruh null dalam message, hasil nya no message
	 **/
	public native void blockUI (String panelId , String messageForBlocked  ) /*-{
		$wnd.$("#" + panelId ).block({message:messageForBlocked}); 
	}-*/;
	
	
	
	/**
	 * versi ini , mengertakan css untuk block panel
	 **/
	public native void blockUI (String panelId , String messageForBlocked  , String cssForBlocker ) /*-{
		$wnd.$("#" + panelId ).block({
			message:messageForBlocked , 
			css : cssForBlocker
			});
	}-*/;
	
	
	
	
	/**
	 * ini memblock seluruh UI dengan. versi ini dengan tombol close. jadinya memungkinkan kita menaruh message yang lumayan panjang dalam panel
	 */
	public native void blockEntirePageWithCloseButton (String htmlFormatedmessageForBlocked) /*-{
		var idCloser = Math.ceil(Math.random()*100000000) + (new Date()).getTime();
		$wnd.$.blockUI({message:"<div align='right'> <button id='"+idCloser+"'> </button></div><hr style='size:1px'/><div align='left'>"+htmlFormatedmessageForBlocked+"</div>"   ,
					css:{
						border: 'none', 
					    padding: '15px', 
					    top:'20px' , 
					    width:'95%',
					    height:'90%',
					    left:'20px',
						backgroundColor: '#fff', 
						'-webkit-border-radius': '10px', 
						'-moz-border-radius': '10px', 
						opacity: .95, 
						color: '#000' , 
						cursor:'default'
						}
					
					});
		$wnd.$("#"+idCloser).button({
			            icons: {
			                primary: "ui-icon-circle-close"
			            },
			            text: false
			        }).click(function () {
						$wnd.$.unblockUI();
			        });
		
	
	
	}-*/;   
	
	
	/**
	 * unblock page. versi ini di delay
	 **/
	public   void unblockEntirePage ( int delayInMilist    ){
		new Timer() {
			
			@Override
			public void run() {
				unblockEntirePage();
				
			}
		}.schedule(delayInMilist);
	}
	
	
	/**
	 * unblock page. versi ini di delay
	 * @param delayInMilist durasi dalam milisecond, kapan blok di buka
	 * @param taskAfterBlockOpened command yang di trigger setelah task selesai di buka
	 **/
	public   void unblockEntirePage ( int delayInMilist    ,final  Command taskAfterBlockOpened){
		new Timer() {
			@Override
			public void run() {
				unblockEntirePage();
				if ( taskAfterBlockOpened!= null)
					taskAfterBlockOpened.execute();
			}
		}.schedule(delayInMilist);
	}
	
	/**
	 * menampilkan tool tip pada widget. versi ini otomatis akan hide tooltip dalam satuan milist
	 * @param targetWidget widget yang di tampilkan hint
	 * @param label label untuk di tampilkan dalam hint
	 * @param timeToAutoHideInMilist durasi, berapa milist hint akan auto hide 
	 */
	public void showToolTip ( final Widget targetWidget ,   final String label, int timeToAutoHideInMilist ) {
		showToolTip(targetWidget , true , label ); 
		new Timer() {
			@Override
			public void run() {
				showToolTip(targetWidget , false , label ); 
			}
		}.schedule(timeToAutoHideInMilist);
	}
	
	/**
	 * menampilkan tool tip pada widget
	 */
	public void showToolTip ( Widget targetWidget , boolean display ,   String label ) {
		final Element elm = targetWidget.getElement() ; 
		if (elm.getPropertyString("id")== null || elm.getPropertyString("id").isEmpty() )
			elm.setPropertyString("id",  DOM.createUniqueId());
		final String curntTttl = elm.getTitle(); 
		elm.setPropertyString("title", label);
		
		
		showToolTip(elm.getPropertyString("id") ,display ? "open" : "close" );
		new Timer() {
			@Override
			public void run() {
				elm.setTitle(curntTttl);
			}
		}.schedule(100);
	}
	
	
	
	/**
	 * menampilkan growl style message
	 * untuk enhancement cek di sini : http://malsup.com/jquery/block/#demos
	 * @param title title dari growl popup
	 * @param message message dalam growl
	 */
	public native void growlUI(String title , String message ) /*-{
		$wnd.$.growlUI(title  ,message); 
	}-*/; 
	
	
	
	private native void showToolTip (String elemId , String openFlag ) /*-{
		$wnd.$("#" + elemId).tooltip().tooltip(openFlag) ; 
	
	}-*/;
	
	
	
	/**
	 * unblock page
	 **/
	public native void unblockEntirePage (    )/*-{
		$wnd.$.unblockUI( );
	}-*/;
	
	
	
	/**
	 * ini akan memblok se isi page
	 **/
	public native void blockEntirePage (  String messageForBlocked  ) /*-{
		$wnd.$.blockUI({
			message:messageForBlocked 
			});
	}-*/;
	
	/**
	 * ini akan memblok se isi page. ini dengan css tertentu
	 * @param messageForBlocked message yang di tampilkan dalam popup 
	 * @param cssForBlocker css 
	 **/
	public native void blockEntirePage (   String messageForBlocked  , String cssForBlocker ) /*-{
		$wnd.$.blockUI({
			message:messageForBlocked , 
			css : cssForBlocker
			});
	}-*/;
	
	
	
	
	 
	
	
	
	
	/**
	 * show element. <br/>  
	 * 
	 * @param elementId id dari element yang mau di show
	 */
	public native void showElement( String elementId ) /*-{
		$wnd.$("#" + elementId).show();
	}-*/;
	
	
	
	/**
	 * hide element dengan id
	 */
	public native void hideElement( String elementId ) /*-{
		$wnd.$("#" + elementId).hide();
	}-*/;
	
	
	
	/**
	 * disable element
	 */
	public native void disableFormElement ( String elementId )/*-{
		$wnd.$("#" + elementId).attr('disabled', 'disabled');
	}-*/;
	
	
	
	/**
	 * enable form element
	 */
	public native void enableFormElement ( String elementId )/*-{
		$wnd.$("#" + elementId).removeAttr('disabled');
	}-*/;
	

}
