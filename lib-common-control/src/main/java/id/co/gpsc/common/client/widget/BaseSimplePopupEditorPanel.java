package id.co.gpsc.common.client.widget;

import id.co.gpsc.common.data.ClientSideListDataEditorContainer;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.container.JQDialog;

import java.io.Serializable;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;



/**
 * base class simple editor. Editor di kirimi data dari opener  
 * 
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @see #saveLabel variable ini menentukan label tombol save (default Simpan indonesian)
 * @see #cancelLabel variable ini menentukan label tombol cancel(default batal)
 **/
public abstract class BaseSimplePopupEditorPanel<DATA > extends BaseResourceBundleFriendlyComposite implements IEditorPanel<DATA> {
	
	
	
	/**
	 * data yang saat ini di render
	 **/
	protected DATA currentData ; 
	
	/**
	 * state editor. edit atau add
	 **/
	protected EditorState editorState ; 
	/**
	 * data container untuk broadcast modifikasi data
	 **/
	protected ClientSideListDataEditorContainer<DATA> dataContainer ;
	
	
	//FIXME : perlu dipikirkan global configureable spt apa
	/**
	 * worker untuk button simpan
	 *
	 **/
	public static String saveLabel = I18Utilities.getInstance()
										.getInternalitionalizeText("mda.common.editor.saveButton", "Save"); 
	
	
	//FIXME : perlu dipikirkan global configureable spt apa
	/**
	 * label untuk tombol batal
	 **/
	public static String cancelLabel = I18Utilities.getInstance()
										.getInternalitionalizeText("mda.common.editor.cancelButton", "Cancel"); ;
	protected JQDialog dialog ; 
	
	
	
	
	/**
	 * lebar dari dialog
	 **/
	private Integer dialogWidth ; 
	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see id.co.sigma.common.client.widget.IEditorPanel#addAndEditNewData(DATA)
	 */
	@Override
	public void addAndEditNewData (DATA data) {
		if ( dataContainer==null){
			Window.alert("Perhatian. Data container belum di set untuk class ->" + this.getClass().getName() +",popup editor tidak akan bekerja");
			return ;
		}
		this.currentData = data ; 
		this.editorState = EditorState.addNew ; 
		this.renderDataToControl(currentData, this.editorState);
		configureDialog();
		disableAllControl(false);
		dialog.show(true);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see id.co.sigma.common.client.widget.IEditorPanel#editExistingData(DATA)
	 */
	@Override
	public void editExistingData (DATA data) {
		if ( dataContainer==null){
			Window.alert("Perhatian. Data container belum di set untuk class ->" + this.getClass().getName() +",popup editor tidak akan bekerja");
			return ;
		}
		this.currentData = data ; 
		this.editorState = EditorState.edit ; 
		this.renderDataToControl(currentData, this.editorState);
		// langsung show editor
		configureDialog();

		disableAllControl(false);
		dialog.show(true);
	}
	
	@Override
	public void viewDataAsReadOnly(DATA data) {
		this.currentData = data ; 
		this.editorState = EditorState.viewReadonly ; 
		this.renderDataToControl(currentData, this.editorState);
		configureDialog();
		disableAllControl(true);
		dialog.show(true);
	}
	
	
	
	
	
	/**
	 * disable all control. ini untuk menampilkan dalam mode read only
	 **/
	protected abstract void disableAllControl(boolean disable);
	
	
	/**
	 * assign data container ke dalam editor
	 **/
	public void setDataContainer(
			ClientSideListDataEditorContainer<DATA> dataContainer) {
		this.dataContainer = dataContainer;
	}
	
	/**
	 * simpan perubahan. di sini include pekerjaan menutup dialog
	 **/
	protected void saveChange () {
		if ( !validateUserEntry(editorState))
			return ;
		this.fetchDataFromControlToObject(currentData, editorState);
		if ( EditorState.addNew.equals(editorState))
			dataContainer.appendNewItem(currentData, false);
		else
			dataContainer.modifyItem(currentData , false);
		dialog.close();
		new Timer() {
			
			@Override
			public void run() {
				dataContainer.fireDataChangeEvent();
			}
		}.schedule(10);
	}
	
	private int  saveBtnIndex;
	private int  cancelBtnIndex ; 
	
	/**
	 * worker untuk configure dialog
	 **/
	protected void configureDialog () {
		//FIXME : set title belum beres untuk dialog
		String title = EditorState.addNew.equals(this.editorState)?
				getDialogLabelForAddNew(currentData) :
				getDialogLabelForEditExiting(currentData);
		
		title=EditorState.viewReadonly.equals(this.editorState)?
				getDialogLabelForViewReadOnly(currentData) :
				title;
				
		if ( dialog==null){
			dialog= new JQDialog(	title
						, this); 
			if ( dialogWidth!=null)
				dialog.setWidth(dialogWidth);
		}
		else
			dialog.setTitle(title);
		
		removeButtonDialog();
		renderButtonDialog();
	}
	
	/**
	 * render button dialog
	 */
	private void renderButtonDialog(){
		if ( !EditorState.viewReadonly.equals(editorState)){
			saveBtnIndex = dialog.appendButton(saveLabel, new Command() {
				@Override
				public void execute() {
					saveChange();
				}
			});
		}
		
		
		cancelBtnIndex = dialog.appendButton(cancelLabel, new Command() {
			
			@Override
			public void execute() {
				//FIXME : bagaimana dengan konfirmasi close editor
				dialog.close();
			}
		});
	}
	
	/**
	 * remove dialog button
	 */
	private void removeButtonDialog(){
		dialog.removeButton(saveBtnIndex);
		dialog.removeButton(cancelBtnIndex);
	}
	/**
	 * render data ke dalam kontrol. ini memindahkan variable-variable ke dalam kontrol
	 * @param data data yang akan di render ke kontrol
	 **/
	protected abstract void renderDataToControl (DATA data, EditorState editorState ) ; 
	
	
	
	
	
	
	/**
	 * reverse dari {@link #renderDataToControl(Serializable)}<br/>
	 * menyalin data dari setiap kontrol dalam form menuju pada object. 
	 * @param targetData target ke mana isian dari user akan di salin
	 * @param editorState state dari editor,add new atau modifikasi existing
	 **/
	protected abstract void fetchDataFromControlToObject (DATA targetData , EditorState editorState ); 
	/**
	 * validasi entry. ini menentukan proses boleh lanjut atau tidak. kalau return = true berarti proses akan di lanjutkan
	 * @param editorState state dari editor
	 * @return true = data bisa di simpan
	 * 
	 **/
	protected abstract boolean validateUserEntry (EditorState editorState ) ; 
	
	
	
	/**
	 * title dialog untuk proses add new data
	 * @param appendedData data yang di append. ini di kirimkan kalau misalnya judul dari dialog memerlukan informasi dari sini
	 **/
	protected abstract String getDialogLabelForAddNew (DATA appendedData); 
	
	
	/**
	 * worker untuk create title dari dialog kalau kasusnya edit exitng data
	 * @param editedData data yang hendak di edit 
	 **/
	protected abstract String getDialogLabelForEditExiting (DATA editedData);
	
	/**
	 * worker untuk create title dari dialog kalau kasusnya view exitng data
	 * @param viewedData data yang hendak di view 
	 **/
	protected abstract String getDialogLabelForViewReadOnly (DATA viewedData);



	/**
	 * lebar dari dialog
	 **/
	public Integer getDialogWidth() {
		return dialogWidth;
	}



	/**
	 * lebar dari dialog
	 **/
	public void setDialogWidth(Integer dialogWidth) {
		this.dialogWidth = dialogWidth;
	}
	
	
	
	
	

}
