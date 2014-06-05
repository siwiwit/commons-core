package id.co.gpsc.common.client.security.common;

/**
 * Interface untuk open dan close dialog
 * @author I Gede Mahendra
 * @since Dec 18, 2012, 10:24:48 AM
 * @version $Id
 */
public interface IOpenCloseDialog<DATA extends Object>{
	
	/**
	 * Open dialog
	 * @param data
	 */
	public void openDialog(DATA data);
	
	/**
	 * Close dialog
	 */
	public void closeDialog();
}