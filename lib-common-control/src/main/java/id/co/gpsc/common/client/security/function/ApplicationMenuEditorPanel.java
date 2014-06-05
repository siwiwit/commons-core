package id.co.gpsc.common.client.security.function;

import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.rpc.SigmaAsyncCallback;
import id.co.gpsc.common.client.security.rpc.FunctionRPCServiceAsync;
import id.co.gpsc.common.client.widget.BaseSigmaComposite;
import id.co.gpsc.common.security.dto.ApplicationMenuDTO;
import id.co.gpsc.jquery.client.container.JQDialog;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class ApplicationMenuEditorPanel extends BaseSigmaComposite {

	private static ApplicationMenuEditorPanelUiBinder uiBinder = GWT
			.create(ApplicationMenuEditorPanelUiBinder.class);
	
	
	@UiField Tree appMenuTree  ; 
	@UiField ExtendedButton btnAdd;
	
	/**
	 * diallog untuk menampilkan pop up menu item editor panel
	 */
	private JQDialog dialog;
	
	/**
	 * menu item editor panel
	 */
	private MenuItemEditorPanel menuItemEditorPanel; 
	
	
	
	
	
	

	interface ApplicationMenuEditorPanelUiBinder extends
			UiBinder<Widget, ApplicationMenuEditorPanel> {
	}

	public ApplicationMenuEditorPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		new Timer() {
			
			@Override
			public void run() {
				
				requestAndRenderTree();
			}
		}.schedule(1000);
	}
	
	 
	
	
	/**
	 * request data via RPC dan render data
	 **/
	protected void requestAndRenderTree () {
		FunctionRPCServiceAsync.Util.getInstance().getCurrentAppMenuDToByAppIdOrderByTreeLevelAndSiblingOrder(new SigmaAsyncCallback<List<ApplicationMenuDTO>>() {
			@Override
			protected void customFailurehandler(Throwable caught) {
				
				
			}
			public void onSuccess(List<ApplicationMenuDTO> appMenus) {
				arrangeMenusAsTree(appMenus, new ArrayList<ApplicationMenuDTO>(), new ArrayList<TreeItem> ());
			};
		});
	}
	

	
	
	
	
	/**
	 * ini akan menyusun data as tree of data. parent have child
	 **/
	protected void arrangeMenusAsTree (List<ApplicationMenuDTO> appMenus , ArrayList<ApplicationMenuDTO> level0 , ArrayList<TreeItem> level0TreeItem) {
		if ( appMenus==null||appMenus.isEmpty())
			return  ; 
		level0 = new ArrayList<ApplicationMenuDTO>();
		level0TreeItem = new ArrayList<TreeItem> (); 
		Map<BigInteger, ApplicationMenuDTO> indexedMenus = new HashMap<BigInteger, ApplicationMenuDTO>();
		
		Map<Integer, ArrayList< ApplicationMenuDTO>> indexedByLevel = new HashMap<Integer, ArrayList<ApplicationMenuDTO>>(); 
		 
		
		for ( ApplicationMenuDTO scn : appMenus){
			indexedMenus.put(scn.getId(), scn);
			if (! indexedByLevel.containsKey(scn.getTreeLevel())){
				indexedByLevel.put(scn.getTreeLevel(), new ArrayList<ApplicationMenuDTO>()); 
			}
			indexedByLevel.get(scn.getTreeLevel()).add(scn) ; 
			if ( scn.getParentId()== null){
				level0.add(scn); 
				 
			}
		}
		for ( ApplicationMenuDTO scn : appMenus){
			if ( scn.getParentId()== null || !indexedMenus.containsKey(scn.getParentId()))
				continue ;
			ApplicationMenuDTO parent =indexedMenus.get(scn.getParentId()) ; 
			if ( parent.getSubMenus()== null){
				 
				parent.setSubMenus(new ArrayList<ApplicationMenuDTO>());
			}
			parent.getSubMenus().add(scn);
		}
		Map<BigInteger, TreeItem> indexedTreeMenus = new HashMap<BigInteger, TreeItem>() ;
		
		// render level per level. max 1000 level
		if (! indexedByLevel.containsKey(1)){
			return ; 
		}
		// level 1 di arrange sendiri
		ArrayList<ApplicationMenuDTO> menusLevel0  = indexedByLevel.get(1);
		for ( ApplicationMenuDTO scn : menusLevel0){
			TreeItem itm = generateMenuItemNode(scn);
			indexedTreeMenus.put(scn.getId(), itm);
			level0TreeItem.add(itm); 
			this.appMenuTree.addItem(itm);
		}
		
		for ( int i = 2 ; i < 1000 ;i++){
			if (! indexedByLevel.containsKey(i))
				break ; 
			ArrayList<ApplicationMenuDTO> menus = indexedByLevel.get(i); 
			if ( menus==null||menus.isEmpty())
				continue ; 
			for ( ApplicationMenuDTO scn : menus){
				TreeItem itm = generateMenuItemNode(scn); 
				indexedTreeMenus.put(scn.getId(), itm); 
				if ( scn.getParentId()!= null && indexedTreeMenus.containsKey(scn.getParentId())){
					TreeItem parntTree =  indexedTreeMenus.get(scn.getParentId()) ;
					if ( parntTree.getChildCount()> 0 ){
						int idx = parntTree.getChildCount()-1 ; 
						TreeItem childTree =  parntTree.getChild(idx) ; 
						if ( childTree== null){
							GWT.log( "child tidak di temukan pada index: " + idx);
							
						}else {
							ApplicationMenuTreeNodePanel prevUserData =(ApplicationMenuTreeNodePanel)childTree.getWidget() ; 
							ApplicationMenuTreeNodePanel crnt =   (ApplicationMenuTreeNodePanel)itm.getWidget();
							prevUserData.setNextNode(crnt);
							crnt.setPrevNode(prevUserData);
						}
						
					}else {
						
					}
					 
					
					parntTree.addItem(itm);
				}
			}
		}
		 
	}
	
	
	
	
	
	/**
	 * worder untuk apend tree item
	 **/
	protected TreeItem appendItemWorker (TreeItem parentItem , ApplicationMenuDTO menuData) {
		TreeItem itemBaru = generateMenuItemNode(menuData);
		parentItem.addItem(itemBaru); 
		return itemBaru ; 
	}
	
	
	
	/**
	 * ini untuk generate node tree. ini tidak rekursif
	 **/
	protected TreeItem generateMenuItemNode  (ApplicationMenuDTO menuData) {
		if ( menuData.getSubMenus()==null|| menuData.getSubMenus().isEmpty()){
			ApplicationMenuTreeNodePanel node = new ApplicationMenuTreeNodePanel(menuData) ; 
			final TreeItem retval =  new TreeItem(node);
			
			node.assignEraseNodeCommand(new Command() {
				@Override
				public void execute() {
					retval.remove(); 
					
				}
			});
			node.assignSubItemAppender(new AppendSubItemhandler() {
				
				@Override
				public void appendSubItem(ApplicationMenuDTO menuData) {
					appendItemWorker(retval, menuData); 
					
				}
			}); 
			retval.setUserObject(menuData);
			return retval ; 
		}else{
			
			ApplicationMenuTreeNodePanel node = new ApplicationMenuTreeNodePanel(menuData) ; 
			final TreeItem retval =  new TreeItem(node);
			node.assignEraseNodeCommand(new Command() {
				@Override
				public void execute() {
					retval.remove(); 
					
				}
			}); 
			
			node.assignSubItemAppender(new AppendSubItemhandler() {
				
				@Override
				public void appendSubItem(ApplicationMenuDTO menuData) {
					appendItemWorker(retval, menuData); 
					
				}
			}); 
			retval.setUserObject(menuData);
			return retval ;
		} 
	}

	@UiHandler("btnAdd")
	void onBtnAddClickHandler(ClickEvent event) {
		ApplicationMenuDTO newData = new ApplicationMenuDTO();
		openMenuItemEditorPanel(newData);
	}
	
	/**
	 * buka pop up editor panel untuk menu item
	 */
	private void openMenuItemEditorPanel(ApplicationMenuDTO data) {
		if (dialog == null) {
			dialog = new JQDialog("");
			dialog.appendButton("Simpan", new Command() {
				
				@Override
				public void execute() {
					getMenuItemEditorPanel().saveMenuItem();
				}
			});
			dialog.appendButton("Batal", new Command() {
				
				@Override
				public void execute() {
					closeDialog();
				}
			});
		}
		MenuItemEditorPanel menuItemEditorPanel = getMenuItemEditorPanel();
		dialog.setWidget(menuItemEditorPanel);
		dialog.setTitle("Tambah Menu");
		menuItemEditorPanel.setEditorState(true);
		menuItemEditorPanel.renderDataToControl(data);
		menuItemEditorPanel.setParentData(null);
		dialog.setHeightToAuto();
		dialog.setWidth(350);
		dialog.setResizable(false);
		dialog.show(true);
	}
	
	/**
	 * get menu item editor panel
	 * @return menu item editor panel
	 */
	public MenuItemEditorPanel getMenuItemEditorPanel() {
		if (menuItemEditorPanel == null) {
			menuItemEditorPanel = new MenuItemEditorPanel();
			menuItemEditorPanel.setSaveSucceedAction(new Command() {
				
				@Override
				public void execute() {
					ApplicationMenuDTO newData = menuItemEditorPanel.getEditedData();
					TreeItem itm = generateMenuItemNode(newData);
					appMenuTree.addItem(itm);
					closeDialog();
				}
			});
		}
		return menuItemEditorPanel;
	}
	
	/**
	 * tutup pop up dialog
	 */
	private void closeDialog() {
		dialog.close();
	}
	
	
	
}

