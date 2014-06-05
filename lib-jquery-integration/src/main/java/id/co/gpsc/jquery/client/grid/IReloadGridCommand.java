package id.co.gpsc.jquery.client.grid;



/**
 * command untuk reload grid. karena kadang reload grid yang tahu adalah di luar grid. jadinya kalau grid perlu kemampuan reload, perlu di kirimkan ke sini
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public interface IReloadGridCommand {
	
	
	/**
	 * paksa grid reload
	 **/
	public void reload(); 

}
