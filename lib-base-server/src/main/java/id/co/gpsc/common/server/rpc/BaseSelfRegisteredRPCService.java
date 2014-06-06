package id.co.gpsc.common.server.rpc;


import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.common.exception.CommonWrappedSerializableException;
import id.co.gpsc.common.server.dao.IGeneralPurposeDao;
import id.co.gpsc.common.server.dao.system.ApplicationConfigurationDao;
import id.co.gpsc.common.server.data.security.SimpleUserData;
import id.co.gpsc.common.server.util.IDTOGenerator;
import id.co.gpsc.common.server.util.IObjectCleanUp;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;



/**
 * rpc yang bisa self registered. syaratnya <ol>
 * <li>di kasi annotation {@link org.springframework.beans.factory.annotation.Autowire}</li>
 * <li>class di annotate {@link org.springframework.context.annotation.Lazy} dengan value=<i>false</i>.Lazy bean akan menyebabkan service tidak di kenali(pemanggilan RPC akan mere turn page not found)</li>
 * 
 * </ol>
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @since 5 aug 2012
 **/
public abstract class BaseSelfRegisteredRPCService extends BaseSimpleRPCServlet  implements RPCServletWrapperController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1526173326844933103L;
	
	
	protected static int AUTOMATED_SERVLET_NAME_COUNTER ; 
	
	
	
	/**
	 * logger instance
	 **/
	private static final Logger logger = LoggerFactory.getLogger(BaseSelfRegisteredRPCService.class); 
	
	
	@Autowired
	protected IGeneralPurposeDao generalPurposeDao ;
	
	
	
	/*
	@Autowired
	@Qualifier(value="urlMapping")
	private SelfRegisterRPCUrlMapper urlMappingBean ; 
	*/
	
	/**
	 * path untuk service RPC ada di mana
	 **/
	public  String getRemoteServicePath(){
		Class<?>[] interfaces =  this.getClass().getInterfaces();
		for (Class<?> scn : interfaces){
			if ( scn.isAnnotationPresent(RemoteServiceRelativePath.class)){
				RemoteServiceRelativePath swap = scn.getAnnotation(RemoteServiceRelativePath.class); 
				return swap.value(); 
			}
		}
		return null ; 
	} 
	
	@Override
	protected void onBeforeRequestDeserialized(String serializedRequest) {
		
		super.onBeforeRequestDeserialized(serializedRequest);
	}
	@Override
	public void init() throws ServletException {
		
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		reflectionRegistration(config.getServletContext()	, config.getServletName());
	} 
	
	
	 
	private void reflectionRegistration (ServletContext ctx , String servletName){
		try {
			
			Method mthd =  ctx.getClass().getMethod("getServletRegistration", String.class);
			
			ServletRegistration register =(ServletRegistration) mthd.invoke(ctx, new Object[]{servletName}); //ctx. getServletRegistration(servletName);
			
			//if ( register.get)
			register.addMapping(getRemoteServicePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	static IObjectCleanUp<?> nullCleaner = null ;  
	
	
	/**
	 * common utuls, membaca data dalam posisi paged
	 * @throws Exception 
	 **/
	protected <POJO> PagedResultHolder<POJO> selectDataPaged (Class<POJO> entityClass , SimpleQueryFilter[] filters , SimpleSortArgument[] sortArgs , int pageSize , int page) throws Exception {
		return selectDataPaged(entityClass, filters, sortArgs, pageSize, page ,(IObjectCleanUp<POJO>) null ) ; 
	}
	
	
	/**
	 * common utuls, membaca data dalam posisi paged
	 * @throws Exception 
	 **/
	protected <POJO> PagedResultHolder<POJO> selectDataPaged (Class<POJO> entityClass , SimpleQueryFilter[] filters , SimpleSortArgument[] sortArgs , int pageSize , int page , IObjectCleanUp<POJO> cleaner) throws Exception {
		Long cnt =  generalPurposeDao.count(entityClass, filters); 
		if ( cnt==null||cnt.longValue()==0)
			return null ; 
		PagedResultHolder<POJO> retval = new PagedResultHolder<POJO>(); 
		int firstRow = retval.adjustPagination(page, pageSize, cnt.intValue());
		List<POJO> datas =  generalPurposeDao.list(entityClass, filters, sortArgs, pageSize, firstRow); 
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
		Long cnt =  generalPurposeDao.count(entityClass, filters); 
		if ( cnt==null||cnt.longValue()==0)
			return null ; 
		PagedResultHolder<DTO> retval = new PagedResultHolder<DTO>(); 
		int firstRow = retval.adjustPagination(page, pageSize, cnt.intValue());
		List<POJO> datas =  generalPurposeDao.list(entityClass, filters, sortArgs, pageSize, firstRow); 
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
	 * common task. kalau di perlukan tambahan filter di sisi server, maka yang di lakukan adalah menambah isi array, method ini pekerjaan nya : 
	 * <ol>
	 * <li>Membuat array dengan size yang sesuai</li>
	 * <li>salin data dari 2 array</li>
	 * 
	 * </ol>
	 **/
	protected SimpleQueryFilter[] appendToArray (SimpleQueryFilter[] original , SimpleQueryFilter ...  arrgs ){
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
	protected SimpleQueryFilter[] appendToArrayClean (SimpleQueryFilter[] original , SimpleQueryFilter ...  arrgs ){
		SimpleQueryFilter[] retval   = new SimpleQueryFilter[original.length + arrgs.length];
		int i=0 ; 
		for ( SimpleQueryFilter scn : original  ){
			retval[i++] = scn ; 
		}
		for ( SimpleQueryFilter scn : arrgs  ){
			retval[i++] = scn ; 
		}
		return retval ; 
	}
	
	
	
	/*
	@PostConstruct
	protected void postInitTask (){
		
		urlMappingBean.registerRPCService(this);  
	}
	*/
	
	protected SimpleUserData getCurrentUser () {
		Authentication swap =  SecurityContextHolder.getContext().getAuthentication();
		if ( !(swap instanceof UsernamePasswordAuthenticationToken))
			return null ; 
		UsernamePasswordAuthenticationToken tkn = (UsernamePasswordAuthenticationToken)swap;
		Object swapPrincipal =  tkn.getPrincipal() ; 
				
		if ( swapPrincipal ==null|| !(swapPrincipal instanceof SimpleUserData))
			return null ; 
		return (SimpleUserData)swapPrincipal ; 
				 
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
	
	
	
	
}
