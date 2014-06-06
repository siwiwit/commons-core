package id.co.gpsc.common.client.security.lookup;

import id.co.gpsc.common.client.control.BaseSimpleSearchComboContentLocator;
import id.co.gpsc.common.client.security.lookup.base.SecurityBaseSimpleSingleResultLookupDialog;
import id.co.gpsc.common.client.security.rpc.FunctionRPCServiceAsync;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.common.security.dto.PageDefinitionDTO;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

import java.math.BigInteger;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public class PageDefinitionLookup extends SecurityBaseSimpleSingleResultLookupDialog<BigInteger , PageDefinitionDTO>{

	
	
	/**
	 * show page url . dalam kasus GWT apps, page url = false
	 **/
	private boolean showPageUrl = false; 
	

	
	private static final SimpleSortArgument SORT_ARGS [] ={
		new SimpleSortArgument("pageCode", true)  
		
	};

	public PageDefinitionLookup(){
		super() ; 
	}
	
	public PageDefinitionLookup (boolean showPageUrl ){
		this() ; 
		this.showPageUrl =showPageUrl ; 
	}
	@Override
	protected Class<? extends PageDefinitionDTO> getLookupDataClass() {
		return PageDefinitionDTO.class;
	}

	@Override
	protected void retrieveData(SimpleQueryFilter[] filters, int page,
			int pageSize,
			AsyncCallback<PagedResultHolder<PageDefinitionDTO>> callback) {
		FunctionRPCServiceAsync.Util.getInstance().getCurrentAppAvailablePages(filters, SORT_ARGS, pageSize, page, callback); 
		
	}

	@Override
	protected Integer getDefaultWidth() {
		return 600;
	}

	@Override
	protected String getDefaultPopupCaption() {
		return "Available Pages";
	}

	@Override
	protected String getGridCaptionI18Key() {
		return null;
	}
	
	@Override
	protected String getLookupTitleI18Key() {
		return null;
	}
	
	

	@Override
	protected String getDefaultGridCaption() {
		return "Pages";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<PageDefinitionDTO, ?>[] getLookupGridColumns() {
		
		
		
		//new Stringcol
		BaseColumnDefinition<PageDefinitionDTO, ?>[] tmp =  (BaseColumnDefinition<PageDefinitionDTO, ?>[])new BaseColumnDefinition<?, ?>[]{
			new StringColumnDefinition<PageDefinitionDTO>("Page Code" , 200 ) {
				@Override
				public String getData(PageDefinitionDTO data) {
					
					return data.getPageCode();
				}
				
			}, 
			new StringColumnDefinition<PageDefinitionDTO>("Page Remark" , 250 ) {
				@Override
				public String getData(PageDefinitionDTO data) {
					return data.getRemark();
				}
				
			}, 
				
				
		};
		
		if ( showPageUrl){
			return (BaseColumnDefinition<PageDefinitionDTO, ?>[])new BaseColumnDefinition<?, ?>[]{
					tmp[0] , tmp[1] , 
					new StringColumnDefinition<PageDefinitionDTO>("Page Url" , 150 ) {
						@Override
						public String getData(PageDefinitionDTO data) {
							
							return data.getPageUrl();
						}
						
					}
			}; 
		}
		else
			return tmp ; 
					 
	}

	@Override
	protected BaseSimpleSearchComboContentLocator[] generateSearchCriteriaLocators() {
		return new BaseSimpleSearchComboContentLocator[]{
				//FIXME : masukan kode untuk i18n 
				new BaseSimpleSearchComboContentLocator("pageCode", "Page Code", null ), 
				new BaseSimpleSearchComboContentLocator("remark", "Page Remark", null)
				
				
		};
	}

	@Override
	protected String getNoColumnI18Key() {
		return null;
	}

	@Override
	protected String getNoneSelectedI18Key() {
		
		return null;
	}

	@Override
	protected String getOkLabelI18Key() {
		return null;
	}

	@Override
	protected String getCancelLabelI18Key() {
		return null;
	}

	@Override
	protected String getSearchButtonLabelI18Key() {
		return null;
	}

	@Override
	protected String getResetButtonLabelI18Key() {
		return null;
	}

}
