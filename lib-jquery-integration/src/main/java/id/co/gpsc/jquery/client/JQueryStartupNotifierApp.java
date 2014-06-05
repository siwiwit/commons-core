package id.co.gpsc.jquery.client;

import id.co.gpsc.jquery.client.grid.BaseJQGridPanel;
import id.co.gpsc.jquery.client.util.JQueryUtils;
import id.co.gpsc.jquery.client.util.NativeJsUtilities;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class JQueryStartupNotifierApp implements EntryPoint {
	
	
	
	
	public static final String JQGRID_LANG_BASE_RELATIVE_FOLDER ="jqgrid-4.4.4/i18n/" ; 
	
	/**
	 * bahasa yang di terima kalau misal nya lang null atau tidak dalam range
	 **/
	private static final String DEFAULT_LANGUAGE = "id";

	
	/**
	 * language yang di terima ama JQGRID
	 **/
	private static final String[] JQGRID_ACCEPTED_LANGUAGES ={
		"ar",
		"bg",
		"cat",
		"cn",
		"cs",
		"da",
		"de",
		"dk",
		"el",
		"en",
		"es",
		"fa",
		"fi",
		"fr",
		"gl",
		"he",
		"hr",
		"hr1",
		"hu",
		"id",
		"is",
		"it",
		"ja",
		"kr",
		"lt",
		"mne",
		"nl",
		"no",
		"pl",
		"pt-br",
		"pt",
		"ro",
		"ru",
		"sk",
		"sr-latin",
		"sr",
		"sv",
		"th",
		"tr",
		"tw",
		"ua",
		"vi"
	};
	@Override
	public void onModuleLoad() {
		
		if ( !GWT.isProdMode()){
			writeLoggerBoth("Anda mempergunakan jquery integration dalam modul anda.Ucapan terima kasih dari kami selaku tim yang terlibat dalam glue library ini"); 
			writeLoggerBoth("Mohon di perhatikan uraian di bawah ini");
			writeLoggerBoth("===============================================================================");
			writeLoggerBoth("1. pastikan internalization untuk datepicker sudah di assign");
			writeLoggerBoth("  yang perlu anda lakukan adalah menginclude file APP_MODUL_FOLDER/js/i18/jquery.ui.datepicker-xxx.js(xx nama lokalisasi anda.anda bisa manfaatkan jsp untuk ini)");
			writeLoggerBoth("2. pastikan internalization untuk jqgrid(contoh nya : js/i18/jquery.ui.datepicker-xx.js)");
			writeLoggerBoth("3. pastikan anda mempergunakan jquery ui theme(css tipikal nya anda perlu file : jquery-ui-xxversionxx.custom.css)");
			writeLoggerBoth("===============================================================================");
			writeLoggerBoth("mohon di evaluasi bersama kelemahan yang masih ada . Let's build a great code !!!!");
			writeLoggerBoth("versi 1.9 : known issue : ");
			writeLoggerBoth("===================================================================== ");
			writeLoggerBoth("css untuk menubar tidak di sertakan dalam jquery-ui-1.9.1.custom.css, css ini perlu di buat manual kalau misalnya tidak di sertakan");
			writeLoggerBoth("JQGrid issue, anda harus memilih akan mempergunakan language apa, "); 
			writeLoggerBoth("set variable : JQGRID_LANGUAGE, kalau null akan di default . Contoh nya");
			
			writeLoggerBoth("<script type=\"text/javascript\">var JQGRID_LANGUAGE=\"id\";</script>");
			writeLoggerBoth("ini akan load language Id ke dalam jqgrid"); 
			
			
		}
		configureJQGridLanguage(); 
		
	}

	
	
	
	
	/**
	 * method ini akan me-load JS language sesuai dengan konfigurasi
	 **/
	protected void configureJQGridLanguage() {
		String lang = NativeJsUtilities.getInstance().getStringVariableToWindowVariable(BaseJQGridPanel.JQGRID_LANGUAGE_VARIABLE_NAME);
		if ( lang==null||lang.isEmpty()){
			writeLoggerBoth("language dari GRID belum di set, di set default menjadi " + DEFAULT_LANGUAGE +", kalau anda perlu ganti silakan set variable ini sebelum main script di jalankan");
			loadJQGridLangage(DEFAULT_LANGUAGE);
			return ; 
		}
		String message="tidak di terima, yang di ijinkan hanya : ";
		for ( String chk : JQGRID_ACCEPTED_LANGUAGES){
			if ( chk.equals(lang)){
				loadJQGridLangage(chk);
				return ; 
			}
			message+=chk + " "; 
		}
		message +="\nAkan di load default lang:" + DEFAULT_LANGUAGE ;  
		writeLoggerBoth(message); 
		loadJQGridLangage(DEFAULT_LANGUAGE);
	}
	
	
	
	
	/**
	 * getter language
	 **/
	private void loadJQGridLangage(String language){
		String url = GWT.getModuleBaseURL() + JQGRID_LANG_BASE_RELATIVE_FOLDER + "grid.locale-" + language + ".js";
		writeLoggerBoth("load jqgrid lang: " + url); 
		JQueryUtils.getInstance().loadScript(url); 
	}
	
	
	private void writeLoggerBoth (String message){
		GWT.log(message); 
		NativeJsUtilities.getInstance().writeToBrowserConsole(message); 
	}
}
