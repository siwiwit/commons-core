package id.co.gpsc.common.client.security.group;


/**
 * 
 * @author Dein
 * @version $Id
 * @since Dec 17, 2012, 3:44:21 PM
 * DATA 
 */
public interface IOpenAndCloseable<DATA extends Object> {
	
	public void closeDialog();
	
	public void openDialog(DATA data);
}