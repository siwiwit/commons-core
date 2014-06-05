package id.co.gpsc.common.client;


/**
 * pasangan dari {@link ClosePanelCommand}, ini panel yang memerlukan close command agar bisa menutup diri nya sendiri
 **/
public interface RequireCloseCommand {
	
	
	
	/**
	 * put close command ke dalam panel
	 **/
	public void assignCloseCommand(ClosePanelCommand command);

}
