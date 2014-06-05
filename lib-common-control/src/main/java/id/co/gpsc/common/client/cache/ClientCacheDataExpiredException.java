package id.co.gpsc.common.client.cache;



/**
 * helper exception.di sisi client untuk menotofikasi kalau data cache di client yang di harapkan oleh user sudah expired(tergantung pada konfigurasi)
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $ID
 **/
public class ClientCacheDataExpiredException extends Exception{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4562738809892695230L;
	/**
	 * id dari cache yang expired
	 **/
	public String cacheId ; 
	
	
	
	
	/**
	 * konstruktor
	 * @param cacheId id dari cache yang expired
	 * @param message message ke user. tipikal nya ini tidak akan di tampilkan ke user. musti request ke server lg kalau kasusnya expired
	 **/
	public ClientCacheDataExpiredException(String message , String cacheId ){
		super(message);
		this.cacheId= cacheId ; 
	}
	
	
	public String getCacheId() {
		return cacheId;
	}
	
	

}
