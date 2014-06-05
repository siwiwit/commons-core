package id.co.gpsc.common.client.util;
 

import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

import id.co.gpsc.common.util.ObjectGeneratorManager;
import id.co.gpsc.common.util.json.BaseParsedJSONContainer;
import id.co.gpsc.common.util.json.IJSONFriendlyObject;
import id.co.gpsc.common.util.json.ParsedJSONArrayContainer;

import java.math.BigDecimal;
import java.util.ArrayList;




/**
 * client side JSON parser container. JSON dengan meng-utilize javascript json
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public class ClientParsedJSONContainer extends BaseParsedJSONContainer{
	
	
	private JSONObject jsonVal  ; 
	
	public ClientParsedJSONContainer(String jsonString){
		JSONValue swap  = JSONParser.parseLenient(jsonString);
		jsonVal= swap.isObject(); 
		
	}
	public ClientParsedJSONContainer(JSONValue rawValue){
		jsonVal = rawValue.isObject(); 
		if ( jsonVal== null )
			jsonVal = new JSONObject(); 
	}
	
	public ClientParsedJSONContainer(){
		jsonVal = new JSONObject(); 
	}
	@Override
	public Boolean getAsBoolean(String key) {
		if ( jsonVal==null ||key==null ||key.length()==0 )
			return null ;
		if (! key.contains(".")){
			return readBooleanWorker(jsonVal, key); 
		}
		
		JSONValue objLates =  findObjectWithPath(key, jsonVal);
		if ( objLates==null)
			return null ; 
		JSONBoolean swapBool =  objLates.isBoolean();
		return swapBool==null ? null : swapBool.booleanValue() ;
	}
	
	
	
	private Boolean readBooleanWorker (JSONObject containerData , String key){
		if ( !containerData.containsKey(key))
			return null ; 
		JSONValue val =  containerData.get(key);
		if ( val==null ||JSONNull.getInstance().equals(val))
			return null; 
		JSONBoolean swapBool =  val.isBoolean() ;
		if ( swapBool==null)
			return null ; 
		return swapBool.booleanValue();
	}
	
	
	
	/**
	 * use case nya spt ini : <br/>
	 * developer meminta dengan path semacam ini : a.b.c.d
	 * d expected nya string.jadinya kita musti ketemu pointer ke c. Method ini akan return refernece ke node c
	 * @param pathWithDot key json yang mengandung .
	 **/
	private JSONValue findObjectWithPath (String pathWithDot , JSONObject jsonData) {
		String [] path = pathWithDot.split("\\.");
		JSONObject current = jsonData ; 
		for ( int i = 0 ; i< path.length-2 ; i++){
			if ( !current.containsKey(path[i]))
				return null ; 
			JSONValue swap = current.get(path[i]);
			current = swap.isObject() ; 
			if (current==null)
				return null ; 
		}
		String latesKey = path[path.length-1] ; 
		if (! current.containsKey(latesKey))
			return null; 
		return current.get(latesKey) ; 
				
				
	}
	
	
	@Override
	public Double getAsNumber(String key) {
		if ( jsonVal==null ||key==null ||key.length()==0 )
			return null ;
		if (! key.contains(".")){
			if (! jsonVal.containsKey(key))
				return null ; 
			JSONValue objStr = jsonVal.get(key);
			if ( objStr.isNull()!=null)
				return null ; 
			JSONNumber swapNumber =  objStr.isNumber();
			return swapNumber==null ? null : swapNumber.doubleValue() ;
		}
		JSONValue objLates =  findObjectWithPath(key, jsonVal);
		if ( objLates==null)
			return null ; 
		JSONNumber swapNumber =  objLates.isNumber();
		return swapNumber==null ? null : swapNumber.doubleValue() ;
	}
	
	
	
	@Override
	public String getAsString(String key) {
		if ( jsonVal==null ||key==null ||key.length()==0 )
			return null ;
		if (! key.contains(".")){
			if (! jsonVal.containsKey(key))
				return null ; 
			JSONValue objStr = jsonVal.get(key);
			if ( objStr.isNull()!=null)
				return null ;
			JSONString swapString =  objStr.isString();
			return swapString==null ? null : swapString.stringValue() ;
		}
		JSONValue objLates =  findObjectWithPath(key, jsonVal);
		if ( objLates==null)
			return null ; 
		JSONString swapString =  objLates.isString();
		return swapString==null ? null : swapString.stringValue() ;
	}
	
	
	
	@Override
	public void put(String key, String value) {
		JSONValue elem = (value==null)? JSONNull.getInstance() : new JSONString(value); 
		if (! key.contains(".")){
			jsonVal.put(key, elem);
		}
		else{
			placeDeepPath(key, elem);
		}
		
	}
	@Override
	public void put(String key, Double value) {
		JSONValue elem = (value==null)? JSONNull.getInstance() : new JSONNumber(value); 
		if (! key.contains(".")){
			jsonVal.put(key, elem);
		}
		else{
			placeDeepPath(key, elem);
		}
		
	}
	@Override
	public void put(String key, Boolean value) {
		JSONValue elem = (value==null)? JSONNull.getInstance() : JSONBoolean.getInstance(value.booleanValue()); 
		if (! key.contains(".")){
			jsonVal.put(key, elem);
		}
		else{
			placeDeepPath(key, elem);
		}
		
	}
	
	
	private void placeDeepPath (String key , JSONValue valueToPush){
		String [] path = key.split("\\.");
		JSONObject current = jsonVal ; 
		for ( int i=0 ; i < path.length-2 ; i++){
			JSONValue val =  current.get( path[i]);
			if ( val== null||val.isNull()==null || val.isObject()==null){
				JSONObject baru  = new JSONObject() ;  
				current.put(path[i],baru );
				current = baru ; 
			}else{
				current = val.isObject(); 
			}
		}
		if ( current != null)
			current.put(path[path.length-1], valueToPush);
	}

	@Override
	public String getJSONString() {
		return (jsonVal==null)?  
				null
				: 
				jsonVal.toString();
	}
	
	@Override
	public String toString() {
		
		return getJSONString();
	}
	@Override
	public void put(String key,
			List<? extends IJSONFriendlyObject<?>> arrayOfData) {
		if ( arrayOfData==null|| arrayOfData.isEmpty()){
			this.jsonVal.put(key, JSONNull.getInstance());
		}else{
			String keyType =key + JSON_ARRAY_DATA_TYPE_SUFFIX;
			String fqcn = arrayOfData.get(0).getClass().getName();
			jsonVal.put(keyType, new JSONString(fqcn));
			JSONArray jsArray = new JSONArray();
			jsonVal.put(key, jsArray);
			int i=0 ; 
			for ( IJSONFriendlyObject<?> scn : arrayOfData){
				ClientParsedJSONContainer jSonParserCnt  = new ClientParsedJSONContainer(); 
				scn.translateToJSON(jSonParserCnt); 
				jsArray.set(i++ ,  jSonParserCnt.jsonVal); 
			}
		}
		
	}
	@Override
	public ParsedJSONArrayContainer getAsArray(String key) {
		String keyType =key + JSON_ARRAY_DATA_TYPE_SUFFIX;
		JSONValue fqcnRaw =  this.jsonVal.get(keyType);
		
		JSONValue swap =  jsonVal.get(key);
		if ( swap==null || swap.isArray() ==null )
			return null ; 
		
		
		ClientSideParsedJSONArrayContainer retval = new ClientSideParsedJSONArrayContainer(swap.isArray());
		if ( fqcnRaw!=null && fqcnRaw.isString()!=null){
			String fqcn = fqcnRaw.isString().stringValue();
			retval.setDataTypeFQCN(fqcn);
		}
		
		return retval;
	}
	
	@Override
	public ParsedJSONArrayContainer getAsArray() {
		JSONArray arr = 
		jsonVal.isArray(); 
		if ( arr== null) 
			return null; 
		return new ClientSideParsedJSONArrayContainer( arr);
	}
	
	

    @Override
    public <T extends IJSONFriendlyObject<T>> ArrayList<T> getAsArraylist(String key , String objectTypeFQCN) {
        if ( objectTypeFQCN== null){
        	 
        	return null ;
        }
        if (! this.jsonVal.containsKey(key))
            return null ; 
        JSONValue swapArr = this.jsonVal.get(key) ; 
        if ( swapArr == null )
            return null ; 
        JSONArray arrActual = swapArr.isArray(); 
        if ( arrActual==null || arrActual.size()==0)
            return null ; 
                
        
         
        T sample = ObjectGeneratorManager.getInstance().instantiateSampleObject(objectTypeFQCN);
        if ( sample== null){
        	
        }
        ArrayList<T> retval = new ArrayList<T>(); 
        for ( int i = 0 ; i < arrActual.size() ; i++){
            JSONValue swapArrElem =  arrActual.get(i) ; 
            if ( swapArrElem==null)
                return null ; 
            JSONObject actElem = swapArrElem.isObject() ;
            if ( actElem==null)
                return null ; 
            
            T swap = sample.instantiateFromJSON(new ClientParsedJSONContainer(actElem)); 
            retval.add(swap);
        }
        return retval ; 
    }
    
      @Override
        public BigDecimal getAsBigDecimal(String key) {
            Number swap = getAsNumber(key); 
            if ( swap == null)
                return null; 
            return new BigDecimal(swap.toString()); 
        }
        
        

    
    
    
    

    @Override
    public <T extends IJSONFriendlyObject<T>> T getAsSubJSONObject(String key, T sampleObjectForConverter) {
        if ( !this.jsonVal.containsKey(key))
            return null ; 
        JSONObject obj =  jsonVal.get(key).isObject();
        if ( obj==null)
            return null ; 
        ClientParsedJSONContainer c = new ClientParsedJSONContainer(obj); 
        return sampleObjectForConverter.instantiateFromJSON(c);
    }

    
    @Override
	public <T extends IJSONFriendlyObject<T>> T getAsSubJSONObject(String key) {
    	 if ( !this.jsonVal.containsKey(key))
             return null ;
    	 String fqcn = jsonVal.get(key + JSON_ARRAY_DATA_TYPE_SUFFIX).toString() ; 
    	 if ( fqcn== null){ 
    		 return null ; 
    	 }
    	T sample =  ObjectGeneratorManager.getInstance().instantiateSampleObject(fqcn);
    	return  getAsSubJSONObject(key  , sample);
	}
    
    
    @Override
    public <T extends IJSONFriendlyObject<T>> void put(String key, T subObject) {
    	if ( subObject==null){
    		this.jsonVal.put(key, JSONNull.getInstance());
    		return  ; 
    	}
        ClientParsedJSONContainer c = new ClientParsedJSONContainer();
        subObject.translateToJSON(c); 
        this.jsonVal.put( key, c.jsonVal);
        this.jsonVal.put(key + JSON_ARRAY_DATA_TYPE_SUFFIX, new JSONString(subObject.getClass().getName()));
    }
	@Override
	public void appendToArray(String key, String value) {
		JSONArray arr =getOrCreateArray(key, String.class.getName()) ;  
		arr.set(arr.size(), value== null? JSONNull.getInstance() : new JSONString(value));
		 
	}
	@Override
	public void appendToArray(String key, Boolean value) {
		JSONArray arr =getOrCreateArray(key, Boolean.class.getName()) ;  
		arr.set(arr.size(), value==null? JSONNull.getInstance()  :  JSONBoolean.getInstance(value));
	}
		 
	@Override
	public void appendToArray(String key, Double value) {
		JSONArray arr =getOrCreateArray(key , Double.class.getName()) ; 
		arr.set(arr.size(), value==null? JSONNull.getInstance()  : new JSONNumber(value));
		 
	}
	
	
	@Override
	public void appendToArray(String key, IJSONFriendlyObject<?>[] value) {
		if ( value== null || value.length==0){
			if ( jsonVal.containsKey(key)){
				jsonVal.put(key, JSONNull.getInstance());
			}
			return ; 
		}
		// kita tebak dulu tipe data.FQCN nya apa
		Class<?> tipe = null ; 
		for ( IJSONFriendlyObject<?> scnType : value){
			if ( scnType == null)
				continue ; 
			tipe = scnType.getClass() ; 
			break ; 
		}
		
		JSONArray arr =getOrCreateArray(key , Double.class.getName()) ;
		jsonVal.put(key + JSON_ARRAY_DATA_TYPE_SUFFIX	, new JSONString(tipe.getName()));
		// masukan object ke dalam array
		for ( IJSONFriendlyObject<?> scn : value){
			if ( scn== null){
				arr.set(arr.size(), JSONNull.getInstance());
				continue ; 
			}
			ClientParsedJSONContainer cnt = new ClientParsedJSONContainer(); 
			scn.translateToJSON(cnt); 
			arr.set(arr.size(), cnt.jsonVal);
		}
	}
	
	
	@Override
	public void appendToArray(String key, IJSONFriendlyObject<?> value) {
		if ( value== null ){
			if ( this.jsonVal.containsKey( key)){
				JSONArray arrySwap =  jsonVal.get(key).isArray();
				arrySwap.set(arrySwap.size(),    JSONNull.getInstance());
			}else{
				JSONArray arr = new JSONArray(); 
				arr.set(0  ,JSONNull.getInstance());
				jsonVal.put (key, arr);
			}
		}else{
			JSONArray arr = getOrCreateArray(key, value.getClass().getName());
			ClientParsedJSONContainer p = new ClientParsedJSONContainer(); 
			value.translateToJSON( p); 
			arr.set(arr.size() ,  p.jsonVal );
		}
		
		
	}
	
	
	/**
	 * mencari array dari json berdasarkan key. kalau ndak nemu maka di buatkan
	 **/
	protected JSONArray getOrCreateArray (String key, String dataTypeFQCN ) {
		if (! jsonVal.containsKey(key)){
			jsonVal.put(key, new JSONArray());
			jsonVal.put(key + JSON_ARRAY_DATA_TYPE_SUFFIX, new JSONString(dataTypeFQCN));
		}
		
		JSONArray arr = jsonVal.get(key).isArray(); 
		if ( arr== null){
			arr =  new JSONArray() ; 
			jsonVal.put(key, arr );
		}
		return arr; 
	}
	
	@Override
	public String[] getAsStringArray(String key) {
		if (!jsonVal.containsKey(key))
			return null ; 
		JSONArray arr  =  jsonVal.get(key).isArray();
		if ( arr == null || arr.size()==0)
			return null; 
		String retval[] = new String[arr.size()];
		for ( int i =0 ; i< arr.size() ; i++){
			JSONValue val = arr.get(i);
			if ( val == null || val.isNull()!= null)
				continue ; 
			
			retval[i] = val.isString().stringValue(); 
		}
		return retval;
	}
	
	@Override
	public void appendToArray(String key, String[] value) {
		if ( value == null|| value.length==0){
			this.jsonVal.put(key, JSONNull.getInstance());
		}else{
			JSONArray arr = new JSONArray();
			this.jsonVal.put(key, arr);
			int i = 0 ; 
			for ( String scn : value){
				arr.set(i, scn== null? JSONNull.getInstance() : new JSONString(scn));
				i++ ; 
			}
		}
		
	}
	 
	@Override
	protected void putNull(String key) { 
		this.jsonVal.put(key, JSONNull.getInstance());
	}
	
	@Override
	public int getArrayLength(String keyOfSuscpectedArrayObject) {
		if (! this.jsonVal.containsKey(keyOfSuscpectedArrayObject)   )
			return 0 ;
		JSONValue val =  this.jsonVal.get(keyOfSuscpectedArrayObject);
		if ( val == null )
			return 0 ; 
		JSONArray arr = val.isArray(); 
		return arr== null ?  0 : arr.size();
	}
	
	@Override
	public boolean contain(String key) {
		
		return jsonVal.containsKey(key);
	}
	public JSONObject getJsonVal() {
		return jsonVal;
	}
	
}

