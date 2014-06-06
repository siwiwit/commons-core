package id.co.gpsc.common.client.form;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;

import id.co.gpsc.common.client.control.ILOVCapableControl2ndLevel;
import id.co.gpsc.common.client.control.IParentLOVEnableControl;
import id.co.gpsc.common.client.lov.ClientSideLOVManager;
import id.co.gpsc.common.client.rpc.SimpleAsyncCallback;
import id.co.gpsc.common.data.CustomDataFormatter;
import id.co.gpsc.common.data.lov.Common2ndLevelLOV;
import id.co.gpsc.common.data.lov.Common2ndLevelLOVHeader;
import id.co.gpsc.common.data.lov.StrongTyped2ndLevelLOVID;
import id.co.gpsc.common.util.I18Utilities;

/**
 * Combo box yang bisa buat LOV 2nd level
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public class LOVCapabled2ndLevelComboBox extends ExtendedComboBox implements ILOVCapableControl2ndLevel{

	
	protected class Level2LovRequestHandler extends SimpleAsyncCallback<Common2ndLevelLOVHeader>{
		
		
		private String lookupValueToSelect ; 
		
		
		private Command afterLoadTask ; 
		public Level2LovRequestHandler(String lookupValueToSelect  , Command afterLoadTask ){
			super();
			this.lookupValueToSelect = lookupValueToSelect  ;
			this.afterLoadTask = afterLoadTask ; 
			
		}

		@Override
		public void onSuccess(Common2ndLevelLOVHeader result) {
			if(afterLoadTask!=null)
				afterLoadTask.execute();
			renderLookupData(result);
			setValue(lookupValueToSelect);
			runAfterLoadTask();
				
		}
		
		
		/**
		 * ini handler kalau load data selesai
		 **/
		private void runAfterLoadTask() {
			if ( afterLoadTask!=null)
				afterLoadTask.execute();
		}

		@Override
		protected void customFailurehandler(Throwable caught) {
			Window.alert("gagal load data Lookup dengan id :" + getLovParameterId().getId() +"(class enum : " + getLovParameterId().getClass().getName()  +"),error message :" + caught.getMessage());
			
			runAfterLoadTask();
		}
		
	}
	
	private StrongTyped2ndLevelLOVID strongTyped2ndLevelLOVID ; 
	
	
	private IParentLOVEnableControl parentControl ; 
	
	
	/**
	 * pada bagian awal apakah ada pilihan silakan pilih atau sebangsanya
	 **/
	private boolean appendNonSelectedChoice =false ; 
	
	/**
	 * label untuk non selected.default <i>-silakan pilih-</i>
	 **/
	private String defaultNoneSelectedLabel ="-silakan pilih-";
	
	
	
	/**
	 * key internalization untuk None selected
	 **/
	private String noneSelectedI18NKey ; 
	/**
	 * label untuk non selected.default <i>-silakan pilih-</i>
	 **/
	public String getDefaultNoneSelectedLabel() {
		return defaultNoneSelectedLabel;
	}
	/**
	 * label untuk non selected.default <i>-silakan pilih-</i>
	 **/
	public void setDefaultNoneSelectedLabel(String defaultNoneSelectedLabel) {
		this.defaultNoneSelectedLabel = defaultNoneSelectedLabel;
	}

	private CustomDataFormatter<Common2ndLevelLOV> customFormatter = new CustomDataFormatter<Common2ndLevelLOV>() {
		
		@Override
		public String getFormattedData(Common2ndLevelLOV data) {
			return data.getLabel();
		}
		public String getStringForValue(Common2ndLevelLOV data) {
			return data.getValue();
		};
	}; 
	
	
	@Override
	public void renderLookupData(Common2ndLevelLOVHeader lookupData) {
		clear();
		if ( appendNonSelectedChoice){
			addItem(I18Utilities.getInstance().getInternalitionalizeText(getNoneSelectedI18NKey(), getDefaultNoneSelectedLabel()));
		}
		if ( lookupData!=null&&lookupData.getLookupValues()!=null){
			List<Common2ndLevelLOV> lovs = lookupData.getLookupValues();
			
			for ( Common2ndLevelLOV scn : lovs){
				addItem(this.customFormatter.getFormattedData(scn)  ,this.customFormatter.getStringForValue(scn) );
			}
		}
		
	}

	@Override
	public void assignParentControl(final IParentLOVEnableControl parentControl) {
		this.parentControl  = parentControl ; 
		parentControl.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				
				ClientSideLOVManager.getInstance().requestLOV(LOVCapabled2ndLevelComboBox.this, new Command() {
					
					@Override
					public void execute() {
						
						
					}
				});
			}
		});
		
	}

	@Override
	public void setValue(final String myValue,final String parentValue,
			final Command afterSetValueDone) {
		clear();
		ClientSideLOVManager.getInstance().requestLOV(parentValue, getLovParameterId(), new SimpleAsyncCallback<Common2ndLevelLOVHeader>() {
			
			@Override
			public void onSuccess(Common2ndLevelLOVHeader result) {
				renderLookupData(result);
				setValue(myValue);
				runNextChain();
			}
			private void runNextChain(){
				if(afterSetValueDone!=null)
					afterSetValueDone.execute(); 
			}
			
			@Override
			protected void customFailurehandler(Throwable caught) {
				runNextChain();
			}
		});
		
	}

	@Override
	public void setValue(String lookupValue , final Command afterSetValueDone) {
		ClientSideLOVManager.getInstance().requestLOV2ndLevelByCurrentLookupValue(lookupValue, getLovParameterId(), new SimpleAsyncCallback<Common2ndLevelLOVHeader>() {
			
			@Override
			public void onSuccess(Common2ndLevelLOVHeader result) {
				
				
			}
			
			@Override
			protected void customFailurehandler(Throwable caught) {
				
				
			}
		});
		
	}
	
	@Override
	public void setLovParameterId(StrongTyped2ndLevelLOVID lovId) {
		this.strongTyped2ndLevelLOVID = lovId ; 
	}

	@Override
	public StrongTyped2ndLevelLOVID getLovParameterId() {
		return strongTyped2ndLevelLOVID;
	}

	@Override
	public String getParentLookupValue() {
		return parentControl.getValue();
	}
	public String getNoneSelectedI18NKey() {
		return noneSelectedI18NKey;
	}
	public void setNoneSelectedI18NKey(String noneSelectedI18NKey) {
		this.noneSelectedI18NKey = noneSelectedI18NKey;
	}
	
	/**
	 * pada bagian awal apakah ada pilihan silakan pilih atau sebangsanya
	 **/
	public boolean isAppendNonSelectedChoice() {
		return appendNonSelectedChoice;
	}
	
	
	
}
