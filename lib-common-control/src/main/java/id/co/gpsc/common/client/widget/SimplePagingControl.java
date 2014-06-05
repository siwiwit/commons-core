package id.co.gpsc.common.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import id.co.gpsc.common.client.form.PagedContentComboBox;



/**
 * paging simple
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @since 8 Aug 2012
 **/
public class SimplePagingControl extends Composite implements BasePagingControl  {

	private static SimplePagingControlUiBinder uiBinder = GWT
			.create(SimplePagingControlUiBinder.class);
	@UiField PagedContentComboBox cmbPaging;
	@UiField Anchor lnkPrev;
	@UiField Anchor lnkNext;

	interface SimplePagingControlUiBinder extends
			UiBinder<Widget, SimplePagingControl> {
	}

	public SimplePagingControl() {
		initWidget(uiBinder.createAndBindUi(this));
		
	}

	

	@Override
	public void adjustPaging(int totalPage , int pageSize  , int currentPagePosition) {
		cmbPaging.setTotalDataPage(totalPage , pageSize ,currentPagePosition);
		if ( currentPagePosition==0){
			lnkPrev.getElement().getParentElement().getStyle().setDisplay(Display.NONE);
			lnkPrev.setVisible(false);
		}else{
			lnkPrev.getElement().getParentElement().getStyle().setProperty("display", "");
			lnkPrev.setVisible(true);
		}
		// tombol next
		if ( currentPagePosition>=totalPage-1){
			lnkNext.getElement().getParentElement().getStyle().setDisplay(Display.NONE);
			lnkNext.setVisible(false);
		}else{
			lnkNext.getElement().getParentElement().getStyle().setProperty("display", "");
			lnkNext.setVisible(true);
		}
		
			
	}
	
	
	
	/**
	 * register page change handler. kontrol ini akan menotifikasi perubahan posisi page. di trigger dengan penekanan next/prev atau perubahan value pada combo box
	 * @param pageChangeHandler page change handler yang akan menangani item ini
	 **/
	public void assignPageChangeHandler(PageChangeHandler pageChangeHandler) {
		
	}

	


}
