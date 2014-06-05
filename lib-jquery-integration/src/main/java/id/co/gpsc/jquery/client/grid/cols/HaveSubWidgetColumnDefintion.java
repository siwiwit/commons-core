package id.co.gpsc.jquery.client.grid.cols;

import com.google.gwt.user.client.ui.Widget;



/**
 * 	column definition yang punya sub widget
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public interface HaveSubWidgetColumnDefintion<DATA> {

	
	/**
	 * id container. untuk column yang have sub widget, yang di masukan ke dalam cell adalah span dengan id. ke dalam span inilah widget akan di masukan
	 * method ini return ID latest span.
	 **/
	public String getLatestWidgetContainerId();
	
	/**
	 * generate widget untuk di tempatkan dalam cell grid
	 * @param data current data yang di masukan ke dalam widget
	 **/
	public Widget[] generateWidgets(DATA data);
}
