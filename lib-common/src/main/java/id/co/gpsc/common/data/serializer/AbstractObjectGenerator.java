package id.co.gpsc.common.data.serializer;

import id.co.gpsc.common.util.IObjectGenerator;

/**
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public abstract class AbstractObjectGenerator implements IObjectGenerator {
	
	
	
	/**
	 * class yang di generate oleh generator
	 **/
	public abstract Class<?> [] generatedClass () ; 
	
	

}
