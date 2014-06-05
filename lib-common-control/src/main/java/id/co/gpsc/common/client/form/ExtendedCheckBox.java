package id.co.gpsc.common.client.form;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.control.IFormElementConfiguration;
import id.co.gpsc.common.client.control.OnScreenConfigurableControl;
import id.co.gpsc.common.client.util.OnScreenConfigurationUtils;
import id.co.gpsc.common.form.BaseFormElement;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.SimpleCheckBox;


/**
 * wrapper checkbox
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * 
 **/
public class ExtendedCheckBox extends SimpleCheckBox implements BaseFormElement,OnScreenConfigurableControl  {

	@Override
	public void setDomId(String id) {
		ensureDebugId(id);
	}

	@Override
	public String getDomId() {
		return getElement().getId();
	}
	
	@Override
	protected void onAttach() {
		if ( getElement().getId()==null||getElement().getId().length()==0)
			getElement().setId(DOM.createUniqueId());
		getElement().setPropertyObject(CommonClientControlConstant.TAG_KEY_SEL_REF, this);
		super.onAttach();
		
		//FIXME: ini belum complye dengan on screen editor
	}
	
	@Override
	protected void onDetach() {	
		super.onDetach();
		try {
			OnScreenConfigurationUtils.getInstance().hideOnScreenEditorControl(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String groupId;
	private String configurationId;
	private String parentFormId;	
	private String formConfigurationId;
	private boolean isEditable;
	
	@Override
	public void showHideEditConfigButton(boolean show) {
				
	}

	@Override
	public String getGroupId() {		
		return this.groupId;
	}

	@Override
	public void setGroupId(String groupId) {
		this.groupId = groupId;		
	}

	@Override
	public void setOnScreenConfigurationId(String configurationId) {
		this.configurationId = configurationId;
	}

	@Override
	public String getOnScreenConfigurationId() {		
		return this.configurationId;
	}

	@Override
	public void setParentFormConfigurationId(String parentFormId) {
		this.parentFormId = parentFormId;	
	}

	@Override
	public String getParentFormConfigurationId() {		
		return this.parentFormId;
	}

	@Override
	public String getGroupFormConfiguration() {		
		return this.formConfigurationId;
	}

	@Override
	public void setOnScreenGroupFormConfiguration(String groupId) {
		this.formConfigurationId = groupId;		
	}

	@Override
	public void setOnScreenEditable(boolean state) {
		this.isEditable = state;
	}

	@Override
	public boolean isOnScreenEditable() {		
		return isEditable;
	}

	@Override
	public void assignConfigurationData(
			IFormElementConfiguration formConfiguration) {
		OnScreenConfigurationUtils.getInstance().applyConfiguration(this, formConfiguration);
	}

	
}
