package id.co.gpsc.jquery.client.grid.cols;

import id.co.gpsc.jquery.client.grid.GridHorizontalAlign;
import id.co.gpsc.jquery.client.util.NativeJsUtilities;

import com.google.gwt.core.client.JavaScriptObject;




/**
 * generic number column definition. disarankan untuk Float dan Double 
 **/
public abstract class NumberColumnDefinition<DATA> extends BaseColumnDefinition<DATA, Number> {

	
	/**
	 * decimal places yang di tampilkan
	 **/
	protected int decimalPlaces=0;
	public NumberColumnDefinition(String headerLabel, int columnWidth) {
		super(headerLabel, columnWidth);
		
	}

	public NumberColumnDefinition(String headerLabel, int columnWidth, String i18Key) {
		super(headerLabel, columnWidth , i18Key);
		
	}

	
	@Override
	public String getFormatterType() {
		return "number";
	}

	@Override
	public String getSorttype() {
		return "float";
	}
	@Override
	public GridHorizontalAlign getHorizontalAlign() {
		return GridHorizontalAlign.right;
	}
	
	
	
	/**
	 * override separator decimala. default nya akan ikut localization dari jqgrid. kalau ada perubahan, return value dari ini perlu di ganti sesuai dengan yang di perlukan<br/>
	 * tipikal nya : . atau ,
	 *  
	 **/
	public  String getOverrideDecimalSeparator (){
		return null ;
	}
	
	
	/**
	 * berapa di belakang koma yang akan di tampilkan
	 **/
	public  int getDecimalPlaces(){
		return this.decimalPlaces;
	}
	/**
	 * berapa di belakang koma yang akan di tampilkan
	 **/
	public void setDecimalPlaces(int decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}
	/**
	 * setipe dengan {@link #getOverrideDecimalSeparator()}, ini override thousand separator. pemisah ribuan pakai apa
	 **/
	public String getOverrideThousandsSeparator(){
		return null;
	}
	
	@Override
	public JavaScriptObject generateRawGridColumnDefinition() {
		
		JavaScriptObject swap=  super.generateRawGridColumnDefinition();
		NativeJsUtilities.getInstance().putObject(swap, "formatoptions", generateFormatOption());
		return swap ;
	}
	
	
	
	/**
	 * generate additional formatting option
	 * pls cek option <strong><i>formatoptions</i></strong> pada jqgrid
	 **/
	protected JavaScriptObject generateFormatOption (){
		JavaScriptObject fmtOpt = JavaScriptObject.createObject();
		String ovrDecSeparator = getOverrideDecimalSeparator();
		if ( ovrDecSeparator!=null&&ovrDecSeparator.length()>0)
			NativeJsUtilities.getInstance().putObject(fmtOpt, "decimalSeparator", ovrDecSeparator);
		//thousandsSeparator: ".", decimalPlaces: 2, defaultValue
		String thousandsSeparator =getOverrideThousandsSeparator();
		if(thousandsSeparator!=null&&thousandsSeparator.length()>0)
			NativeJsUtilities.getInstance().putObject(fmtOpt, "thousandsSeparator", thousandsSeparator);
		NativeJsUtilities.getInstance().putObject(fmtOpt, "decimalPlaces", getDecimalPlaces());
		return fmtOpt;
	}
	@Override
	public void extractAndPutDataToObject(DATA data,
			JavaScriptObject targetObject) {
		Number swap = getData(data);
		if ( swap==null){
			NativeJsUtilities.getInstance().putNullObject(targetObject, rawJsDataName);
			return ;
		}
		
		NativeJsUtilities.getInstance().putStringButNumber(targetObject, rawJsDataName, swap.toString());
		
	}

}
