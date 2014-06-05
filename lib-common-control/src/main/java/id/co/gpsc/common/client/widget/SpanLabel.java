package id.co.gpsc.common.client.widget;



import java.util.Date;

import id.co.gpsc.common.client.style.CommonResourceBundle;
import id.co.gpsc.common.client.util.FormatingUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * label dengan base element = Span. ini untuk mengurangi ketergantungan layout dengan div, yang ada line break sesudah ya
 * Tipe yang di terima
 * <ol>
 * <li>String</li>
 * <li>Date</li>
 * <li>Integer</li>
 * </ol>
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class SpanLabel extends Widget{

	/**
	 * formater date sederhana. timpa ini kalau anda memerlukan formater yang bukan dd/MM/yy"
	 */
	public static DateTimeFormat SHORT_DATE_FORMATER = DateTimeFormat.getFormat("dd/MM/yy");
	
	public SpanLabel(){
		super(); 
		Element e = DOM.createSpan(); 
		e.setId(DOM.createUniqueId());
		setElement(e);
		
	}
	
	
	
	/**
	 * menaruh label dalam node. ini bisa mempergunakan HTML tag
	 **/
	public void setLabel(String label) {
		if ( label==null)
			label="" ; 
		getElement().setInnerHTML(label);  
		
	}
	
	
	/**
	 * menaruh date ke dalam label. ini otomatis melakukan formating terhadap data
	 * @param label 
	 */
	public void setLabel(Date value) {
		if ( value== null)
			setLabel("");
		else
			setLabel(SHORT_DATE_FORMATER.format(value));
	}
	
	
	/**
	 * render angka ke dalam label
	 */
	public void setLabel(Integer value) {
		if ( value== null)
			setLabel("");
		else{
			setLabel(  FormatingUtils.getInstance().format(value)); 
		}
		
	}
	
	/**
	 * label dari node ini.
	 **/
	public String getLabel(){
		return getElement().getInnerHTML(); 
	}
	
	
	
	private Element imageElement ; 
	
	
	
	
	
	
	/**
	 * mematikan animasi 
	 */
	public void removeAnimatedImage () {
		if ( !isAttached() || imageElement== null)
			return ; 
		imageElement.removeFromParent();  
		imageElement = null ; 
		
	}
	
	
	
	/**
	 * menaruh icon loading wheel. (untuk menunjukan loading)
	 */
	public void putLoadingWheelAnimationImage () {
		 putAnimateImage(  AbstractImagePrototype.create(CommonResourceBundle.getResources().iconLoadingWheel()).createImage()); 
	}
	
	
	/**
	 * hidupkan animasi loading
	 */
	public void putAnimateImage (Image animationImage) {
		if (! isAttached())
			return ; 
		if ( imageElement== null){
			 imageElement = DOM.createSpan(); 
			 getElement().getParentElement().insertAfter(imageElement, getElement()); 
		}
		
		if ( imageElement.getChildCount()>1){
			for ( int i = imageElement.getChildCount()-1 ; i>=0 ; i--){
				imageElement.getChild(i).removeFromParent();
			}
		}
		imageElement.appendChild(animationImage.getElement());
			
	}
	
	
	
}

