package id.co.gpsc.common.rpc.common;

import java.math.BigInteger;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SigmaSimpleSortArgument;
import id.co.gpsc.common.rpc.JSONSerializedRemoteService;
import id.co.gpsc.common.util.json.IJSONFriendlyObject;

/**
 * RPC service general purpose, ini di pergunakan untuk akses method yang shared.bisa di pergunakan berbagai macam
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface GeneralPurposeRPC extends JSONSerializedRemoteService{
	
	
	
	/**
	 * select data dengan ke page. data di akases dengan FQCN dari data. 
	 * <ol>
	 * <li>data yang di request merupakan data yang di JPA mapped, bukan DTO</li>
	 * <li>data di filter dengan filter sederhana, client perlu menyediakan filter + sorts</li>
	 * </ol>
	 * 
	 **/
	public PagedResultHolder<IJSONFriendlyObject<?>> getPagedData (String objectFQCN , SigmaSimpleQueryFilter[] filters , SigmaSimpleSortArgument[] sorts , int page , int pageSize) throws Exception ;
	
	
	
	/**
	 * mencari data dengan big integer ID
	 **/
	IJSONFriendlyObject<?> getObjectByBigInteger ( String objectFQCN  ,  BigInteger objectId )  throws Exception;
	
	
	
	/**
	 * select data dengan id. ID berupa long.ini hanya bekerja untuk JPA object
	 */
	IJSONFriendlyObject<?> getObjectById ( String objectFQCN  ,  Long objectId )  throws Exception;
	
	
	/**
	 * membaca data dengan key string
	 */
	IJSONFriendlyObject<?> getObjectById ( String objectFQCN  ,  String objectId )  throws Exception;
	
	
	
	
	
	/**
	 * select data dengan id. ID berupa Integer. ini hanya bekerja untuk JPA object
	 * @param objectFQCN FQCN dari object
	 */
	IJSONFriendlyObject<?> getObjectById ( String objectFQCN  ,  Integer objectId )  throws Exception;

}
