package id.co.gpsc.jquery.client.grid.cols;

import id.co.gpsc.common.util.I18Utilities;

/**
 * group of column header.wrapper js untuk JQgrid. <br/>
 * 
 * silakan refer ke doc : http://www.trirand.com/jqgridwiki/doku.php?id=wiki:groupingheadar<br/>
 * Prinsip nya : 
 * <ol>
 * <li>title dari group, mutlak ada</li>
 * <li>column yang paling kiri, reference nya perlu anda kirimkan</li>
 * <li>berapa column yang anda sertakan, hati-hati dengan overlap</li>
 * </ol>
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class GridColumnGroup {
	
	
	/**
	 * column dari kiri, mana yang di jadikan start dari kelompok
	 **/
	private BaseColumnDefinition<?, ?> startOfGroupColumn ; 
	
	
	/**
	 * berapa column yang menjadi member dari group
	 **/
	private int numberOfGroupColumn ; 
	
	
	
	/**
	 * default title dari column group
	 **/
	private String groupDefaultTitle ; 
	
	
	
	/**
	 * key internalization dari group 
	 **/
	private String groupTitleI18nKey ; 
	
	
	
	public GridColumnGroup(BaseColumnDefinition<?, ?> startOfGroupColumn,
			int numberOfGroupColumn, String groupTitle) {
		super();
		this.startOfGroupColumn = startOfGroupColumn;
		this.numberOfGroupColumn = numberOfGroupColumn;
		this.groupDefaultTitle = groupTitle;
	}
	
	
	public GridColumnGroup(BaseColumnDefinition<?, ?> startOfGroupColumn,
			int numberOfGroupColumn, String groupTitle, String groupTitleI18nKey) {
		super();
		this.startOfGroupColumn = startOfGroupColumn;
		this.numberOfGroupColumn = numberOfGroupColumn;
		this.groupDefaultTitle = groupTitle;
		this.groupTitleI18nKey = groupTitleI18nKey ;
	}
	
	
	



	public GridColumnGroup(){
		
	}
	
	

	/**
	 * column dari kiri, mana yang di jadikan start dari kelompok
	 **/
	public BaseColumnDefinition<?, ?> getStartOfGroupColumn() {
		return startOfGroupColumn;
	}

	/**
	 * column dari kiri, mana yang di jadikan start dari kelompok
	 **/
	public void setStartOfGroupColumn(
			BaseColumnDefinition<?, ?> startOfGroupColumn) {
		this.startOfGroupColumn = startOfGroupColumn;
	}
	
	/**
	 * berapa column yang menjadi member dari group
	 **/
	public int getNumberOfGroupColumn() {
		return numberOfGroupColumn;
	}
	/**
	 * berapa column yang menjadi member dari group
	 **/
	public void setNumberOfGroupColumn(int numberOfGroupColumn) {
		this.numberOfGroupColumn = numberOfGroupColumn;
	}

	 
	/**
	 * default title dari column group
	 **/
	public void setGroupDefaultTitle(String groupDefaultTitle) {
		this.groupDefaultTitle = groupDefaultTitle;
	}
	/**
	 * default title dari column group
	 **/
	public String getGroupDefaultTitle() {
		return groupDefaultTitle;
	}
	
	
	
	/**
	 * title yang akhirnya di pakai oleh grid
	 */
	public String getGroupTitle() {
		return I18Utilities.getInstance().getInternalitionalizeText(groupTitleI18nKey, groupDefaultTitle);
	}
	
	
	
	
	/**
	 * key internalization dari group 
	 **/
	public void setGroupTitleI18nKey(String groupTitleI18nKey) {
		this.groupTitleI18nKey = groupTitleI18nKey;
	}
	/**
	 * key internalization dari group 
	 **/
	public String getGroupTitleI18nKey() {
		return groupTitleI18nKey;
	}

}
