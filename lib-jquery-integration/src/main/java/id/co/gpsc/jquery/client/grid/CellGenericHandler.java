/**
 * 
 */
package id.co.gpsc.jquery.client.grid;

import id.co.gpsc.common.control.DataProcessWorker;

/**
 * creator hyperlink ke dalam grid cell. <br/>
 * yang bisa di pergunakan
 * <ol>
 * <li>{@link CellHyperlinkHandler}</li>
 * 
 * </ol>
 * @author <a href="mailto:gede.wibawa@sigma.co.id">Agus Gede Adipartha Wibawa</a>
 * @since Oct 4, 2013 11:18:11 AM
 * 
 */
public abstract class CellGenericHandler<DATA> implements DataProcessWorker<DATA>{

	
	

	
	
	
	
	/**
	 * nama method untuk on click
	 **/
	private String methodOnClickName ; 
	
	
	
	
	 

 

	
	/**
	 * data driven show hide button, jadinya datanya yang menentukan apakah data nya harus visible atau tidak. misal kalau state = deleted tombol edit = invis
	 **/
	public boolean isDataAllowMeToVisible (DATA data) {
		return true ; 
	}

	/**
	 * nama method untuk on click
	 **/
	public void setMethodOnClickName(String methodOnClickName) {
		this.methodOnClickName = methodOnClickName;
	}
	/**
	 * nama method untuk on click
	 **/
	public String getMethodOnClickName() {
		return methodOnClickName;
	}
	
	
	
	/**
	 * argument untuk invoke method. kuranglebih semacam ini : on_click(\"1\"); 1 merupakan row id
	 **/
	public String generateMethodInvokeStatement (String rowId  ){
		return methodOnClickName +"('" + rowId +"')" ; 
	}
	
	
	
	
	/**
	 * membuat node html untuk cell grid. a atau yang lain di handle di sini
	 * 
	 * @param data yang sedang di render
	 * @param clickHandlerMethodNameAlsoWithParam ini nama method sudah include parameter.<br/> jadiya cukup di pasang dalam tag. misal<br> 
	 * <code>
	 *   return "&lt;a href='javascript:" + clickHandlerMethodNameAlsoWithParam +"'&gt;Test&lt;/a&gt;"
	 * 
	 *  </code><br/>
	 *  jadinya : &lt;a href="javascript:test(xxxx)"&gt;&lt;/a&gt;. semacam ini yang perlu di sediakan
	 **/
	public abstract String generateHTMLNode ( DATA data , String clickHandlerMethodNameAlsoWithParam) ; 
}
