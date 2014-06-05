package id.co.gpsc.common.client.control;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class BindToExistingElementFlowPanel extends ComplexPanel  implements InsertPanel.ForIsWidget{
	
	public BindToExistingElementFlowPanel(Element element, Panel parentToBe){
		setElement(element);
		
	}

	@Override
	public void insert(Widget w, int beforeIndex) {
		
		
	}

	@Override
	public void insert(IsWidget w, int beforeIndex) {
		
		
	}

}
