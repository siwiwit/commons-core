package id.co.gpsc.common.server.rpc.impl;

import java.math.BigInteger;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.common.rpc.common.GeneralPurposeRPC;
import id.co.gpsc.common.server.rpc.BaseServerRPCService;
import id.co.gpsc.common.util.json.IJSONFriendlyObject;

/**
 *
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class GeneralPurposeRPCImpl extends BaseServerRPCService<GeneralPurposeRPC> implements GeneralPurposeRPC{

	
	
	
	
	
	@Override
	public Class<GeneralPurposeRPC> implementedInterface() {
		return GeneralPurposeRPC.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PagedResultHolder<IJSONFriendlyObject<?>> getPagedData(
			String objectFQCN, SimpleQueryFilter[] filters,
			SimpleSortArgument[] sorts, int page, int pageSize)
			throws Exception {
		return (PagedResultHolder<IJSONFriendlyObject<?>>) selectDataPaged(Class.forName(objectFQCN), filters, sorts, pageSize, page);
	}

	@Override
	public IJSONFriendlyObject<?> getObjectByBigInteger(String objectFQCN,
			BigInteger objectId) throws Exception {
		if ( objectId== null)
			return null ;
		return (IJSONFriendlyObject<?>) generalPurposeDao.get(Class.forName(objectFQCN), objectId);
		
	}

	@Override
	public IJSONFriendlyObject<?> getObjectById(String objectFQCN, Long objectId)
			throws Exception {
		if ( objectId== null)
			return null ;
		return (IJSONFriendlyObject<?>) generalPurposeDao.get(Class.forName(objectFQCN), objectId);
	}

	@Override
	public IJSONFriendlyObject<?> getObjectById(String objectFQCN,
			Integer objectId) throws Exception {
		if ( objectId== null)
			return null ;
		return (IJSONFriendlyObject<?>) generalPurposeDao.get(Class.forName(objectFQCN), objectId);
	}

	@Override
	public IJSONFriendlyObject<?> getObjectById(String objectFQCN,
			String objectId) throws Exception {
		if ( objectId== null)
			return null ;
		return (IJSONFriendlyObject<?>) generalPurposeDao.get(Class.forName(objectFQCN), objectId);
	}

}
