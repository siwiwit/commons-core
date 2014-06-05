package id.co.gpsc.jquery.client.grid.cols;

import com.google.gwt.core.client.JavaScriptObject;

import id.co.gpsc.jquery.client.grid.GridHorizontalAlign;
import id.co.gpsc.jquery.client.util.NativeJsUtilities;



/**
 * grid column dengan tipe data String
 **/
public abstract class StringColumnDefinition<DATA> extends BaseColumnDefinition<DATA, String>{

	public StringColumnDefinition(String headerLabel, int columnWidth) {
		super(headerLabel, columnWidth);
	}
	
	
	
	
	
	
	public StringColumnDefinition(String headerLabel, int columnWidth, String i18Key) {
		super(headerLabel, columnWidth , i18Key);
		
	}


	@Override
	public String getFormatterType() {
		return null;
	}
	
	@Override
	public String getSorttype() {
		return "text";
	}
	@Override
	public GridHorizontalAlign getHorizontalAlign() {
		return GridHorizontalAlign.left;
	}
	

	@Override
	public void extractAndPutDataToObject(DATA data,
			JavaScriptObject targetObject) {
		String swap = getData(data);
		if ( swap==null){
			NativeJsUtilities.getInstance().putObject(targetObject, rawJsDataName , "");
			return ;
		}
		NativeJsUtilities.getInstance().putObject(targetObject, rawJsDataName , swap);
		
	}
}
