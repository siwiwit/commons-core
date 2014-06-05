package id.co.gpsc.common.exception;

import id.co.gpsc.common.util.json.IJSONFriendlyObject;
import id.co.gpsc.common.util.json.ParsedJSONContainer;

public class DataValidationException extends BaseIsSerializableException implements IJSONFriendlyObject<DataValidationException> {
	private static final long serialVersionUID = 1934997475583113575L;
	
	public DataValidationException() {
		super();
	}
	
	public DataValidationException(String message) {
		super(message);
	}

	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainer) {
		jsonContainer.put("message", this.message);
		jsonContainer.put("fullStackTrace", this.fullStackTrace);
	}

	@Override
	public DataValidationException instantiateFromJSON(
			ParsedJSONContainer jsonContainer) {
		
		DataValidationException retval = new DataValidationException();
		
		retval.message = jsonContainer.getAsString("message"); 
		retval.fullStackTrace = jsonContainer.getAsString("fullStackTrace");
		
		return null;
	}

}
