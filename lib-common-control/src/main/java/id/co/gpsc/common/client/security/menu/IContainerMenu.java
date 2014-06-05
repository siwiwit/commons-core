package id.co.gpsc.common.client.security.menu;

/**
 * Container menu
 * @author Gede Sutarsa
 * @author I Gede Mahendra
 * @since Apr 12, 2013, 3:45:46 PM
 * @version $Id
 */
public interface IContainerMenu<DATA extends IBaseMenu> extends IBaseMenu{
		
	public  void add(DATA   subMenu);
}