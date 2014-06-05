package id.co.gpsc.jquery.client.form;

import id.co.gpsc.common.form.BaseFormElement;

import java.text.ParseException;
import java.util.List;

import com.google.gwt.text.shared.testing.PassthroughParser;
import com.google.gwt.text.shared.testing.PassthroughRenderer;




/**
 * jquery auto complete textbox. 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class JQAutoComplete extends JQBaseAutoComplete<String> implements BaseFormElement{
	
	
	public JQAutoComplete(){
		super(PassthroughRenderer.instance(), PassthroughParser.instance());
	}
	

	
	
	
	/**
	 * menaruh data ke dalam LOV. tipe di taruh : arraylist
	 **/
	public void setLOVSource (List<String> lovData){
		resetArray() ;
		if( lovData!=null&& !lovData.isEmpty()){
			for ( String scn : lovData){
				putToArray(scn);
			}
		}
		if ( attached)
			triggerOption(elementId, "source", dataSource);
		
	}
	
	
	/**
	 * menaruh data ke dalam LOV. tipe di taruh : array 
	 **/
	public void setLOVSource(String[] lovData){
		resetArray() ;
		if( lovData!=null&& lovData.length>0){
			for ( String scn : lovData){
				putToArray(scn);
			}
		}
		if ( attached)
			triggerOption(elementId, "source", dataSource);
	}
	
	@Override
	public String getValueOrThrow() throws ParseException {
		return this.getStringValue();
	}
	
	@Override
	public void setValue(String value, boolean fireEvents) {
		super.setValue(value, fireEvents);
	}
	


}
