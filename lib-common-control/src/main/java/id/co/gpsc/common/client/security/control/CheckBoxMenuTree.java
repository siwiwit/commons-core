/**
 * 
 */
package id.co.gpsc.common.client.security.control;

import id.co.gpsc.common.client.security.BaseAriumSecurityComposite;
import id.co.gpsc.common.security.domain.Function;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;

/**
 * @author Dode
 * @version $Id
 * @since Jan 8, 2013, 12:08:45 PM
 */
public class CheckBoxMenuTree extends BaseAriumSecurityComposite {
	
	/**
	 * root of tree
	 */
	private Tree rootTree;
	
	/**
	 * list of all menu in an application
	 */
	private List<Function> menus;
	
	/**
	 * list of added menu
	 */
	private List<Function> addedMenu;
	
	/**
	 * list of removed menu
	 */
	private List<Function> removedMenu;
	
	/**
	 * list of existing menu id in group application
	 */
	private List<BigInteger> selectedMenus;
	
	/**
	 * map of tree item
	 */
	private Map<BigInteger, ExtendedMenuTreeItem> treeMap;
	
	/**
	 * panel of the tree
	 */
	private ScrollPanel panel;
	
	public CheckBoxMenuTree() {
		//insialisasi variabel
		rootTree = new Tree();
		addedMenu  = new ArrayList<Function>();
		removedMenu = new ArrayList<Function>();
		treeMap = new HashMap<BigInteger, ExtendedMenuTreeItem>();
				
		panel = new ScrollPanel(rootTree);
		panel.setSize("200px", "200px");
		
		initWidget(panel);
	}
	
	/**
	 * generate tree item
	 * jika merupakan parent menu tampilkan dalam teks saja, jika tidak tampilkan sebagai check box
	 * @param treeItems list of function
	 * @param checkedTreeItems list of function id of group
	 */
	public void generateTree() {
		if (menus == null || selectedMenus == null) {
			new Timer() {
				
				@Override
				public void run() {
					generateTree();
				}
			}.schedule(50);
			return ;
		}
		treeMap.clear();
		addedMenu.clear();
		removedMenu.clear();
		for (final Function item : menus) {
			final ExtendedMenuTreeItem treeNode = new ExtendedMenuTreeItem(item.getFunctionLabel());
			treeMap.put(item.getId(), treeNode);
			treeNode.setFunction(item);
			treeNode.getchCheckBox().addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent arg0) {
					if (treeNode.getCheckBoxValue()) {
						addMenuItem(item);
					} else {
						removeMenuItem(item);
					}
				}
			});
			if (item.getFunctionIdParent() == null) {
				rootTree.addItem(treeNode);
			} else {
				treeMap.get(item.getFunctionIdParent()).setCheckBoxVisbility(false);
				treeMap.get(item.getFunctionIdParent()).addItem(treeNode);
			}
		}
		
		for (BigInteger selectedFunctionId : selectedMenus) {
			ExtendedMenuTreeItem menuItem = treeMap.get(selectedFunctionId);
			menuItem.setCheckBoxValue(true);
			if (treeMap.get(menuItem.getFunction().getFunctionIdParent()) != null)
				treeMap.get(menuItem.getFunction().getFunctionIdParent()).incNumOfSelectedChild();
		}
	}
	
	/**
	 * checking is parent add to removed list
	 * @param parentItem parent menu item
	 */
	private void removeParentChecking(Function parentItem) {
		if (parentItem == null || treeMap.get(parentItem.getId()).getNumOfSelectedChild() != 0)
			return ;
		else
			removeMenuItem(parentItem);
	}
	
	/**
	 * remove item menu dari group menu
	 * @param menuItem menu item yang di remove
	 */
	private void removeMenuItem(Function menuItem) {
		//remove menu item dari added list jika ada
		addedMenu.remove(menuItem);
		//add menu item ke removed list
		removedMenu.add(menuItem);
		ExtendedMenuTreeItem parentMenuItem = treeMap.get(menuItem.getFunctionIdParent());
		//decrase number of selected child di parentnya
		parentMenuItem.decNumOfSelectedChild();
		//cek apa parent perlu di remove atau tidak
		removeParentChecking(parentMenuItem.getFunction());
	}
	
	/**
	 * checking is parent add to added list
	 * @param parentItem parent menu item
	 */
	private void addParentChecking(Function parentItem) {
		//jika parent nya null atau mempunyai selected child lebih dari 1 ga usah di add lagi
		if (parentItem == null || treeMap.get(parentItem.getId()).getNumOfSelectedChild() > 1)
			return ;
		else
			addMenuItem(parentItem);
	}
	
	/**
	 * add menu item to group menu
	 * @param menuItem item that add
	 */
	private void addMenuItem(Function menuItem) {
		//kalo sudah ada di list of selected menu jangan di add ke added list lagi
		if (!selectedMenus.contains(menuItem.getId()))
			addedMenu.add(menuItem);
		//remove jika menu ada di removed list
		removedMenu.remove(menuItem);
		ExtendedMenuTreeItem parentMenuItem = treeMap.get(menuItem.getFunctionIdParent());
		//increase number of selected child dari parentnya
		parentMenuItem.incNumOfSelectedChild();
		//cek apakah parentnya prelu di add jg atau tidak
		addParentChecking(parentMenuItem.getFunction());
	}
	
	/**
	 * get list of menu
	 * @return
	 */
	public List<Function> getMenus() {
		return menus;
	}
	
	/**
	 * set list of menu
	 * @param menus
	 */
	public void setMenus(List<Function> menus) {
		this.menus = menus;
	}
	
	/**
	 * get selected menu
	 * @return list of selected menu id
	 */
	public List<BigInteger> getSelectedMenus() {
		return selectedMenus;
	}
	
	/**
	 * set selected menu (list of id selected menu)
	 * @param selectedMenus list of id selected menu
	 */
	public void setSelectedMenus(List<BigInteger> selectedMenus) {
		this.selectedMenus = selectedMenus;
	}
	
	/**
	 * set panel size
	 * @param width panel width
	 * @param height panel height
	 */
	public void setSize(int width, int height) {
		panel.setSize(width + "px", height + "px");
	}
	
	/**
	 * clear menu tree
	 */
	public void clearTree() {
		rootTree.clear();
	}
	
	/**
	 * get added menu item
	 * @return list of added menu item
	 */
	public List<Function> getAddedMenu() {
		return addedMenu;
	}
	
	/**
	 * get removed menu item
	 * @return list of removed menu item
	 */
	public List<Function> getRemovedMenu() {
		return removedMenu;
	}
}
