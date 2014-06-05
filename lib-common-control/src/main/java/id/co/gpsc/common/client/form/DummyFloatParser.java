package id.co.gpsc.common.client.form;


import java.text.ParseException;

import com.google.gwt.text.shared.Parser;


/**
 * parser float. ini untukpenyempurnaan parser data
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id 
 **/
public class DummyFloatParser implements Parser<Float>{
	
	
	
	private ValueProvider<Float> valueProvider ; 
	public ValueProvider<Float> getValueProvider() {
		return valueProvider;
	}
	public void setValueProvider(ValueProvider<Float> valueProvider) {
		this.valueProvider = valueProvider;
	}
	@Override
	public Float parse(CharSequence theCar) throws ParseException {
		return valueProvider.getValue();
	}
	

}
