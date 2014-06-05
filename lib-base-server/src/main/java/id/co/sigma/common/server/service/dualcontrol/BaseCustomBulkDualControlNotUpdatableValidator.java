package id.co.sigma.common.server.service.dualcontrol;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseCustomBulkDualControlNotUpdatableValidator<T>  implements ICustomBulkDualControlNotUpdatableValidator<T>, 
	InitializingBean{
	
	@Autowired
	private ICustomBulkDualControlFieldRegister bulkDualControlFieldRegister;

	@Override
	public void afterPropertiesSet() throws Exception {
		bulkDualControlFieldRegister.registerBulkValidator(this);
	}
}
