package id.co.gpsc.common.client.util;

import java.math.BigDecimal;


/**
 * number format untuk menghilangkan titik dan koma yang terbaca pada textbox
 * @author ashadi.pratama
 * @version $id
 * @since 2013-01-10
 *
 */
public final class NumberFormatGenerator {
	private static NumberFormatGenerator instance;

	/**
	 * singleton instance
	 **/
	public static NumberFormatGenerator getInstance() {
		if ( instance==null){
			instance=new NumberFormatGenerator();
		}
		return instance;
	}
	
	private String generateStringNumber(String number){
		StringBuffer numberFormat=new StringBuffer();
		try {
			String afterRemoveThousand =number.replaceAll("\\" +  CommonClientControlUtil.getInstance().getThousandSeparator(), "");
			String afterNormalizeDecomal = afterRemoveThousand.replaceAll("\\" + CommonClientControlUtil.getInstance().getDecimalSeparator(), ".");
			
			numberFormat.append(afterNormalizeDecomal);
			System.out.println("no thousand separator :" +  afterRemoveThousand + ", normalized decimal point:" + afterNormalizeDecomal+" thousand separator : "+CommonClientControlUtil.getInstance().getThousandSeparator()) ;
			
			return numberFormat.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return number;
		}
	}
	
	public BigDecimal generateBigDecimalNumber(String number){
		try {
			return new BigDecimal(generateStringNumber(number));
		} catch (Exception e) {
			return null;
		}
	}
	
	public Float generateFloatNumber(String number){
		try {
			return new Float(number);
		} catch (Exception e) {
			return null;
		}
	}
	
	
}
