package id.co.gpsc.jquery.client.grid.cols;


import id.co.gpsc.jquery.client.grid.GridHorizontalAlign;
import id.co.gpsc.jquery.client.util.NativeJsUtilities;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;



/**
 * grid dengan tipe data Date
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 **/
public abstract class DateColumnDefinition<DATA> extends BaseColumnDefinition<DATA, Date> {

	/**
	 * default date formatter. ini ikut pattern DateTimeFormat
	 * 
	 **/
	public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	
	
	/**
	 * pattern date yg di pergunakan. pls cek pattern pada  {@link DateTimeFormat}
	 **/
	private String dateFormatter =DEFAULT_DATE_FORMAT; 
	
	
	
	
	/**
	 * versi paling sederhana, dengan label header + column width 
	 * @param headerLabel label header
	 * @param columnWidth lebar column header
	 */
	public DateColumnDefinition(String headerLabel, int columnWidth) {
		super(headerLabel, columnWidth);
	}
	
	

	
	/**
	 * versi ini dengan date formatter sendiri
	 * @param dateFormatter  formatter date. hati-hati dengan i18. Pls check {@link #dateFormatter} 
	 * @param headerLabel label header
	 * @param columnWidth lebar column
	
	 **/
	public DateColumnDefinition(String headerLabel, int columnWidth , String dateFormatter){
		super(headerLabel, columnWidth);
		this.dateFormatter = dateFormatter ; 
	}
	
	

	/**
	 * 
	 * @param dateFormatter  formatter date. hati-hati dengan i18. Pls check {@link #dateFormatter} 
	 * @param headerLabel header label
	 * @param columnWidth lebar column
	 * @param i18Key key i18 untuk headerlabel
	 * 
	
	 **/
	public DateColumnDefinition(String headerLabel, int columnWidth , String dateFormatter, String i18Key){
		super(headerLabel, columnWidth, i18Key);
		this.dateFormatter = dateFormatter ; 
	}
	
	@Override
	public final String getSorttype() {
		return "date";
	}

	@Override
	public GridHorizontalAlign getHorizontalAlign() {
		return GridHorizontalAlign.center;
	}
	
	@Override
	public String getFormatterType() {
		return "text";
	}
	
	@Override
	public void extractAndPutDataToObject(DATA data,
			JavaScriptObject targetObject) {
		Date tgl = getData(data);
		if ( tgl==null){
			//NativeJsUtilities.getInstance().putNullObject(targetObject, rawJsDataName);
			//kasi string kosong untuk string
			NativeJsUtilities.getInstance().putObject(targetObject, rawJsDataName, "");
			return ;
		}
		String fmt = dateFormatter!=null&&dateFormatter.length()>0? dateFormatter: DEFAULT_DATE_FORMAT ; 
		NativeJsUtilities.getInstance().putObject(targetObject, "datefmt", fmt);
		//formatoptions ={}
		
		
		String dateAsStr = DateTimeFormat.getFormat(fmt).format(tgl);
		NativeJsUtilities.getInstance().putObject(targetObject, rawJsDataName, dateAsStr);
		
		GWT.log("formatted date is :" + dateAsStr + " , formatter is :" + fmt + " date is : "+ tgl);
		
		
		// di format dengan src vs new
		//NativeJsUtilities.getInstance().putObject(targetObject, rawJsDataName, NativeJsUtilities.getInstance().generateNativeJsDate(tgl));
	}
	
	

	
}
