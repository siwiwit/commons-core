package id.co.gpsc.common.server.service.dualcontrol;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import id.co.gpsc.common.data.app.DualControlEnabledData;





/**
 * Base class untuk bulk custom validator
 * @author gede
 */
public abstract class BaseCustomBulkDualControlValidator<DATA extends DualControlEnabledData<?, ?>>  implements ICustomBulkDualControlValidator<DATA> , InitializingBean{

	
	@Autowired
	ICustomBulkDualControlValidatorManager bulkDualControlValidatorManager ; 
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		bulkDualControlValidatorManager.registerValidatorBulk(this);
		
	}
	
}
