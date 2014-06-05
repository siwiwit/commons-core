package id.co.gpsc.common.data.impl;




import id.co.gpsc.common.data.DataConverter;



/**
 * String to double converter
 **/
public class FloatDataConverter extends DataConverter<Float> {

	
	
	@Override
	public Float translateData(String stringRepresentation) {
		try {
			return Float.parseFloat(stringRepresentation); 
		} catch (Exception e) {
			e.printStackTrace(); 
			return null;
		}
		
	}

}
