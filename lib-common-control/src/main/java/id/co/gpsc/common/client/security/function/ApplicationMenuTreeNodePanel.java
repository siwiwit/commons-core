package id.co.gpsc.common.client.security.function;

import id.co.gpsc.common.client.rpc.SigmaAsyncCallback;
import id.co.gpsc.common.client.security.BaseAriumSecurityComposite;
import id.co.gpsc.common.client.security.rpc.FunctionRPCServiceAsync;
import id.co.gpsc.common.client.widget.SpanButton;
import id.co.gpsc.common.client.widget.SpanLabel;
import id.co.gpsc.common.security.domain.PageDefinition;
import id.co.gpsc.common.security.dto.ApplicationMenuDTO;
import id.co.gpsc.common.security.exception.MenuHaveChildException;
import id.co.gpsc.jquery.client.container.JQDialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * editor node menu
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class ApplicationMenuTreeNodePanel extends BaseAriumSecurityComposite {

	private static ApplicationMenuTreeNodePanelUiBinder uiBinder = GWT
			.create(ApplicationMenuTreeNodePanelUiBinder.class);

	
	public static String MENU_ICON_CSS_PARENT ="ui-icon-folder-collapsed"  ; 
	
	public static String MENU_ICON_CSS_LINK ="ui-icon-note"  ;
	
	
	@UiField SpanLabel appMenuLabel ; 
	@UiField SpanLabel appMenuIconSpan ;
	
	@UiField SpanButton btnAdd;
	@UiField SpanButton btnEdit ; 
	@UiField SpanButton btnErase ; 
	@UiField HTMLPanel  pnlPageContainer ; 
	@UiField SpanLabel lblPageCode ; 
	@UiField SpanLabel lblPageRemark ; 
	@UiField SpanButton btnUp; 
	@UiField SpanButton btnDown ; 
	/**
	 * nomor urutan sebelumnya
	 */
	private ApplicationMenuTreeNodePanel nextNode ; 
	
	/**
	 * nomor urutan sebelumnya
	 */
	private ApplicationMenuTreeNodePanel prevNode ; 
	
	/**
	 * command untuk meremove treenode dari tree view
	 **/
	private Command eraseNodeCommand ; 
	
	/**
	 * current menu data
	 */
	private ApplicationMenuDTO currentMenuData ;
	
	/**
	 * diallog untuk menampilkan pop up menu item editor panel
	 */
	private JQDialog dialog;
	
	/**
	 * menu item editor panel
	 */
	private MenuItemEditorPanel menuItemEditorPanel; 
	
	/**
	 * worker utnuk add sub node
	 **/
	private AppendSubItemhandler subItemAppender ; 
	
	 
	
	interface ApplicationMenuTreeNodePanelUiBinder extends
			UiBinder<Widget, ApplicationMenuTreeNodePanel> {
	}

	public ApplicationMenuTreeNodePanel(ApplicationMenuDTO menuData ) {
		initWidget(uiBinder.createAndBindUi(this));
		//lblMenuLabel.setText(menuData.getLabel());
		assignData(menuData); 
		
		btnDown.setVisible(false);
		btnUp.setVisible(false);
		btnErase.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (Window.confirm("Apakah anda yakin ingin menghapus menu " + currentMenuData.getLabel()+" ?") == true) {
					// hapus dari tree
					try {
						FunctionRPCServiceAsync.Util.getInstance().eraseApplicationMenu(currentMenuData.getId(), new SigmaAsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {
								eraseNodeCommand.execute();
							}

							@Override
							protected void customFailurehandler(Throwable e) {
								if ( e instanceof MenuHaveChildException) {
									Window.alert(((MenuHaveChildException) e).getDefaultFriendlyMessage());
								} else {
									Window.alert("Gagal menghapus menu " + currentMenuData.getLabel() +" karena terjadi kesalahan pada server ! ");
								}
								e.printStackTrace();
							}
						});
					} catch (Exception e) {
						if ( e instanceof MenuHaveChildException) {
							Window.alert(((MenuHaveChildException) e).getDefaultFriendlyMessage());
						} else {
							Window.alert("Gagal menghapus menu " + currentMenuData.getLabel() +" karena terjadi kesalahan pada server ! ");
						}
						e.printStackTrace();
					} 
				}
				
				
			}
		});
		btnEdit.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
//				currentMenuData.setLabel( "---" + currentMenuData.getLabel()); 
//				reloadData(); 
				openMenuItemEditorPanel(currentMenuData, false);
			}
		});
		
		
		btnAdd.addClickHandler(new ClickHandler() {
			
//			private int counter =1 ; 
			@Override
			public void onClick(ClickEvent event) {
				ApplicationMenuDTO childData = new ApplicationMenuDTO();
				openMenuItemEditorPanel(childData, true);
			}
		});
		 
	}
	
	
	
	/**
	 * set menu data ke dalam menu item
	 **/
	public void assignData (ApplicationMenuDTO menuData) {
		this.currentMenuData = menuData ; 
		boolean haveChild = menuData.getPageId() !=null ;
		
		
		String menuLabel = "[" + menuData.getCode() + "]-" + menuData.getLabel() ; 
		
		
		String label = haveChild? "<strong>" +  menuLabel + "</strong>" : menuLabel; 
		appMenuLabel.setLabel(label);
		appMenuIconSpan.setStyleName("ui-icon "+ (haveChild? MENU_ICON_CSS_PARENT: MENU_ICON_CSS_LINK));
		if ( haveChild){
			
			
			/*PageDefinitionDTO pageData =  menuData.getPageDetail();
			this.lblPageCode.setLabel(pageData.getPageCode());
			this.lblPageRemark.setLabel(pageData.getRemark());
			*/
			FunctionRPCServiceAsync.Util.getInstance().getPageDefinition(menuData.getPageId(), new AsyncCallback<PageDefinition>() {
				
				@Override
				public void onSuccess(PageDefinition result) {
					lblPageCode.setLabel(result.getPageCode());
					lblPageRemark.setLabel(result.getRemark());
					pnlPageContainer.setVisible(true);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
					
				}
			});
		}
	}
	
	/**
	 * command untuk meremove treenode dari tree view
	 **/
	public void assignEraseNodeCommand(Command eraseNodeCommand) {
		this.eraseNodeCommand = eraseNodeCommand;
	}
	
	/**
	 * reload data
	 **/
	public void reloadData () {
		assignData(currentMenuData); 
	}
	
	
	public ApplicationMenuDTO getCurrentMenuData() {
		return currentMenuData;
	}
	
	
	/**
	 * worker utnuk add sub node
	 **/
	public void assignSubItemAppender(AppendSubItemhandler subItemAppender) {
		this.subItemAppender = subItemAppender;
	}
	
	/**
	 * buka pop up editor panel untuk menu item
	 */
	private void openMenuItemEditorPanel(ApplicationMenuDTO data, boolean isStateAddNew) {
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
		menuItemEditorPanel.setEditorState(isStateAddNew);
		if (isStateAddNew) {
			dialog.setTitle("Tambah Function");
			menuItemEditorPanel.setParentData(currentMenuData);
		} else {
			dialog.setTitle("Edit Function");
		}
		menuItemEditorPanel.renderDataToControl(data);
		
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
					subItemAppender.appendSubItem(menuItemEditorPanel.getEditedData());
					closeDialog();
				}
			});
			menuItemEditorPanel.setUpdateSucceedAction(new Command() {
				
				@Override
				public void execute() {
					currentMenuData = menuItemEditorPanel.getEditedData();
					reloadData(); 
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


	/**
	 * nomor urutan sebelumnya
	 */
	public ApplicationMenuTreeNodePanel getNextNode() {
		return nextNode;
	}


	/**
	 * nomor urutan sebelumnya
	 */
	public void setNextNode(ApplicationMenuTreeNodePanel nextNode) {
		this.nextNode = nextNode;
		if ( this.nextNode!= null){
			new Timer() {
				
				@Override
				public void run() {
					
					//btnDown.setVisible(true);
				}
			}.schedule(10);
		}	
		else{
			GWT.log("well, next nya null");
		}
			
	}


	/**
	 * nomor urutan sebelumnya
	 */
	public ApplicationMenuTreeNodePanel getPrevNode() {
		return prevNode;
	}


	/**
	 * nomor urutan sebelumnya
	 */
	public void setPrevNode(ApplicationMenuTreeNodePanel prevNode) {
		this.prevNode = prevNode;
		if ( this.prevNode!= null) {
			new Timer() {
				
				@Override
				public void run() {
					btnUp.setVisible(true);
				}
			}.schedule(10);
			
		}
		else{
			GWT.log("well, prev nya null");
		}
	}
}

