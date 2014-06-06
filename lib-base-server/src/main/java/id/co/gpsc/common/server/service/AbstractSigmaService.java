package id.co.gpsc.common.server.service;

import id.co.gpsc.common.data.ICreateAuditedData;
import id.co.gpsc.common.data.IModifyAuditedData;
import id.co.gpsc.common.exception.BaseIsSerializableException;
import id.co.gpsc.common.exception.IStackTraceToStringWorker;
import id.co.gpsc.security.server.SigmaUserDetail;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



/**
 * abstract class service
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public abstract class AbstractSigmaService implements IBaseService{
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractSigmaService.class);
	
	/**
	 * init proccess
	 **/
	@PostConstruct
	protected void postConstruct(){
		if ( BaseIsSerializableException.STACKTRACE_TO_STRING_WORKER==null){
			BaseIsSerializableException.STACKTRACE_TO_STRING_WORKER=new IStackTraceToStringWorker() {
				
				@Override
				public String extractStackTraceToString(Throwable exception) {
					StringWriter swr = new StringWriter();
					exception.printStackTrace(new PrintWriter(swr));
					return swr.toString();
				}
			};
		}
		
	}
	
	
	
	
	/**
	 * ini membaca file dari resource properties
	 * @param resourceFileName nama file resource yang perlu di baca
	 */
	protected String getTextFileContentFromResource (String resourceFileName){
		InputStream is =  getClass().getClassLoader().getResourceAsStream(resourceFileName);
		InputStreamReader reader = new InputStreamReader(is); 
		StringBuffer buffer = new StringBuffer(); 
		try {
			String line ; 
			BufferedReader br = new BufferedReader(reader);
			while( (line= br.readLine())!= null){
				buffer.append(line  +"\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return buffer.toString() ; 
	}
	
	
	
//	protected abstract<T> void setIncludedObject(T object, String[] includedObject);
	
	
	
	
	
	
	
	
	/**
	 * membaca IP dari current login
	 **/
	public String getCurrentUserIpAddress (){
		try {
			 String remoteAddress = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
				       .getRequest().getRemoteAddr();
			 return remoteAddress; 
		} catch (Exception e) {
			logger.error("gagal membaca data IP address dari current user. bisa jadi proses ini bukan berasal dari servlet, error message :" + e.getMessage() , e);
			return null ; 
		}
		
	}
	
	
	
	/**
	 * current user yang login, ini ikut dengan spring security
	 **/
	public String getCurrentUserName () {
		try {
			if ( SecurityContextHolder.getContext()== null)
				return null ; 
			if (SecurityContextHolder.getContext().getAuthentication() == null)
				return null ; 
			Object swap =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if ( swap != null &&  swap instanceof UserDetails ){
				UserDetails d = (UserDetails) swap;
				return d.getUsername() ; 
			}
			
			return null ;
		} catch (Exception e) {
			logger.error("gagal membaca data current user. error : "  + e.getMessage() , e);
			return null ; 
		}
		 
		
	}
	
	
	
	/**
	 * kode cabang dari user
	 */
	public String getCurrentBranchCode () {
		Object swap =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if ( swap != null &&  swap instanceof SigmaUserDetail ){
			SigmaUserDetail d = (SigmaUserDetail) swap;
			return d.getCurrentBranchCode() ; 
		}
		
		return null ;
	}
	
	
	/**
	 * taruh modify timestamp pada data
	 **/
	public void touchModifyTimestamp (IModifyAuditedData modifiedData) {
		if ( modifiedData==null)
			return ; 
		modifiedData.setModifiedByIPAddress(getCurrentUserIpAddress());
		modifiedData.setModifiedBy(getCurrentUserName());
		modifiedData.setModifiedOn(new Date());
	}
	/**
	 * menaruh create audit trail pada data
	 **/
	public void touchCreateTimestamp (ICreateAuditedData createdData) {
		if ( createdData==null)
			return ; 
		createdData.setCreatorIPAddress(getCurrentUserIpAddress());
		createdData.setCreatedBy(getCurrentUserName());
		createdData.setCreatedOn(new Date());
	}
	
	
	
	
        
        
        
        
        /**
         * render read me file. jadinya bersamaan dengan class ada file dengan format : namaclass_readme.txt. Ini akan di render ke sysout dan debug pada saat app up. ini untuk memberi tahu developer apa yang pelru di perhatikan
        */
        protected void renderReadMeFile (/*String fileName*/) {
        
            
            String fileName = this.getClass().getName() ; 
            fileName  = fileName.replaceAll("\\."	, File.separator);
            fileName +=   "_readme.txt" ;
            try{
                InputStream is = getClass().getResourceAsStream( "/" + fileName);
                if ( is!= null ){
                    BufferedReader reader = new BufferedReader( new InputStreamReader(is)  );
                    String line  ;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        LoggerFactory.getLogger(this.getClass().getName()).info(line);
                    }
                    
                }
                 
            }catch ( Exception exc){
                LoggerFactory.getLogger(this.getClass().getName()).error("gagal membaca file :" + fileName +",error message :" + exc.getMessage() , exc);
            }
             
            
        
        }
}
