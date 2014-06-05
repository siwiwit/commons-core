package id.co.gpsc.common.client.widget.dialog;

/**
 * Enum untuk relative operator
 * @author I Gede Mahendra
 * @since Jan 25, 2013, 4:13:20 PM
 * @version $Id
 */
public enum RelativeOperatorEnum {	
	Plus("+"),	
	Minus("-"),
	
	Day("d"),
	Month("m"),
	Year("y");
	
	private String operator;
	
	private RelativeOperatorEnum(String operator) {
		this.operator=operator;
	}
	
	@Override
	public String toString() {	
		return operator;
	}
}