package id.co.gpsc.common.client.control.worklist;



import id.co.gpsc.common.client.control.IFormElementConfiguration;
import id.co.gpsc.common.client.control.OnScreenConfigurableControl;
import id.co.gpsc.common.client.util.OnScreenConfigurationUtils;
import id.co.gpsc.common.control.ResourceBundleConfigurableControl;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;



/**
 * konfigurasi i18 support untuk column definition. 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class I18ColumnDefinition<DATA> implements ResourceBundleConfigurableControl , OnScreenConfigurableControl{
	
	
	private String i18Key ; 
	
	private BaseColumnDefinition<DATA, ?> columnDefinition; 
	/**
	 * id on screen configuration
	 **/
	private String onScreenConfigurationId  ; 
	
	/**
	 *  id dari form di mana dia di taruh. ini di pergunakan untuk load/save. ini di inject oleh container
	 **/
	private String parentFormConfigurationId; 
	/**
	 *  id dari form di mana dia di taruh. ini di pergunakan untuk load/save. ini di inject oleh container
	 **/
	@Override
	public String getParentFormConfigurationId() {
		return parentFormConfigurationId;
	}
	
	/**
	 *  id dari form di mana dia di taruh. ini di pergunakan untuk load/save. ini di inject oleh container
	 **/
	@Override
	public void setParentFormConfigurationId(String parentFormId) {
		this.parentFormConfigurationId = parentFormId ; 
		
	}

/**
	 * id on screen configuration
	 **/
	@Override
	public String getOnScreenConfigurationId() {
		return onScreenConfigurationId;
	}
	
	/**
	 * id on screen configuration
	 **/
	@Override
	public void setOnScreenConfigurationId(String configurationId) {
		this.onScreenConfigurationId = configurationId ; 
		
	}
	
	public I18ColumnDefinition(BaseColumnDefinition<DATA, ?> columnDefinition, String i18LabelKey){
	
		this.columnDefinition=columnDefinition; 
		this.i18Key=i18LabelKey;
	}



	@Override
	public void setI18Key(String key) {
		this.i18Key=key;
		
	}

	@Override
	public String getI18Key() {
		return i18Key;
	}

	@Override
	public void setConfiguredText(String text) {
		columnDefinition.setOverrideLabelHeader(text);
	}

	
	
	@Override
	public void showHideEditConfigButton(boolean show) {
		
	}
	

	private String groupId;
	
	@Override
	public String getGroupFormConfiguration() {		
		return this.groupId;
	}

	@Override
	public void setOnScreenGroupFormConfiguration(String groupId) {
		this.groupId = groupId;		
	}
	
	/**
	 * id group, di inject oleh parent container/form
	 **/
	@Override
	public String getGroupId() {
		return groupId;
	}
	/**
	 * id group, di inject oleh parent container/form
	 **/
	@Override
	public void setGroupId(String groupId) {
		this.groupId = groupId;
		
	}
	
	/**
	 * penanada apakah kontrol boleh di konfigurasi atau tidak
	 */
	private boolean screenEditable = true;

	@Override
	public void setOnScreenEditable(boolean state) {
		this.screenEditable = state;
	}

	@Override
	public boolean isOnScreenEditable() {
		return screenEditable;
	}
	
	@Override
	public void assignConfigurationData(
			IFormElementConfiguration formConfiguration) {
		OnScreenConfigurationUtils.getInstance().applyConfiguration(this, formConfiguration);
	}
}