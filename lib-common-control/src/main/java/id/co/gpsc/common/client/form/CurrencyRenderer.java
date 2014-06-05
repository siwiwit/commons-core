package id.co.gpsc.common.client.form;

import java.io.IOException;
import java.math.BigDecimal;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.text.shared.Renderer;

public class CurrencyRenderer implements Renderer<BigDecimal> {

	/**
	 * pattern render float
	 **/
	private String renderPattern ;
	
	
	
	private NumberFormat formatterDepan = NumberFormat.getFormat("#,##0");
	
	private NumberFormat formatterBelakang  = NumberFormat.getFormat("#,##0.####");  
	
	
	/**
	 * ada pembulatan atau tidak 
	 **/
	private boolean roundAfterDot;
	
	
	/**
	 * di bulatakan berapa angka setelah koma. valid kalau {@link #roundAfterDot}=true
	 **/
	private int numberOfRoundingAfterDot=4; 
	
	public CurrencyRenderer(){
		
	}
	
	/**
	 * di bulatakan berapa angka setelah koma. valid kalau {@link #roundAfterDot}=true
	 **/
	public int getNumberOfRoundingAfterDot() {
		return numberOfRoundingAfterDot;
	}
	
	/**
	 * ada pembulatan atau tidak 
	 **/
	public boolean isRoundAfterDot() {
		return roundAfterDot;
	}
	
	
	/**
	 * di bulatakan berapa angka setelah koma. valid kalau {@link #roundAfterDot}=true
	 **/
	public void setNumberOfRoundingAfterDot(int numberOfRoundingAfterDot) {
		this.numberOfRoundingAfterDot = numberOfRoundingAfterDot;
	}
	
	
	
	
	
	/**
	 * ndak ada rounding
	 **/
	public void doNotRounding () {
		this.roundAfterDot = false ; 
	}
	
	
	
	
	/**
	 * ada pembulatan atau tidak 
	 **/
	public void doRounding(int numberOfDisplayedData) {
		this.numberOfRoundingAfterDot = numberOfDisplayedData; 
		this.roundAfterDot =true ;
		
		String pattern = "#,##0."; 
		for ( int i=0;i< numberOfDisplayedData;i++){
			pattern+= "#";
		}
		formatterBelakang = NumberFormat.getFormat(pattern);
		
	}

	public String getRenderPattern() {
		return renderPattern;
	}


	
	
	
	

	@Override
	public String render(BigDecimal floatData) {
		if ( floatData==null)
			return "";
		try {
			
			String asStr = String.valueOf(floatData);
			
			if ( asStr.indexOf(".")>-1){
				String [] arr = asStr.split("\\.");
				String dpn = arr[0];
				
				String blk =arr.length>1? arr[1]:"";
				
				if (blk.length()>0){
					
				}
				else{
					
				}
			}
			return this.formatterBelakang.format(floatData);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} 
		
	}
	
	
	/**
	 * konversi string ke float
	 **/
	private native BigDecimal makeFloat(String someString)/*-{
		try{
			return someString/1;
		}
		catch(ex){
			return 0 ;
		}
		
	
	}-*/;

	@Override
	public void render(BigDecimal floatData, Appendable targetString) throws IOException {
		targetString.append(render(floatData));
	}
	
	
	public void setRenderPattern(String renderPattern) {
		this.renderPattern = renderPattern;
		formatterBelakang = NumberFormat.getFormat(this.renderPattern);
	}

}
