package id.co.gpsc.common.client.widget.dialog;

import id.co.gpsc.jquery.client.form.JQDatePicker;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Ini adalah composit yg membungkus relative date composite.Jd relative adalah
 * bagian dari absolute date value composite
 * @author I Gede Mahendra
 * @since Jan 25, 2013, 11:31:44 AM
 * @version $Id
 */
public class AbsoluteDateValueComposite extends Composite{
		
	private VerticalPanel vpanel;
	private HorizontalPanel hpanelRadio;
	private RadioButton rdiAbsolute;
	private RadioButton rdiRelative;
	private RelativeDateValueComposite relativeDate;
	private JQDatePicker datePicker;
	
	/**
	 * Construcktor Default
	 */
	public AbsoluteDateValueComposite() {
		String id = DOM.createUniqueId();
		hpanelRadio = new HorizontalPanel();
		vpanel = new VerticalPanel();
		relativeDate = new RelativeDateValueComposite();
		datePicker = new JQDatePicker();
		rdiAbsolute = new RadioButton(id);
		rdiRelative = new RadioButton(id);		
		
		rdiAbsolute.setValue(true);
		rdiRelative.setValue(false);
		datePicker.setWidth("100px");
		
		hpanelRadio.setSpacing(2);
		hpanelRadio.add(rdiAbsolute);
		hpanelRadio.add(new HTML("Absoloute"));
		hpanelRadio.add(rdiRelative);
		hpanelRadio.add(new HTML("Relative (app date)"));
		
		relativeDate.showPanelRelativeDate(false);
		vpanel.add(hpanelRadio);
		vpanel.add(datePicker);
		vpanel.add(relativeDate);
		
		initWidget(vpanel);
		initEventHandler();
	}
	
	/**
	 * Event handler
	 */
	private void initEventHandler() {
		rdiAbsolute.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent arg0) {
				datePicker.setValue(null);
				relativeDate.showPanelRelativeDate(false);
				datePicker.setVisible(true);
			}
		});
		
		rdiRelative.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent arg0) {
				relativeDate.clearDataInControl();
				relativeDate.showPanelRelativeDate(true);
				datePicker.setVisible(false);
			}
		});
	}

	/*GETTER CONTROL*/
	
	public RadioButton getRdiAbsolute() {
		return rdiAbsolute;
	}

	public RadioButton getRdiRelative() {
		return rdiRelative;
	}

	public RelativeDateValueComposite getRelativeDate() {
		return relativeDate;
	}

	public JQDatePicker getDatePicker() {
		return datePicker;
	}
}