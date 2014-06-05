package id.co.gpsc.jquery.client.grid.cols;

import id.co.gpsc.jquery.client.grid.GridHorizontalAlign;
import id.co.gpsc.jquery.client.util.NativeJsUtilities;

import java.math.BigDecimal;

import com.google.gwt.core.client.JavaScriptObject;



/**
 * column definition untuk menampilkan Currency 
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id 
 * @since 19-aug-2012
 **/
public abstract class CurrencyColumnDefinition<DATA> extends NumberColumnDefinition<DATA> {

	
	/**
	 * default berapa decimal yang di tampilkan untuk grid. di default {@value #DEFAULT_DECIMAL_PLACES}, 
	 * kalau anda berencana merubah nilai default nya secara global dalam 1 aplikasi, set variable ini
	 **/
	public static int DEFAULT_DECIMAL_PLACES =2 ; 
	
	public CurrencyColumnDefinition(String headerLabel, int columnWidth) {
		super(headerLabel, columnWidth);
		this.decimalPlaces=DEFAULT_DECIMAL_PLACES;
		
	}
	
	
	public CurrencyColumnDefinition(String headerLabel, int columnWidth, String i18Key) {
		super(headerLabel, columnWidth, i18Key);
		this.decimalPlaces=DEFAULT_DECIMAL_PLACES;
		
	}

	private String overridePrefix =null ; 
	private String overrideSuffix =null; 
	@Override
	public String getFormatterType() {
		return "currency";
	}

	@Override
	public String getSorttype() {
		return "float";
	}
	@Override
	public GridHorizontalAlign getHorizontalAlign() {
		return GridHorizontalAlign.right;
	}
	
	@Override
	public abstract BigDecimal getData(DATA data);
	
	//prefix: "", suffix:""
	
	/**
	 * prefix,awalan(sample:Rp 10.000). anda bisa menyisipkan tipe currency di sini(misal Rp, Dollar etc). default ikut konfigurasi i-18 jqgrid
	 * @return null -> ikut default i18 jqgrid, else akan ada prefix
	 **/
	public String getOverridePrefix(){
		return overridePrefix ;
	}
	
	
	
	/**
	 * <i>only</i> work sebelum grid di render. set prefix
	 **/
	public void setOverridePrefix(String overridePrefix) {
		this.overridePrefix = overridePrefix;
	}
	
	/**
	 * suffix,di belakang(sample: 10.000 Rp). anda bisa menyisipkan tipe currency di sini(misal Rp, Dollar etc). default ikut konfigurasi i-18 jqgrid
	 * @return null -> ikut default i18 jqgrid, else akan ada prefix
	 **/
	public String getOverrideSuffix(){
		return overrideSuffix;
	}
	
	
	
	/**
	 * <i>only</i> work sebelum grid di render. set suffix
	 **/
	public void setOverrideSuffix(String overrideSuffix) {
		this.overrideSuffix = overrideSuffix;
	}
	
	@Override
	protected JavaScriptObject generateFormatOption() {
		JavaScriptObject swap =  super.generateFormatOption();
		if ( getOverridePrefix()!=null)
			NativeJsUtilities.getInstance().putObject(swap, "prefix", getOverridePrefix());
		if ( getOverrideSuffix()!=null)
			NativeJsUtilities.getInstance().putObject(swap, "suffix", getOverrideSuffix());
		
		return swap ;
	}
	
	@Override
	public void extractAndPutDataToObject(DATA data,
			JavaScriptObject targetObject) {
		BigDecimal swap = getData(data);
		if ( swap==null){
			NativeJsUtilities.getInstance().putNullObject(targetObject, rawJsDataName);
			return ;
		}
		NativeJsUtilities.getInstance().putObject(targetObject, rawJsDataName , swap.floatValue());
		
	}

}
