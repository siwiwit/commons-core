package id.co.gpsc.common.client.app;

import javax.xml.bind.annotation.XmlElementWrapper;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

import id.co.gpsc.common.client.JSONFriendlyObject;
import id.co.gpsc.common.client.util.ArrayGenerator;
import id.co.gpsc.common.client.util.JSONUtilities;
import id.co.gpsc.common.data.app.AppFormConfiguration;
import id.co.gpsc.common.data.app.ConfigurationLabel;

public class JSONFriendlyAppFormConfiguration extends AppFormConfiguration implements JSONFriendlyObject<JSONFriendlyAppFormConfiguration>{

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7230909190285490312L;
	
	
	
	/**
	 * label configuration.versi json friendly
	 **/
	private JSONFriendlyConfigurationLabel[] jsonFriendlyLabels ; 
	
	@Override
	@XmlElementWrapper(name = "configurationlabels")
	public ConfigurationLabel[] getConfigurationLabel() {
		return jsonFriendlyLabels;
	}
	
	public JSONFriendlyAppFormConfiguration(){}
	
	public JSONFriendlyAppFormConfiguration(AppFormConfiguration sample ){
		super(sample);
		if ( sample.getConfigurationLabel()!=null&& sample.getConfigurationLabel().length>0){
			jsonFriendlyLabels = new JSONFriendlyConfigurationLabel[sample.getConfigurationLabel().length];
			ConfigurationLabel[] lbls =  sample.getConfigurationLabel();
			for ( int i=0;i< lbls.length;i++){
				jsonFriendlyLabels[i] = new JSONFriendlyConfigurationLabel(lbls[i]);
			}
		}
	}
	
	
	@Override
	public JSONValue translateToJSON() {
		JSONObject retval = new JSONObject(); 
		JSONUtilities.getInstance().put(retval,"parentId",parentId);
		JSONUtilities.getInstance().put(retval,"localeId",localeId);
		JSONUtilities.getInstance().put(retval,"version",version);
		JSONUtilities.getInstance().put(retval,"missingLabelKeys",missingLabelKeys);
		JSONUtilities.getInstance().puts(retval,"configurationLabels", jsonFriendlyLabels);
		
		return retval;
	}
	@Override
	public JSONFriendlyAppFormConfiguration instantiateFromJSON(JSONValue jsonValueRef) {
		JSONFriendlyAppFormConfiguration retval = new JSONFriendlyAppFormConfiguration();
		retval.setParentId(JSONUtilities.getInstance().getString(jsonValueRef, "parentId")) ;
		retval.setLocaleId(JSONUtilities.getInstance().getString(jsonValueRef, "localeId")) ;
		retval.setVersion(JSONUtilities.getInstance().getInteger(jsonValueRef, "version")) ;
		
		ArrayGenerator<JSONFriendlyConfigurationLabel> generator = new ArrayGenerator<JSONFriendlyConfigurationLabel>() {
			@Override
			public JSONFriendlyConfigurationLabel[] generateArray(int size) {
				
				return new JSONFriendlyConfigurationLabel[size];
			}
		};
		retval.setConfigurationLabel( JSONUtilities.getInstance().getArray(jsonValueRef, "configurationLabels", generator, new JSONFriendlyConfigurationLabel())   );
		retval.setMissingLabelKeys(JSONUtilities.getInstance().getArrayOfString(jsonValueRef, "missingLabelKeys"));
		return retval;
	}
	/**
	 * label configuration.versi json friendly
	 **/
	public JSONFriendlyConfigurationLabel[] getJsonFriendlyLabels() {
		return jsonFriendlyLabels;
	}
	/**
	 * label configuration.versi json friendly
	 **/
	public void setJsonFriendlyLabels(
			JSONFriendlyConfigurationLabel[] jsonFriendlyLabels) {
		this.jsonFriendlyLabels = jsonFriendlyLabels;
	}

	
}
