package id.co.gpsc.common.client.dualcontrol;

import id.co.gpsc.common.client.common.CommonBus;
import id.co.gpsc.common.client.control.EditorOperation;

import com.google.gwt.event.shared.GwtEvent;

/**
 *
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class DataChangedEvent<T  > extends GwtEvent<DataChangedEventHandler<T>>  {

	
	
	/**
	 * data yang mengalami masalah. ini apa yang di add atau yang di edit.
	 **/
	private T modifiedData ; 
	
	
	
	
	/**
	 * operasi yang di lakukan . add / edit delete
	 **/
	private EditorOperation editorOperation ; 
	 
	
	private DataChangedEvent(T modifiedData , EditorOperation editorOperation){
		this.modifiedData = modifiedData  ; 
		this.editorOperation=editorOperation ; 
	
	}
	
	
	
	
	/**
	 * mempush kasus ada data di add ke dalam event bus. 
	 **/
	public static   void publishDataAdded (Object data){
		@SuppressWarnings({ "rawtypes", "unchecked" })
		DataChangedEvent evt = new DataChangedEvent(data , EditorOperation.ADD);
		CommonBus.getInstance().fireEvent(evt);
	}
	
	/**
	 * ini kalau ada data yang di edit. ini mempublish ke dalam event bus
	 **/
	public static   void publishDataEdited (Object data){
		@SuppressWarnings({ "rawtypes", "unchecked" })
		DataChangedEvent evt = new DataChangedEvent(data , EditorOperation.EDIT);
		CommonBus.getInstance().fireEvent(evt);
	}
	
	/**
	 * ini kalau ada data yang di hapus
	 **/
	public static   void publishDataErased (Object data){
		@SuppressWarnings({ "rawtypes", "unchecked" })
		DataChangedEvent evt = new DataChangedEvent(data , EditorOperation.DELETE);
		CommonBus.getInstance().fireEvent(evt);
	}
	
	
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DataChangedEventHandler<T>> getAssociatedType() {
		return new com.google.gwt.event.shared.GwtEvent.Type<DataChangedEventHandler<T>>();
	}

	@Override
	protected void dispatch(DataChangedEventHandler<T> handler) {
		handler.handleDataChange(modifiedData, editorOperation);
		
	}

	 

}
