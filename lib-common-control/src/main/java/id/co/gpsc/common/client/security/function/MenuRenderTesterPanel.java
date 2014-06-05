package id.co.gpsc.common.client.security.function;

import java.math.BigInteger;
import java.util.ArrayList;

import id.co.gpsc.common.data.app.security.MenuEditingData;
import id.co.gpsc.common.security.domain.Function;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class MenuRenderTesterPanel extends Composite{
	
	
	
	private VerticalPanel outmostPanel ; 
	
	private Button btnEdtable ; 
	private Button btnNotEditable ;
	MenuSelectorPanel menuSelectorPanel; 
	
	public MenuRenderTesterPanel() {
		outmostPanel = new VerticalPanel(); 
		initWidget(outmostPanel);
		HorizontalPanel pnlButtn = new HorizontalPanel(); 
		outmostPanel.add(pnlButtn); 
		menuSelectorPanel = new MenuSelectorPanel(); 
		outmostPanel.add(menuSelectorPanel);
		outmostPanel.getElement().getStyle().setProperty("border", "black solid 1px");
		btnEdtable = new Button("Editable" , new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				menuSelectorPanel.renderMenu(generateMenuEditingData());
				
			}
		}); 
		btnNotEditable = new Button("not editable", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				menuSelectorPanel.renderMenu(generateMenuEditingData());
				
			}
		}); 
		pnlButtn.add(btnEdtable);
		pnlButtn.add(btnNotEditable);
		
	}
	
	private MenuEditingData generateMenuEditingData () {
		MenuEditingData retval = new MenuEditingData() ; 
		Function l01 = new Function();
		l01.setId(new BigInteger("1"));
		l01.setFunctionLabel("Menu 1");
		l01.setMenuTreeCode("1");
		l01.setSiblingOrder(1);
		l01.setTreeLevelPosition(1);
		
		
		Function l02 = new Function();
		l02.setId(new BigInteger("2"));
		l02.setFunctionLabel("Menu 2");
		l02.setMenuTreeCode("2");
		l02.setSiblingOrder(2);
		l02.setTreeLevelPosition(1);
		
		
		
		Function l03 = new Function(); 
		l03.setId(new BigInteger("3"));
		l03.setFunctionLabel("Menu 3");
		l03.setMenuTreeCode("3");
		l03.setSiblingOrder(3);
		l03.setTreeLevelPosition(1);
		
		
		
		
		Function l11 = new Function();
		l11.setId(new BigInteger("4"));
		l11.setFunctionLabel("Menu 1.1");
		l11.setMenuTreeCode("1.4");
		l11.setSiblingOrder(1);
		l11.setFunctionIdParent(new BigInteger("1"));
		l11.setTreeLevelPosition(2);
		
		
		Function l12 = new Function();
		l12.setId(new BigInteger("5"));
		l12.setFunctionLabel("Menu 1.2");
		l12.setMenuTreeCode("1.5");
		l12.setSiblingOrder(2);
		l12.setFunctionIdParent(new BigInteger("1"));
		l12.setTreeLevelPosition(2);
		
		
		
		retval.setAllMenus( new ArrayList<Function>());
		retval.getAllMenus().add(l01);
		retval.getAllMenus().add(l02);
		retval.getAllMenus().add(l03);
		retval.getAllMenus().add(l11);
		retval.getAllMenus().add(l12);
		
		retval.setAllSelectedIds(new ArrayList<BigInteger>());
		
		retval.getAllSelectedIds().add(new BigInteger("5")); 
		
		return retval ; 
	}
	

}
