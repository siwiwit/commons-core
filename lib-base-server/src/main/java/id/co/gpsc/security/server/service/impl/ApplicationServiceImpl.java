/**
 * 
 */
package id.co.gpsc.security.server.service.impl;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.domain.Application;
import id.co.gpsc.common.security.dto.ApplicationDTO;
import id.co.gpsc.security.server.dao.impl.ApplicationDaoImpl;
import id.co.gpsc.security.server.service.IApplicationService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dode
 * @version $Id
 * @since Dec 19, 2012, 3:03:57 PM
 */
@Service
public class ApplicationServiceImpl implements IApplicationService , InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class); 
	
	public ApplicationServiceImpl(){
		System.out.println("selamat datang, saya adalah class : " + getClass().getName()  );
		System.out.println("saya memerlukan ini : security.securityApplicationId, anda harus mendefine id dari app ini dalam *.properties file anda. dalam kasus 1 app untuk table sec_applications anda cukup men set = 1 ") ; 
		
		
	}

	@Autowired
	private ApplicationDaoImpl applicationDao;
	
	
	
	@Resource(name="security.securityApplicationId")
	private String currentApplicationIdAsString ; 
	
	
	/**
	 * Id app current, refer ke {@link #currentApplicationIdAsString}
	 **/
	private BigInteger currentApplicationId = BigInteger.ONE;
	@Override
	public List<Application> getApplicationList() throws Exception {
		return applicationDao.getApplicationList();
	}

	@Transactional(readOnly=true)
	@Override
	public PagedResultHolder<ApplicationDTO> getApplicationList(int pagePosition, int pageSize) throws Exception {
		PagedResultHolder<ApplicationDTO> retval = new PagedResultHolder<ApplicationDTO>();
		List<Application> actualData = new ArrayList<Application>();
		Integer count = 0;
		
		actualData = applicationDao.getApplicationList(pagePosition, pageSize);
		if (actualData != null){
			 count = actualData.size();
		}
		
		List<ApplicationDTO> holderData = new ArrayList<ApplicationDTO>();
		if(count == null || count == 0){
			return null;
		}
		
		for (Application app : actualData) {
			ApplicationDTO dto = new ApplicationDTO();
			dto.setId(app.getId());
			dto.setApplicationCode(app.getApplicationCode());
			dto.setApplicationName(app.getApplicationName());
			dto.setApplicationLoginUrl(app.getAutentificationLoginUrl());
			dto.setApplicationNotifyUrl(app.getNotifyAuthenticateResultUrl());
			dto.setApplicationUrl(app.getApplicationUrl());			
			if(app.getStatus().equals("A")){
				dto.setIsActive(true);
			}else{
				dto.setIsActive(false);
			}			
			
			if(app.getKickPreviousLogin().equals("Y")){
				dto.setIsConcurentUser(true);
			}else{
				dto.setIsConcurentUser(false);
			}
			holderData.add(dto);
		}
		
		retval.setHoldedData(holderData);
		retval.setPage(pagePosition);
		retval.setPageSize(pageSize);
		retval.setTotalData(count);
		retval.adjustPagination();
		
		return retval;
	}

	@Transactional(readOnly=false)
	@Override
	public void saveOrUpdate(ApplicationDTO data) throws Exception {
		Application application = new Application();
		
		if(data.getId() != null){
			application = (Application) applicationDao.find(Application.class, data.getId());
			application.setModifiedBy(data.getUserId());
			application.setModifiedOn(new Date());
			
			/*Set status*/
			if(data.getIsActive()){
				application.setStatus("A");
			}else{
				application.setStatus("N");
			}
		}else{
			application.setCreatedBy(data.getUserId());
			application.setCreatedOn(new Date());
			
			/*Set Default Value*/
			application.setLanguage("en");
			application.setSessionTimeOut(2000);
			application.setNotifyAuthenticateResultUrl("/security-communication-channel/notifyLoginSuccedService.sigma");
			application.setStatus("A");
		}
		
		application.setApplicationCode(data.getApplicationCode());
		application.setApplicationName(data.getApplicationName());
		application.setApplicationUrl(data.getApplicationUrl());
		application.setAutentificationLoginUrl(data.getApplicationLoginUrl());
		
		/*Set Kick Concurent User*/
		if(data.getIsConcurentUser()){
			application.setKickPreviousLogin("Y");
		}else{
			application.setKickPreviousLogin("N");
		}
		
		if(application.getId() != null){
			applicationDao.update(application);			
		}else{
			applicationDao.insert(application);
		}
	}

	@Override
	public Application getCurrentAppDetailData() {
		BigInteger id = new BigInteger(this.currentApplicationIdAsString); 
		try {
			return this.applicationDao.get(Application.class, id);
		} catch (Exception e) {
			
			e.printStackTrace();
			return null ; 
		} 
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			this.currentApplicationId = new BigInteger(currentApplicationIdAsString);
		} catch (Exception e) {
			logger.error("gagal konversi id app , error message:" + e.getMessage(), e); 
		}
		
		
		
	}
	/**
	 * Id app current, refer ke {@link #currentApplicationIdAsString}
	 **/
	public BigInteger getCurrentApplicationId() {
		return currentApplicationId;
	}
}