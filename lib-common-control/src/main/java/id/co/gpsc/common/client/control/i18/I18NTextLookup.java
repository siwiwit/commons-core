package id.co.gpsc.common.client.control.i18;

import id.co.gpsc.common.client.control.BaseSimpleSearchComboContentLocator;
import id.co.gpsc.common.client.control.lookup.BaseSimpleSingleResultLookupDialog;
import id.co.gpsc.common.client.rpc.ApplicationConfigRPCServiceAsync;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.entity.I18Text;
import id.co.gpsc.common.data.entity.I18TextPK;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleQueryFilterOperator;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * textbox untuk browse i18n Text
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public class I18NTextLookup extends BaseSimpleSingleResultLookupDialog<I18TextPK, I18Text>{

	@Override
	protected String getNoColumnI18Key() {
		return null;
	}

	@Override
	protected String getLookupTitleI18Key() {
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
	protected Class<? extends I18Text> getLookupDataClass() {
		return I18Text.class;
	}

	@Override
	protected void retrieveData(SigmaSimpleQueryFilter[] filters, int page,
			int pageSize, AsyncCallback<PagedResultHolder<I18Text>> callback) {
		if ( filters!=null){
			for (SigmaSimpleQueryFilter scn : filters){
				System.out.println( scn.toString());
			}
		}
		ApplicationConfigRPCServiceAsync.Util.getInstance().getI18NTexts(pageSize, page, filters, callback); 
	}

	@Override
	protected SigmaSimpleQueryFilter[] generateDataFilters() {
		
		SigmaSimpleQueryFilter[] baseResult =  super.generateDataFilters();
		int size = baseResult==null? 0 : baseResult.length; 
		
		
		
		SigmaSimpleQueryFilter[] retval = new SigmaSimpleQueryFilter[size+1];
		if ( baseResult!= null){
			for ( int i=0;i< baseResult.length; i++){
				retval[i] = baseResult[i];
			}
		}
		
		retval[retval.length-1] = new SigmaSimpleQueryFilter("id.localeCode"  , SimpleQueryFilterOperator.equal , I18Utilities.getInstance().getCurrentLocalizationCode()) ;
		return retval ; 
	}
	@Override
	protected Integer getDefaultWidth() {
		return 800;
	}

	@Override
	protected String getDefaultPopupCaption() {
		return "Browse For Internationalized Text";
	}

	@Override
	protected String getGridCaptionI18Key() {
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

	@Override
	protected String getDefaultGridCaption() {
		return "Text List";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<I18Text, ?>[] getLookupGridColumns() {
		return (BaseColumnDefinition<I18Text, ?>[])new BaseColumnDefinition<?, ?>[]{
			new StringColumnDefinition<I18Text>("Locale Code" , 100 , "--") {
				@Override
					public String getData(I18Text data) {
						return data.getId().getLocaleCode();
					}	
			},
			new StringColumnDefinition<I18Text>("Group Code" , 100 , "-N8-") {
				@Override
					public String getData(I18Text data) {
						return data.getGroupCode();
					}	
			},
			new StringColumnDefinition<I18Text>("Text Key" , 250 , "-N2-") {
				@Override
					public String getData(I18Text data) {
						return data.getId().getTextKey();
					}	
			},
			new StringColumnDefinition<I18Text>("Text Value" , 250 , "-N3-") {
				@Override
					public String getData(I18Text data) {
						return data.getLabel();
					}	
			}
			
		};
	}

	@Override
	protected BaseSimpleSearchComboContentLocator[] generateSearchCriteriaLocators() {
		
		return new BaseSimpleSearchComboContentLocator[]{
				new BaseSimpleSearchComboContentLocator("id.textKey", "Text Key", "--", SimpleQueryFilterOperator.likePercentProvided), 
				new BaseSimpleSearchComboContentLocator("label", "Label", "--", SimpleQueryFilterOperator.likePercentProvided),
				new BaseSimpleSearchComboContentLocator("groupCode", "Text Group", "--", SimpleQueryFilterOperator.equal),
		};
	}

}
