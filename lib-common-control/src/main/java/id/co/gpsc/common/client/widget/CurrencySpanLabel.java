package id.co.gpsc.common.client.widget;

import id.co.gpsc.common.client.util.FormatingUtils;

import java.math.BigDecimal;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * span dedicated untuk menampilkan currency
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class CurrencySpanLabel extends Widget{

	
	/**
	 * lebar span. dalam PX
	 */
	public static int LABEL_WIDTH_IN_PX =100 ; 
	
	
	
	private Element divElement ; 
	
	public CurrencySpanLabel(){
		super(); 
		Element e = DOM.createSpan(); 
		e.setId(DOM.createUniqueId());
		setElement(e);
		
		divElement = DOM.createDiv(); 
		e.appendChild(divElement); 
		divElement.getStyle().setWidth(LABEL_WIDTH_IN_PX, Unit.PX);
		divElement.getStyle().setTextAlign(com.google.gwt.dom.client.Style.TextAlign.RIGHT);
		
	}
	
	
	private BigDecimal currentValue ; 
	
	
	
	
	
	
	public BigDecimal getValue () {
		return currentValue ; 
	}
	
	
	/**
	 * set value ke label
	 */
	public void setValue (BigDecimal value) {
		this.currentValue = value ; 
		if ( value== null)
			divElement.setInnerHTML("");
		else
			divElement.setInnerHTML(FormatingUtils.getInstance().formatCurrency(currentValue)); 
		
	}
	
	
	
	
	
	
}