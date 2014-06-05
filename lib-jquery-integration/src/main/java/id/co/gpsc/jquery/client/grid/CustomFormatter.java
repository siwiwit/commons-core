package id.co.gpsc.jquery.client.grid;



/**
 * custom data formatter grid. anda wajib paham HTML untuk mempergunakan ini
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @since 18-aug-2012
 **/
public interface CustomFormatter<DATA> {
	
	
	/**
	 * extract field di perlukan dari data dan 
	 **/
	public String format(DATA data);

}
