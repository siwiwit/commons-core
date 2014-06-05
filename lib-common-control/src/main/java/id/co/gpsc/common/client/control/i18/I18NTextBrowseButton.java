package id.co.gpsc.common.client.control.i18;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.dom.client.Element;

import id.co.gpsc.common.client.control.lookup.BaseSimpleSingleResultLookupDialog;
import id.co.gpsc.common.client.form.BrowseTextBoxWithSingleResultLookup;
import id.co.gpsc.common.client.form.SimpleSpanImageButton;
import id.co.gpsc.common.client.form.i18.I18TextEditor;
import id.co.gpsc.common.client.style.CommonResourceBundle;
import id.co.gpsc.common.client.util.CommonClientControlUtil;
import id.co.gpsc.common.control.SingleValueLookupResultHandler;
import id.co.gpsc.common.data.entity.I18Text;
import id.co.gpsc.common.data.entity.I18TextPK;
import id.co.gpsc.common.util.I18Utilities;

/**
 * 
 * button brows untuk i18 text
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public class I18NTextBrowseButton extends BrowseTextBoxWithSingleResultLookup<I18TextPK, I18Text>{

	
	
	/**
	 *  ini tombol untuk menambah i18 text
	 **/
	private SimpleSpanImageButton addTextButton; 
	
	
	private Element currentTextLabel ; 
	
	
	
	private String currentI18NTextKey ;
	
	public I18NTextBrowseButton(){
		super(); 
		//FIXME: pls perbaiki icon disabled
		addTextButton =new SimpleSpanImageButton(CommonResourceBundle.getResources().iconPlus() ,CommonResourceBundle.getResources().iconPlus() );
	
		CommonClientControlUtil.getInstance().addWidgetToSwapContainer(addTextButton);
		addTextButton.addClickHandler(new Command() {
			
			@Override
			public void execute() {
				I18TextEditor.getInstance().showEditorDialog(null);
				
			}
		});
		currentTextLabel = DOM.createSpan();  
		super.setLookupHandler(new SingleValueLookupResultHandler<I18Text>() {
			
			@Override
			public void onSelectionDone(I18Text data) {
				
				if ( localLookupHandler!=null)
					localLookupHandler.onSelectionDone(data);
				setValue(  data==null? null : data.getId().getTextKey());
				currentI18NTextKey = data==null? null : data.getId().getTextKey(); 
			}
		});
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * versi ini untuk mengeset hanya ID data
	 **/
	public void setValue(String i18NCode) {
		currentTextLabel.setInnerHTML( "<br/><i>Label:</i>" +  I18Utilities.getInstance().getInternalitionalizeText(i18NCode, ""));
		
		this.currentI18NTextKey=i18NCode ;
		setText(i18NCode==null?"":i18NCode);
	}
	
	
	
	
	/**
	 * current selected i18 N key
	 **/
	public String getValueString() {
		return currentI18NTextKey;
	}
	/**
	 * handler lookup
	 **/
	private SingleValueLookupResultHandler<I18Text> localLookupHandler  ; 
	@Override
	public void setLookupHandler(
			SingleValueLookupResultHandler<I18Text> lookupHandler) {
		localLookupHandler = lookupHandler ; 
	}
	
	@Override
	public SingleValueLookupResultHandler<I18Text> getLookupHandler() {
		return localLookupHandler;
	}
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		addTextButton.setEnabled(enabled);
	}
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		addTextButton.setVisible(visible);
	}
	@Override
	protected BaseSimpleSingleResultLookupDialog<I18TextPK, I18Text> instantiateLookup() {
		return new I18NTextLookup();
	}

	@Override
	protected String getDataForTextBox(I18Text data) {
		return data.getId().getTextKey();
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		getElement().getParentElement().insertAfter(addTextButton.getElement(), resetButtonElement.getElement());
		getElement().getParentElement().insertAfter(currentTextLabel, addTextButton.getElement());
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		try {
			addTextButton.getElement().removeFromParent();
			currentTextLabel.removeFromParent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
