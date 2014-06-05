package id.co.gpsc.jquery.client;



import id.co.gpsc.jquery.client.util.JQueryUtils;
import id.co.gpsc.jquery.client.util.NativeJsUtilities;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;

public abstract class BaseJqueryWidget extends Widget{



	/**
	 * durasi delay untuk mengijinkan proses render selesai
	 **/
	private static final int GIVE_RENDER_TIME_DELAY=10;


	/**
	 * key untuk menaruh jquery init arg
	 **/
	public static final String DEBUG_KEY_CONSTRUCTOR_ARGUMENT ="jqueryConstructorArgument";
	

	/**
	 * flag jquery sudah di render atau belum
	 **/
	protected boolean jqueryRendered =false ;


	/**
	 * flag delay sudah di lewatu atau tidak
	 **/
	private   boolean delayPassed = false ;

	
	
	/**
	 * reference hasil instantiate jquery
	 **/
	protected JavaScriptObject jqueryWidgetReference ; 
	
	
	
	protected JavaScriptObject widgetConstructArgument = JavaScriptObject.createObject() ; 
	

	
	public BaseJqueryWidget(){
		setElement(generateUnderlyingElement());
	}

	protected   JavaScriptObject generateDate(Date value){
		JavaScriptObject theDate=null;
		if ( value!=null)
			theDate=RawJsDate.generateNewDateInstance(value);
		return theDate;
	}



	/**
	 * geneate javascript date dari java.util.Date
	 **/
	private native JavaScriptObject generateDate(int year , int month , int date , int hour , int minute , int second)/*-{
		return new Date(year , month , date, hour , minute , second);
	}-*/;
	/**
	 * untuk kemudahan trigger option.
	 * apa nama jqeuery. misalnya : <i>datepicker</i>,<i>accordion</i>
	 **/
	protected abstract String getJQueryClassName();
	
	
	
	
	/**
	 * base element untuk widget node
	 **/
	protected abstract Element generateUnderlyingElement() ;
	
	

	@Override
	protected void onAttach() {
		super.onAttach();
		renderJQWidgetOnAttachWorker();
		getElement().setPropertyObject(DEBUG_KEY_CONSTRUCTOR_ARGUMENT, widgetConstructArgument);

	}

	
	/**
	 * hanler yg di panggil pada saat widget di attach
	 **/
	protected void renderJQWidgetOnAttachWorker(){
		try {
			if ( !jqueryRendered)
				if ( getJQWidgetId()==null ||getJQWidgetId().length()==0){
					getElement().setId(DOM.createUniqueId());
				}
			renderJQueryWidget(getJQWidgetId(), getJQueryClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Timer() {

			@Override
			public void run() {
				delayPassed = true ;
			}
		}.schedule(GIVE_RENDER_TIME_DELAY);

	}
	
	

	/**
	 * normalnya render widget akan di panggil pada saat method {@link #onAttach()} ter triger. kalau anda merasa perlu panggil manual, untuk memaksa render, makan panggil saja method ini
	 **/
	public void renderJQueryWidget (){
		renderJQueryWidget(getJQWidgetId(), getJQueryClassName());
	}
	
	
	/**
	 * worker untuk menaruh argument dalam constructor argumen
	 * 
	 **/
	protected native void putConstructorArgument (String optionName  , Object argValue) /*-{
		this.@id.co.sigma.jquery.client.BaseJqueryWidget::widgetConstructArgument[optionName]=argValue;
	
	}-*/;

	/**
	 * remove jquery function. balikin node ke normal state
	 **/
	public void destroy(){
		triggerVoidMethod(getJQWidgetId(), getJQueryClassName(), "destroy");
	}

	/**
	 * common method. render widget
	 * @param widgetId id dari widget
	 * @param jqueryClassname nama class jquery yang perlu di render
	 **/
	protected   void renderJQueryWidget(String widgetId, String jqueryClassname){
		
		this.jqueryWidgetReference =  JQueryUtils.getInstance().renderJQueryWidget(widgetId, jqueryClassname, widgetConstructArgument);
		jqueryRendered=true;
	}
	

	


	/**
	 * baca option yang return boolean
	 **/
	protected  boolean triggerBooleanReturnMethod (String controlId , String jqueryObjectName , String methodName){
		return JQueryUtils.getInstance().triggerBooleanReturnMethod(controlId, jqueryObjectName, methodName);
	}
	
	
	


	protected  JsDate triggerDateReturnMethod (String controlId , String jqueryObjectName , String methodName){
		return JQueryUtils.getInstance().triggerDateReturnMethod(controlId, jqueryObjectName, methodName);
	}
	



	/**
	 * trigger method return integer
	 **/
	protected  int triggerIntegerReturnMethod (String controlId , String jqueryObjectName , String methodName){
		return JQueryUtils.getInstance().triggerIntegerReturnMethod(controlId, jqueryObjectName, methodName);
	}
	


	/**
	 * triggger option pada widget. cross check ke Jquery docs untuk option yang tersedia<br/>
	 * value yang di set <i>Boolean</i>
	 * @param optionName nama option yang hendak di trigger
	 * @param optionValue nilai option yang hendak di set
	 **/
	protected  void triggerOption (final String optionName ,final boolean optionValue){
		if ( ! this.jqueryRendered){
			putConstructorArgument(optionName, optionValue);
			return ;
		}
		triggerOption(getJQWidgetId(), getJQueryClassName(), optionName, optionValue);
	}
	
	
	/**
	 * set date ke dalam jquery widget
	 * @param controlId id dari jquery based widget
	 * @param optionName nama option yang hendak di trigger
	 * @param optionValue nilai option yang hendak di set
	 **/
	protected void triggerOption( final String optionName ,final Date optionValue){
		if ( ! this.jqueryRendered){
			putConstructorArgument(optionName, optionValue);
			return ;
		}
		triggerOption(getJQWidgetId() , getJQueryClassName(), optionName, generateDate(optionValue));
	}



	/**
	 * triggger option pada widget. cross check ke Jquery docs untuk option yang tersedia<br/>
	 * value yang di set <i>Double</i>
	 * @param optionName nama option yang hendak di trigger
	 * @param optionValue nilai option yang hendak di set
	 **/
	protected  void triggerOption (final String optionName ,final Double optionValue){
		if ( ! this.jqueryRendered){
			putConstructorArgument(optionName, optionValue);
			return ;
		}
		triggerOption(getJQWidgetId(), getJQueryClassName(), optionName, optionValue);
	}




	/**
	 * triggger option pada widget. cross check ke Jquery docs untuk option yang tersedia<br/>
	 * value yang di set <i>Float</i>
	 * @param optionName nama option yang hendak di trigger
	 * @param optionValue nilai option yang hendak di set
	 **/
	protected  void triggerOption (final String optionName ,final float optionValue){
		if ( ! this.jqueryRendered){
			putConstructorArgument(optionName, optionValue);
			return ;
		}
		triggerOption(getJQWidgetId(), getJQueryClassName(), optionName, optionValue);
	}




	/**
	 * triggger option pada widget. cross check ke Jquery docs untuk option yang tersedia<br/>
	 * value yang di set <i>Integer</i>

	 * @param optionName nama option yang hendak di trigger
	 * @param optionValue nilai option yang hendak di set
	 **/
	protected  void triggerOption (final String optionName ,final int optionValue){
		if ( ! this.jqueryRendered){
			putConstructorArgument(optionName, optionValue);
			return ;
		}
		triggerOption(getJQWidgetId(), getJQueryClassName(), optionName, optionValue);
	}

	/**
	 * triggger option pada widget. cross check ke Jquery docs untuk option yang tersedia<br/>
	 * value yang di set <i>raw js object</i>. Handle with care untuk versi ini

	 * @param optionName nama option yang hendak di trigger
	 * @param optionValue nilai option yang hendak di set
	 **/
	protected  void triggerOption (final String optionName ,final JavaScriptObject optionValue){
		if ( ! this.jqueryRendered){
			putConstructorArgument(optionName, optionValue);
			return ;
		}
		triggerOption(getJQWidgetId(), getJQueryClassName(), optionName, optionValue);
	}


	/**
	 * triggger option pada widget. cross check ke Jquery docs untuk option yang tersedia<br/>
	 * value yang di set <i>String</i>
	 * @param optionName nama option yang hendak di trigger
	 * @param optionValue nilai option yang hendak di set
	 **/
	protected void triggerOption (  final String optionName , final String optionValue){
		if ( ! this.jqueryRendered){
			putConstructorArgument(optionName, optionValue);
			return ;
		}
		triggerOption(getJQWidgetId(), getJQueryClassName(), optionName, optionValue);
	}


	/**
	 * jsni worker. ini bertugas mengeset option pada jquery based widget
	 * @param controlId id dari jquery based widget
	 * @param jqueryObjectName nama jquery object.
	 * @param optionName nama option yang hendak di trigger
	 * @param optionValue nilai option yang hendak di set
	 * @category jsni
	 **/
	protected  void triggerOption (String controlId , String jqueryObjectName , String optionName , Object optionValue){
		NativeJsUtilities.getInstance().putObjectRawType(this.widgetConstructArgument, optionName, optionValue);
		JQueryUtils.getInstance().triggerOption(controlId, jqueryObjectName, optionName, optionValue);
	}
	


	/**
	 * baca boolean var dari option
	 * @param controlId id dari jquery node
	 * @param jqueryObjectName nama object jqeuery
	 * @return boolean value dari option
	 **/
	protected  boolean triggerOptionReturnBoolean (String controlId , String jqueryObjectName , String optionName ){
		return JQueryUtils.getInstance().triggerOptionReturnBoolean(controlId, jqueryObjectName, optionName);
	} 
	



	/**
	 * trigger jquery method dengan 1 argument
	 *
	 **/
	protected  void triggerSingleArgmentJQueryMethod (String controlId , String jqueryObjectName , String methodName , Object argument){
		JQueryUtils.getInstance().triggerSingleArgmentJQueryMethod(controlId, jqueryObjectName, methodName, argument);
	}
	


	/**
	 * trigger void method. jquery native
	 * @param controlId id control
	 * @param jqueryObjectName nama object jquery
	 * @param methodName nama method
	 **/
	protected  void triggerVoidMethod(String controlId , String jqueryObjectName , String methodName){
		JQueryUtils.getInstance().triggerVoidMethod(controlId, jqueryObjectName, methodName);
	}
	
	
	
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
	protected  void triggerVoidMethod(String controlId , String jqueryObjectName ,String methodName , Object methodArgument){
		JQueryUtils.getInstance().triggerVoidMethod(controlId, jqueryObjectName, methodName, methodArgument);
	}
	
	
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
	protected  void triggerVoidMethod(String controlId , String jqueryObjectName ,String methodName ,  Object methodArgument1, Object methodArgument2){
		JQueryUtils.getInstance().triggerVoidMethod(controlId, jqueryObjectName, methodName, methodArgument1, methodArgument2);
	}
	
	
	
	
	
	
	
	/**
	 * dalam beberapa kasus , element outmost tidak di pergunaakna untuk render jquery based widget. 
	 * jadinya untuk penyeragaman, akses ke id jquery widget di lewatkan ke sini. untuk menjaga konsistensi code
	 **/
	protected String getJQWidgetId() {
		return getElement().getId();
	}
	
	
	/**
	 * counter dari {@link #getJQWidgetId()} set id internal jquery widget
	 **/
	protected void setJQWidgetId(String id){
		getElement().setId(id);
	}
}
