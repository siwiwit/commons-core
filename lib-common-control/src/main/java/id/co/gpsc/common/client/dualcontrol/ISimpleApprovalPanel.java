package id.co.gpsc.common.client.dualcontrol;

import com.google.gwt.user.client.Command;

import id.co.gpsc.common.data.approval.CommonApprovalHeader;
import id.co.gpsc.common.data.approval.ISimpleApprovableObject;




/**
 * interface simple approval, unutk penyeragaman panel approval
 **/
public interface ISimpleApprovalPanel {
	
	
	
	
	
	/**
	 * yang perlu dilakuan di sini :
	 * <ol>
	 * <li>request data via rpc, data yang perlu di render untuk apporval. primary key dari data di dapat dari variable <i>approvalHeaderData.targetObjectIdAsJSON</i> , anda perlu convert json string menjadi object kembali</li>
	 * <li>render data pada screen </li>
	 * <li>tambah tombol approve,reject , cancel dsb</li>
	 * </ol>
	 **/
	public void renderDataForApproval ( CommonApprovalHeader approvalHeaderData ) ;
	
	
	
	/**
	 * destroy widget. 
	 */
	public void destroyWidget ()  ;
	
	
	/**
	 * class yang di handle. ini core class yang perlu di approve
	 **/
	public Class<? extends ISimpleApprovableObject<?>> handledClass () ;
	
	
	/**
	 * jika ada task yang perlu dilakukan pada panel list approval panel 
	 * setelah melakukan approval. misalnya reload daftar approval. method ini di inject kan dari grid
	 * @param afterApprovalCommand command task2 yang dilakukan after approval
	 */
	public void setAfterApprovalCommand(Command afterApprovalCommand);
}
