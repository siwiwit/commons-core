package id.co.gpsc.common.client.form;

import id.co.gpsc.common.client.style.CommonResourceBundle;
import id.co.gpsc.common.form.BaseFormElement;
import id.co.gpsc.common.util.I18Utilities;

import java.io.IOException;
import java.text.ParseException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;



/**
 * base class untuk class text box dengan tombol browse + tombol reset. handler untuk tombol browse + reset tidak di define di sini
 * @author <a href="mailto:ashadi.pratama@sigma.co.id">Ashadi Pratama</a>
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * 
 **/
public abstract class BrowseTextBox<ENTITY> extends ValueBoxBase<ENTITY> implements BaseFormElement{

	private static VerticalPanel SWAP_PANEL ; 
	static {
		SWAP_PANEL = new VerticalPanel(); 
		SWAP_PANEL.setVisible(false);
		RootPanel.get().add(SWAP_PANEL);
	}
	protected ENTITY currentEntity ; 
	
	
	protected SimpleSpanImageButton browseButtonElement = new SimpleSpanImageButton(CommonResourceBundle.getResources().searchIcon(), CommonResourceBundle.getResources().searchIconDisabled()) ; 
	protected SimpleSpanImageButton resetButtonElement = new SimpleSpanImageButton(CommonResourceBundle.getResources().iconCancel(), CommonResourceBundle.getResources().iconCancelDisabled()) ; 
	
	private Command browseClickHandler ; 
	private Command resetClickHandler ; 
	
        
         
    
	
	
	/**
	 * title button
	 */
	private String keyTitleBrowse;
	private String keyTitleReset;
	
	protected LookupTypeRenderer<ENTITY> renderer;
	protected LookupTypeParser<ENTITY> parser;
	
	
	public BrowseTextBox(){
		
		this( DOM.createInputText() , new LookupTypeRenderer<ENTITY>() ,new LookupTypeParser<ENTITY>() );
		SWAP_PANEL.add(browseButtonElement); 
		SWAP_PANEL.add(resetButtonElement);
		
	}
	
	protected BrowseTextBox(Element elem, LookupTypeRenderer<ENTITY> renderer,
			LookupTypeParser<ENTITY> parser) {
		super(elem, renderer, parser);
		super.setEnabled(false);
		this.renderer = renderer;
		this.parser = parser;
		renderer.setActualRender(new Renderer<ENTITY>() {
			@Override
			public String render(ENTITY data) {
				return getDataForTextBox(data);
			}

			@Override
			public void render(ENTITY data, Appendable target) throws IOException {
				target.append(getDataForTextBox(data));
			}
		});
		parser.setActualParser(new Parser<ENTITY>() {
			@Override
			public ENTITY parse(CharSequence data) throws ParseException {
				return currentEntity;
			}
		});
                
               
		
	}
        
        
        
        

	public Command getBrowseClickHandler() {
		return browseClickHandler;
	}
	
	
	
	
	private HandlerRegistration currentBrowseHandler ; 
	private HandlerRegistration currentResetHandler ;

	public void setBrowseClickHandler(Command browseClickHandler) {
		this.browseClickHandler = browseClickHandler;
		if ( currentBrowseHandler!=null)
			currentBrowseHandler.removeHandler(); 
		this.currentBrowseHandler =  browseButtonElement.addClickHandler(browseClickHandler);
	}
	
	public Command getResetClickHandler() {
		return resetClickHandler;
	}

	public void setResetClickHandler(Command resetClickHandler) {
		this.resetClickHandler = resetClickHandler;
		if ( currentResetHandler!=null)
			currentResetHandler.removeHandler(); 
		this.currentResetHandler =  resetButtonElement.addClickHandler(resetClickHandler);
	}

	public String getKeyTitleBrowse() {
		return keyTitleBrowse;
	}

	public void setKeyTitleBrowse(String keyTitleBrowse) {
		this.keyTitleBrowse = keyTitleBrowse;
	}

	public String getKeyTitleReset() {
		return keyTitleReset;
	}

	public void setKeyTitleReset(String keyTitleReset) {
		this.keyTitleReset = keyTitleReset;
	}

	@Override
	public ENTITY getValue() {
		return currentEntity;
	}
	
	@Override
	public void setValue(ENTITY value) {
		this.currentEntity = value;
		setText(renderer.render(value));
	}
	
	@Override
	public void setValue(ENTITY value, boolean fireEvents) {
		this.currentEntity = value;
		ENTITY oldValue = getValue();
	    setText(renderer.render(value));
	    if (fireEvents) {
	      ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
	    }
	}
	
	@Override
	public ENTITY getValueOrThrow() throws ParseException {
		
		return currentEntity;
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		getElement().getParentElement().insertAfter(resetButtonElement.getElement(), getElement());
		getElement().getParentElement().insertAfter(browseButtonElement.getElement(), getElement());
		
		
	}
	
	
	protected void addButton (Element buttonElement){
		getElement().getParentElement().insertAfter(buttonElement, getElement());
	}
	
	@Override
	protected void onDetach() {
		try {
			if(browseButtonElement!=null)
				browseButtonElement.removeFromParent();
			if(resetButtonElement!=null)
				resetButtonElement.removeFromParent();
		} catch (Exception e) {
			GWT.log("gagal remove tombol reset dan browse.error : " + e.getMessage() , e); 
		}
		super.onDetach();
		
	}


	@Override
	public void setDomId(String id) {
		getElement().setAttribute("id", id);
		
	}


	@Override
	public String getDomId() {
		return getElement().getAttribute("id");
	}

	/**
	 * tombol reset .visible atau tidak
	 **/
	public boolean isShowResetButton() {
		return resetButtonElement.isVisible();
	}

	/**
	 * tombol reset .visible atau tidak
	 **/
	public void setShowResetButton(boolean showResetButton) {
		resetButtonElement.setVisible(showResetButton);
		
		
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		browseButtonElement.setEnabled(enabled); 
		resetButtonElement.setEnabled(enabled); 
			
	}
	
	
	
	
	
	protected native void bindClickHandler (String buttonId , Command clickHandler)/*-{
		$wnd.$("#" + buttonId).click(function () {
		//$wnd.alert("button id : " + buttonId + ", di click");
			clickHandler.@com.google.gwt.user.client.Command::execute()();
		});
	
	}-*/;
	
	
	
	
	private String getTitleI18Button(String key){
		return I18Utilities.getInstance().getInternalitionalizeText(key);
	}
	
	
	/**
	 * baca string untuk textbox
	 **/
	protected abstract String getDataForTextBox(ENTITY data);
	
	/**
	 * menambahkan show hide untuk button browse dan button reset
	 */
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		browseButtonElement.setVisible(visible);
		resetButtonElement.setVisible(visible);
	}
        
        
        
   
}
