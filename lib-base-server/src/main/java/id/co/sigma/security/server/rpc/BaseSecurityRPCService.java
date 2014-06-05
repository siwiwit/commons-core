package id.co.sigma.security.server.rpc;

import java.util.ArrayList;
import java.util.List;

import id.co.sigma.common.data.PagedResultHolder;
import id.co.sigma.common.data.query.SigmaSimpleQueryFilter;
import id.co.sigma.common.data.query.SigmaSimpleSortArgument;
import id.co.sigma.common.exception.CommonWrappedSerializableException;
import id.co.sigma.common.server.dao.IGeneralPurposeDao;
import id.co.sigma.common.server.dao.system.ApplicationConfigurationDao;
import id.co.sigma.common.server.rpc.impl.BaseRPCHandler;
import id.co.sigma.common.server.util.IDTOGenerator;
import id.co.sigma.common.server.util.IObjectCleanUp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class BaseSecurityRPCService<T> extends BaseRPCHandler<T> {

	/**
	 * logger instance
	 **/
	private static final Logger logger = LoggerFactory.getLogger(BaseSecurityRPCService.class);
	
	@Autowired
	protected IGeneralPurposeDao configurationDao ;
	
	/**
	 * ide nya : membungkus exeption yang non serializable menjadi exception yang lebih user frienldy.  kalau tidak, logger yang muncul adalah call server fail
	 **/
	protected Exception translateToSerializableException (Exception exception) {
		logger.error("actual error class : " + exception.getMessage()  + ", error message :" + exception.getMessage() + "\nStack  trace :" + stackTraceToString(exception) , exception); 
		//FIXME: masukan proses translasi error di sini
		if ( exception instanceof IsSerializable)
			return exception ; 
		CommonWrappedSerializableException a = new CommonWrappedSerializableException(exception.getMessage() , exception) ; 
		return a ; 
	}
	
	protected String stackTraceToString(Throwable e) {
	    StringBuilder sb = new StringBuilder();
	    for (StackTraceElement element : e.getStackTrace()) {
	        sb.append(element.toString());
	        sb.append("\n");
	    }
	    return sb.toString();
	}
	
	/**
	 * common task. kalau di perlukan tambahan filter di sisi server, maka yang di lakukan adalah menambah isi array, method ini pekerjaan nya : 
	 * <ol>
	 * <li>Membuat array dengan size yang sesuai</li>
	 * <li>salin data dari 2 array</li>
	 * 
	 * </ol>
	 **/
	protected SigmaSimpleQueryFilter[] appendToArray (SigmaSimpleQueryFilter[] original , SigmaSimpleQueryFilter ...  arrgs ){
		if ( original != null){
			if ( arrgs==null|| arrgs.length==0)
				return original ; 
			return appendToArrayClean(original, original);
		}
		else{
			if ( arrgs==null||arrgs.length==0)
				return null ; 
			return arrgs; 
		} 
	}
	
	/**
	 * ini versi sudah bersih, cukup menyalin saja dari array ke dalam array baru
	 **/
	protected SigmaSimpleQueryFilter[] appendToArrayClean (SigmaSimpleQueryFilter[] original , SigmaSimpleQueryFilter ...  arrgs ){
		SigmaSimpleQueryFilter[] retval   = new SigmaSimpleQueryFilter[original.length + arrgs.length];
		int i=0 ; 
		for ( SigmaSimpleQueryFilter scn : original  ){
			retval[i++] = scn ; 
		}
		for ( SigmaSimpleQueryFilter scn : arrgs  ){
			retval[i++] = scn ; 
		}
		return retval ; 
	}
	
	/**
	 * common utuls, membaca data dalam posisi paged
	 * @throws Exception 
	 **/
	protected <POJO> PagedResultHolder<POJO> selectDataPaged (Class<POJO> entityClass , SigmaSimpleQueryFilter[] filters , SigmaSimpleSortArgument[] sortArgs , int pageSize , int page) throws Exception {
		return selectDataPaged(entityClass, filters, sortArgs, pageSize, page ,(IObjectCleanUp<POJO>) null ) ; 
	}
	
	
	/**
	 * common utuls, membaca data dalam posisi paged
	 * @throws Exception 
	 **/
	protected <POJO> PagedResultHolder<POJO> selectDataPaged (Class<POJO> entityClass , SigmaSimpleQueryFilter[] filters , SigmaSimpleSortArgument[] sortArgs , int pageSize , int page , IObjectCleanUp<POJO> cleaner) throws Exception {
		Long cnt =  configurationDao.count(entityClass, filters); 
		if ( cnt==null||cnt.longValue()==0)
			return null ; 
		PagedResultHolder<POJO> retval = new PagedResultHolder<POJO>(); 
		int firstRow = retval.adjustPagination(page, pageSize, cnt.intValue());
		List<POJO> datas =  configurationDao.list(entityClass, filters, sortArgs, pageSize, firstRow); 
		retval.setHoldedData(datas); 
		if ( cleaner!=null && datas!= null){
			for ( POJO scn : datas){
				cleaner.cleanUp(scn); 
			}
		}
		return retval ; 
	}
	
	
	/**
	 * common utuls, membaca data dalam posisi paged
	 * @throws Exception 
	 **/
	protected <POJO , DTO> PagedResultHolder<DTO> selectDataPaged (Class<POJO> entityClass , SigmaSimpleQueryFilter[] filters , SigmaSimpleSortArgument[] sortArgs , int pageSize , int page , IDTOGenerator<POJO , DTO> dtoGenerator) throws Exception {
		Long cnt =  configurationDao.count(entityClass, filters); 
		if ( cnt==null||cnt.longValue()==0)
			return null ; 
		PagedResultHolder<DTO> retval = new PagedResultHolder<DTO>(); 
		int firstRow = retval.adjustPagination(page, pageSize, cnt.intValue());
		List<POJO> datas =  configurationDao.list(entityClass, filters, sortArgs, pageSize, firstRow); 
		ArrayList<DTO> actualDatas = new ArrayList<DTO>(); 
		retval.setHoldedData(actualDatas); 
		
		
		
		if ( dtoGenerator!=null && datas!= null){
			for ( POJO scn : datas){
				actualDatas.add( dtoGenerator.generateDTO( scn)); 
			}
		}
		return retval ; 
	}
}
