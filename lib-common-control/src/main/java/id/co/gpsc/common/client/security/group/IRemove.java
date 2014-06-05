package id.co.gpsc.common.client.security.group;


/**
 * Remove group
 * @author I Gede Mahendra
 * @since Dec 13, 2012, 4:40:30 PM
 * @version $Id
 */
public interface IRemove<DATA extends Object> {
	
	/**
	 * Remove
	 * @param parameter
	 */
	public void remove(DATA parameter);
}