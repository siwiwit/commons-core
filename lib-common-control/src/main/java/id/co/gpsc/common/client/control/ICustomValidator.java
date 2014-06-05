package id.co.gpsc.common.client.control;

import id.co.gpsc.common.client.form.exception.CommonFormValidationException;



/**
 * validator terpisah untuk layer UI
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface ICustomValidator<DATA> {

	
	/**
	 * validasi data. cek data. kalau ada yang tidak cocok, maka akan throws Exception
	 * @exception CommonFormValidationException kalau ada part data yang salah
	 */
	public void validate (DATA data) throws CommonFormValidationException ; 
}
