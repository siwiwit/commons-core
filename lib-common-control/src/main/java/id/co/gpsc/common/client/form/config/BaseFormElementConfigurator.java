package id.co.gpsc.common.client.form.config;

import com.google.gwt.user.client.ui.FlexTable;

import id.co.gpsc.common.client.form.advance.TextBoxWithLabel;
import id.co.gpsc.common.client.widget.BaseResourceBundleFriendlyComposite;



/**
 * base class untuk form configurator
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class BaseFormElementConfigurator extends BaseResourceBundleFriendlyComposite{
	
	
	
	protected FlexTable formTable ;

	private TextBoxWithLabel txtHintI18NKey ;
	public BaseFormElementConfigurator(){
		formTable=new FlexTable();
		initWidget(formTable);
		
	}
	@Override
	public String getPanelShortCode() {
		return "SYSTEM-BASE-FORM-CONFIGURATOR";
	}

}
