package id.co.gpsc.common.client.form;

import java.util.ArrayList;

import id.co.gpsc.jquery.client.util.JQueryUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * ini tombol image dengan span
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public class SimpleSpanImageButton extends Widget{
	
	
	public static final String OUTMOST_CSS_NAME="common_control_icon-place"; 
	
	
	private Image imageEnabledElement ; 
	
	private Image imageDisabledElement ;
	
	
	
	
	/**
	 * enable/disabled dari button
	 **/
	private boolean enabled = true ; 
	
	
	private ArrayList<Command> clickHandlers = new ArrayList<Command>(); 
	
	
	/**
	 * construct button, ini secara default akan mempergunakan icon browse
	 **/
	public SimpleSpanImageButton(){
		Element span = DOM.createSpan(); 
		span.setId(DOM.createUniqueId());
		span.setClassName(OUTMOST_CSS_NAME);
		setElement(span);
		
	}
	
	
	boolean attached1StTimeTaskRun = false ; 
	@Override
	protected void onAttach() {
		
		super.onAttach();
		if (! attached1StTimeTaskRun){
			attached1StTimeTaskRun = true ; 
			new Timer() {
				
				@Override
				public void run() {
					JQueryUtils.getInstance().appendClickHandler(getElement().getId(), new Command() {
						@Override
						public void execute() {
							
							if ( !enabled){
								GWT.log("maaf saya disabled, tidak melayani click");
								return ; 
							}
							for ( Command scn : clickHandlers){
								scn.execute(); 
							}
							
						}
					});
					
				}
			}.schedule(100);
			
		}
		
	}
	/**
	 * actual recommended constructor, dengan icon enabled + disabled one
	 **/
	public SimpleSpanImageButton(Image enabledImage , Image disabledImage){
		this();
		this.imageEnabledElement =  enabledImage ; 
		this.imageDisabledElement = disabledImage ; 
		 getElement().appendChild(enabledImage.getElement());
		getElement().appendChild(disabledImage.getElement());
		disabledImage.getElement().getStyle().setDisplay(Display.NONE);
		
			
	}
	
	
	/**
	 * constructor dengan resource bundle
	 **/
	public SimpleSpanImageButton(ImageResource enabledImage , ImageResource disabledImage){
		this(AbstractImagePrototype.create(enabledImage).createImage() , AbstractImagePrototype.create(disabledImage).createImage());
	}
	
	
	/**
	 * register click handler
	 **/
	public HandlerRegistration addClickHandler(final Command clickHandler){
		if ( clickHandlers.contains(clickHandler))
			return null ; 
		clickHandlers.add(clickHandler);
		return new HandlerRegistration() {
			
			@Override
			public void removeHandler() {
				clickHandlers.remove(clickHandler);
				
			}
		};
	}
	
	/**
	 * enable/disabled dari button
	 **/
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * enable/disabled dari button
	 **/
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		imageEnabledElement.getElement().getStyle().setProperty("display", enabled? "":"none");
		imageDisabledElement.getElement().getStyle().setProperty("display", !enabled? "":"none");
		if(enabled){
			getElement().setClassName(OUTMOST_CSS_NAME);
		}else{
			getElement().setClassName("");
		}
	}

}
