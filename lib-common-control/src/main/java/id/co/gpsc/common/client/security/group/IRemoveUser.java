package id.co.gpsc.common.client.security.group;

import id.co.gpsc.common.security.dto.UserGroupAssignmentDTO;

/**
 * Remove User
 * @author I Gede Mahendra
 * @since Dec 12, 2012, 12:17:06 PM
 * @version $Id
 */
public interface IRemoveUser {
	
	/**
	 * Remove
	 * @param data
	 */
	public void remove(UserGroupAssignmentDTO data);
}