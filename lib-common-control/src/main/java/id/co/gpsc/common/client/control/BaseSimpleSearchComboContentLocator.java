package id.co.gpsc.common.client.control;

import id.co.gpsc.common.data.query.SimpleQueryFilterOperator;



import java.util.Date;



/**
 * simple search locator
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $ID
 **/
public class BaseSimpleSearchComboContentLocator {
	
	
	
	/**
	 * tipe yang di terima oleh filter.
	 */
	public enum AcceptedFilterType {
		LONG(Long.class)  , 
		INTEGER(Integer.class) , 
		STRING(String.class), 
		DATE(Date.class) /*, 
		FLOAT(Float.class) , 
		BIGINTEGER(BigInteger.class) , 
		CURRENCY (BigDecimal.class), 
		BIGDECIMAL(BigDecimal.class)*/; 
		private Class<?> accpetedType ; 
		
		private AcceptedFilterType(Class<?> actualType){
			this.accpetedType = actualType ; 
		}
		
		
		public Class<?> getAccpetedType() {
			return accpetedType;
		}
	}
	
	protected String queryFilter ; 
	protected String defaultLabel ; 
	protected String i18Key ; 
	protected Class<?> filterClassType =String.class;
	
	/**
	 * operator untuk query
	 **/
	private SimpleQueryFilterOperator operator ; 
	
	
	/**
	 * @param queryFilter data di filter dengan field apa
	 * @param defaultLabel label standar. ini muncul kalau blm ada i18 def
	 * @param i18Key key i18, label yang muncul apa
	 *  
	 **/
	public BaseSimpleSearchComboContentLocator(String queryFilter , String defaultLabel , String i18Key , SimpleQueryFilterOperator operator){
		this(queryFilter , defaultLabel , i18Key);
		this.operator=operator;
		
		
	}
	
	public BaseSimpleSearchComboContentLocator(String queryFilter , String defaultLabel , String i18Key , SimpleQueryFilterOperator operator, AcceptedFilterType acceptedType){
		this(queryFilter  , defaultLabel  , i18Key ,operator); 
		this.filterClassType = acceptedType.getAccpetedType();
	}

	
	
	public BaseSimpleSearchComboContentLocator(String queryFilter , String defaultLabel , String i18Key){
		this.queryFilter=queryFilter;
		this.defaultLabel=defaultLabel ;
		this.i18Key=i18Key;
	}
	public BaseSimpleSearchComboContentLocator(String queryFilter , String defaultLabel , String i18Key , AcceptedFilterType acceptedType){
		this(queryFilter , defaultLabel , i18Key ) ; 
		this.filterClassType = acceptedType.getAccpetedType(); 
	}
	
	
	public void setFilterClassTypeFloat () {
		this.filterClassType = Float.class;
	}
	public void setFilterClassTypeInt() {
		this.filterClassType = Integer.class;
	}
	public void setFilterClassDate() {
		this.filterClassType = Date.class;
	}
	public void setFilterClassTypeString() {
		this.filterClassType = String.class;
	}

	public String getQueryFilter() {
		return queryFilter;
	}

	public String getDefaultLabel() {
		return defaultLabel;
	}

	public String getI18Key() {
		return i18Key;
	}


	public Class<?> getFilterClassType() {
		return filterClassType;
	}
	
	/**
	 * operator untuk query
	 **/
	public void setOperator(SimpleQueryFilterOperator operator) {
		this.operator = operator;
	}
	/**
	 * operator untuk query
	 **/
	public SimpleQueryFilterOperator getOperator() {
		return operator;
	}

}
