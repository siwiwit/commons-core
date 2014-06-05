package id.co.gpsc.common.client.util;

/**
 * Utilities untuk encrypt dan decrypt dengan cipher AES pada client side. 
 * 
 * @author <a href="mailto:iwayan.ariagustina@gmail.com">Wayan Ari</a>
 * 
 * date: Jan 28, 2014
 * time: 5:59:17 PM
 *
 */
public class ClientSideCrypthoUtil {
	
	private static final ClientSideCrypthoUtil instance;
	
	static {
		instance = new ClientSideCrypthoUtil();
	}
	
	public static ClientSideCrypthoUtil getInstance() {
		return instance;
	}
	
	/**
	 * Transform dari plain text menjadi encrypted text.
	 * 
	 * @param plain Plain Text
	 * @param key AES Key/Password
	 * @return Encrypted text
	 */
	public native String aesEncrypt(String plain, String key)/*-{
		return CryptoJS.AES.encrypt(plain, key);
	}-*/;
	
	/**
	 * Transform dari encrypted text menjadi plain text.
	 * 
	 * @param encrypted Encrypted text
	 * @param key AES Key/Password
	 * @return Plain Text
	 */
	public native String aesDecrypt(String encrypted, String key)/*-{
		return CryptoJS.AES.decrypt(encrypted, key);
	}-*/;

}
