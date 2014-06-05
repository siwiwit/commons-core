package id.co.gpsc.common.client.control;

import com.google.gwt.user.client.ui.Widget;

import id.co.gpsc.common.client.lov.LOVCapabledControl;
import id.co.gpsc.common.client.lov.LovPoolPanel;

public class LOVEnabledControlSelfRegisterWorker extends ChildSelfRegisterWorker<LOVCapabledControl	, LovPoolPanel>{

	

	

	@Override
	protected boolean checkIsExpectedParent(Widget w) {
		return w instanceof LovPoolPanel;
	}

	@Override
	protected void actualRegisterWorker(LovPoolPanel parentNode) {
		parentNode.registerLOVCapableControl(getWidgetToRegister());
		
	}

	@Override
	protected boolean computeIsReadyToRegisterWorker(LOVCapabledControl widget) {
		return widget.getParameterId()!=null&&widget.getParameterId().length()>0;
	}
	
	@Override
	protected void detachFromParentWorker(LovPoolPanel currentParent) {
		currentParent.unregister(getWidgetToRegister());
		
	}

}
