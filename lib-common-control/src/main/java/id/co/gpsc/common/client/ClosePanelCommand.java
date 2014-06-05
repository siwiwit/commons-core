package id.co.gpsc.common.client;




/**
 * sebenarnya plain, spt {@link com.google.gwt.user.client.Command}. class ini di buat kusus untuk menandai bagaimana menutup panel. 
 * use case nya <br/>
 * Panel menjadi child dari panel lain. yang tahu bagaimana menutup panel ini adalah parent panel. jadinya parent panel yang akan inject close command ke dalam widget
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 20-sept-2012
 **/
public interface ClosePanelCommand {

	/**
	 * command untuk close panel
	 **/
	public void closePanel() ; 
}
