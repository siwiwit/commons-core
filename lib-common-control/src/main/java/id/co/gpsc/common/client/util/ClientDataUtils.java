package id.co.gpsc.common.client.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.i18n.client.DateTimeFormat;




import id.co.gpsc.common.data.DataConverter;



/**
 * Utils data client
 **/
public final class ClientDataUtils {

	/**
	 * pattern yang di pergunakan untuk mengirim data dalam bentuk date as string. 
	 **/
	public static final String DATE_TO_STRING_SERIALIZATION_PATTERN = "yyyy-MM-dd HH:mm:ss:S";
	
	
	private static final DateTimeFormat STRING_TO_DATE_DESERIALIZER = DateTimeFormat.getFormat(DATE_TO_STRING_SERIALIZATION_PATTERN);
	
	
	
	private static final DateTimeFormat MONTH_ONLY_FORMAT = DateTimeFormat.getFormat("MM"); 
	private static final DateTimeFormat DAY_ONLY_FORMAT = DateTimeFormat.getFormat("dd");
	private static final DateTimeFormat YEAR_ONLY_FORMAT = DateTimeFormat.getFormat("yyyy");
	
	private static ClientDataUtils instance ; 
	
	private DataConverter<BigDecimal> bigdecConterter = new DataConverter<BigDecimal>() {
		@Override
		public BigDecimal translateData(String stringRepresentation) {
			if ( stringRepresentation==null||stringRepresentation.length()==0)
				return null; 
			try {
				return new BigDecimal(stringRepresentation);
			} catch (Exception e) {
				return null;
			}
			
		}
	};
	
	
	
	private DataConverter<String> stringConterter = new DataConverter<String>() {
		@Override
		public String translateData(String stringRepresentation) {
			return stringRepresentation;
			
		}
	};
	
	
	private DataConverter<BigInteger> bigintConterter = new DataConverter<BigInteger>() {
		@Override
		public BigInteger translateData(String stringRepresentation) {
			if ( stringRepresentation==null||stringRepresentation.length()==0)
				return null; 
			try {
				return new BigInteger(stringRepresentation);
			} catch (Exception e) {
				return null;
			}
			
		}
	};
	
	private DataConverter<Float> floatConverter = new DataConverter<Float>() {
		@Override
		public Float translateData(String stringRepresentation) {
			if ( stringRepresentation==null||stringRepresentation.length()==0)
				return null; 
			try {
				return Float.parseFloat(stringRepresentation);
			} catch (Exception e) {
				return null;
			}
			
		}
	};
	
	private DataConverter<Integer> intConverter = new DataConverter<Integer>() {
		@Override
		public Integer translateData(String stringRepresentation) {
			if ( stringRepresentation==null||stringRepresentation.length()==0)
				return null; 
			try {
				return Integer.parseInt(stringRepresentation);
			} catch (Exception e) {
				return null;
			}
			
		}
	};
	
	
	private DataConverter<Double> doubleConverter = new DataConverter<Double>() {
		@Override
		public Double translateData(String stringRepresentation) {
			if ( stringRepresentation==null||stringRepresentation.length()==0)
				return null; 
			try {
				return Double.parseDouble(stringRepresentation);
			} catch (Exception e) {
				return null;
			}
			
		}
	};
	private DataConverter<Long> longConverter = new DataConverter<Long>() {
		@Override
		public Long translateData(String stringRepresentation) {
			if ( stringRepresentation==null||stringRepresentation.length()==0)
				return null; 
			try {
				return Long.parseLong(stringRepresentation);
			} catch (Exception e) {
				return null;
			}
			
		}
	};
	
	private DataConverter<Boolean> booleanConverter = new DataConverter<Boolean>() {
		@Override
		public Boolean translateData(String stringRepresentation) {
			if ( stringRepresentation==null||stringRepresentation.length()==0)
				return null; 
			return Boolean.TRUE.toString().equalsIgnoreCase(stringRepresentation);
		}
	};
	
	
	private DataConverter<Date> dateConverter = new DataConverter<Date>() {
		@Override
		public Date translateData(String stringRepresentation) {
			if ( stringRepresentation==null||stringRepresentation.length()==0)
				return null;
			try {
				return STRING_TO_DATE_DESERIALIZER.parse(stringRepresentation);
			} catch (Exception e) {
				return null ; 
			}
			 
		}
	};
	
	private HashMap<String, DataConverter<?>> converters ; 
	
	private ClientDataUtils(){
		
		converters = new HashMap<String, DataConverter<?>>();
		converters.put(Long.class.getName(), longConverter);
		converters.put("long", longConverter);
		
		converters.put(Integer.class.getName(), intConverter);
		converters.put("int", longConverter);
		
		converters.put(Double.class.getName(), doubleConverter);
		converters.put("double", longConverter);
		
		converters.put(Float.class.getName(), floatConverter);
		converters.put("float", longConverter);
		
		converters.put(Boolean.class.getName(), booleanConverter);
		converters.put("boolean", longConverter);
		
		converters.put(BigInteger.class.getName(), bigintConterter);
		converters.put(BigDecimal.class.getName(), bigdecConterter);
		
		converters.put(Date.class.getName(), dateConverter);
		converters.put(String.class.getName(), stringConterter);
		
	}
	
	public static ClientDataUtils getInstance() {
		if (instance==null)
			instance = new ClientDataUtils(); 
		return instance;
	}
	
	
	
	/**
	 * generate data converter dari tipe yang di perlukan
	 **/
	public DataConverter<?> [] generateDataConvertersArray(String[] classNamesType){
		if ( classNamesType==null||classNamesType.length==0)
			return null ;
		 DataConverter<?> [] retval = new  DataConverter<?> [classNamesType.length] ;
		 for ( int i=0;i< classNamesType.length;i++){
			 DataConverter< ?> converterMine =converters.get(classNamesType[i]);
			 if (converterMine==null)
				 System.out.println("no converter for :" + classNamesType[i]);
			 retval[i]= converterMine;
		 }
		 return retval ; 
	}
	

	
	
	/**
	 * worker untuk convert data dari string ke dalam object
	 **/
	public Object[] convertData(String[] dataAsString , DataConverter<?>[] workers){
		if ( dataAsString==null|| workers==null||dataAsString.length!=workers.length)
			return null ; 
		Object[] retval = new Object[dataAsString.length];
		for ( int i = 0 ; i< dataAsString.length; i++){
			retval[i]=workers[i].translateData(dataAsString[i]);
		}
		return retval ; 
	}
	
	
	
	/**
	 * membaca hanya hari dari {@link Date} yang di kirimkan ke dalam fungsi
	 **/
	public Integer getDay (Date date) {
		if ( date==null)
			return null ; 
		return Integer.parseInt(DAY_ONLY_FORMAT.format(date)); 
	}
	
	/**
	 * membaca hanya hari dari {@link Date} yang di kirimkan ke dalam fungsi
	 **/
	public Integer getMonth (Date date) {
		if ( date==null)
			return null ; 
		return Integer.parseInt(MONTH_ONLY_FORMAT.format(date)); 
	}
	
	/**
	 * membaca hanya hari dari {@link Date} yang di kirimkan ke dalam fungsi
	 **/
	public Integer getYear (Date date) {
		if ( date==null)
			return null ; 
		return Integer.parseInt(YEAR_ONLY_FORMAT.format(date)); 
	}
}
