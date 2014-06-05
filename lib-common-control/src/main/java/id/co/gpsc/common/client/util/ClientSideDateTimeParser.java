package id.co.gpsc.common.client.util;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.i18n.client.DateTimeFormat;

import id.co.gpsc.common.util.json.CrossDateTimeParser;



/**
 * parser date client side
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public final class ClientSideDateTimeParser implements CrossDateTimeParser{

	
	
	
	private HashMap<String, DateTimeFormat> indexedDateTimeFormat = new HashMap<String, DateTimeFormat>(); 
	
	
	private static ClientSideDateTimeParser instance ; 
	
	
	/**
	 * singleton instance
	 **/
	public static ClientSideDateTimeParser getInstance() {
		if ( instance ==null)
			instance = new ClientSideDateTimeParser(); 
		return instance;
	}
	
	private ClientSideDateTimeParser(){}
	
	
	@Override
	public String format(Date date, String dateFormat) {
		if ( date==null)
			return null ; 
		if (! indexedDateTimeFormat.containsKey( dateFormat))
			indexedDateTimeFormat.put(dateFormat, DateTimeFormat.getFormat(dateFormat));  
		
		return indexedDateTimeFormat.get(dateFormat).format(date);
	}

	@Override
	public Date parse(String dateAsString, String dateFormat) throws Exception{
		if ( dateAsString==null||dateAsString.length()==0)
			return null ; 
		if ( dateFormat==null||dateFormat.length()==0)
			return null ;
		if (! indexedDateTimeFormat.containsKey(dateFormat))
			indexedDateTimeFormat.put(dateFormat, DateTimeFormat.getFormat(dateFormat));
		return indexedDateTimeFormat.get(dateFormat).parse(dateAsString);
	}

}
