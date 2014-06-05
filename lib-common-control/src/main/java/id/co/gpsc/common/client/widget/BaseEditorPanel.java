package id.co.gpsc.common.client.widget;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;

import id.co.gpsc.common.client.lov.LOVCapabledControl;

/**
 * 
 * Base class untuk editor panel. generalisasi proses editor di lakukan di class ini. <br/>
 * <ol>
 * <li>Mekanisme render data dari POJO ke kontrol dan sebalik nya di generalisir</li>
 * <li>Add di seragamkan dengan {@link BaseEditorPanel#addAndEditNewData(Object)}</li>
 * <li>edit data di seragamkan dengan {@link BaseEditorPanel#editExistingData(Object)}</li>
 * <li>view data di seragamkan dengan {@link BaseEditorPanel#viewDataAsReadOnly(Object)}</li>
 * <li>Proses deteksi status dari editor di lakukan dengan variable {@link BaseEditorPanel#editorState} </li>
 * </lo>
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public abstract class BaseEditorPanel<DATA> extends BaseResourceBundleFriendlyComposite implements IEditorPanel<DATA>{
	
	
	
	/**
	 * command yang akan di trgger sementara load LOV belum selesai dilakukan. di trigger kalau sudah selesai di isikan LOV nya
	 **/
	private ArrayList<Command> afterLOVLoadCompleteTasks = new ArrayList<Command>() ; 
	
	
	/**
	 * flag, init sudah di run atau tidak
	 **/
	private boolean initInvoked =false ; 
	/**
	 * state dari editor, add, edit atau view readonly
	 **/
	protected EditorState editorState ; 
	/**
	 * flag LOV load complete atau tidak
	 **/
	protected boolean lovLoadComplete =false ; 
	
	
	/**
	 * data yang di add/ edit
	 **/
	protected DATA currentData ; 
	
	public BaseEditorPanel(){
		super();
		
		initUnderlyingWidget(generateUnderlyingWidget());
		
		runAfterConstructorAdditionalTask(); 
	}
	
	
	
	/**
	 * sederhananya ini akan memanggil init widget. ini di masukan ke dalam method sendiri agar bisa di override. misal nya kalau kita memerlukan panel tertentu dalam widget
	 * @param uiBinderGeneratedWidget ini widget yang di generate oleh UI binder. masukan handler di sini kalau anda perlu custom
	 */
	protected void initUnderlyingWidget ( Widget uiBinderGeneratedWidget ){
		super.initWidget(uiBinderGeneratedWidget);
	}
	
	
	/**
	 * task yang di run setelah constructor, override ini kalau anda memerlukan task tambahan setelah constructor
	 **/
	protected void runAfterConstructorAdditionalTask() {
		
	}

	/**
	 * @deprecated init widget di deprecated, mohon jangan di panggil ini, karena ini di panggil dalam constructor. Anda cukup memangil 
	 **/
	@Override
	@Deprecated
	protected void initWidget(Widget widget) {
		super.initWidget(widget);
	}
	
	
	/**
	 * worker yang akan menggenerate widget. kalau anda mempergunakan UI binder, cukup return uiBinder.createAndBindUi(this) di sini
	 **/
	protected abstract Widget generateUnderlyingWidget() ;
	
	
	/**
	 * mengambil LOV capabled controls yang ada dalam composite. ini akan di render otomatis after load done
	 **/
	protected abstract LOVCapabledControl[] getAutomaticRegisteredLOVCapableControls()  ; 
	
	
	/**
	 * worker kalau LOV load selesai di lakukan. di sini akan di trigger method-method yang pended selama LOV controls belum selesai di lakukan
	 **/
	protected void onLovLoadComplete(ArrayList<Command> task) {
		lovLoadComplete = true ;
		// trigger pended command
		if (! task.isEmpty()){
			for ( Command scn : task){
				scn.execute();
			}
		}
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		if(!initInvoked){
			runInitTask();
			initInvoked = true ; 
		}
	}
	
	/**
	 * run initial task, ini di trigger agar control di preparasi. task nya antara lain :<ol>
	 * <li>Load LOV</li>
	 * <li>load form configuration dari database/cache(mandatory non mandatory etc)</li> 
	 *  </ol>
	 * 
	 **/
	public void runInitTask () {
		LOVCapabledControl[] lovControls = getAutomaticRegisteredLOVCapableControls();
		if ( lovControls==null||lovControls.length==0){
			onLovLoadComplete(afterLOVLoadCompleteTasks);
		}else{
			this.registerLOVs(lovControls);
			fillLookupValue(new Command() {
				@Override
				public void execute() {
					onLovLoadComplete(afterLOVLoadCompleteTasks);
				}
			});
		}
		
	}
	
	@Override
	public void addAndEditNewData(final DATA data) {
		this.editorState = EditorState.addNew;
		this.currentData = data ;
		final Command  cmd =new Command() {
			
			@Override
			public void execute() {
				renderDataToControl(data, editorState);
			}
		};
		if (!lovLoadComplete ){
			
			afterLOVLoadCompleteTasks.add(cmd);
		}
		else
			cmd.execute();
		/*if ( lovLoadComplete){
			cmd.execute(); 
			return ; 
		}*/
		
		
	}
	
	@Override
	public void editExistingData(DATA data) {
		this.editorState = EditorState.edit;
		this.currentData = data ;
		
		renderDataToControl(data, editorState);
	}
	@Override
	public void viewDataAsReadOnly(DATA data) {
		this.currentData = data ;
		this.editorState = EditorState.viewReadonly;
		renderDataToControl(data, editorState);
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
	 * memasukan handler ke dalam listener. ini untuk register pada saat LOV load done. ini memastikan proses akan ter trigger pada saat LOV selesai di load 
	 */
	protected HandlerRegistration registerHandlerAfterLOVLoadDone (final Command handler ){
		if ( afterLOVLoadCompleteTasks.contains(handler) || handler== null){
			return new HandlerRegistration() {
				
				@Override
				public void removeHandler() {
				}
			};
		}
		afterLOVLoadCompleteTasks.add(handler); 
		return new HandlerRegistration() {
			
			@Override
			public void removeHandler() {
				afterLOVLoadCompleteTasks.remove(handler);
			}
		};
	}
	/**
	 * data yang di add/ edit
	 **/
	public DATA getCurrentData() {
		return currentData;
	}
}
