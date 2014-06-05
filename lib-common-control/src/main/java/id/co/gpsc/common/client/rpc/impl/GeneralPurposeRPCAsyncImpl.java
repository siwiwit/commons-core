package id.co.gpsc.common.client.rpc.impl;

import java.math.BigInteger;

import com.google.gwt.user.client.rpc.AsyncCallback;

import id.co.gpsc.common.client.rpc.GeneralPurposeRPCAsync;
import id.co.gpsc.common.client.rpc.ManualJSONSerializeRPCService;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SigmaSimpleSortArgument;
import id.co.gpsc.common.rpc.common.GeneralPurposeRPC;
import id.co.gpsc.common.util.json.IJSONFriendlyObject;

/**
 *
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class GeneralPurposeRPCAsyncImpl extends ManualJSONSerializeRPCService<GeneralPurposeRPC> implements GeneralPurposeRPCAsync{

	@Override
	public <DATA extends IJSONFriendlyObject<DATA>> void getPagedData (String objectFQCN , SigmaSimpleQueryFilter[] filters , SigmaSimpleSortArgument[] sorts , int page , int pageSize , AsyncCallback<PagedResultHolder<DATA>> callback) {
		this.submitRPCRequestRaw( "getPagedData",
			new Class[]{
				String.class,
				SigmaSimpleQueryFilter[].class , 
				SigmaSimpleSortArgument[].class , 
				int.class , 
				int.class
			}, 
			new Object []{
				objectFQCN,
				filters, 
				sorts , 
				page, pageSize
				
		}, callback);
		
	}

	@Override
	protected Class<GeneralPurposeRPC> getServiceInterface() {
		return GeneralPurposeRPC.class;
	}

	@Override
	public void getObjectByBigInteger(String objectFQCN, BigInteger objectId,
			AsyncCallback<IJSONFriendlyObject<?>> callback) {
		this.submitRPCRequestRaw( "getObjectByBigInteger",
				new Class[]{
					String.class,
					BigInteger.class 
				}, 
				new Object []{
					objectFQCN,
					objectId
					
			}, callback);
			
		
	}

	@Override
	public void getObjectById(String objectFQCN, Long objectId,
			AsyncCallback<IJSONFriendlyObject<?>> callback) {
		this.submitRPCRequestRaw( "getObjectById",
				new Class[]{
					String.class,
					Long.class 
				}, 
				new Object []{
					objectFQCN,
					objectId
					
			}, callback);
		
	}

	@Override
	public void getObjectById(String objectFQCN,
			Integer objectId, AsyncCallback<IJSONFriendlyObject<?>> callback) {
		this.submitRPCRequestRaw( "getObjectById",
				new Class[]{
					String.class,
					Integer.class 
				}, 
				new Object []{
					objectFQCN,
					objectId
					
			}, callback);
	}

	@Override
	public void getObjectById(String objectFQCN, String objectId,
			AsyncCallback<IJSONFriendlyObject<?>> callback) {
		this.submitRPCRequestRaw( "getObjectById",
				new Class[]{
					String.class,
					String.class 
				}, 
				new Object []{
					objectFQCN,
					objectId
					
			}, callback);
	}

}
