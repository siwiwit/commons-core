package id.co.gpsc.jquery.client.form;


import id.co.gpsc.common.form.BaseFormElement;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ValueBoxBase;

public abstract class JQBaseAutoComplete<DATA> extends ValueBoxBase<DATA> implements BaseFormElement{

	
	
	
	
	protected boolean attached = false ; 
	
	/**
	 * key untuk menaruh nilai dari auto complete
	 **/
	protected static final String ACTUAL_VALUE_KEY ="actualValue"; 
	
	
	protected JavaScriptObject dataSource = JavaScriptObject.createArray();
	
	protected  String elementId ="AUTO_COMPLETE" + DOM.createUniqueId() ;  

	
	/**
	 * argumen konstruktor auto complete
	 **/
	protected JavaScriptObject autocompleteArg = JavaScriptObject.createObject(); 
	
	protected JQBaseAutoComplete( Renderer<DATA> renderer,
			Parser<DATA> parser) {
		super(DOM.createInputText(), renderer, parser);
		getElement().setId(elementId);
		
	}
	
	
	
	
	/**
	 * delay untuk display(dalam milisecond) auto complete(default 300)
	 **/
	public void setDelay(int delay) {
		triggerOption(elementId, "delay", delay);
	}
	
	/**
	 * The minimum number of characters a user has to type before the Autocomplete activates. Zero is useful for local data with just a few items. Should be increased when there are a lot of items, where a single character would match a few thousand items.<br/>
	 * Default:1
	 **/
	public void setMinLength(int minLength){
		triggerOption(elementId, "minLength", minLength);
	}
	
	/**
	 * If set to true the first item will be automatically focused.<br/>
	 * Default : false
	 **/
	public void setAutoFocus(boolean autoFocus){
		triggerOption(elementId, "autoFocus", autoFocus);
	}
	
	/**
	 * Disables (true) or enables (false) the autocomplete. Can be set when initialising (first creating) the autocomplete.<br/>
	 * Default: false
	 **/
	public void setDisabled(boolean disabled){
		triggerOption(elementId, "disabled", disabled);
	}
	
	
	
	/**
	 * Triggers a search event, which, when data is available, then will display the suggestions; can be used by a selectbox-like button to open the suggestions when clicked. If no value argument is specified, the current input's value is used. Can be called with an empty string and minLength: 0 to display all items.
	 **/
	public native void search () /*-{
		this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::search();
	}-*/;
	
	/**
	 * close auto complete
	 **/
	public native void close () /*-{
		this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::close();
	}-*/;

	@Override
	protected void onAttach() {
		
		super.onAttach();
		this.attached = true ;
		renderAutoComplete();
		
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		this.attached = false ;
		destroy(elementId);
	}
	
	
	
	/**
	 * glue method untuk mem-trigger change handler
	 **/
	protected void propagateChangeEvent () {
		ValueChangeEvent.fire(this, getValue()) ; 
	}
	
	
	/**
	 * worker untuk bind event change handler ke underlying auto complete
	 **/
	protected native void buildChangeEventWire () /*-{
		var swapThis = this ; 
		var evtHandler = function (event, ui) {
			swapThis.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::propagateChangeEvent();
		};
		this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::autocompleteArg["change"] = evtHandler ; 
	}-*/;
	
	

	
	
	/**
	 * reset array dengan array baru
	 **/
	protected native void resetArray()/*-{
		this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::dataSource=[];
		this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::autocompleteArg["source"] =this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::dataSource ;  
	}-*/;
	
	/**
	 * menaruh string dalam array LOV
	 **/
	protected native void putToArray (String value)/*-{
		this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::dataSource.push(value); 
	}-*/;

	
	/**
	 * set option. pls check <i>option</i> pada jquery
	 **/
	protected native void triggerOption (String widgetId , String optionName , Object optionArgument ) /*-{
		
		this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::autocompleteArg[optionName] = optionArgument ;
		$wnd.$("#" +widgetId ).autocomplete("option",optionName , optionArgument); 
	}-*/;
	 
	
	/**
	 * hilangkan jquery function. pada saat detach
	 **/
	protected native void destroy(String widgetId)/*-{
			$wnd.$("#" +widgetId ).autocomplete( "destroy" );
	}-*/;
	
	
	
	/**
	 * worker untuk render widget
	 **/
	protected native void renderAutoComplete ()/*-{
		
		
		 
		try{
			
			 
			$wnd.$("#" +this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::elementId ).autocomplete(
				this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::autocompleteArg 
			);	
		}
		catch(exc){
		}		
			
	}-*/;

	
	/**
	 * set value untuk widget
	 * @param valueForWidget value untuk di set ke dalam widget
	 **/
	protected native void setStringValue(String valueForWidget)/*-{
		var elemId  = "#" +  this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::elementId ; 
		$wnd.$(elemId).val(valueForWidget) ; 
		
	}-*/;
	
	
	/**
	 * membaca underlying value(string)
	 **/
	protected native String getStringValue()/*-{
		return $wnd.$( "#" +  this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::elementId ).val() ; 
	
	}-*/;
	
	
	
	
	/**
	 * taruh data source dummy
	 **/
	protected native void putDummyLOVSource () /*-{
		this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::autocompleteArg["source"] ;
		
	
	}-*/;
	
	@Override
	public void setDomId(String elementId) {
		this.elementId = elementId ;
		if ( attached){
			this.destroy(getElement().getId());
			ensureDebugId(elementId);
			renderAutoComplete();
			
		}
		else{
			ensureDebugId(elementId);
		}
		ensureDebugId(elementId) ;
	}
	@Override
	public String getDomId() {
		return getElement().getId();
	}
	
	
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return super.isEnabled();
	}
	
	
	@Override
	public void setEnabled(boolean enabled) {
		
		super.setEnabled(enabled);
	}

}
