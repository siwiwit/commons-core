package id.co.gpsc.common.client.widget;



/**
 * state editor
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 **/
public enum EditorState {
	
	/**
	 * mode add new
	 **/
	addNew("EditorState::ADD_NEW") , 
	/**
	 * mode modifikasi exiting data
	 **/
	edit("EditorState::EDIT") , 
	/**
	 * mode view readonly data
	 **/
	viewReadonly("EditorState::VIEW"),
	/**
	 * mode awal
	 */
	startState("EditorState:START_BEGIN");
	
	
	private String internalRepresenation  ; 
	
	private EditorState(String code) {
		this.internalRepresenation = code ; 
	}
	
	@Override
	public String toString() {
		return internalRepresenation;
	}

}
