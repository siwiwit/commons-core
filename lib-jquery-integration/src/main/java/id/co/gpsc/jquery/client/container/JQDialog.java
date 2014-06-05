package id.co.gpsc.jquery.client.container;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import id.co.gpsc.common.form.CloseablePanel;
import id.co.gpsc.common.form.DisposeablePanel;
import id.co.gpsc.jquery.client.util.JQueryUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/**
 * wrapper dialog jquery
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class JQDialog implements CloseablePanel , DisposeablePanel{
	
	private JavaScriptObject dialogArgument = JavaScriptObject.createObject() ;
	private JavaScriptObject buttonsArray = JavaScriptObject.createArray() ;
	private static int BUTTON_HANDLER_COUNTER =1; 
	
	
	
	/**
	 * berkorelasi dengan {@link #buttonIdPrefix}, ini counter ID dari button apa
	 **/
	private static int BUTTON_ID_PREFIX_COUNTER =1; 
	
	
	private static String BUTTON_ID_PREFIX="GPS_JQDIALOG_BUTTON";
	private final String dialogId = DOM.createUniqueId();
	private final SimplePanel containerPanel = new SimplePanel();
	  
	private static FlowPanel SPACE_HOLDER;
	private Map<Integer, Command> methodIndexer = new HashMap<Integer, Command>();
	
	/**
	 * widget yang di tampilkan dalam dialog
	 **/
	private Widget underlyingWidget ;
	
	
	
	
	private String buttonIdPrefix  = BUTTON_ID_PREFIX + ( BUTTON_ID_PREFIX_COUNTER++); 
	
	
	
        /**
         * child yang disposable
         */
	private ArrayList<DisposeablePanel> disposableChildren = new ArrayList<DisposeablePanel>(); 
	
	/**
         * reference ke parent disposable panel
         */
	private DisposeablePanel parentOfDisposablePanel ; 
	/**
	 * @param title title dari dialog
	 **/
	public JQDialog(String title){
		if(SPACE_HOLDER==null){
			SPACE_HOLDER = new FlowPanel(); 
			SPACE_HOLDER.setVisible(false);
			RootPanel.get().add(SPACE_HOLDER);
		}
		SPACE_HOLDER.add(containerPanel);
		containerPanel.getElement().setId(dialogId);
		setOption("title", title);
	}
	
	/**
	 * @param title title dari dialog
	 * @param widget widget untuk di taruh di atas dialog
	 **/
	public JQDialog(String title , Widget widget){
		this(title); 
		setWidget(widget);
	}
	
	/**
	 * ganti widget
	 **/
	public void setWidget (Widget widget) {
		this.underlyingWidget = widget ; 
		this.containerPanel.setWidget(this.underlyingWidget); 
	}
	
	
	/**
	 * show(non modal)
	 **/
	public void show () {
		show(false);
	}

	/**
	 * show dialog dengan flag modal/ tidak
	 * @param modal true -> modal dialog dan sebaliknya
	 **/
	public void show (boolean modal) {
		try {
			setOption("modal", modal);
			showDialogWorker();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	/**
	 * set resizeable(default = true)
	 **/
	public void setResizable(boolean resizeable){
		setOption("resizable", resizeable);
	}
	
	
	
	
	
	/**
	 * set height dari dialog denan ukuran tertentu. default tinggi dialog adalah auto.<br/> 
	 * @see {@link #setHeightToAuto()}
	 * @param height tinggi dari dialog
	 **/
	public void setHeight(int height) {
		setOption("height", height);
	}
	
	
	/**
	 * The width of the dialog, in pixels.<br/>
	 * Default:<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;300
	 * @param width new width for dialog
	 * 
	 **/
	public void setWidth (int width){
		setOption("width", width);
	}
	
	
	/**
	 * rubah mode tinggi dari dialog menjadi auto height
	 * 
	 **/
	public void setHeightToAuto() {
		setOption("height", "auto");
	}
	
	/**
	 * The maximum height to which the dialog can be resized, in pixels.
	 * default : false
	 **/
	public void setMaximizeHeight(boolean maxHeight){
		setOption("maxHeight", maxHeight);
	}
	
	/**
	 * The maximum width to which the dialog can be resized, in pixels.<br/>
	 * default false
	 **/
	public void setMaximizeWidth(boolean maxWidth){
		setOption("maxWidth", maxWidth);
	}
	
	
	/**
	 * limiting minimum height of dialog. Default value is 150
	 * @param minHeight minimum height to be set. 
	 **/
	public void setMinimumHeight(int minHeight){
		setOption("minHeight", minHeight);
	}
	
	/**
	 * limit minimum width dari dialog. default : 150
	 * @param minWidth new minimum width for dialog 
	 **/
	public void setMinimumWidth(int minWidth){
		setOption("minWidth", minWidth);
	}
	
	
	/**
	 * The starting z-index for the dialog.<br/>
	 * Default:<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;300
	 * @param zIndex new zIndex untuk dialog
	 **/
	public void setZIndex (int zIndex){
		setOption("zIndex", zIndex);
	}
	
	
	/**
	 * ganti title dari dialog
	 **/
	public void setTitle (String title) {
		//FIXME : proses show masih belum clean
		setOption("title", title);
	}
	
	/**
	 * ini hanya untuk di panggil pada saat dialog dalam posisi show. ini akan membawa dialog pada posisi teratas
	 **/
	public native void moveToTop()/*-{
		var dlgId = this.@id.co.gpsc.jquery.client.container.JQDialog::dialogId ;
		 
	}-*/;
	/**
	 * menambahkan tombol ke dalam dialog
	 **/
	public  int appendButton (String buttonLabel , Command buttonHandler ){
		int idButton = BUTTON_HANDLER_COUNTER; 
		BUTTON_HANDLER_COUNTER++ ; 
		appendButtonWorker(idButton, buttonLabel , generateButtonDOMId(idButton));
		methodIndexer.put(idButton , buttonHandler);
		return idButton ; 
	}
	
	
	
	/**
	 * unified akses untuk generate ID
	 **/
	protected String generateButtonDOMId(int buttonIndex){
		return buttonIdPrefix + buttonIndex ; 
	}
	
	
	
	/**
	 * disable tombol dengan index
	 **/
	public void disableButton (int buttonIndex){
		JQueryUtils.getInstance().triggerVoidMethod(generateButtonDOMId(buttonIndex), "dialog", "disable");
	}
	
	
	/**
	 * disable tombol dengan index
	 **/
	public void enableButton (int buttonIndex){
		JQueryUtils.getInstance().triggerVoidMethod(generateButtonDOMId(buttonIndex), "dialog", "enable");
	}
	
	/**
	 * worker untuk append button
	 * @param idButton id untuk button. ini di perlukan untuk proses remove
	 * @param buttonLabel
	 **/
	private native void appendButtonWorker (int idButton ,  String buttonLabel , String buttonDomId )/*-{
		var retval = {} ; 
		var thisSwap = this ; 
		retval["text"]   = buttonLabel ; 
		retval["id"] = buttonDomId ;
		retval["click"] = function (){
			thisSwap.@id.co.gpsc.jquery.client.container.JQDialog::propagateExecutor(I)(idButton);
		} ;
		retval["buttonIndexer"]= idButton ;
		this.@id.co.gpsc.jquery.client.container.JQDialog::buttonsArray.push( retval ) ; 
	
	}-*/;
	
	
	/**
	 * trigger method (ini handler click)
	 **/
	protected void propagateExecutor (int idButton ) {
		if ( !this.methodIndexer.containsKey(idButton))
			return ;
		this.methodIndexer.get(idButton).execute();
	}
	
	public   void removeButton (int buttonIdIndexer) {
		removeButtonWorker(buttonIdIndexer); 
		methodIndexer.remove(buttonIdIndexer);
	}
	public native void removeButtonWorker (int buttonIdIndexer) /*-{
		var arr = this.@id.co.gpsc.jquery.client.container.JQDialog::buttonsArray ;
		var swap = [];
		for ( i=0 ; i<arr.length;i++){
			if ( arr[i].buttonIndexer == buttonIdIndexer)
				continue  ;
			swap.push(arr[i]);
		} 
		this.@id.co.gpsc.jquery.client.container.JQDialog::buttonsArray  = swap;
	
	}-*/;
	
	
	
	
	/**
	 * set option dialog
	 **/
	private native void setOption (String option , Object argument) /*-{
		this.@id.co.gpsc.jquery.client.container.JQDialog::dialogArgument[option]=argument;
	
	}-*/;
	
	
	
	/**
	 * cabut option dari dialog
	 * @category native
	 **/
	private native void removeOption (String option ) /*-{
		delete this.@id.co.gpsc.jquery.client.container.JQDialog::dialogArgument[option];
	}-*/;


	@Override
	public native void close() /*-{
		$wnd.$("#" +  this.@id.co.gpsc.jquery.client.container.JQDialog::dialogId).dialog("close") ;
	}-*/;


	@Override
	public void dispose() {
		destroy() ; 
		if ( this.underlyingWidget!=null && this.underlyingWidget instanceof DisposeablePanel){
			((DisposeablePanel)underlyingWidget).dispose();
		}
	}
	
	
	
	
	/**
	 * worker untuk destroy jquery dialog function dari panel
	 **/
	private native void destroy() /*-{
		$wnd.$("#" +  this.@id.co.gpsc.jquery.client.container.JQDialog::dialogId).dialog("destroy") ;
	}-*/;
	
	
	//FIXME : pls di optimasi init bagus nya cuma sekali saja
	private native void showDialogWorker () /*-{
	
		 
	
		try{
		
			this.@id.co.gpsc.jquery.client.container.JQDialog::dialogArgument["buttons"]=this.@id.co.gpsc.jquery.client.container.JQDialog::buttonsArray ;
		
			$wnd.$("#" +  this.@id.co.gpsc.jquery.client.container.JQDialog::dialogId).dialog(
			this.@id.co.gpsc.jquery.client.container.JQDialog::dialogArgument 
		) ;
		}catch (exc){
			$wnd.alert(exc.message);
		}
		 
	
	}-*/;
	
	
	/**
	 * dom ID dari dialog
	 **/
	public String getDialogId() {
		return dialogId;
	}

    @Override
    public void registerDisposableChild(DisposeablePanel disposableChild) {
       if ( disposableChild==null||disposableChildren.contains(disposableChild)){
           return ; 
       }
       disposableChildren.add(disposableChild);
    }

    @Override
    public void unregisterDisposableChild(DisposeablePanel disposableChild) {
        if ( disposableChild==null||disposableChildren.contains(disposableChild)){
            return ; 
        }
        disposableChildren.remove(disposableChild);
            
    }

    @Override
    public DisposeablePanel getParentDisposablePanel() {
        return this.parentOfDisposablePanel;
    }

    @Override
    public void setParentDisposablePanel(DisposeablePanel parent) {
        this.parentOfDisposablePanel = parent ; 
    }
}
