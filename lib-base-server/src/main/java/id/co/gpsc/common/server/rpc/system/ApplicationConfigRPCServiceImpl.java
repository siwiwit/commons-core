package id.co.gpsc.common.server.rpc.system;




import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.app.AppFormConfiguration;
import id.co.gpsc.common.data.app.ApplicationConfigRPCService;
import id.co.gpsc.common.data.app.I18FormMasterConfigurationNotDefinedException;
import id.co.gpsc.common.data.app.LabelDataWrapper;
import id.co.gpsc.common.data.app.NoConfigurationChangeException;
import id.co.gpsc.common.data.entity.FormConfigurationSummary;
import id.co.gpsc.common.data.entity.FormElementConfiguration;
import id.co.gpsc.common.data.entity.I18Code;
import id.co.gpsc.common.data.entity.I18NTextGroup;
import id.co.gpsc.common.data.entity.I18Text;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SigmaSimpleSortArgument;
import id.co.gpsc.common.server.dao.base.PagedResultExecutorTemplate;
import id.co.gpsc.common.server.dao.system.ApplicationConfigurationDao;
import id.co.gpsc.common.server.data.security.SigmaSimpleUserData;
import id.co.gpsc.common.server.rpc.BaseServerRPCService;
import id.co.gpsc.common.server.service.system.ApplicationConfigService;
import id.co.gpsc.common.server.util.JaxbConverterUtils;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;




/**
 * provider application configuration
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @author <a href="gede.mahendra@sigma.co.id">Gede Mahendra</a>
 * @version $Id
 **/
/*@WebServlet(
		description="servlet service provider konfigurasi"  , 
		name="id.co.sigma.common.server.rpc.system.ApplicationConfigRPCServiceImpl" ,
		urlPatterns={"/corelib-rpc/app-cnf-data.app-rpc"})*/
public class ApplicationConfigRPCServiceImpl extends /*BaseSelfRegisteredRPCService*/BaseServerRPCService<ApplicationConfigRPCService>  implements ApplicationConfigRPCService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8407745659790018657L;
	
	/**
	 * logger instance
	 **/
	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigRPCServiceImpl.class); 
	
	@Autowired
	private ApplicationConfigurationDao  applicationConfigurationDao;
	
	@Autowired
	private ApplicationConfigService applicationConfigService;

	@Override
	public AppFormConfiguration getFormConfiguration(String formId,
			String localeCode, Integer currentVersioOnClientCache)
			throws NoConfigurationChangeException,
			I18FormMasterConfigurationNotDefinedException {
		// baca dulu dari master data cache. 
		FormConfigurationSummary cached =  applicationConfigurationDao.readFormConfigurationSummary(formId, localeCode);
		if ( cached!=null&& !cached.getVersion().equals(currentVersioOnClientCache)){
			/*Sudah di fix dg code dibawah ini*/
			AppFormConfiguration tempAppCnf = JaxbConverterUtils.getInstance().convertXmlToPojo(cached.getLabels(), AppFormConfiguration.class);
			
			AppFormConfiguration appCnf = new AppFormConfiguration();
			appCnf.setLocaleId(cached.getId().getLocaleCode());
			appCnf.setParentId(cached.getId().getFormCode());
			appCnf.setVersion(cached.getVersion());
			appCnf.setConfigurationLabel(tempAppCnf.getConfigurationLabel());				
			
			//return applicationConfigService.generateConfigurationObjectFromCachedData(cached);
			return appCnf;
		}
		//FIXME: enhancement kalau cached not null maka  AppFormConfiguration(--destination--) di create dari FormConfigurationSummary(--source---variable cached)
		// parentId		-> id.formCode
		//localeId		-> id.localeCode
		//version			-> version
		//configurationLabels -> transformasi xml to pojo dari field labels
		return applicationConfigService.createAndWriteConfiguration(formId , localeCode);
	}

	
	
	
	
	@Override
	public void saveLabel(String localeCode, String key, String label) {
		//FIXME : simpan perubahan pada label
		
	}
	
	@Override
	public LabelDataWrapper[] getLabels(String key) {
		List<I18Text> texts =  applicationConfigurationDao.readLabels(key);
		if ( texts==null||texts.isEmpty())
			return null ; 
		LabelDataWrapper[] retval = new LabelDataWrapper[texts.size()];
		int i=0;
		for ( I18Text scn : texts){
			retval[i] = new LabelDataWrapper(scn.getId().getLocaleCode(), scn.getLabel());
		}
		return retval;
	}
	
	
	@Override
	public void saveLabels(String textKey, LabelDataWrapper[] labels) {
		
		
	}

	
	@Override	
	public PagedResultHolder<LabelDataWrapper> getI18NGroupId(I18Text groupId, int pageSize, int rowPosition) {		
		return applicationConfigService.getI18NGroupId(groupId, pageSize, rowPosition);				
	}


	@Override	
	public void saveLabel(I18Text data) {		
		try {
			applicationConfigService.saveLabel(data);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	@Override
	public void updateLabel(I18Text data) {
		try {
			applicationConfigService.updateLabel(data);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}





	@Override
	public List<I18Code> getAvaliableLanguages() {
		List<I18Code> swap = null ;
		try {
			swap =  applicationConfigurationDao.getAvaliableLanguages(); 
		} catch (Exception e) {
			e.printStackTrace();
//			log("gagal membaca data languages", e);
			logger.error("gagal membaca data languages", e);
		}
		return swap  ;
		
	}


	@Override
	public void saveControlConfiguration(FormElementConfiguration data) throws Exception{
		applicationConfigService.saveControlConfiguration(data);	
	}


	@Override
	public FormElementConfiguration readControlConfiguration(String formId,String elementId) throws Exception{		
		return applicationConfigService.readControlConfiguration(formId, elementId);
	}





	final SigmaSimpleSortArgument i18textSorter []={
			new SigmaSimpleSortArgument("groupCode", true) ,
			new SigmaSimpleSortArgument("id.textKey", true) , 
			new SigmaSimpleSortArgument("label", true) ,
	}; 
	@Override
	public PagedResultHolder<I18Text> getI18NTexts(int pageSize,
			int pagePosition,final SigmaSimpleQueryFilter[] filters)
			throws Exception {
		PagedResultExecutorTemplate<I18Text> executor  = new PagedResultExecutorTemplate<I18Text>() {
			@Override
			protected List<I18Text> list(int pageSize, int firstRowPosition)
					throws Exception {
				return applicationConfigurationDao.list(I18Text.class, filters, i18textSorter, pageSize, firstRowPosition);
			}
			
			@Override
			protected Integer count() throws Exception {
				Long swap =   applicationConfigurationDao.count(I18Text.class, filters);
				if ( swap==null)
					return null ; 
				return swap.intValue(); 
			}
			@Override
			protected id.co.gpsc.common.server.dao.base.PagedResultExecutorTemplate.DataCleanUpWorker<I18Text> getDataCleaner() {
				
				return new DataCleanUpWorker<I18Text>(){
					@Override
					public void cleanUpData(I18Text data) {
						data.setGroupId(new I18NTextGroup());
						data.getGroupId().setId(data.getGroupCode());
						
					}
				};
			}
		}; 
				
		return executor.executeQuery(pageSize, pagePosition);
	}




	/**
	 * ini sorter 
	 **/
	private final SigmaSimpleSortArgument sortLanguages[] = new SigmaSimpleSortArgument[]{
			new SigmaSimpleSortArgument("id",true)
	};

	
	
	@Override
	public List<I18NTextGroup> getTextGroups() {
		return applicationConfigurationDao.list(I18NTextGroup.class, sortLanguages);
	}





	@Override
	public List<I18Text> getAllLanguagesTextById(String i18Nkey) {
		if(i18Nkey==null||i18Nkey.length()==0)
			return null ; 
		return applicationConfigurationDao.getAllAvailableLanguageTextById(i18Nkey);
	}
	
	@Override
	public void saveLabels(I18Text[] texts) throws Exception {
		SigmaSimpleUserData usr = getCurrentUser();
		String usrName = usr==null?null : usr.getUsername();
		
		applicationConfigService.saveLabels(texts, usrName, getApplicationDate());
		
	}





	@Override
	public Class<ApplicationConfigRPCService> implementedInterface() {
		return ApplicationConfigRPCService.class;
	}
}