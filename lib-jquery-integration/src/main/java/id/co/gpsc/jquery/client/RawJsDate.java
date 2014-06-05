package id.co.gpsc.jquery.client;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;

public class RawJsDate  extends JavaScriptObject {

	private final static DateTimeFormat formatter=DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * generete js instance. date saja. hanya date
	 * @param date date yang hendak di generate raw js nya
	 **/
	public static RawJsDate generateNewDateInstance (Date date){
		int month= CommonJQueryUtilities.getInstance().getMonthBased1Index(date)-1;
		int year=CommonJQueryUtilities.getInstance().getFullYear( date);
		int day=CommonJQueryUtilities.getInstance().getDate(date);
		return generateNewInstance(year, month, day);
	}

	/**
	 * generate raw js instance dari tanggal tertentu. full date + jam
	 * @param date date yang hendak di generate raw js nya
	 **/

	public static RawJsDate generateNewFullDateInstance (Date date){
		int month= CommonJQueryUtilities.getInstance().getMonthBased1Index(date)-1;
		int year=CommonJQueryUtilities.getInstance().getFullYear( date);
		int day=CommonJQueryUtilities.getInstance().getDate(date);
		@SuppressWarnings("deprecation")
		int jam =date.getHours();
		@SuppressWarnings("deprecation")
		int menit =date.getMinutes();
		@SuppressWarnings("deprecation")
		int detik =date.getSeconds();
		return generateNewInstance(year, month, day, jam, menit, detik);
	}



	/**
	 * generate instance date
	 * @param year tahun
	 * @param monthZeroBased bulan. 0= januari
	 * @param date tanggal
	 **/
	protected native static RawJsDate generateNewInstance(int year , int monthZeroBased , int date )/*-{
		var rawJs=  new Date(year , monthZeroBased , date);

		return rawJs;
	}-*/;

	/**
	 * generate instance date
	 * @param year tahun
	 * @param monthZeroBased bulan. 0= januari
	 * @param date tanggal
	 * @param  hour jam
	 * @param minute menit
	 * @param second detik
	 **/
	protected native static RawJsDate generateNewInstance(int year , int monthZeroBased , int date , int hour , int minute , int second)/*-{
		return new Date(year , monthZeroBased , date ,hour , minute  , second);
	}-*/;


	protected RawJsDate(){}



	/**
	 * generate instance tanggal dari js mentah
	 **/
	public final Date generateDateInstance (){
		return formatter.parse(getFullYear() + "-" + getMonth() + "-" + getDate()  + " " + getHours() + ":" + getMonth() + ":" + getSeconds());
	}



	public final native int getDate()/*-{
		return this.getDate() ;
	}-*/;

	public final native int getFullYear()/*-{
		return this.getFullYear() ;
	}-*/;

	public final native int getHours()/*-{
		return this.getFullYear() ;
	}-*/;


	public final native int getMinutes()/*-{
		return this.getFullYear() ;
	}-*/;

	/**
	 * bulan
	 **/
	public final native int getMonth()/*-{
			return this.getMonth() ;
	}-*/;

	public final native int getSeconds()/*-{
		return this.getFullYear() ;
	}-*/;
}
