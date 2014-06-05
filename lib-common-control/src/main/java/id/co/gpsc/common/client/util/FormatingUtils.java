package id.co.gpsc.common.client.util;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;



/**
 * utilities class untuk formatting. misal  : 
 * - formating tanggal dengan format indonesia
 * - formating currency dengan - pada bagia belakangnya 
 **/
public class FormatingUtils {


	/**
	 * singleton instance
	 **/
	private static FormatingUtils instance ; 
	
	
	private DateTimeFormat indonesianDateOnlyFormatter = DateTimeFormat.getFormat("dd-MM-yyyy"); 
	
	
	
	/**
	 * pemisah decimal. default pakai system inggris ( .)
	 */
	private char decimalSeparatorChar = '.'; 
	
	
	/**
	 * ini message untuk memperingatkan user kalau misal nya user memasukan decimal separator yang salah 
	 */
	private String defaultMessageForInvalidDecimalSeparator = "Decimal separator is .(dot)please fix your entries"; 
	
	/**
	 * formatter
	 **/
	NumberFormat formatter=NumberFormat.getFormat("#,###");
	
	NumberFormat currencyNoAfterComaFormater=NumberFormat.getFormat("#,###.##");
	
	private FormatingUtils(){
		
	}
	
	/**
	 * instance
	 **/
	public static FormatingUtils getInstance() {
		if ( instance==null)
			instance=new FormatingUtils();
		return instance;
	}
	
	
	
	
	/**
	 * format integer dengan angka. pisah digit 1000
	 **/
	public String format(Integer number){
		if  ( number==null)
			return ""; 
		
		return formatAsIndonesianFormat(number) ; 
	}

	
	/**
	 * format currency
	 **/
	public String formatCurrency (BigDecimal number){
		
		/*
		if(number==null)
			return "";
		String str=currencyNoAfterComaFormater.format(number) ; 
		
		
		
		
		//GWT.log("formated string -> " + str); 
		String[] arr= str.split("\\.");
		arr[0] = arr[0].replace(",", "."); 
		if ( arr.length==2)
			return arr[0] + "," + arr[1]; 
		
		 return arr[0] ;*/
		return formatCurrency(number , CommonClientControlUtil.getInstance().isUseDotForThousandSeparator());
	}
	
	
	/**
	 * versi ini, kalau di perlukan akan mengganti .(bahasa inggris) dengan ,(indonesia, jerman). contoh nya 
	 * seribu , lima puluh sen (1000.50 dalam formating inggris) menjadi -> <i>1.000,50</i> dalam formating indonesia
	 * @param number anggka yang di konversi
	 * @param useDotForThousandSeparator true : pemisah ribuan <strong>[.]</strong> lain nya pakai ,(koma)
	 **/
	public String formatCurrency (BigDecimal number , boolean useDotForThousandSeparator ){
		/*String raw = formatCurrency(number);
		if ( raw==null|| raw.length()==0 || !useDotForThousandSeparator)
			return raw ; 
		return formatDecimalSeparatorWithComa(raw);*/
		
		if(number==null)
			return "";
		String str=currencyNoAfterComaFormater.format(number) ; 
		
		//GWT.log("formated string -> " + str); 
		String[] arr= str.split("\\.");
		if ( useDotForThousandSeparator)
			arr[0] = arr[0].replace(",", "."); 
		if ( arr.length==2)
			return arr[0] +  (useDotForThousandSeparator?   "," :"." )+ arr[1]; 
		
		 return arr[0] ;
		 
		 
		 
	}
	
	
	/**
	 * ganti . dengan , dan sebaliknya. akomdasi bahasa indonesia
	 **/
	private String formatDecimalSeparatorWithComa(String currentEntry){
		
		if ( currentEntry==null||currentEntry.length()==0){
			return null;
		}	

		String[] divString=currentEntry.split("\\.");
		String thousand =divString[0].replaceAll(",",CommonClientControlUtil.getInstance().getThousandSeparator());
		String decimal="";
		if(divString.length>1)
			decimal=CommonClientControlUtil.getInstance().getDecimalSeparator()+divString[1];
		
		String backFormater=thousand+decimal;
		return backFormater ; 
	}
	
	/**
	 * format tanggal. format indonesia : tgl - bulan - tahun
	 **/
	public String formatDateOnly (Date date){
		if ( date==null)
			return ""; 
		return indonesianDateOnlyFormatter.format(date); 
	}
	
	/**
	 * format tanggal sesuai dengan format (tgl/bln/thn nya) sesuai dengan keinginan
	 * <br>tambahan oleh andri
	 */
	public String formatDateCustom(Date date, String formatnya){
		if ( date==null)
		return "";
		
		DateTimeFormat customFormatter = DateTimeFormat.getFormat(formatnya);
		return customFormatter.format(date);
	}
	
	/**
	 * konversi format date menjadi format yyyy-MM-dd
	 * @param dateOld Date
	 * @return <b>newDate</b> Date	 
	 */
	public Date formatStringToDate(Date dateOld){
		DateTimeFormat customFormatter = DateTimeFormat.getFormat("yyyy-MM-dd");
		String dateOldString = customFormatter.format(dateOld);		
		Date newDate = customFormatter.parse(dateOldString);
		return newDate;
	}
	
	/**
	 * Konversi string dg format yyyy-MM-dd menjadi Date
	 * @param dateString - string date dg format date yyyy-MM-dd
	 * @return Date
	 * @author I Gede Mahendra
	 */
	public Date formatStringToDate(String dateString){
		DateTimeFormat customFormater = DateTimeFormat.getFormat("yyyy-MM-dd");
		Date newDate = customFormater.parse(dateString);
		return newDate;
	}
			
	/**
	 * format number dengan pilihan bagian di belakang koma di rounding atau tidak.<br/> 
	 * mohon jangan pergunakan ini kalau misalnya integer
	 * @param theNumber number yang hendak di format
	 **/
	public String formatSimpleFloat(Number theNumber , boolean doRoundAfterDot , int numberAfterDotPresented){
		if (theNumber == null)
			return null;
		if (doRoundAfterDot) {
			String pattern = "#,###";
			if (numberAfterDotPresented > 0) {
				pattern += ".";
				for (int i = 0; i < numberAfterDotPresented; i++) {
					pattern += "#";
				}
			}
			
			String formated = NumberFormat.getFormat(pattern).format(theNumber);
			return formated;
			
		} else {
			String numberAsString = theNumber.toString();
			String[] arr = numberAsString.split("\\.");

			String frontSide = arr[0]; // NumberFormat.getFormat("#,###.############").format(theNumber);
			Integer angkaDepan = Integer.parseInt(frontSide);
			frontSide = this.formatter.format(angkaDepan);
			frontSide = frontSide.replace(",", ".");
			
			if (arr.length > 1 && arr[1]!= null)
				frontSide += "," + arr[1];
			
			return frontSide;
		}
		
	}
	
	
	private String formatAsIndonesianFormat(Number number){
		String retval= formatter.format(number);
		retval =  retval.replace(",", ".");
		return retval  ; 
		
	}

	/**
	 * ini message untuk memperingatkan user kalau misal nya user memasukan decimal separator yang salah 
	 */
	public void setDefaultMessageForInvalidDecimalSeparator(
			String defaultMessageForInvalidDecimalSeparator) {
		this.defaultMessageForInvalidDecimalSeparator = defaultMessageForInvalidDecimalSeparator;
	}
	/**
	 * ini message untuk memperingatkan user kalau misal nya user memasukan decimal separator yang salah 
	 */
	public String getDefaultMessageForInvalidDecimalSeparator() {
		return defaultMessageForInvalidDecimalSeparator;
	}
	
	
	/**
	 * pemisah decimal. default pakai system inggris ( .)
	 */
	public void setDecimalSeparatorChar(char decimalSeparatorChar) {
		this.decimalSeparatorChar = decimalSeparatorChar;
	}
	/**
	 * pemisah decimal. default pakai system inggris ( .)
	 */
	public char getDecimalSeparatorChar() {
		return decimalSeparatorChar;
	}
	
}
