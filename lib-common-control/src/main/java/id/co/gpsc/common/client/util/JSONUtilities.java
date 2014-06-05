package id.co.gpsc.common.client.util;





import id.co.gpsc.common.client.JSONFriendlyObject;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.json.client.JSONBoolean;



public final class JSONUtilities {


	
	
	private JSONUtilities(){}
	/**
	 * our singleton instance
	 **/
	private static JSONUtilities instance ;
	
	
	
	
	/**
	 * membaca boolean value dari json value
	 * @param  key key dari json object yang hendak di akses
	 * @param val json data dari maana data akan di baca
	 **/
	public Boolean getBoolean (JSONValue val , String key){
		if ( val==null||key==null || key.length()==0)
			return null ; 
		JSONObject obj=val.isObject() ;  
		if ( obj==null ||JSONNull.getInstance().equals(  obj))
			return null ; 
		if (!obj.containsKey(key))
			return null; 
		JSONValue actualVal =  obj.get(key);
		if ( actualVal.isBoolean() !=null)
			return actualVal.isBoolean().booleanValue(); 
		return null ; 
		
	}
	
	
	/**
	 * membaca string dari json value, dengan key
	 * @param val value json dari mana data akan di baca
	 * @param key key dari data yang akan di baca
	 **/
	public String getString(JSONValue val , String key){
		if ( val==null||key==null || key.length()==0)
			return null ; 
		JSONObject obj=val.isObject() ;  
		if ( obj==null )
			return null ; 
		if (!obj.containsKey(key))
			return null; 
		JSONValue actualVal =  obj.get(key);
		if ( actualVal.isNull() !=null)
			return null ;
		if ( actualVal.isString() !=null)
			return actualVal.isString().stringValue();
		if ( actualVal.isNumber()!=null)
			return actualVal.isNumber().toString();
		return null ; 
	}
	
	
	/**
	 * membaca double(angka) dari json
	 **/
	public Double getDouble(JSONValue val , String key){
		if ( val==null||key==null || key.length()==0)
			return null ; 
		JSONObject obj=val.isObject() ;  
		if ( obj==null )
			return null ; 
		if (!obj.containsKey(key))
			return null; 
		JSONValue actualVal =  obj.get(key);
		if ( actualVal.isNull() !=null)
			return null ; 
		if ( actualVal.isNumber() !=null)
			return actualVal.isNumber().doubleValue();
		return null ; 
	}
	
	
	
	/**
	 * membaca Long(angka) dari json
	 **/
	public Long getLong(JSONValue val , String key){
		if ( val==null||key==null || key.length()==0)
			return null ; 
		JSONObject obj=val.isObject() ;  
		if ( obj==null )
			return null ; 
		if (!obj.containsKey(key))
			return null; 
		JSONValue actualVal =  obj.get(key);
		if ( actualVal.isNull() !=null)
			return null ; 
		if ( actualVal.isNumber() !=null)
			return ((Double)actualVal.isNumber().doubleValue()).longValue();
		return null ; 
	}
	
	
	
	/**
	 * membaca Long(angka) dari json
	 **/
	public Integer getInteger(JSONValue val , String key){
		if ( val==null||key==null || key.length()==0)
			return null ; 
		JSONObject obj=val.isObject() ;  
		if ( obj==null )
			return null ; 
		if (!obj.containsKey(key))
			return null; 
		JSONValue actualVal =  obj.get(key);
		if ( actualVal.isNull() !=null)
			return null ; 
		if ( actualVal.isNumber() !=null)
			return ((Double)actualVal.isNumber().doubleValue()).intValue();
		return null ; 
	}
	
	
	
	/**
	 * menarik JSON array dari
	 * @param val json object
	 * @param key key dari json 
	 **/
	public JSONArray getArray (JSONValue val , String key) {
		JSONObject obj =  val.isObject();
		if (obj==null)
			return null ; 
		JSONValue arr =  obj.get(key);
		return arr.isArray();
		
	}
	
	
	/**
	 * membaca array of String
	 **/
	public String[] getArrayOfString(JSONValue val , String key){
		JSONArray arr = getArray(val, key); 
		if ( arr==null|| arr.size()==0)
			return null ; 
		String[] retval = new String[arr.size()];
		for ( int i=0;i< arr.size();i++){
			retval[i]= arr.get(i).toString();
		}
		return retval ; 
		
	}
	
	
	
	
	/**
	 * generate array of object dengan tipe yang di tentukan
	 **/
	public <DATA extends JSONFriendlyObject<DATA>> DATA[] getArray(JSONValue val , String key ,ArrayGenerator<DATA> arrayGenerator ,  DATA sampleObject){
		JSONArray arr = getArray(val, key); 
		if ( arr==null|| arr.size()==0)
			return null ; 
		DATA[]  retval= arrayGenerator.generateArray(arr.size());
		for(int i = 0 ; i < arr.size(); i++){
			retval[i]= sampleObject.instantiateFromJSON(  arr.get(i));
		}
		return retval ; 
		
	}
	
	
	/**
	 * mennaruj object dalam json
	 * @param target object tempat menaruh json
	 * @param key key dari json object 
	 * @param value nilai untuk di taruh dalam object
	 **/
	@SuppressWarnings("rawtypes")
	public void put (JSONObject target , String key ,Object value) {
		if ( value==null){
			target.put(key, JSONNull.getInstance());
			return ; 
		}
		if ( value instanceof String[]){
			String arr[]  =(String[])value;
			if ( arr.length>0){
				JSONArray objArr = new JSONArray();
				int i  =0;
				for ( String scn : arr){
					objArr.set(i++, new JSONString(scn));
				}
				target.put(key, objArr);
			}
		}
		if ( value instanceof int[]){
			int arr[]  =(int[])value;
			if ( arr.length>0){
				JSONArray objArr = new JSONArray();
				int i  =0;
				for ( int scn : arr){
					objArr.set(i++, new JSONNumber(scn));
				}
				target.put(key, objArr);
			}
		}
		if ( value instanceof float[]){
			float arr[]  =(float[])value;
			if ( arr.length>0){
				JSONArray objArr = new JSONArray();
				int i  =0;
				for ( float scn : arr){
					objArr.set(i++, new JSONNumber(scn));
				}
				target.put(key, objArr);
			}
		}
		if ( value instanceof double[]){
			double arr[]  =(double[])value;
			if ( arr.length>0){
				JSONArray objArr = new JSONArray();
				int i  =0;
				for ( double scn : arr){
					objArr.set(i++, new JSONNumber(scn));
				}
				target.put(key, objArr);
			}
		}
		
		if ( value instanceof String)
			target.put(key, new JSONString((String)value));
		else if ( value instanceof Boolean)
			target.put(key,  JSONBoolean.getInstance((Boolean)value));
		else if (value instanceof Integer)
			target.put(key,  new JSONNumber((Integer)value));
		else if (value instanceof Float)
			target.put(key, new JSONNumber((Float)value));
		else if ( value instanceof 	 Double ) 
			target.put(key, new JSONNumber((Double)value));//
		else if (value instanceof Long) 
			target.put(key, new JSONNumber((Long)value));//
		else if ( value instanceof JSONFriendlyObject<?>){
			target.put(key, ((JSONFriendlyObject)value).translateToJSON());
		}
			
	}
	/**
	 * taruh string json dalam object
	 * @param target di mana data akan di taruh dalam json
	 * @param key key dari object
	 * @param value value yang di taruh dalam json object
	 **/
	public void putString (JSONObject target , String key , String value ){
		if ( value==null){
			target.put(key, JSONNull.getInstance());
			return ; 
		}
		target.put(key, new JSONString(value)); 
	}
	
	
	
	/**
	 * put array of object
	 **/
	public  <DATA extends JSONFriendlyObject<DATA>> void puts(JSONObject target , String key , DATA[] array){
		if ( array==null|| array.length==0)
			return ; 
		JSONArray arr = new JSONArray();
		int i = 0 ;
		for(DATA scn : array){
			arr.set(i++,   scn.translateToJSON()); 
		}
		target.put(key, arr);
	}
	
	/**
	 * taruh double dalam json object
	 * @param target di mana data akan di taruh dalam json
	 * @param key key dari object
	 * @param value angka yang di taruh dalam json object
	 **/
	public void putDouble (JSONObject target , String key , Double value){
		if ( value==null){
			target.put(key, JSONNull.getInstance());
			return ; 
		}
		target.put(key, new JSONNumber(value));
	}
	
	/**
	 * taruh Integer dalam json object
	 * @param target di mana data akan di taruh dalam json
	 * @param key key dari object
	 * @param value angka yang di taruh dalam json object
	 **/
	public void putInteger (JSONObject target , String key , Integer value){
		if ( value==null){
			target.put(key, JSONNull.getInstance());
			return ; 
		}
		target.put(key, new JSONNumber(value));
	}
	
	
	/**
	 * taruh Flaot dalam json object
	 * @param target di mana data akan di taruh dalam json
	 * @param key key dari object
	 * @param value angka yang di taruh dalam json object
	 **/
	public void putFloat (JSONObject target , String key , Float value){
		if ( value==null){
			target.put(key, JSONNull.getInstance());
			return ; 
		}
		target.put(key, new JSONNumber(value));
	}
	
	/**
	 * taruh Long dalam json object
	 * @param target di mana data akan di taruh dalam json
	 * @param key key dari object
	 * @param value angka yang di taruh dalam json object
	 **/
	public void putLong (JSONObject target , String key , Long value){
		if ( value==null){
			target.put(key, JSONNull.getInstance());
			return ; 
		}
		target.put(key, new JSONNumber(value));
	}
	
	/**
	 * @param target di mana data akan di taruh dalam json
	 * @param key key dari object
	 * @param value angka yang di taruh dalam json object 
	 **/
	public void putBoolean(JSONObject target , String key , Boolean value){
		if ( value==null){
			target.put(key, JSONNull.getInstance());
			return ; 
		}
		target.put(key,  JSONBoolean.getInstance(value));
	}
	
	
	
	
	/**
	 * translate ke js array of string dari string array java
	 **/
	public JsArrayString generateArray(String[] array){
		if ( array==null||array.length==0)
			return null ;
		
		JsArrayString retval = (JsArrayString)JavaScriptObject.createArray();
		for ( String scn : array){
			retval.push(scn);
		}
		return retval;
		
	}
	
	
	
	
	public static JSONUtilities getInstance() {
		if ( instance==null)
			instance=new JSONUtilities(); 
		
		return instance;
	}
	
	
	

}
