package id.co.gpsc.jquery.client.grid.cols;

/**
 * 
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public abstract class CheckboxColumnDefinition<DATA> extends StringColumnDefinition<DATA> {

	
	public CheckboxColumnDefinition(String headerLabel, int columnWidth) {
		super(headerLabel, columnWidth);
		// TODO Auto-generated constructor stub
	}
	
	public CheckboxColumnDefinition(String headerLabel, int columnWidth, String i18Key) {
		super(headerLabel, columnWidth , i18Key);
		
	}



	@Override
	public String getData(DATA data) {
		boolean value = getBooleanDataRepresentation(data);
		String checkString = value?  "checked='checked'"  :"" ; 
		return "<input type='checkbox' id='' "+checkString+"/>"; 
	}
	
	
	
	/**
	 * ini untuk yang tipe nya chekbox
	 **/
	protected abstract boolean getBooleanDataRepresentation(DATA data); 
}
