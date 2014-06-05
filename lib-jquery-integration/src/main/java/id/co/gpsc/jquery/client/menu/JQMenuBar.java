package id.co.gpsc.jquery.client.menu;

import java.util.List;

import id.co.gpsc.jquery.client.util.JQueryUtils;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;


/**
 * menu bar
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class JQMenuBar<MENUDATA> extends Widget{
	
	private Element ulElement ; 
	
	private boolean menuRendered =false ; 
	
	
	private boolean attached ; 
	
	
	private Command onAttachedWorker ; 
	
	protected static final String JQUERY_NAME ="menubar";
	
	public JQMenuBar(){
		setElement(Document.get().createDivElement());
		ulElement  = DOM.createElement("ul");
		getElement().setId(DOM.createUniqueId());
		ulElement.setId(DOM.createUniqueId());
		getElement().appendChild(ulElement);
		
	}
	
	
	
	
	/**
	 * call render menu. panggil ini kalau semua sudah siap
	 **/
	public void renderMenu () {
		renderMenu(null);
		
	}
	
	
	
	
	/**
	 * render menu. versi ini dnegan callback setelah task selesai di lakukan
	 **/
	public void renderMenu (final Command handlerAfterRender) {
		Command cmd = new Command() {
			
			@Override
			public void execute() {
				if ( menuRendered)
					JQueryUtils.getInstance().destroy(ulElement.getId(), JQUERY_NAME);
				menuRendered = true ; 
				ulElement.setClassName("menubar-icons");
				renderMenuWorker(ulElement.getId(), JQUERY_NAME);
				if ( handlerAfterRender!=null){
					new Timer() {
						@Override
						public void run() {
							handlerAfterRender.execute();
						}
					}.schedule(5);
				}
			}
		};
		
		if ( !attached){
			this.onAttachedWorker=cmd;
		}else{
			cmd.execute();
		}
		
	}
	
	
	@Override
	protected void onAttach() {
		// TODO Auto-generated method stub
		super.onAttach();
		if (onAttachedWorker!=null)
			onAttachedWorker.execute(); 
		this.attached=true ; 
	}
	
	/**
	 * tambah menu ke dalam menubar
	 **/
	public void appendMenu(JQBaseMenuWidget<MENUDATA> menu){
		ulElement.appendChild(menu.getUnderlyingElement());
	}
	
	
	/**
	 * append array of menu. level 0 saja
	 **/
	public void appendMenus(List<JQBaseMenuWidget<MENUDATA>> menus){
		if (menus==null||menus.isEmpty())
			return ;
		for (JQBaseMenuWidget<MENUDATA> menu : menus ){
			ulElement.appendChild(menu.getUnderlyingElement());
		}
		
	}
	
	
	
	
	/**
	 * set lebar (dalam pixel)
	 **/
	public void setWidthPx(int widthInPx){
		getElement().getStyle().setWidth(widthInPx, Unit.PX);
	}
	
	

	/**
	 * set lebar (dalam persen)
	 **/
	public void setWidthPct(int widthInPct){
		getElement().getStyle().setWidth(widthInPct, Unit.PCT);
	}
	
	
	
	
	/**
	 * worker untuk render Jquery menubar
	 **/
	protected native void renderMenuWorker (String menuBarid, String jqueryName)/*-{
		$wnd.$("#" + menuBarid).menubar({
			autoExpand: true,
			menuIcon: true,
			buttons: true
		});
	
	}-*/;

}
