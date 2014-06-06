package id.co.gpsc.common.security.rpc;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.rpc.JSONSerializedRemoteService;
import id.co.gpsc.common.security.menu.UserDomain;

/**
 * User domain untuk windows authentifikasi RPC Service
 * @author I Gede Mahendra
 * @since Nov 29, 2012, 5:46:36 PM
 * @version $Id
 */
public interface UserDomainRPCService extends JSONSerializedRemoteService{
	
	/**
	 * Mendapatkan user domain dari server IIS
	 * @param parameter
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PagedResultHolder<UserDomain> getUserDomainFromIIS(SimpleQueryFilter[] filter, int page, int pageSize);
}