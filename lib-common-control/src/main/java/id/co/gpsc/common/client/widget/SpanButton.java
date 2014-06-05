package id.co.gpsc.common.client.widget;


import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM; 
import com.google.gwt.user.client.ui.Widget;

/**
 * tombol dengan basis Span, ada hover dan click. berbasis jquery UI icon
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class SpanButton extends Widget{
	
	
	
	/**
	 * css untuk status norman
	 **/
	public static String CSS_BOUNDRY_NORMAL ="ui-state-default";
	
	
	public static String CSS_BOUNDRY_DISABLED ="ui-state-disabled";
	
	/**
	 * css yang di pergunakan untuk icon ini
	 **/
	private String buttonIconCssName ="ui-icon-document"; 
	
	private ArrayList<ClickHandler> clickHandlers = new ArrayList<ClickHandler>(); 
	
	
	/**
	 * tombol enabled atau tidak
	 **/
	private boolean enabled = true ; 
	
	public SpanButton(){
		super() ;
		
		
		
		final Element e =DOM.createSpan()  ;  
		setElement(e);
		
		//e.setAttribute("cursor" ,  "pointer");
		e.addClassName("ui-icon");
		e.setId(DOM.createUniqueId());
		getElement().addClassName(buttonIconCssName);
	
		MouseOutHandler mo = new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				if ( enabled)
					e.removeClassName(CSS_BOUNDRY_NORMAL);
			}
		};
		addDomHandler(mo, MouseOutEvent.getType());
		MouseOverHandler mhHandler = new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if ( enabled)
					e.addClassName( CSS_BOUNDRY_NORMAL);
				
			}
		};
		addDomHandler(mhHandler,MouseOverEvent.getType());
		
		addDomHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if ( !enabled)
					return ; 
				for ( ClickHandler scn : clickHandlers){
					scn.onClick(event);
				}
			}
		}, ClickEvent.getType());
				 
	}
	
	
	
	
	@Override
	public void setVisible(boolean visible) {
		if ( visible){
			GWT.log("display button id : " + getElement().getId());
			getElement().getStyle().setProperty("display", "");
			GWT.log("skr  : " + getElement().getStyle().getProperty("display"));
		}
		else{
			getElement().getStyle().setProperty("display", "none");
		}
	}
	
	
	private native void showHelper (String id ) /*-{
		$wnd.$("#" + id).show() ; 
	}-*/;
	
	
	private native void hideHelper (String id ) /*-{
		$wnd.$("#" + id).hide() ; 
	}-*/;
	/**
	 * register click handler ke dalam tombol
	 **/
	public HandlerRegistration addClickHandler (final ClickHandler clickHandler) {
		clickHandlers.add(clickHandler); 
		return new HandlerRegistration() {
			
			@Override
			public void removeHandler() {
				clickHandlers.remove(clickHandler);
				
			}
		};
		
		
		
	}
	
	
	
	
	
	/**
	 * css yang di pergunakan untuk icon ini
	 **/
	public void setButtonIconCssName(String buttonIconCssName) {
		if ( this.buttonIconCssName!= null && !this.buttonIconCssName.isEmpty()){
			try {
				getElement().removeClassName( this.buttonIconCssName); 
			} catch (Exception e) {
				GWT.log("gagal remove class["+buttonIconCssName+"] .error :" + e.getMessage() );
			}
		}
		this.buttonIconCssName = buttonIconCssName;
		getElement().addClassName(buttonIconCssName);
	}
	
	/**
	 * css yang di pergunakan untuk icon ini
	 **/
	public String getButtonIconCssName() {
		return buttonIconCssName;
	}

	/**
	 * tombol enabled atau tidak
	 **/
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (!enabled){
			getElement().addClassName(CSS_BOUNDRY_DISABLED);
		}
		else
			getElement().removeClassName(CSS_BOUNDRY_DISABLED);
	}
	/**
	 * tombol enabled atau tidak
	 **/
	public boolean isEnabled() {
		return enabled;
	}
}

