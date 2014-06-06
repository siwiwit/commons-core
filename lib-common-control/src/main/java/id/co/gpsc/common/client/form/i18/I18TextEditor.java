package id.co.gpsc.common.client.form.i18;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

import id.co.gpsc.common.client.form.ExtendedTextBox;
import id.co.gpsc.common.client.form.LOVCapabledComboBox;
import id.co.gpsc.common.client.rpc.ApplicationConfigRPCServiceAsync;
import id.co.gpsc.common.client.rpc.SimpleAsyncCallback;
import id.co.gpsc.common.client.util.CommonClientControlUtil;
import id.co.gpsc.common.client.widget.BaseSimpleComposite;
import id.co.gpsc.common.data.CoreLibLookup;
import id.co.gpsc.common.data.entity.I18Code;
import id.co.gpsc.common.data.entity.I18Text;
import id.co.gpsc.common.data.entity.I18TextPK;
import id.co.gpsc.jquery.client.container.JQDialog;

/**
 * 
 * Editor i18 tet
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public final class I18TextEditor extends BaseSimpleComposite {
	
	
	private static I18TextEditor instance ; 
	
	
	
	/**
	 * singleton instance
	 **/
	public static I18TextEditor getInstance() {
		if ( instance==null){
			instance = new I18TextEditor(); 
			CommonClientControlUtil.getInstance().addWidgetToSwapContainer(instance);
		}
		return instance;
	}

	private static I18TextEditorUiBinder uiBinder = GWT
			.create(I18TextEditorUiBinder.class);
	@UiField FlexTable langEntryContainer;
	@UiField Label textKeyLabel;
	@UiField LOVCapabledComboBox groupCmb;
	@UiField ExtendedTextBox textKeyTxt;
	
	
	private JQDialog dialog ; 

	
	
	
	/**
	 * ini array value dari entrianuser
	 **/
	private Map<String, ExtendedTextBox> indexedLabelByLanguageCode = new HashMap<String  ,  ExtendedTextBox>();
	
	interface I18TextEditorUiBinder extends UiBinder<Widget, I18TextEditor> {
	}

	private I18TextEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		groupCmb.setCustomLookupParameter(CoreLibLookup.i18TextGroup);
		registerLOVCapableControl(groupCmb);
		fillLookupValue(new Command() {
			
			@Override
			public void execute() {
				
				
			}
		});
		
		// get languages
		ApplicationConfigRPCServiceAsync.Util.getInstance().getAvaliableLanguages(new AsyncCallback<List<I18Code>>() {
			
			@Override
			public void onSuccess(List<I18Code> result) {
				if ( result!=null&&!result.isEmpty()){
					int i=0 ; 
					for ( I18Code scn : result){
						langEntryContainer.setText(i, 0, scn.getCode() +"-"  + scn.getDescription());
						final ExtendedTextBox txt = new ExtendedTextBox();  
						langEntryContainer.setWidget(i, 1, txt);
						indexedLabelByLanguageCode.put(scn.getCode()	, txt);
						i++;
					}
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				
			}
		});
		
		
	}
	
	
	
	/**
	 * menampilkan editor dialog. 
	 * @param i18NKey key i18n yang akan di edit, kalau null berarti dalam mode add, else maka edit existing
	 **/
	public void showEditorDialog(String i18NKey){
		if ( dialog==null){
			configureDialog(); 
		}
		if ( i18NKey==null||i18NKey.length()==0){
			textKeyLabel.setVisible(false);
			textKeyTxt.setVisible(true);
			textKeyTxt.setValue("");
			clearAllLang();
			groupCmb.setEnabled(true);
		}else{
			readI18NData(i18NKey);
		}
		dialog.show(true);
	}
	
	
	
	private void clearAllLang() {
		for (ExtendedTextBox scn : indexedLabelByLanguageCode.values()){
			scn.setValue("");
		}
	}
	
	
	private void readI18NData(String i18NKey) {
		textKeyLabel.setVisible(true);
		textKeyTxt.setVisible(false);
		textKeyLabel.setText(i18NKey);
		textKeyTxt.setValue(i18NKey);
		
		for (ExtendedTextBox scn : indexedLabelByLanguageCode.values()){
			scn.setValue("");
		}
		groupCmb.setEnabled(false);
		ApplicationConfigRPCServiceAsync.Util.getInstance().getAllLanguagesTextById(i18NKey, new SimpleAsyncCallback<List<I18Text>>() {

			@Override
			public void onSuccess(List<I18Text> result) {
				if ( result==null||result.isEmpty()){
					clearAllLang();
				}
				else{
					for ( I18Text scn : result){
						indexedLabelByLanguageCode.get(scn.getId().getLocaleCode()).setValue(scn.getLabel());
					}
					groupCmb.setValue(result.get(0).getGroupCode());
					
				}
				
			}

			@Override
			protected void customFailurehandler(Throwable caught) {
				Window.alert("error while read langages configuration form database.error message is :" + caught.getMessage() );
			}
			
		});
		
		
	}
	
	
	
	/***
	 * ini mengkonfigure dialog
	 **/
	private JQDialog configureDialog() {
		dialog = new JQDialog("Add/Modify i18N text" , this);
		dialog.setWidth(500);
		dialog.appendButton("Save", new Command() {
			
			@Override
			public void execute() {
				saveClickHandler();
				
			}
		});
		dialog.appendButton("Cancel", new Command() {
			@Override
			public void execute() {
				dialog.close();
			}
		});
		return dialog ; 
	}
	
	
	
	/**
	 * ini menangani penekanan tombol Save
	 **/
	protected void saveClickHandler() {
		String i18nKey = textKeyTxt.getValue() ; 
		if (i18nKey==null||i18nKey.length()==0){
			Window.alert("text key should not be empty");
			return ; 
		}
		String grpCode = groupCmb.getValue();
		int versi = (int)(new Date()).getTime() ;
		ArrayList<I18Text> txts = new ArrayList<I18Text>(); 
		for ( String key : indexedLabelByLanguageCode.keySet()){
			I18Text txt = new I18Text(); 
			txts.add(txt);
			txt.setId(new I18TextPK());
			txt.getId().setLocaleCode(key);
			txt.getId().setTextKey(i18nKey);
			txt.setGroupCode(grpCode);
			txt.setVersion(versi);
			
			String lbl = indexedLabelByLanguageCode.get(key).getValue() ; 
			if ( lbl==null||lbl.length()==0){
				Window.alert("You should fill i18N text for language :" + key+",save does not permited before you fill this");
				return ;
			}
				
			txt.setLabel(lbl);
			
		}
		I18Text[] arr = new I18Text[txts.size()];
		txts.toArray(arr);
		ApplicationConfigRPCServiceAsync.Util.getInstance().saveLabels(arr, new SimpleAsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				dialog.close();
				
				
			}

			@Override
			protected void customFailurehandler(Throwable caught) {
				Window.alert("unable to save text change, error message is :" + caught.getMessage());
				
			}
		});
		
		
	}

}
