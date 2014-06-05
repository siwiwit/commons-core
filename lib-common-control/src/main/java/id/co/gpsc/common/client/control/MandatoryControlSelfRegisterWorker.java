package id.co.gpsc.common.client.control;

import com.google.gwt.user.client.ui.Widget;

import id.co.gpsc.common.client.form.MandatoryValidationEnabledControl;
import id.co.gpsc.common.client.widget.IMandatoryEnabledContainer;

public class MandatoryControlSelfRegisterWorker extends ChildSelfRegisterWorker<MandatoryValidationEnabledControl, IMandatoryEnabledContainer>{

	

	

	@Override
	protected boolean checkIsExpectedParent(Widget w) {
		return w instanceof IMandatoryEnabledContainer;
	}

	@Override
	protected void actualRegisterWorker(IMandatoryEnabledContainer parentNode) {
		parentNode.registerMandatoryComponent(getWidgetToRegister());
		
	}

	@Override
	protected boolean computeIsReadyToRegisterWorker(MandatoryValidationEnabledControl widget) {
		return widget.getI18Key()!=null&&widget.getI18Key().length()>0;
	}
	
	@Override
	protected void detachFromParentWorker(
			IMandatoryEnabledContainer currentParent) {
		if ( currentParent==null)
			return ;
		try{
			
			currentParent.unregisterMandatoryComponent(getWidgetToRegister());
		}
		catch ( Exception exc){
			exc.printStackTrace();
		}
	}
}
