package id.co.gpsc.common.client.control;

import com.google.gwt.user.client.ui.Widget;

import id.co.gpsc.common.client.form.ResourceBundleEnableContainer;
import id.co.gpsc.common.control.ResourceBundleConfigurableControl;

public class ResourceAwareControlSelfRegisterWorker extends ChildSelfRegisterWorker<ResourceBundleConfigurableControl, ResourceBundleEnableContainer>{

	

	@Override
	protected boolean checkIsExpectedParent(Widget w) {
		return w instanceof ResourceBundleEnableContainer;
	}

	@Override
	protected void actualRegisterWorker(ResourceBundleEnableContainer parentNode) {
		if(parentNode!=null)
			parentNode.registerResourceBundleEnabledNode(getWidgetToRegister());
	}
	@Override
	protected boolean computeIsReadyToRegisterWorker(ResourceBundleConfigurableControl widget) {
		return widget.getI18Key()!=null&&widget.getI18Key().length()>0;
	}

	@Override
	protected void detachFromParentWorker(
			ResourceBundleEnableContainer currentParent) {
		if ( currentParent!=null)
			currentParent.unregisterResourceBundleEnabledNode(getWidgetToRegister());
		
	}
}
