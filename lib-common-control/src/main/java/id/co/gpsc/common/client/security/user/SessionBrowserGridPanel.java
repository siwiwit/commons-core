package id.co.gpsc.common.client.security.user;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

import id.co.gpsc.common.client.control.worklist.SimpleRPCDrivenPagedSimpleGridPanel;
import id.co.gpsc.common.client.security.rpc.ApplicationSessionManagementRPCServiceAsync;
import id.co.gpsc.common.client.style.CommonResourceBundle;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.common.security.ApplicationSessionRegistry;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.CellGenericHandler;
import id.co.gpsc.jquery.client.grid.CellHyperlinkHandler;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

/**
 * 
 * 
 * panel session browser
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class SessionBrowserGridPanel extends SimpleRPCDrivenPagedSimpleGridPanel<ApplicationSessionRegistry>{
	
	
	
	/**
	 * internalizartion key untuk grid title
	 */
	public static final String I18_KEY_TITLE_GRID="security.sessionlist.grid.title";
	
	
	
	/**
	 * id session
	 */
	public static final String I18_KEY_SESSION_ID="security.sessionlist.grid.sessionid";
	
	
	public static final String I18_KEY_ACTION="security.sessionlist.grid.action";
	
	
	/**
	 * internalization key untuk grid header username
	 */
	public static final String I18_KEY_USERNAME="security.sessionlist.grid.username";
	/**
	 * internalization key untuk full name
	 */
	public static final String I18_KEY_FULLNAME="security.sessionlist.grid.fullname";
	/**
	 * internalization key untuk email
	 */
	public static final String I18_KEY_EMAIL="security.sessionlist.grid.email";
	
	
	 
	
	
	/**
	 * message konfirm untuk force loggof
	 */
	public static final String I18_KEY_CONFIRM_FORCE_LOGOFF="security.sessionlist.forcelogoff.message";
	
	
	
	
	/**
	 * label untuk force logoff
	 **/
	public static final String I18_KEY_CONFIRM_FORCE_LOGOFF_LABEL="security.sessionlist.forcelogoff.label";
	
	
	/**
	 * key kalau gagal memaksa logoff user
	 */
	public static final String I18_KEY_FAIL_FORCE_LOGOFF_MESSAGE ="security.sessionlist.fail.force.loggoff.message";
	
	
	/**
	 * ip address
	 */
	public static final String I18_KEY_IPADDRESS="security.sessionlist.grid.ipaddress";
	
	
	
	
	
	/**
	 * message untuk konfirmasi paksa logout user {0} = user yang di paksa logout
	 */
	public static String DEFAULT_CONFIRM_FORCE_LOGOFF_MESSAGE="apakah anda yakin akan melogof user {0} ?" ; 
	
	
	
	/**
	 * message default kalau gagal log off user. {0} = error message
	 **/
	public static String DEFAULT_FORCE_LOGOFF_FAIL_MESSAGE="Maaf proses paksa logout user gagal, error : {0}" ; 
	
	
	private CellHyperlinkHandler<ApplicationSessionRegistry> logoutHandler = new CellHyperlinkHandler<ApplicationSessionRegistry>(){

		@Override
		public void runProccess(ApplicationSessionRegistry data) {
			String template  = I18Utilities.getInstance().getInternalitionalizeText(I18_KEY_CONFIRM_FORCE_LOGOFF, DEFAULT_CONFIRM_FORCE_LOGOFF_MESSAGE) ;
			
			template = I18Utilities.format(new Object[]{data.getUserName()}, template); 
			if (! Window.confirm(template)){
				return ; 
			}
			showHideLoadingBlockerScreen(true); 
			ApplicationSessionManagementRPCServiceAsync.Util.getInstance().forceLogoff(data.getSessionId(), new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void result) {
					showHideLoadingBlockerScreen(false);
					reload(); 
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
					String templateError  = I18Utilities.getInstance().getInternalitionalizeText(DEFAULT_FORCE_LOGOFF_FAIL_MESSAGE, DEFAULT_CONFIRM_FORCE_LOGOFF_MESSAGE) ;
					
					templateError = I18Utilities.format(new Object[]{caught.getMessage()}, templateError);
					
					
					
					showHideLoadingBlockerScreen(false);
					
					
					Window.alert(templateError); 
				}
			}); 
		}

		@Override
		protected String generateHyperlinkLabel(ApplicationSessionRegistry data) {
			return   logoutImg +  "&nbsp;<strong>force logout</strong>";
		}
		

	
	}; 
	
	
	
	
	private StringColumnDefinition<ApplicationSessionRegistry> actionColumn ; 
	
	
	
	private String logoutImg ; 
	
	@SuppressWarnings("unchecked")
	public SessionBrowserGridPanel(){
		super(); 
		setCaption(I18Utilities.getInstance().getInternalitionalizeText(I18_KEY_TITLE_GRID, "Daftar User yg login"));
		
		actionColumn = generateCustomCell(new CellGenericHandler[]{
				logoutHandler
				
		}, "Tindakan", I18_KEY_ACTION, 100);
		
		logoutImg = "<img src='" + AbstractImagePrototype.create( CommonResourceBundle.getResources().iconCancel()).createImage().getUrl() +"' title='Force user to logout' border='0'/>"; 
	}

	@Override
	protected String generateMessageOnRequestDataGridFailure(Throwable caught) {
		return "Maaf, gagal membaca data user";
	}

	@Override
	protected Class<ApplicationSessionRegistry> getRenderedClass() {
		return ApplicationSessionRegistry.class;
	}
	
	
	

	@Override
	protected BaseColumnDefinition<ApplicationSessionRegistry, ?>[] getColumnDefinitions() {
		@SuppressWarnings("unchecked")
		BaseColumnDefinition<ApplicationSessionRegistry, ?>[]  retval = (BaseColumnDefinition<ApplicationSessionRegistry, ?>[] ) new BaseColumnDefinition<?, ?>[] {
			generateRowNumberColumnDefinition("No", 80),
			
			
			actionColumn, 
			new StringColumnDefinition<ApplicationSessionRegistry>(I18Utilities.getInstance().getInternalitionalizeText(I18_KEY_SESSION_ID, "Session Id"), 180) {
				@Override
				public String getData(ApplicationSessionRegistry data) {
					return data.getSessionId();
				}
			}, 
			new StringColumnDefinition<ApplicationSessionRegistry>(I18Utilities.getInstance().getInternalitionalizeText(I18_KEY_USERNAME, "Username") , 100) {
				@Override
				public String getData(ApplicationSessionRegistry data) {
					return data.getUserName();
				}
			}, 
			new StringColumnDefinition<ApplicationSessionRegistry>(I18Utilities.getInstance().getInternalitionalizeText(I18_KEY_FULLNAME, "Nama User") , 200) {
				@Override
				public String getData(ApplicationSessionRegistry data) {
					return data.getRealName();
				}
			},
			new StringColumnDefinition<ApplicationSessionRegistry>(I18Utilities.getInstance().getInternalitionalizeText(I18_KEY_EMAIL, "Email")  , 100) {
				@Override
				public String getData(ApplicationSessionRegistry data) {
					return data.getEmail();
				}
			},
			
			new StringColumnDefinition<ApplicationSessionRegistry>(I18Utilities.getInstance().getInternalitionalizeText(I18_KEY_IPADDRESS, "IP Address") , 100) {
				@Override
				public String getData(ApplicationSessionRegistry data) {
					return data.getIpAddress();
				}
			},
		};
		return retval;
	}
	@Override
	protected void submitRPCRequest(SimpleQueryFilter[] filters,
			SimpleSortArgument[] sortsArgument, int page, int pageSize) {
		String email = null ; 
		String usernameWildCard = null ;
		String realNameWildCard= null; 
		for ( SimpleQueryFilter scn : filters){
			if ( "email".equals(scn.getField()))
				email = scn.getFilter(); 
			else if ( "realName".equals(scn.getField()))
				realNameWildCard = scn.getFilter() ;
			else if ( "userName".equals(scn.getField()))
				usernameWildCard = scn.getFilter();
		}
		ApplicationSessionManagementRPCServiceAsync.Util.getInstance().getCurrentlyLogedInUser(usernameWildCard, realNameWildCard, email, pageSize, page, getDataCallback()); 
	}

}
