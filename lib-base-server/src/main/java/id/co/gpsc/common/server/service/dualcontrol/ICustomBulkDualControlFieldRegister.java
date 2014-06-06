package id.co.gpsc.common.server.service.dualcontrol;



public interface ICustomBulkDualControlFieldRegister {
	
	/**
	 * Register manager untuk validasi field - field tertentu dari master table.
	 */
	public void registerBulkValidator(ICustomBulkDualControlNotUpdatableValidator<?> validator);

}
