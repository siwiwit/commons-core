/**
 * 
 */
package id.co.gpsc.common.client.security.control;

import id.co.gpsc.common.client.security.BaseAriumSecurityComposite;
import id.co.gpsc.common.security.domain.Function;
import id.co.gpsc.common.util.I18Utilities;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

/**
 * comoponent untuk membuat tree menu
 * cara menggunakan :
 * menu tree baru bisa di create setelah menu items di set
 * @author Dode
 * @version $Id
 * @since Jan 4, 2013, 5:26:48 PM
 */
public class MenuTree extends BaseAriumSecurityComposite {
	
	
	
	/**
	 * hash map dari item2 menu untuk tree
	 */
	private Map<BigInteger, TreeItem> treeMap;
	
	/**
	 * root tree dari menu
	 */
	private Tree rootTree;
	
	/**
	 * scroll panel tempat tree
	 */
	private ScrollPanel treePanel;
	
	/**
	 * height and width tree panel
	 */
	private int width;
	
	private int height;
	
	/**
	 * list of menu items yang akan di jadikan tree
	 */
	private List<Function> menuItems;
	
	public MenuTree() {
		
	    rootTree = new Tree();
	    treeMap = new HashMap<BigInteger, TreeItem>();
	    treePanel = new ScrollPanel(rootTree);
	    initWidget(treePanel);
	}
	  
	  /**
	   * build tree dan set menu items
	   * @param menuItems menu items
	   */
	  public void generateTree(List<Function> menuItems) {
		  this.menuItems = menuItems;
		  generateTree();
	  }
	  
	  /**
	   * build tree
	   * @param menuItems list item menu yang dimasukkan ke dalam tree
	   * @param theTree root tree
	   */
	  public void generateTree() {
		  try {
			  treeMap.clear();
			  //kalo menu item null kosong tree
			  if (menuItems == null) {
				  rootTree.clear();
				  return ;
			  }
			  
			  // lup 1 masukan dalam has map
			  for (Function menuItem : menuItems) {
				  treeMap.put(menuItem.getId(), new TreeItem(SafeHtmlUtils.fromString(menuItem.getFunctionLabel())));
				  if (menuItem.getFunctionIdParent() == null)
					  rootTree.addItem(treeMap.get(menuItem.getId()));
				  else{
					
				  }
			  }
			  // pasang pada parent nya
			  for (Function menuItem : menuItems) {
				  if (menuItem.getFunctionIdParent() == null){
					 //DO nothing
				  }
				  else{
					  TreeItem t =treeMap.get(menuItem.getFunctionIdParent()) ; 
					  if ( t== null){
						  
						  
					  }else{
						  t.addItem(treeMap.get(menuItem.getId()));
					  }
					  
				  }
				  
			  }
		  } catch (Exception e) {
			  Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.control.menutree.errorincorrectdata", "There is incorrect menu data, please fixed !, error : " + e.getMessage()));
			  e.printStackTrace(); 
		  }
	  }
	  
	  public void clearTree() {
		  rootTree.removeItems();
	  }
	  
	  public void setWidth(int width) {
		  this.width = width;
	  }
	  
	  public int getWidth() {
		  return width;
	  }
	  
	  public void setHeight(int height) {
		  this.height = height;
	  }
	  
	  public int getHeight() {
		  return height;
	  }
	  
	/**
	 * set tree panel size
	 * @param width width panel
	 * @param height height panel
	*/
	public void setSize(int width, int height) {
	    this.width = width;
	    this.height = height;
	    treePanel.setSize("" + this.width + "px", "" + this.height + "px");
	}
	
	/**
	 * set menu items
	 * @param menuItems list of menu item
	 */
	public void setMenuItems(List<Function> menuItems) {
		this.menuItems = menuItems;
	}
	
	/**
	 * get menu items
	 * @return list of menu items
	 */
	public List<Function> getMenuItems() {
		return menuItems;
	}
}
