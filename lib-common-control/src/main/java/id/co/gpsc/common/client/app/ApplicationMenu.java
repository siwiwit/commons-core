package id.co.gpsc.common.client.app;





/**
 * ini adalah DTO class. representasi menu
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public class ApplicationMenu<KEY> {
	
	
	/**
	 * id menu. sebaiknya String, Integer saja limitasi nya
	 **/
	private KEY menuId ; 
	/**
	 * label dari menu
	 **/
	private String label ; 
	
	private String menuCode;
	
	private Integer sequence;
	
	/**
	 * flag ada child atau tidak
	 **/
	private boolean haveChildren;
	
	/**
	 * referensi ke parent menu
	 **/
	private ApplicationMenu<KEY> parentMenu ;
	
	
	
	
	/**
	 * id dari parent menu. ini untuk menyusun ulang tree menu
	 **/
	private KEY parentMenuId ; 
	/**
	 * array of children
	 **/
	private ApplicationMenu<KEY>[] children ;
	/**
	 * flag child node sudah di load atau tidak
	 **/
	private boolean childrenLoaded =false;
		
	/**
	 * field serba guna. bisa temp untuk children
	 **/
	private Object miscObject ;
	
	/**
	 * menu ini invoke apa
	 **/
	private String actionCommand ;
	
	
	/**
	 * data tambahan, json formatted data di taruh dalam informasi menu
	 **/
	private String additionalData ; 
	public ApplicationMenu(){}
	
	
	public ApplicationMenu(KEY menuId , String label){
		this.menuId=menuId ; 
		this.label=label;
		
	}
	/**
	 * field serba guna. bisa temp untuk children
	 **/
	public Object getMiscObject() {
		return miscObject;
	}
	/**
	 * field serba guna. bisa temp untuk children
	 **/
	public void setMiscObject(Object miscObject) {
		this.miscObject = miscObject;
	}
	
	/**
	 * label dari menu
	 **/
	public String getLabel() {
		return label;
	}
	/**
	 * label dari menu
	 **/
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * id menu. sebaiknya String, Integer saja limitasi nya
	 **/
	public void setMenuId(KEY menuId) {
		this.menuId = menuId;
	}
	/**
	 * id menu. sebaiknya String, Integer saja limitasi nya
	 **/
	public KEY getMenuId() {
		return menuId;
	}
	/**
	 * referensi ke parent menu
	 **/
	public ApplicationMenu<KEY> getParentMenu() {
		return parentMenu;
	}
	/**
	 * referensi ke parent menu
	 **/
	public void setParentMenu(ApplicationMenu<KEY> parentMenu) {
		this.parentMenu = parentMenu;
	}
	/**
	 * flag ada child atau tidak
	 **/
	public void setHaveChildren(boolean haveChildren) {
		this.haveChildren = haveChildren;
	}
	/**
	 * flag ada child atau tidak
	 **/
	public boolean isHaveChildren() {
		return haveChildren;
	}
	
	/**
	 * array of children
	 **/
	public ApplicationMenu<KEY>[] getChildren() {
		return children;
	}
	/**
	 * array of children
	 **/
	public void setChildren(ApplicationMenu<KEY>[] children) {
		this.children = children;
		if ( this.children!=null&&this.children.length>0)
			haveChildren=true;
	}
	/**
	 * flag child node sudah di load atau tidak
	 **/
	public void setChildrenLoaded(boolean childrenLoaded) {
		this.childrenLoaded = childrenLoaded;
	}
	/**
	 * flag child node sudah di load atau tidak
	 **/
	public boolean isChildrenLoaded() {
		return childrenLoaded;
	}
	/**
	 * menu ini invoke apa
	 **/
	public String getActionCommand() {
		return actionCommand;
	}
	/**
	 * menu ini invoke apa
	 **/
	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public Integer getSequence() {
		return sequence;
	}
	
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	/**
	 * id dari parent menu. ini untuk menyusun ulang tree menu
	 **/
	public KEY getParentMenuId() {
		return parentMenuId;
	}

	/**
	 * id dari parent menu. ini untuk menyusun ulang tree menu
	 **/
	public void setParentMenuId(KEY parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	
	/**
	 * data tambahan, json formatted data di taruh dalam informasi menu
	 **/
	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}
	/**
	 * data tambahan, json formatted data di taruh dalam informasi menu
	 **/
	public String getAdditionalData() {
		return additionalData;
	}
		
}