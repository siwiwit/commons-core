package id.co.gpsc.common.client.app;





/**
 * versi simple, all string untuk menu. jadinya data ID di kirim as string ke client
 **/
public class RPCFriendlyApplicationMenu extends ApplicationMenu<String>{
	
	@Override
	public void setMenuId(String menuId) {
		super.setMenuId(menuId);
	}
	
	
	@Override
	public String getMenuId() {
		return super.getMenuId();
	}

}
