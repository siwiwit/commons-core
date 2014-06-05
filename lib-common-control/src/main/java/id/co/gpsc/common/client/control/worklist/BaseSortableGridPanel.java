/**
 * 
 */
package id.co.gpsc.common.client.control.worklist;

import id.co.gpsc.common.data.ClientSideListDataEditorContainerSortable;
import id.co.gpsc.common.data.ISortableData;
import id.co.gpsc.jquery.client.grid.event.GridSelectRowHandler;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;

/**
 * @author balicamp
 * @since Mar 25, 2013, 3:09:47 PM
 * @version $Id
 */
public abstract class BaseSortableGridPanel<DATA extends ISortableData> extends I18EnabledSimpleGrid<DATA> {

	/**
	 * move up and move down button id
	 */
	protected String moveUpBtnId;
	protected String moveDownBtnId;
	protected Element moveUpBtnElement;
	protected Element moveDownBtnElement;
	protected ClientSideListDataEditorContainerSortable <DATA> columnDataContainer;
	
	/**
	 * data yang di pilih 
	 **/
	protected DATA currentSelectedData;
	
	
	
	public BaseSortableGridPanel(){
		super() ; 
		
		columnDataContainer = new ClientSideListDataEditorContainerSortable<DATA>();
		columnDataContainer.appendDataChangeHandler(new Command() {
			
			@Override
			public void execute() {
				reloadGrid();
			}
		});
		
		
		new Timer() {
			
			@Override
			public void run() {
				addRowSelectedHandler(new GridSelectRowHandler<DATA>() {
					@Override
					public void onSelectRow(String rowId,
							DATA selectedData) {
						if ( selectedData==null){
							// FIXME disable tombol up + down
							enableDisableUpAndDownButton(false);
							currentSelectedData = null  ; 
							return ;
						}
						
						currentSelectedData = selectedData ; 
						int idx =  columnDataContainer.getDataRowIndex(selectedData);
						int totalDtaCount = columnDataContainer.getAvaliableDataCount() ;
						
						//FIXME: enable up + down button
						enableDisableUpAndDownButton(true);
						if ( idx==0){
							// FIXME: disable up button
							enableDisableMoveUpButton(false);
						}
						if ( idx== totalDtaCount-1){
							// FIXME: disable down button
							enableDisableMoveDownButton(false);
						}
						
					}
				}); 
				
			}
		}.schedule(50);
	}
	
	
	protected void enableDisableMoveUpButton(boolean enableUpButton) {
		if (moveUpBtnElement == null) {
			moveUpBtnElement = DOM.getElementById(moveUpBtnId);
		}
		if (!enableUpButton) {
			moveUpBtnElement.setAttribute("style", "display:none");
		} else {
			moveUpBtnElement.setAttribute("style", "display:inline-table");
		}
	}
	
	protected void enableDisableMoveDownButton(boolean enableDownButton) {
		
		if (moveDownBtnElement == null) {
			moveDownBtnElement = DOM.getElementById(moveDownBtnId);
		}
		if (!enableDownButton) {
			moveDownBtnElement.setAttribute("style", "display:none");
		} else {
			moveDownBtnElement.setAttribute("style", "display:inline-table");
		}
	}
	
	protected void enableDisableUpAndDownButton(boolean enable) {
		enableDisableMoveUpButton(enable);
		enableDisableMoveDownButton(enable);
	}
	
	
	
	
	/**
	 * worker untuk move up/down
	 **/
	protected void renderMoveUpDownButton() {
		moveUpBtnId = getGridButtonWidget().appendButton("Naik", "ui-icon-arrowthick-1-n", new Command() {
			
			@Override
			public void execute() {
				columnDataContainer.moveDataUp(currentSelectedData);
				reloadGrid();
			}
		});
		
		moveDownBtnId = getGridButtonWidget().appendButton("Turun", "ui-icon-arrowthick-1-s", new Command() {
			
			@Override
			public void execute() {
				columnDataContainer.moveDataDown(currentSelectedData);
				reloadGrid();
			}
		});
	}
	
	
	/**
	 * worker untuk reload grid
	 **/
	protected abstract void reloadGrid(); 
}
