package id.co.gpsc.common.client.control;



/**
 * filter data di client boleh visible atau tidak. 
 * contoh penggunaan <br/><br/>
 * Contoh 1. Lookup Nested, parent child<br/>
 * <ol>
 * <li>data child di load semuanya dari database ke cache di local</li>
 *  <li>data di render berdasarkan parent yang di pilih. Jadinya yang parent != current tidak di tampilan</li>
 *  
 *  </ol>
 * 
 * untuk kasus ini, method {@link UIDataFilter#isDataAllowedToVisible(Object)}  return false untuk data yang di tampilkan
 * 
 * <br/><br/>
 * Class 
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public interface UIDataFilter<DATA> {
	
	
	
	
	/**
	 * data boleh tampil atau tidak
	 * @param data data yang di check boleh visible atau tidak 
	 *  
	 **/
	public boolean isDataAllowedToVisible (DATA data) ; 

}
