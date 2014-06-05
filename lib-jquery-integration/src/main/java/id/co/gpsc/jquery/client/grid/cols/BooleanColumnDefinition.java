package id.co.gpsc.jquery.client.grid.cols;

import com.google.gwt.core.client.JavaScriptObject;

import id.co.gpsc.jquery.client.grid.GridHorizontalAlign;
import id.co.gpsc.jquery.client.util.NativeJsUtilities;








/**
 * grid column definition dengan boolean. true akan di render checked, false akan di render un-checked
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @since 18-aug-2012
 **/
public abstract class BooleanColumnDefinition<DATA> extends BaseColumnDefinition<DATA, Boolean> {

	public BooleanColumnDefinition(String headerLabel, int columnWidth) {
		super(headerLabel, columnWidth);
		
	}
	
	
	public BooleanColumnDefinition(String headerLabel, int columnWidth, String i18Key) {
		super(headerLabel, columnWidth, i18Key);
		
	}

	@Override
	public String getFormatterType() {
		return "checkbox";
	}

	@Override
	public GridHorizontalAlign getHorizontalAlign() {
		return GridHorizontalAlign.center;
	}
	@Override
	public String getSorttype() {
		return "text";
	}

	@Override
	public void extractAndPutDataToObject(DATA data,
			JavaScriptObject targetObject) {
		Boolean swap = getData(data);
		
		if ( swap==null)
			swap=false ;
		NativeJsUtilities.getInstance().putObject(targetObject, rawJsDataName, swap);
		
	}

	

	
	
	
}
