package id.co.gpsc.common.data.app.security;

import id.co.gpsc.common.security.domain.Function;
import id.co.gpsc.common.util.json.IJSONFriendlyObject;
import id.co.gpsc.common.util.json.ParsedJSONContainer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * data untuk menu editing
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class MenuEditingData implements IJSONFriendlyObject<MenuEditingData>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2422219585825944379L;



	/**
	 * data functions yang available dalam 1 application
	 */
	private List<Function> allMenus ; 
	
	
	
	/**
	 * menu yang kondisi nya selected
	 */
	private List<BigInteger> allSelectedIds ; 
	
	/**
	 * data functions yang available dalam 1 application
	 */
	public void setAllMenus(List<Function> allMenus) {
		this.allMenus = allMenus;
	}
	/**
	 * data functions yang available dalam 1 application
	 */
	public List<Function> getAllMenus() {
		return allMenus;
	}
	
	/**
	 * menu yang kondisi nya selected
	 */
	public List<BigInteger> getAllSelectedIds() {
		return allSelectedIds;
	}
	/**
	 * menu yang kondisi nya selected
	 */
	public void setAllSelectedIds(List<BigInteger> allSelectedIds) {
		this.allSelectedIds = allSelectedIds;
	}
	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainer) {
		jsonContainer.put("allMenus", allMenus);
		if ( allSelectedIds!= null && allSelectedIds.isEmpty()){
			BigInteger[] ids = new BigInteger[allSelectedIds.size()];
			int i= 0 ; 
			for ( BigInteger scn : allSelectedIds){
				ids[i++] = scn  ;
			}
			jsonContainer.appendToArray("allSelectedIds", ids);
		}
	}
	@Override
	public MenuEditingData instantiateFromJSON(ParsedJSONContainer jsonContainer) {
		MenuEditingData retval = new MenuEditingData(); 
		retval.allMenus = jsonContainer.getAsArraylist("allMenus", Function.class.getName()); 
		BigInteger[] selectedIdsJson =  jsonContainer.getAsArrayOfBigIntegers("allSelectedIds" );
		if ( selectedIdsJson!= null && selectedIdsJson.length>0){
			retval.allSelectedIds = new ArrayList<BigInteger>(); 
			for ( BigInteger scn : selectedIdsJson) {
				retval.allSelectedIds.add(scn); 
			}
		}
		return retval;
	}

}
