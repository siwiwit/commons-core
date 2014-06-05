package id.co.gpsc.common.client.widget.notification;

import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.container.JQDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * 
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public abstract class BaseNotificationDialog {
	
	protected String dialogDefaultTitle ; 
	
	/**
	 * key internalization untuk dialog title
	 **/
	protected String dialogTitleI18NKey ; 
	
	
	/**
	 * label default untuk tombol OK
	 **/
	protected String defaultOKLabel = "OK"; 
	
	
	/**
	 * key internalization untuk tombol OK
	 **/
	protected String buttonOKI18NKey ; 
	
	/**
	 * method ini akan menampilkan dialog ke user.Dengan tombol OK saja
	 **/
	protected JQDialog showNotificationMessage (  Widget widget) {
		final JQDialog dialog = new JQDialog(I18Utilities.getInstance().getInternalitionalizeText(dialogTitleI18NKey, dialogDefaultTitle), widget);
		dialog.appendButton(I18Utilities.getInstance().getInternalitionalizeText(buttonOKI18NKey, defaultOKLabel), new Command() {
			
			@Override
			public void execute() {
				dialog.close();
			}
		});
		return dialog ; 
	}
	
	
	/**
	 * message standard untuk popup notification
	 **/
	public  String getDialogDefaultTitle () {
		return dialogDefaultTitle; 
	}
	
	/**
	 * message standard untuk popup notification
	 **/
	public void setDialogDefaultTitle(String dialogDefaultTitle) {
		this.dialogDefaultTitle = dialogDefaultTitle;
	}
	
	

	/**
	 * label default untuk tombol OK
	 **/
	public void setDefaultOKLabel(String defaultOKLabel) {
		this.defaultOKLabel = defaultOKLabel;
	}

	/**
	 * label default untuk tombol OK
	 **/
	public String getDialogTitleI18NKey() {
		return dialogTitleI18NKey;
	}

	/**
	 * key internalization untuk tombol OK
	 **/
	public String getButtonOKI18NKey() {
		return buttonOKI18NKey;
	}
	/**
	 * key internalization untuk tombol OK
	 **/
	public void setButtonOKI18NKey(String buttonOKI18NKey) {
		this.buttonOKI18NKey = buttonOKI18NKey;
	}
}
