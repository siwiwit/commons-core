package id.co.gpsc.common.client.form.advance;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.form.BrowseTextBox;

import java.util.List;



/**
 * textbox dengan browse button with label
 * @author ashadi.pratama
 * @version $id
 * @since 23-11-2012
 **/
public abstract class BrowseTextBoxWithLabel<ENTITY> extends BrowseTextBox<ENTITY> implements AutomaticLabeledControl{
	
	private String myLabel="Browse Text Label";
	private boolean isPoint = true;
	private String i18nKey;
	
	private Element label;
	private Element point;
	
	public String getMyLabel() {
		return myLabel;
	}

	public void setMyLabel(String defaultLabel) {
		this.myLabel = defaultLabel;
	}

	public boolean isPoint() {
		return isPoint;
	}

	public void setPoint(boolean isPoint) {
		this.isPoint = isPoint;
	}

	public Element getLabel() {
		return label;
	}

	public void setLabel(Element label) {
		this.label = label;
	}

	public Element getPoint() {
		return point;
	}

	public void setPoint(Element point) {
		this.point = point;
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		
		if(getElement().getId()==null && getElement().getId().length()==0){
			getElement().setId(DOM.createUniqueId());
                }
		List<Element> result = AdvanceControlUtil.getInstance().createdLabelAndPoint(getElement().getPropertyString(CommonClientControlConstant.I18_KEY_ON_DOM), isPoint, myLabel, getElement());
		if(!result.isEmpty()){
			if(isPoint){
				this.point = result.get(0);
				this.label = result.get(1);
			}else{
				this.label = result.get(0);
			}
		}
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		if(isPoint){
			point.removeFromParent();
		}
		label.removeFromParent();
	}

	@Override
	public void addCssToLabel(String cssName) {
		this.label.addClassName(cssName);
	}

	@Override
	public void removeCssFromLabel(String cssName) {
		this.label.removeClassName(cssName);
	}

	@Override
	public void addCssToPoint(String cssName) {
		this.point.addClassName(cssName);
	}

	@Override
	public void removeCssFromPoint(String cssName) {
		this.point.removeClassName(cssName);
	}

	@Override
	public String getCssToLabel() {
		return label.getClassName();
	}

	@Override
	public void setCssToLabel(String cssName) {
		this.label.setClassName(cssName);
	}

	@Override
	public String getCssToPoint() {
		return point.getClassName();
	}

	@Override
	public void setCssToPoint(String cssName) {
		this.point.setClassName(cssName);
	}
	
	@Override
	public void setVisible(boolean visible) {
            super.setVisible(visible);
            if ( label!=null){
                label.getStyle().setProperty("display", visible?"":"none");
            }        
            if ( point!=null){
                point.getStyle().setProperty("display", visible?"":"none");
            }        
	}

	public String getI18nKey() {
		return i18nKey;
	}

	public void setI18nKey(String i18nKey) {
		this.i18nKey = i18nKey;
	}
	
	
}
