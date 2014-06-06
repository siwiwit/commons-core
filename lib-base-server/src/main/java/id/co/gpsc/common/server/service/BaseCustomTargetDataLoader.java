package id.co.gpsc.common.server.service;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.app.DualControlEnabledData;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.common.server.dao.IGeneralPurposeDao;
import id.co.gpsc.common.server.dao.system.ApplicationConfigurationDao;

/**
 * 
 * base template untuk class loader custom dual control data. ini untuk kebutuhan data kusus. 
 * item-item yang perlu join dengan table lain di masukan di sini
 * 
 * . ini <strong>tidak lazy</strong>( lazy=false)
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public abstract class BaseCustomTargetDataLoader<DATA extends DualControlEnabledData<DATA, ?>> implements ICustomTargetDataLoader<DATA> , InitializingBean{

	
	
	
	@Autowired
	private ICustomTargetDataLoaderContainer customTargetDataLoaderContainer ; 
	
	
	@Autowired
	protected IGeneralPurposeDao generalPurposeDao ; 
	
	
	
	@Override
	public DATA loadDataByPackedId(String packedId) throws Exception {
		return generalPurposeDao.get(this.getTargetClass(), packedId);
	}
	
	
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		customTargetDataLoaderContainer.register(this); 
		
	}
	
	
	
	/**
	 * akses data mentahan
	 **/
	@SuppressWarnings("unchecked")
	protected  PagedResultHolder<DATA> genericlistDataRaw( Class<? extends DualControlEnabledData<?, ?>> entCls,
			int page, int pageSize, SimpleQueryFilter[] filters,
			SimpleSortArgument[] sortArguments) throws Exception  {
		
		Long cnt = generalPurposeDao.count(entCls, filters); 
		if ( cnt==null)
			return null ; 
		PagedResultHolder<DATA> original = new PagedResultHolder<DATA>(); 
		original.setPage(page); 
		original.setPageSize(pageSize);
		int firstRow =  original.adjustPagination(page, pageSize, cnt.intValue());
		List<DualControlEnabledData<?,?>> dataList =  generalPurposeDao.list(entCls , filters , sortArguments , pageSize , firstRow);
		original.setHoldedData((List<DATA>) dataList); 
		return original;
	}
	
	
	
}
