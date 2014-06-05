package id.co.gpsc.common.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.control.OnScreenConfigurableControl;
import id.co.gpsc.common.client.lov.ClientSideLOVManager;
import id.co.gpsc.common.client.lov.LOVCapabledControl;
import id.co.gpsc.common.client.lov.LovPoolPanel;
import id.co.gpsc.common.data.lov.LOVRequestArgument;
import id.co.gpsc.common.form.DisposeablePanel;
import id.co.gpsc.common.security.dto.UserDetailDTO;
import id.co.gpsc.common.util.I18Utilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



/**
 * Base Composite. Composite dengan fungsi dasar dari yang shared: 
 * <ol>
 * <li>Lookup registration</li>
 * <li>application date</li>
 * </ol>
 * 
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 **/
public abstract class BaseSigmaComposite extends Composite implements LovPoolPanel ,DisposeablePanel{
	
	
	
	
	/**
	 * data security dari current login
	 **/
	public static UserDetailDTO userDetail;
    
        /**
         * on attach dari method ini secara otomatis akan memeriksa ke atas . ini menentukan maksimal berapa level ke atas nya komponen akan melakukan scan. akan give up scanning kalau max sudah tercapa
        */
        protected static final int MAX_DISPOSABLE_PARENT_DEEP_SCAN = 50 ; 
    
	/**
	 * tanggal applikasi
	 **/
	public static Date APPLICATION_DATE =new Date(); 
	
	
        
        
        /**
         * array of disposable item
         */
	private ArrayList<DisposeablePanel> disposableChildren = new ArrayList<DisposeablePanel>(); 
	
	
	/**
	 * dataregister event handler ke dalam LOV manager. di index agar bisa di detach kembali pad saat di panggil {@link #unregister(LOVCapabledControl)}
	 **/
	private Map<LOVCapabledControl, HandlerRegistration> indexedRegistration = new HashMap<LOVCapabledControl, HandlerRegistration>();
	
	/**
	 * lov yang LOV enabled
	 **/
	private ArrayList<LOVCapabledControl> lovControls = new ArrayList<LOVCapabledControl>();
	
        
        
        /**
         * disposable panel parent
        */
	private DisposeablePanel parentDisposeablePanel;
	
	
	
	/**
	 * method titipan untuk di trigger pada saat on load. ini akan di trigger on attach. memastikan LOV control siap di pergunakan
	 */
	private Command loadLOVCommand ; 
	//@org.springframework.beans.factory.annotation.Autowired
	//private IClientSideLOVManager clientSideLOVManager;  
	@Override
	public void registerLOVCapableControl(LOVCapabledControl control) {
		if (lovControls.contains(control)) {
			GWT.log("LOVFRAMEWORK: register di reject karena sudah ada item yang sama di register");
                    return ;
                }
		lovControls.add(control);
		HandlerRegistration reg =  ClientSideLOVManager.getInstance()/*clientSideLOVManager*/.registerLOVChangeListener(this, control);
		indexedRegistration.put(control ,  reg) ; 
		
	}
	
	@Override
	public void unregister(LOVCapabledControl control) {
		if ( !lovControls.contains(control)) {
                    return ;
                } 
		if (indexedRegistration.containsKey(control)) {
                    indexedRegistration.get(control).removeHandler();
             }
		lovControls.remove(control);
		
	}

        @Override
        public void unregisterAllLOVControls() {
            if ( indexedRegistration.isEmpty()) {
                return ;
            } 
            for ( HandlerRegistration scn : indexedRegistration.values()){
                scn.removeHandler();
            }
            indexedRegistration.clear();
            lovControls.clear();
        }
	
        
        
        
        
	@Override
	public void fillLookupValue() {
		fillLookupValue(null);
	}
	
	@Override
	public void fillLookupValue(final Command afterLoadCompleteCommand) {
		if ( isAttached())
			actualFillLookupValue(afterLoadCompleteCommand);
		else{
			loadLOVCommand = new Command() {
				@Override
				public void execute() {
					actualFillLookupValue(afterLoadCompleteCommand);
				}
			};
		}
	}
	
	
	/**
	 * actual handler. ini pintu untuk meudahkan pemasangan timer
	 */
	protected void actualFillLookupValue(Command afterLoadCompleteCommand) {
		if ( lovControls.isEmpty()) {
                    return ;
                } 
		if ( afterLoadCompleteCommand!= null){
			final Command orgCommand = afterLoadCompleteCommand ; 
			afterLoadCompleteCommand = new Command() {
				
				@Override
				public void execute() {
					
					for ( LOVCapabledControl scn : lovControls){
						scn.stopLoadingAnimation(); 
					}
					orgCommand.execute();
				}
			};
		}else{
			afterLoadCompleteCommand = new Command() {
				@Override
				public void execute() {
					
					for ( LOVCapabledControl scn : lovControls){
						scn.stopLoadingAnimation(); 
					}
					
				}
			};
		}
		for ( LOVCapabledControl scn : lovControls){
			scn.startLoadingAnimation(); 
		}
		ArrayList<LOVRequestArgument> args= new ArrayList<LOVRequestArgument>();
		ArrayList<String> avoidDuplicateHandler = new ArrayList<String>();
		
		for ( LOVCapabledControl scn : lovControls){
			String key = scn.getLOVSource().toString()+"::"+ scn.getParameterId() + "-" + getCurrentLocalizationCode() ;
			if ( avoidDuplicateHandler.contains(key)) {
                            continue ;
                        }
			avoidDuplicateHandler.add(key);
			LOVRequestArgument baru = new LOVRequestArgument(scn.getParameterId(), scn.getLOVSource());
			args.add(baru);
		}
		 ClientSideLOVManager.getInstance().requestLOVUpdate( getCurrentLocalizationCode(), this, args , afterLoadCompleteCommand);
	}
	
	@Override
	protected void initWidget(Widget widget) {
		super.initWidget(widget);
		if ( widget.getElement().getId()== null ||  widget.getElement().getId().isEmpty()){
			widget.getElement().setId(DOM.createUniqueId());
		}
			
		getElement().setPropertyObject(CommonClientControlConstant.TAG_KEY_SEL_REF, this);
		String debugerFlag = Window.Location.getParameter(CommonClientControlConstant.SHOW_DEBUGER_THING_KEY) ; 
		if ( !GWT.isProdMode() || "true".equalsIgnoreCase(debugerFlag) ){
			widget.setTitle("class:" + getClass().getName());
		}
		if ( widget.getElement().getId()==null||widget.getElement().getId().length()==0){
			widget.getElement().setId("SIGMA-COMPOSITE-" + DOM.createUniqueId());
		}
		if ( widget.getElement().getId()==null||widget.getElement().getId().length()==0){
			 widget.getElement().setId("SIGMA_COMPOSITE_" + DOM.createUniqueId());
		}
	}
	
	

	/**
	 * LOV capable controls yang sudah bisa di register
	 **/
	public ArrayList<LOVCapabledControl> getLovControls() {
		return lovControls;
	}
	
	
	
	@Override
	public void registerLOVs(final LOVCapabledControl[] controls) {
		 
		if ( controls==null||controls.length==0){
			return ; 
                }
		for ( LOVCapabledControl scn : controls){
			registerLOVCapableControl(scn);
		}
		
		
	}
	
	
	
	/**
	 * kode localization current
	 **/
	public String getCurrentLocalizationCode (){
		return I18Utilities.getInstance().getCurrentLocalizationCode();
	}

        @Override
        protected void onAttach() {
            super.onAttach();
            try {
            	 if ( loadLOVCommand!= null){
                 	loadLOVCommand.execute(); 
                 	loadLOVCommand = null ; 
                 }
                 
                 Widget bapak = this.getParent() ; 
                 int scanDepth = 0 ; 
                 while(bapak !=null){
                     if(bapak instanceof DisposeablePanel){
                         DisposeablePanel dspParent =(DisposeablePanel)bapak;
                         setParentDisposablePanel(dspParent);
                         dspParent.registerDisposableChild(this);
                         break ; 
                     }
                     scanDepth ++;
                     if(scanDepth>=MAX_DISPOSABLE_PARENT_DEEP_SCAN){
                         return ; 
                     }    
                     bapak=bapak.getParent();
                 }
			} catch (Exception e) {
				GWT.log("Gagal dalam proses attach :" + e.getMessage() , e);
			}
           
        }

        @Override
        protected void onDetach() {
        	try {
        		super.onDetach();
			} catch (Exception e) {
				GWT.log("gagal dalam proses (super)detach :" + e.getMessage() , e );
			}
            
            try {
            	if(this.parentDisposeablePanel!=null){
                    this.parentDisposeablePanel.unregisterDisposableChild(this);
                }
			} catch (Exception e) {
				GWT.log("gagal dalam proses detach :" + e.getMessage() , e );
			}
               
        }
        
        
        
        
	
	/**
	 * flag mode admin on. kalau ini on editor 
	 **/
	public boolean isAppAdminModeOn (){
		//FIXME: give me real live code!!!
		return false;
	}
	
	
	/**
	 * tanggal applikasi
	 **/
	public Date getApplicationDate (){
		//FIXME: perbaiki ini yah. gunakan real life app date
		return APPLICATION_DATE;
	}

        @Override
        public void dispose() {
            customDisposeTask();
            unregisterAllLOVControls();
            if (! this.disposableChildren.isEmpty()){
                for (DisposeablePanel scn : disposableChildren){
                    scn.dispose();
                }
            }
        }
        
        
        
        /**
         * task tambahan dalam proses dispose. masukan di sini kalau anda berencana untuk menambahkan pekerjaan tambahan dalam proses dispose<br/>
         * Pekerjaan di sini adalah : menghancurkan semua widget. ini unutk membebaskan isi memory
         */
        protected void customDisposeTask() {
           
        }

        @Override
        public void registerDisposableChild(DisposeablePanel disposableChild) {
            if ( disposableChild==null|| this.disposableChildren.contains(disposableChild)){
                return ; 
            }
            disposableChildren.add(disposableChild);
        }

        @Override
        public void unregisterDisposableChild(DisposeablePanel disposableChild) {
              if ( disposableChild==null|| !this.disposableChildren.contains(disposableChild)){
                return ; 
              }
              disposableChildren.remove(disposableChild);
        }

        @Override
        public void setParentDisposablePanel(DisposeablePanel parent) {
            this.parentDisposeablePanel = parent ; 
        }

        @Override
        public DisposeablePanel getParentDisposablePanel() {
            return this.parentDisposeablePanel  ; 
        }
	
	
	
        
        /**
         * semua controls yang configurables
         **/
        protected  ArrayList<OnScreenConfigurableControl> getAllOnScreenConfiguratbleControls() {
        	return null;
        }
        
        /**
    	 * Get current user login at application
    	 * @return String
    	 */
    	protected String getCurrentUserLogin(){
    		/*return "Sample User";*/
    		if(userDetail == null){
    			return null;
    		}
    		return userDetail.getUsername();
    	}
    	
    	protected UserDetailDTO getUserDetail() {
    		return userDetail;
    	}

    	protected void setUserDetail(UserDetailDTO user) {
    		userDetail = user;
    	}
}
