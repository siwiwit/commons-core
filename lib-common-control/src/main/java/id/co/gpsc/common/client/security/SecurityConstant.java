package id.co.gpsc.common.client.security;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Taruh constant yg anda perlukan disini
 * @author I Gede Mahendra
 * @since Dec 17, 2012, 11:48:33 AM
 * @version $Id
 */
public class SecurityConstant implements Serializable,IsSerializable{

	private static final long serialVersionUID = -6566510150348725009L;
	
	/*Konstanta untuk sub modul User @author I Gede Mahendra*/
	public static final String FILTER_USERNAME = "userCode";
	public static final String FILTER_FULLNAME = "realName";
	public static final String FILTER_EMAIL = "email";
	
	//silahkan tambahkan konstanta yg anda butuhkan dsini
}