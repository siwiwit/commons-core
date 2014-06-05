package id.co.gpsc.common.client.control;

/**
 * handler proses file upload
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface CommonChainedUploadHandler {
	
	/**
	 * handler kalau file sukses di upload
	 */
	public void onUploadFileSuccess (String fileKey ) ;
	
	
	/**
	 * handler kalau proses upload gagal
	 * @param messsage message di kirim dari server
	 * @param filePath file yang di upload
	 */
	public void onUploadFileFailed ( String messsage , String filePath ) ; 
	

}
