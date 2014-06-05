package id.co.gpsc.common.client.app;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

import id.co.gpsc.common.client.JSONFriendlyObject;
import id.co.gpsc.common.client.util.JSONUtilities;
import id.co.gpsc.common.data.entity.FormElementConfiguration;

/**
 * JSON Friendly Field Control Configuration
 * @author I Gede Mahendra
 * @version $Id
 */
public class JSONFriendlyFieldControlConfiguration extends FormElementConfiguration implements JSONFriendlyObject<JSONFriendlyFieldControlConfiguration>{

	private static final long serialVersionUID = -1676461418804348264L;

	@Override
	public JSONValue translateToJSON() {
		JSONObject retval = new JSONObject();
		JSONUtilities.getInstance().put(retval, "formId", getFormId());
		JSONUtilities.getInstance().put(retval, "elementId", getElementId());
		JSONUtilities.getInstance().put(retval, "hintI18NKey", getHintI18NKey());
		JSONUtilities.getInstance().put(retval, "mandatory", getMandatory());
		JSONUtilities.getInstance().put(retval, "maxLength", getMaxLength());
		JSONUtilities.getInstance().put(retval, "maxValue", getMaxValue());
		JSONUtilities.getInstance().put(retval, "minValue", getMinValue());
		JSONUtilities.getInstance().put(retval, "placeHolderI18NKey", getPlaceHolderI18NKey());
		return retval;
	}

	@Override
	public JSONFriendlyFieldControlConfiguration instantiateFromJSON(JSONValue jsonValueRef) {
		JSONFriendlyFieldControlConfiguration retval = new JSONFriendlyFieldControlConfiguration();
		retval.setFormId(JSONUtilities.getInstance().getString(jsonValueRef, "formId"));
		retval.setElementId(JSONUtilities.getInstance().getString(jsonValueRef, "elementId"));
		retval.setHintI18NKey(JSONUtilities.getInstance().getString(jsonValueRef, "hintI18NKey"));
		retval.setMandatory(JSONUtilities.getInstance().getBoolean(jsonValueRef, "mandatory"));
		retval.setMaxLength(JSONUtilities.getInstance().getInteger(jsonValueRef, "maxLength"));
		retval.setMaxValue(JSONUtilities.getInstance().getString(jsonValueRef, "maxValue"));
		retval.setMinValue(JSONUtilities.getInstance().getString(jsonValueRef, "minValue"));
		retval.setPlaceHolderI18NKey(JSONUtilities.getInstance().getString(jsonValueRef, "placeHolderI18NKey"));
		return retval;
	}
}