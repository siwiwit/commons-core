package id.co.gpsc.common.client.control;

import com.google.gwt.user.client.Command;



/**
 * beberapa widget tidak boleh di switch kalau dalam mode editing. Contoh use case nya : <br/>
 * <ol>
 * <li> user membuka menu Nasabah, dan memilih edit</li>
 * <li> screen berganti pada mode edit data nasabah</li>
 * <li>User click menu lain, dalam kasus ini, user perlu di warning. apakah benar2 berganti atau tidak sebab kalau tidak user bisa kehilangan data</li>
 
 * </ol> 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public interface BlockingCapableWidget {
	
	
	/**
	 * state current,blocking atau tidak 
	 **/
	public boolean isCurrentlyBlocking();
	
	
	/**
	 * di sini proses nya adalah mengkonfirmasi ke user, di ijinkan atau tidak untuk mengganti screen(dalam kasus blocking screen). kalau user menolak berarti no further action, 
	 * setuju berarti screen akan di ganti. pergunakan confirm message di sini. code nya semacam ini:<br/> 
	 * <div style="border:solid green 1px;width:500px;color:blue">
	 * <code>
	 * 
	 * if(Window.confirm("navigate ke halaman lain?")){<br/>
	 *	&nbsp;&nbsp;&nbsp;	handlerOnUserAccept.execute();<br/>
	 *	&nbsp;}<br/>
	 * </code>
	 * </div>
	 * harap di perhatikan, internalization di perlukan
	 * @param handlerOnUserAccept method handler kalau user confirm : yes
	 **/
	public void confirmUserForChangeScreen (Command handlerOnUserAccept); 

}
