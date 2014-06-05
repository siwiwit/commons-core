package id.co.gpsc.jquery.client.form;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.Window;


/**
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public class JQAutoCompleteExtended<DATA> extends JQBaseAutoComplete<DATA> {

	
	
	
	
	 
	
	
	/**
	 * indexer, all element
	 **/
	private Map<String, DATA> indexer = new HashMap<String, DATA>();
	
	/**
	 * element yang currently selected
	 **/
	private DATA currentSelectedData ; 
	
	private LOVExtractor<DATA> currentDataExtractor ;
 	public JQAutoCompleteExtended() {
 		super(new Renderer<DATA>() {
 			@Override
 			public String render(DATA object) {
 				return null;
 			}

			@Override
			public void render(DATA object, Appendable appendable)
					throws IOException {
				
				
			}
		}, new Parser<DATA>() {
			@Override
			public DATA parse(CharSequence text) throws ParseException {
				// TODO Auto-generated method stub
				return null;
			}
		});
 		
 		putDummyLOVSource();
 		
 		
		
	}
	
	
	private LOVExtractor<DATA> dataExtractor ;
	
	@Override
	public void setValue(DATA value, boolean fireEvents) {
		if ( value==null){
			setStringValue("");
			if ( this.currentSelectedData !=null)
				ValueChangeEvent.fire(this, value) ;
		}
		else{
			if ( dataExtractor!= null ) {
				String val = dataExtractor.getValue(value);
				setStringValue(val);
			}else{
				setStringValue("");
			}
			
			if (fireEvents&& !value.equals(this.currentSelectedData)) {
			      ValueChangeEvent.fire(this, value) ; 
			}
		}
		this.currentSelectedData = value ; 
		
	}
	
	
	
	@Override
	public DATA getValueOrThrow() throws ParseException {
		return currentSelectedData;
	}
	/**
	 * set LOV Source
	 * @param lovData data LOV yang akan di push ke dalam auto complete
	 **/
	public void setLOVSource (Collection<DATA> lovData ,LOVExtractor<DATA> dataExtractor){
		this.currentDataExtractor = dataExtractor ; 
		this.currentSelectedData = null ; 
		if ( lovData==null||lovData.isEmpty())
			return ;
		resetArray();
		indexer.clear();
		for ( DATA scn : lovData){
			String key =dataExtractor.getValue(scn); 
			pushLOV(key, dataExtractor.getLabel(scn), dataExtractor.getDescription(scn), scn);
			indexer.put(key, scn);
		}
		if ( this.attached)
			triggerOption(elementId, "source", dataSource);
	}
	
	
	/**
	 * set LOV data(dengan array of object)
	 **/
	public void setLOVSource (DATA[] lovData ,LOVExtractor<DATA> dataExtractor){
		try{
			this.currentDataExtractor = dataExtractor ; 
			this.currentSelectedData = null ;
			resetArray();
			indexer.clear();
			if ( lovData==null||lovData.length==0)
				return ;
			
			
			for ( DATA scn : lovData){
				String key =dataExtractor.getValue(scn); 
				pushLOV(key, dataExtractor.getLabel(scn), dataExtractor.getDescription(scn), scn);
				indexer.put(key, scn);
			}
			if ( this.attached)
				triggerOption(elementId, "source", dataSource);
		}catch ( Exception exc){
			Window.alert("gagal menaruh LOV source.error message : " + exc.getMessage()); 
		}
		
		
	}
	
	
	/**
	 * worker untuk menaruh lomv source
	 * @param theValue key 
	 * @param theLabel label untuk auto complete
	 * @param theDescription description auto complete
	 * @param rawData data original
	 **/
	private native void pushLOV (String theValue , String theLabel , String theDescription , DATA rawData)/*-{
		var swap = {
			value: theValue,
			label: theLabel,
			desc: theDescription ,
			originalData : rawData
		};
		this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::autocompleteArg["source"].push(swap);
		 
	}-*/;
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	protected String generateLabelBridget (Object data) {
		return this.currentDataExtractor.generateHTMLStringForAutocompleteNode((DATA)data);
	}
	
	
	protected native void renderAutoComplete() /*-{
		$wnd["LATEST_AUTO_COMPLETE_RENDER"] = this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::autocompleteArg;
		try{
			var swapThis = this ; 
			var idPagar = this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::elementId ;  
			// focus handler
			this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::autocompleteArg["focus"] = function (event, ui) {
				$wnd.$("#" + idPagar ).val(ui.item.label) ;
				return false ;  	
			} ; 
			// focus handler
			this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::autocompleteArg["select"] = function (event, ui) {
				$wnd.$("#" + idPagar ).val(ui.item.label) ; 	
				return false ; 
			} ;
			
			
			
			$wnd.$("#" + idPagar ).autocomplete(
				this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::autocompleteArg
			)
			.data( "autocomplete" )._renderItem = 
				function( ul, item ) {
					 
					var generatedLabel = swapThis.@id.co.gpsc.jquery.client.form.JQAutoCompleteExtended::generateLabelBridget(Ljava/lang/Object;)( item.originalData)  ; 
					return $wnd.$( "<li></li>" )
						.data( "item.autocomplete", item )
						.append(   generatedLabel )
						.appendTo( ul );
			};
		}
		catch(exc){
			alert(exc.message);
		}
		
	
	
	}-*/;

	
	
	
	
}
