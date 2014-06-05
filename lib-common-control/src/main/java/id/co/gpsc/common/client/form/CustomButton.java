package id.co.gpsc.common.client.form;



import id.co.gpsc.common.client.CommonClientControlConstant;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ButtonBase;

public class CustomButton extends ButtonBase{

	protected CustomButton() {
		super(DOM.createInputText());
		getElement().setPropertyString("type", "button" );
		setStyleName("button");
	}
	
	@Override
	public void setText(String text) {
		getElement().setPropertyString("value", text);
	}

	@Override
	public String getText() {
		return getElement().getPropertyString("value");
	}
	 
	@Override
	public void setHTML(String text) {
		getElement().setPropertyString("value", text);
	}

	@Override
	public String getHTML() {
		return getElement().getPropertyString("value");
	}
	
	
	
	@Override
	protected void onAttach() {
		if ( getElement().getId()==null||getElement().getId().length()==0)
			getElement().setId(DOM.createUniqueId());
		getElement().setPropertyObject(CommonClientControlConstant.TAG_KEY_SEL_REF, this);
		super.onAttach();
	}
	
	
}
