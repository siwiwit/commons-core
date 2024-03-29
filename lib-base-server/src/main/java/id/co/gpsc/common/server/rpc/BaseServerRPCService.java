/**
 * 
 */
package id.co.gpsc.common.server.rpc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.gwt.user.client.rpc.IsSerializable;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.common.exception.CommonWrappedSerializableException;
import id.co.gpsc.common.server.dao.ICustomJoinHqlProvider;
import id.co.gpsc.common.server.dao.ICustomJoinHqlProviderManager;
import id.co.gpsc.common.server.dao.IGeneralPurposeDao;
import id.co.gpsc.common.server.dao.system.ApplicationConfigurationDao;
import id.co.gpsc.common.server.data.security.SimpleUserData;
import id.co.gpsc.common.server.rpc.impl.BaseRPCHandler;
import id.co.gpsc.common.server.service.IObjectCleanUpManager;
import id.co.gpsc.common.server.util.IDTOGenerator;
import id.co.gpsc.common.server.util.IObjectCleanUp;
import id.co.gpsc.security.server.SimpleUserDetail;

/**
 * @author Agus Gede Adipartha Wibawa
 * @since Sep 9, 2013, 10:36:38 AM
 *
 */
public abstract class BaseServerRPCService<T> extends BaseRPCHandler<T>{
	
	/**
	 * logger instance
	 **/
	private static final Logger logger = LoggerFactory.getLogger(BaseSelfRegisteredRPCService.class);
	
	
	@Autowired
	private ICustomJoinHqlProviderManager customJoinHqlProviderManager; 
	
	
	
	@Autowired
	private IObjectCleanUpManager cleanUpManager; 
	
	
	@Autowired
	protected IGeneralPurposeDao generalPurposeDao ;
	
	
	
	@Autowired
	private HttpServletRequest httpServletRequest ; 
	
	protected SimpleUserData getCurrentUser () {
		Authentication swap =  SecurityContextHolder.getContext().getAuthentication();
		if ( !(swap instanceof UsernamePasswordAuthenticationToken))
			return null ; 
		UsernamePasswordAuthenticationToken tkn = (UsernamePasswordAuthenticationToken)swap;
		Object swapPrincipal =  tkn.getPrincipal() ; 
				
		if ( swapPrincipal ==null) 
			return null ; 
			
			
		if (swapPrincipal instanceof SimpleUserData)
			return (SimpleUserData)swapPrincipal ; 
		if ( swapPrincipal instanceof SimpleUserDetail){
			SimpleUserDetail act = (SimpleUserDetail) swapPrincipal ; 
			SimpleUserData retval = new SimpleUserData(); 
			retval.setBranchCode(act.getCurrentBranchCode());
			retval.setEmail(act.getEmail());
			retval.setFullName(act.getFullNameUser());
			retval.setUsername(act.getUsername());
			return retval ; 
			
		}
			
			return null ; 
		
				 
	}
	
	/**
	 * application date
	 **/
	protected Date getApplicationDate() {
		//FIXME : masukanimplementasi app date
		return new Date();
	}
	
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
	 * common utuls, membaca data dalam posisi paged
	 * @throws Exception 
	 **/
	protected <POJO> PagedResultHolder<POJO> selectDataPaged (Class<POJO> entityClass , SimpleQueryFilter[] filters , SimpleSortArgument[] sortArgs , int pageSize , int page , IObjectCleanUp<POJO> cleaner) throws Exception {
		String joinStatment =entityClass.getName() +" a "; 
		if ( customJoinHqlProviderManager.contains(entityClass.getName())){
			ICustomJoinHqlProvider<?> prv =  customJoinHqlProviderManager.getCustomProvider(entityClass.getName());
			  joinStatment = prv.generateCustomJoinStatement("a");
		}		
		
		Long cnt =  generalPurposeDao.count(  joinStatment, "a", filters); 
		if ( cnt==null||cnt.longValue()==0)
			return null ; 
		PagedResultHolder<POJO> retval = new PagedResultHolder<POJO>(); 
		int firstRow = retval.adjustPagination(page, pageSize, cnt.intValue());
		List<POJO> datas =  generalPurposeDao.list(joinStatment , "a", filters, sortArgs, pageSize, firstRow); 
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
	protected <POJO , DTO> PagedResultHolder<DTO> selectDataPaged (Class<POJO> entityClass , SimpleQueryFilter[] filters , SimpleSortArgument[] sortArgs , int pageSize , int page , IDTOGenerator<POJO , DTO> dtoGenerator) throws Exception {
		String joinStatment =entityClass.getName() +" a "; 
		if ( customJoinHqlProviderManager.contains(entityClass.getName())){
			ICustomJoinHqlProvider<?> prv =  customJoinHqlProviderManager.getCustomProvider(entityClass.getName());
			  joinStatment = prv.generateCustomJoinStatement("a");
		}	
		
		
		
		Long cnt =  generalPurposeDao.count(joinStatment,"a", filters); 
		if ( cnt==null||cnt.longValue()==0)
			return null ; 
		PagedResultHolder<DTO> retval = new PagedResultHolder<DTO>(); 
		int firstRow = retval.adjustPagination(page, pageSize, cnt.intValue());
		List<POJO> datas =  generalPurposeDao.list(joinStatment, "a", filters, sortArgs, pageSize, firstRow); 
		ArrayList<DTO> actualDatas = new ArrayList<DTO>(); 
		retval.setHoldedData(actualDatas); 
		
		
		
		if ( dtoGenerator!=null && datas!= null){
			for ( POJO scn : datas){
				actualDatas.add( dtoGenerator.generateDTO( scn)); 
			}
		}
		return retval ; 
	}
	
	/**
	 * common utuls, membaca data dalam posisi paged
	 * @throws Exception 
	 **/
	protected <POJO> PagedResultHolder<POJO> selectDataPaged (Class<POJO> entityClass , SimpleQueryFilter[] filters , SimpleSortArgument[] sortArgs , int pageSize , int page) throws Exception {
		@SuppressWarnings("unchecked")
		IObjectCleanUp<POJO> cleaner =(IObjectCleanUp<POJO>) this.cleanUpManager.getObjectCleaner(entityClass.getName()); 
		return selectDataPaged(entityClass, filters, sortArgs, pageSize, page ,cleaner) ; 
	}
	
	
	/**
	 * ini membaca absolute path dari file yang di upload. file di taruh dengan key UUID. uuid ini di kirim ke client sebagai pengenal file yang di upload. method ini untuk mengambil absolute path dari file
	 */
	protected String getUploadedFileAbsolutePath (String fileKey ) {
		return (String)httpServletRequest.getSession().getAttribute(fileKey);
	}
	
	
	/**
	 * nama file original dari data yang di upload
	 */
	protected String getUploadedFileOriginalFileName (String fileKey ) {
		return (String)httpServletRequest.getSession().getAttribute(fileKey +"__FILENAME");
	}
	
}
