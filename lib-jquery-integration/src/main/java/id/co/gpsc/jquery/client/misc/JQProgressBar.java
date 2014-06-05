package id.co.gpsc.jquery.client.misc;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

import id.co.gpsc.jquery.client.BaseJqueryWidget;



/**
 * 
 * Progress bar widget
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public class JQProgressBar extends BaseJqueryWidget{

	
	private static final String CLAZZ_NAME="progressbar";
	
	
	private   final String nodeId = DOM.createUniqueId();
	
	
	@Override
	protected Element generateUnderlyingElement() {
		Element div = DOM.createDiv();
		div.setId(nodeId);
		return div;
	}
	@Override
	protected String getJQueryClassName() {
		return CLAZZ_NAME;
	}
	
	
	
	/**
	 * set value dari progress bar. 0 s.d 100
	 * @param value nilai dari progress bar. 0 s.d 100
	 **/
	public void setValue (int value) {
		if ( value>100)
			value = 100 ; 
		if ( value<0)
			value=0; 
		//FIXME : setvalue masih belum bekerja
		triggerOption("value", value);
	}
	
	
	
	
	/**
	 * getter. membaca nilai current dari progress bar
	 **/
	public  int getValue (){
		return this.triggerIntegerReturnMethod(nodeId, getJQueryClassName(), "value");
	}

}
