package id.co.gpsc.common.client.form;

import com.google.gwt.user.client.DOM;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;



/**
 * simple readonly label, basis span untuk menaruh label readonly
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class SimpleReadonlyLabel extends Widget{
	
	
	
	/**
	 * prefix id dari text
	 **/
	public static final String ID_PREFIX ="SIMPLE_READONLY_TEXT_";
	
	public SimpleReadonlyLabel(){
		Element span =DOM.createSpan() ; 
		setElement(span);
		span.setId(ID_PREFIX + DOM.createUniqueId()); 
	}
	
	
	
	/**
	 * representasi text dari elemen
	 **/
	public String getText () {
		return getElement().getInnerText();
	}
	
	/**
	 * plug string. di interpretasikan as text
	 **/
	public void setText (String text) {
		getElement().setInnerText(text);
	}
	
	
	
	/**
	 * set inner HTML dari label
	 **/
	public String getHTML () {
		return getElement().getInnerHTML();
	}
	
	
	/** 
	 * set inner HTML dari label
	 **/
	public void setHTML (String html) {
		getElement().setInnerHTML(html);
	}

}
