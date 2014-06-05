/**
 * 
 */
package id.co.gpsc.common.client.security.passwordpolicy;

import id.co.gpsc.common.client.control.worklist.I18ColumnDefinition;
import id.co.gpsc.common.client.control.worklist.I18EnabledSimpleGrid;
import id.co.gpsc.common.client.security.group.IOpenAndCloseable;
import id.co.gpsc.common.client.security.group.IRemove;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.security.domain.PasswordPolicy;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.CellButtonHandler;
import id.co.gpsc.jquery.client.grid.IReloadGridCommand;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.IntegerColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

import java.math.BigInteger;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

/**
 * @author Dode
 * @version $Id
 * @since Jan 30, 2013, 2:20:01 PM
 */
public class PasswordPolicyGridPanel extends I18EnabledSimpleGrid<PasswordPolicy> {

	//cell button component
	private CellButtonHandler<PasswordPolicy> btnDelete;
	private CellButtonHandler<PasswordPolicy>[] actionButtons;
	
	private IRemove<BigInteger> removeUtil;
	private IOpenAndCloseable<PasswordPolicy> openCloseable;
	
	public PasswordPolicyGridPanel() {
		actionButtons = generateActionButton();
		
		new Timer() {			
			@Override
			public void run() {
				getGridButtonWidget().appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.action.hint.add", "Add"), "ui-icon ui-icon-plus", new Command() {			
					@Override
					public void execute() {
						PasswordPolicy newData = new PasswordPolicy();
						openCloseable.openDialog(newData);			
					}
				});				
			}
		}.schedule(50);
	}
	
	/**
	 * generate button di action column
	 * @return array cell button
	 */
	private CellButtonHandler<PasswordPolicy>[] generateActionButton() {
		
		btnDelete = new CellButtonHandler<PasswordPolicy>("ui-icon-closethick", I18Utilities.getInstance().getInternalitionalizeText("security.common.button.action.hint.erase", "Erase"), new DataProcessWorker<PasswordPolicy>() {
			@Override
			public void runProccess(PasswordPolicy data) {
				Boolean result = Window.confirm(I18Utilities.getInstance().getInternalitionalizeText("security.common.notification.deleteuser", "Are you sure to delete this data?"));
				if(result){
					removeUtil.remove(data.getId());
				}
			}				
		});
		
		return (CellButtonHandler<PasswordPolicy>[]) new CellButtonHandler<?>[] {btnDelete};
	}
	
	@Override
	public I18ColumnDefinition<PasswordPolicy>[] getI18ColumnDefinitions() {
		
		return null;
	}
	
	@Override
	public String appendRow(final PasswordPolicy data) {
final String rowId =  super.appendRow(data);
		
		new Timer() {
			
			@Override
			public void run() {
				appendSubGridToGrid(rowId, data);
			}
		}.schedule(5);
		return rowId  ; 
		
	}
	
	
	private void appendSubGridToGrid(String rowId ,  PasswordPolicy data) {
		Element trCurrent =  DOM.getElementById(rowId);
		Element trSubGrid = DOM.createTR(); 
		final Element tdSUbgrid = DOM.createTD(); 
		trSubGrid.appendChild(tdSUbgrid);
		// atur jarak
 	 	tdSUbgrid.getStyle().setPaddingTop(3, Unit.PX);
 	 	tdSUbgrid.getStyle().setPaddingLeft(10, Unit.PX);
 	 	tdSUbgrid.getStyle().setPaddingRight(10, Unit.PX);
 	 	tdSUbgrid.getStyle().setPaddingBottom(7, Unit.PX);
	 	trCurrent.getParentElement().insertAfter(trSubGrid, trCurrent);
	 	tdSUbgrid.setAttribute("colspan", "" + getColumnDefinitions().length );
	 	tdSUbgrid.setInnerHTML(data.getRegularExpression());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<PasswordPolicy, ?>[] getColumnDefinitions() {
		BaseColumnDefinition<PasswordPolicy, ?>[] retval = (BaseColumnDefinition<PasswordPolicy, ?>[]) new BaseColumnDefinition<?, ?>[] {
			generateRowNumberColumnDefinition(I18Utilities.getInstance().getInternalitionalizeText("security.common.header.grid.no", "No"), 20, ""),
			generateButtonsCell(actionButtons, I18Utilities.getInstance().getInternalitionalizeText("security.common.header.grid.action", "Action"), "", 50),
			new IntegerColumnDefinition<PasswordPolicy>(I18Utilities.getInstance().getInternalitionalizeText("security.passwordpolicy.table.header.minlength", "Min. Length"), 100) {

				@Override
				public Integer getData(PasswordPolicy data) {
					return data.getMinimumLength().intValue();
				}
			},
			new IntegerColumnDefinition<PasswordPolicy>(I18Utilities.getInstance().getInternalitionalizeText("security.passwordpolicy.table.header.minalphabet", "Min. Alphabets"), 110) {

				@Override
				public Integer getData(PasswordPolicy data) {
					return data.getMinimumAlphabet().intValue();
				}
			},
			new IntegerColumnDefinition<PasswordPolicy>(I18Utilities.getInstance().getInternalitionalizeText("security.passwordpolicy.table.header.minnumeric", "Min. Numerics"), 110) {

				@Override
				public Integer getData(PasswordPolicy data) {
					return data.getMinimumNumeric().intValue();
				}
			},
			new IntegerColumnDefinition<PasswordPolicy>(I18Utilities.getInstance().getInternalitionalizeText("security.passwordpolicy.table.header.maxloginattempt", "Max. Login Attempts"), 120) {

				@Override
				public Integer getData(PasswordPolicy data) {
					return data.getMaximumLoginAttempt().intValue();
				}
			},
			new IntegerColumnDefinition<PasswordPolicy>(I18Utilities.getInstance().getInternalitionalizeText("security.passwordpolicy.table.header.inactivelimit", "Inactive Limit"), 110) {

				@Override
				public Integer getData(PasswordPolicy data) {
					return data.getInactiveLimit().intValue();
				}
			},
			new IntegerColumnDefinition<PasswordPolicy>(I18Utilities.getInstance().getInternalitionalizeText("security.passwordpolicy.table.header.disabledlimit", "Disabled Limit"), 110) {

				@Override
				public Integer getData(PasswordPolicy data) {
					return data.getDisabledLimit().intValue();
				}
			},
			new IntegerColumnDefinition<PasswordPolicy>(I18Utilities.getInstance().getInternalitionalizeText("security.passwordpolicy.table.header.passwordage", "Password Age"), 110) {

				@Override
				public Integer getData(PasswordPolicy data) {
					return data.getPasswordAge().intValue();
				}
			},
			new IntegerColumnDefinition<PasswordPolicy>(I18Utilities.getInstance().getInternalitionalizeText("security.passwordpolicy.table.header.oldpasswordage", "Old Password Age"), 130) {

				@Override
				public Integer getData(PasswordPolicy data) {
					return data.getOldPasswordAge().intValue();
				}
			},
			new StringColumnDefinition<PasswordPolicy>(I18Utilities.getInstance().getInternalitionalizeText("security.passwordpolicy.table.header.oldpasswordbase", "Old Password Base"), 130) {

				@Override
				public String getData(PasswordPolicy data) {
					return data.getOldPasswordBase();
				}
			}
		}; 
		return retval;
	}
	
	/**
	 * object untuk open close dialog
	 * @param openCloseable
	 */
	public void setOpenCloseable(IOpenAndCloseable<PasswordPolicy> openCloseable) {
		this.openCloseable = openCloseable;
	}
	
	/**
	 * object untuk remove data
	 * @param removeUtil
	 */
	public void setRemoveUtil(IRemove<BigInteger> removeUtil) {
		this.removeUtil = removeUtil;
	}
	
	/**
	 * object untuk mereload grid
	 * @param reloadGrid
	 */
	public void setReloadGrid(IReloadGridCommand reloadGrid) {
		this.reloadCommand = reloadGrid;
	}
}
