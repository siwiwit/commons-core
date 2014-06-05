package id.co.gpsc.jquery.client.grid;

import id.co.gpsc.jquery.client.grid.cols.IntegerColumnDefinition;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * plain simple grid. grid berbasis JQGrid, dengan tambahan fungsi sederhana : 
 * <ol>
 * <li>Row number, nomor urut data dalam grid(paging akan menentukan juga). kalau paging di sertakan, maka anda harus mengeset nilai dari firstRowNumberToRender(ini tergantung paging page 1 = 1, page 2 = pageSize *page + 1)</li>
 * <li>multiple selection grid</li>
 * </ol>
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 **/
public abstract class SimpleGridPanel<DATA> extends BaseJQGridPanel<DATA>{

	/**
	 * posisi data paling awal. ini berguna untuk paging
	 **/
	protected int firstRowNumberToRender = 1;
	/**
	 * ini helper no urut field. jadinya bisa di ketahui row nomor berapa
	 **/
	protected int currentRowNumberToRender = 0;

	public SimpleGridPanel(){
		super();
	}
	
	
	
	/**
	 * constructor dengan multiple selection
	 **/
	public SimpleGridPanel(boolean multipleSelection){
		super(multipleSelection); 
		
	}
	
	
	
	/**
	 * constructor dengan multiple selection
	 **/
	public SimpleGridPanel(boolean multipleSelection, boolean renderOnAttach){
		super(multipleSelection , renderOnAttach); 
		
	}
	
	/**
	 * generate row number column. versi ini tanpa internalization, jadinya label di masukan dari parameter
	 * @param defaultHeaderLabel label untuk column
	 * @param width lebar dari column, <i>recommended</i> :80 
	 **/
	public IntegerColumnDefinition<DATA> generateRowNumberColumnDefinition (String defaultHeaderLabel , int width){
		return generateRowNumberColumnDefinition(defaultHeaderLabel , width , null);
	}
			
			
	/**
	 * generate row counter
	 * @param defaultHeaderLabel label untuk column
	 * @param width lebar dari column
	 * @param i18Key key internalization untuk column header. ini dalam kasus applikasi di set support i18n
	 **/
	public IntegerColumnDefinition<DATA> generateRowNumberColumnDefinition (String defaultHeaderLabel , int width , String i18Key){
		return new IntegerColumnDefinition<DATA>(defaultHeaderLabel , width , i18Key){
			@Override
			public Integer getData(DATA data) {
				return firstRowNumberToRender + currentRowNumberToRender;
			}
		};
	}

	@Override
	protected void addRowData(String gridId, String rowId, JavaScriptObject jsData,
			String newRowPosition, String rowIdRelativeTo) {
				
				super.addRowData(gridId, rowId, jsData, newRowPosition, rowIdRelativeTo);
				currentRowNumberToRender++;
			}
	
	
	@Override
	public void clearData() {
		
		super.clearData();
		currentRowNumberToRender=0;
	}
	

	

}
