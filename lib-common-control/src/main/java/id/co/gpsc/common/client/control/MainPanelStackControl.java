package id.co.gpsc.common.client.control;

import java.util.ArrayList;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;




/**
 * control stack. ini menaruh widget-widget dalam bentuk stack. di tumpuk jadinya kalau di close, urutan nya kembali bersasarkan stack nya<br/>
 * Contoh nya seperti ini:<br/>
 * 
 * <table>
 * <tr>
 * 	<td><div style="width:100px;height:50px;border:1px red solid">Panel 1 <br/></div></td>
 * <td><div style="width:100px;height:50px;border:1px green solid">Panel 2 <br/></div></td>
 * <td><div style="width:100px;height:50px;border:1px purple solid">Panel 3 <br/></div></td>
 * </tr>
 * 
 * </table>
 * Jadinya kalau panel 1 di tutup, panel aktiv kembali ke panel 2 dst. kalau di push lg panel baru, panel 1 di tendang ke dalam stack 1 level
 * 
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public final class MainPanelStackControl {
	
	
	
	/**
	 * singleton instance kita
	 **/
	private static MainPanelStackControl instance ; 
	 
	
	/**
	 * panel renderer. ini merender panel dalam outmost container. stack container
	 **/
	private IPanelRenderWorker panelRenderWorker ; 
	
	private MainPanelStackControl(){
		GWT.log("selamat datang dan terima kasih sudah mempergunakan :" + MainPanelStackControl.class.getName());
		GWT.log("=========================================================================="); 
		GWT.log("");
		GWT.log("1. class ini hanya facade nya saja, anda harus mengeset method :MainPanelStackControl#setPanelRenderWorker");
		GWT.log("2. "); 
		
	}
	
	
	/**
	 * wrapper menaruh data panel dalam stack. kita memerlukan panel + view mode 
	 **/
	protected class PanelWrapper {
		private Widget panel ; 
		private ViewScreenMode viewScreenMode ; 
		 
		 
		
		 
		public PanelWrapper(Widget panel, ViewScreenMode viewScreenMode
				) {
			super();
			this.panel = panel;
			this.viewScreenMode = viewScreenMode;
		}
		public Widget getPanel() {
			return panel;
		}
		public void setPanel(Widget panel) {
			this.panel = panel;
		}
		public ViewScreenMode getViewScreenMode() {
			return viewScreenMode;
		}
		public void setViewScreenMode(ViewScreenMode viewScreenMode) {
			this.viewScreenMode = viewScreenMode;
		}
		
		
	}
	
	
	/**
	 * stack dari panel
	 **/
	private ArrayList<PanelWrapper> panelStacks = new ArrayList<MainPanelStackControl.PanelWrapper>(); 
	
	
	/**
	 * maksimal panel yang ada di dalam stack. lebih dari ini akan di buang
	 **/
	private int maxStackedPanel = 100  ; 
	
	
	
	/**
	 * widget yang saat ini berada dalam posisi aktiv
	 **/
	private PanelWrapper currentWidget; 
	
	
	
	
	/**
	 * menaruh penal dari stack. ini akan menaruh panel dalam posisi tampil dan mempush panel sebelumnya dari dalam stack
	 **/
	public void putPanel(final Widget panel , ViewScreenMode screenMode) {
		if ( this.panelRenderWorker==null){
			GWT.log("Anda Belum mengeset renderer worker, stack tidak tahu bagaimana menaruh data dalam container");
			return ; 
		}
		
		//1. hapus dulu current
		if ( currentWidget!=null){
			try {
				panelRenderWorker.removePanel(currentWidget.getPanel(), currentWidget.getViewScreenMode());
			} catch (Exception e) {
				GWT.log("error : " +  e.getMessage() , e);
			}
			
		}
		
		//2. taruh panel baru
		PanelWrapper  w= new PanelWrapper(panel, screenMode); 
		panelStacks.add(w); 
		switchViewMode(screenMode);
		currentWidget = w ; 	 
		panelRenderWorker.putPanel(panel, screenMode);
		
	}
	
	
	
	/**
	 * worker untuk menutup panel yang saat ini berada dalam posisi atas. ini lawan dari {@link #putPanel(Widget, ViewScreenMode)}, method ini secara otomatis akan mengaktivkan panel berikut yang ada dalam stack
	 **/
	public void closeCurrentPanel() {
		if ( currentWidget==null)
			return ; 
		PanelWrapper prevPanel  = null;
		panelRenderWorker.removePanel(currentWidget.getPanel(), currentWidget.getViewScreenMode());
		panelStacks.remove(currentWidget);
		currentWidget = null ; 
		if ( this.panelStacks.size()>0){
			prevPanel = panelStacks.get(panelStacks.size()-1);
		}
		else{
			System.out.println("stack of panel size =" + panelStacks.size());
		}
		currentWidget = prevPanel ;
		if ( prevPanel!=null){
			panelRenderWorker.putPanel(prevPanel.getPanel(), prevPanel.getViewScreenMode());
			switchViewMode(prevPanel.getViewScreenMode());
		}	
	}
	
	
	
	/**
	 * menutup panel. ini berarti juga semua panel berikut dalam stack, harus di remove dari stack
	 **/
	public void closePanel(Widget w ) {
		int idxFound =-1 ; 
		PanelWrapper prevPanel = null ; 
		ViewScreenMode modeCurrent = null ; 
		for ( int i = 0 ; i< this.panelStacks.size() ; i++){
			PanelWrapper swap = panelStacks.get(i); 
			if (swap.getPanel().equals(w)){
				idxFound = i ;
				modeCurrent = swap.getViewScreenMode(); 
				
				if(i>0){
					prevPanel = panelStacks.get(i-1);
				}
				break ; 
				
			}
		}
		if(idxFound==-1){
			GWT.log("close panel tidak bisa di lakukan karena reference widget sama sekali tidak di temukan dalam stack of widget");
			return ; 
		}
		for(int i=this.panelStacks.size() -1; i>=idxFound ; i--){
			panelStacks.remove(i);
		}
		
		panelRenderWorker.removePanel(w, modeCurrent);
		if ( prevPanel!=null){
			this.currentWidget = prevPanel ; 
			panelRenderWorker.putPanel(prevPanel.getPanel(), prevPanel.getViewScreenMode());
		}
	}
	
	/**
	 * rubah mode dari view, full screen atau normal
	 **/
	private void switchViewMode(ViewScreenMode screenMode) {
		if ( ViewScreenMode.fullScreen.equals(screenMode)){
			panelRenderWorker.activateFullScreenMode();
		}else{
			panelRenderWorker.activateNormalScreenMode(); 
		}
	}
	
	
	
	/**
	 * kosongkan stack dan kembalikan dalam mode normal screen
	 **/
	public void clearStack () {
		if ( currentWidget!=null){
			panelRenderWorker.removePanel(currentWidget.getPanel(), currentWidget.getViewScreenMode());
		}
		currentWidget = null ; 
		this.panelStacks.clear(); 
		
	}
	
	
	
	public static MainPanelStackControl getInstance() {
		if(instance==null)
			instance = new MainPanelStackControl(); 
		return instance;
	}
	
	/**
	 * maksimal panel yang ada di dalam stack. lebih dari ini akan di buang
	 **/
	public int getMaxStackedPanel() {
		return maxStackedPanel;
	}
	/**
	 * maksimal panel yang ada di dalam stack. lebih dari ini akan di buang
	 **/
	public void setMaxStackedPanel(int maxStackedPanel) {
		this.maxStackedPanel = maxStackedPanel;
	}
	/**
	 * panel renderer. ini merender panel dalam outmost container. stack container
	 **/
	public void setPanelRenderWorker(IPanelRenderWorker panelRenderWorker) {
		this.panelRenderWorker = panelRenderWorker;
	}
	/**
	 * panel renderer. ini merender panel dalam outmost container. stack container
	 **/
	public IPanelRenderWorker getPanelRenderWorker() {
		return panelRenderWorker;
	}

}
