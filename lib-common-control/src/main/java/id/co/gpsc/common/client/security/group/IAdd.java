package id.co.gpsc.common.client.security.group;


/**
 * Add User
 * @author I Gede Mahendra
 * @since Dec 11, 2012, 5:07:08 PM
 * @version $Id
 */
public interface IAdd<DATA extends Object> {
	
	/**
	 * User add
	 * @param data
	 */
	public void add(DATA data);
}