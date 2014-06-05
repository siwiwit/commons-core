package id.co.gpsc.jquery.client.form;

import id.co.gpsc.common.form.BaseFormElement;
import id.co.gpsc.jquery.client.BaseJqueryWidget;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasValue;




/**
 * wrapper Slider JQuery
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class JQSliderPanel extends BaseJqueryWidget implements BaseFormElement , HasValue<Integer>{

	private static final String CLASS_NAME ="slider";

	private final Element underlyingElement =DOM.createDiv();


	protected String elementId ;
	
	@Override
	protected Element generateUnderlyingElement() {
		
		
		elementId = DOM.createUniqueId() ;
		underlyingElement.setId(elementId);
		return  underlyingElement;
	}

	@Override
	protected String getJQueryClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean isEnabled() {
		return !triggerOptionWorkerReturnBoolean(underlyingElement.getId(),   "disabled");
	}

	/**
	 * Type:<br/>
	 * Boolean<br/>
	 * Default:<br/>
	 * false<br/>
	 * Disables (true) or enables (false) the slider. Can be set when initialising (first creating) the slider.<br/>
	 **/
	@Override
	public  void setEnabled(boolean enable) {
		triggerOptionWorker(underlyingElement.getId(),  "disabled" , enable);
	}


	@Override
	public Integer getValue() {
		return triggerOptionWorkerReturnInt(underlyingElement.getId(), "value");
	}

	@Override
	public void setValue(Integer value) {
		setValue(value, true) ;

	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			final ValueChangeHandler<Integer> handler) {

		return addHandler(handler, ValueChangeEvent.getType());

	}


	@Override
	public void setValue(Integer value, boolean fireEvents) {
		setValueWorker(underlyingElement.getId(), value);
	}


	/**
	 * nilai max dari slider
	 **/
	public   void setMax (int max){
		triggerOption("max", max);
	}




	/**
	 * true akan set posisi slider menjadi vertical
	 **/
	public void setVerticalOrientation (boolean vertical) {
		this.triggerOption("orientation", vertical?"vertical" :"horizontal");
	}

	/**
	 * set nilai min dari slider
	 **/
	public   void setMin (int min){
		triggerOption("min", min);
	}

	@Override
	protected void renderJQueryWidget(String widgetId, String jqueryClassname) {
		super.renderJQueryWidget(widgetId, jqueryClassname);
		plugChangeHandler(underlyingElement.getId());
	}



	protected void propagateChangeNotification () {

		Integer val = getValue() ;
		ValueChangeEvent.fire(this, val);

	}


	/**
	 * register change handler ke dalam slider
	 **/
	private native void plugChangeHandler (String elementId)/*-{
		var swapThis = this ;
		 try{
		 	 $wnd.$("#" +elementId ).bind( "slidechange",
		 	function(event, ui) {
				swapThis.@id.co.gpsc.jquery.client.form.JQSliderPanel::propagateChangeNotification()();
			});
		 }catch(exc){
			console.log(exc.message);
		 }


	}-*/;


	private native void setValueWorker (String id , Integer value) /*-{
		$wnd.$("#" + id).slider({value:value});
	}-*/;


	/**
	 * worker untuk trigger option
	 **/
	private native void triggerOptionWorker (String id , String option, boolean enabled )/*-{
		$wnd.$("#" + id).slider("option" , option,!enabled );


	}-*/;

	private native boolean triggerOptionWorkerReturnBoolean (String id , String option )/*-{
		return $wnd.$("#" + id).slider("option" , option );

	}-*/;


	private native int triggerOptionWorkerReturnInt (String id , String option )/*-{
		return $wnd.$("#" + id).slider("option" , "" + option + ""  );

	}-*/;
	
	
	

	@Override
	public void setDomId(String elementId) {
		this.elementId = elementId ; 
		if ( this.jqueryRendered){
			destroy();
			ensureDebugId(elementId);
			renderJQueryWidget();
		}
		ensureDebugId(elementId) ;
	}
	@Override
	public String getDomId() {
		return getElement().getId();
	}
	
	
	






}
