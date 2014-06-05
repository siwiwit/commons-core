package id.co.gpsc.common.client.control.i18;

import id.co.gpsc.common.client.control.worklist.I18ColumnDefinition;
import id.co.gpsc.common.client.control.worklist.I18EnabledSimpleGrid;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.IntegerColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

/**
 * List panel dari i18 Text Editor
 * @author I Gede Mahendra
 * @since Sep 18, 2012, 1:52:24 PM
 * @version $Id
 */
public class I18TextListPanel extends I18EnabledSimpleGrid<I18DataListPanel>{
	
	IntegerColumnDefinition<I18DataListPanel> colNo = new IntegerColumnDefinition<I18DataListPanel>("No", 40) {
		@Override
		public Integer getData(I18DataListPanel data) {			
			return data.getNo();
		}		
	};
	
	StringColumnDefinition<I18DataListPanel> colKey = new StringColumnDefinition<I18DataListPanel>("Key",165) {
		@Override
		public String getData(I18DataListPanel data) {			
			return data.getKey();
		}
	};
	
	StringColumnDefinition<I18DataListPanel> colLabel = new StringColumnDefinition<I18DataListPanel>("Label",165) {
		@Override
		public String getData(I18DataListPanel data) {
			return data.getLabel();
		}		
	};
	
	BaseColumnDefinition<?, ?>[] gridColumnDefinision = {colNo,colKey,colLabel};	
	
	@Override
	public I18ColumnDefinition<I18DataListPanel>[] getI18ColumnDefinitions() {
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<I18DataListPanel, ?>[] getColumnDefinitions() {		
		return (BaseColumnDefinition<I18DataListPanel, ?>[]) gridColumnDefinision;
	}
}