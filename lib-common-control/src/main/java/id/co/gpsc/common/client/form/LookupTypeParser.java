package id.co.gpsc.common.client.form;

import java.text.ParseException;

import com.google.gwt.text.shared.Parser;



/**
 * dummy parser, pass ke parser lain
 **/
public class LookupTypeParser<T> implements Parser<T>{
	
	
	
	public LookupTypeParser(){}
	
	
	private Parser<T> actualParser ; 

	@Override
	public T parse(CharSequence data) throws ParseException {
		return actualParser.parse(data);
	}

	public Parser<T> getActualParser() {
		return actualParser;
	}

	public void setActualParser(Parser<T> actualParser) {
		this.actualParser = actualParser;
	}

}
