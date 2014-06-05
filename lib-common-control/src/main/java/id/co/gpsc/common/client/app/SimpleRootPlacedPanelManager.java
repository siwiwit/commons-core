/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.gpsc.common.client.app;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import id.co.gpsc.common.client.style.CommonResourceBundle;
import id.co.gpsc.common.util.I18Utilities;
import id.co.sigma.common.util.NativeJsUtilities;

import java.util.ArrayList;

/**
 * ini merupakan manager yang mengatur panel yang di taruh dalam root. panel di stack , dengan n panel di keep
 * kalau panel yang di taruh dalam manager ini overflow dari size yang akan di ijinkan, maka widget yang overflow akan di dispose
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public abstract class SimpleRootPlacedPanelManager {
    
    
    
    
    protected static final String PANEL_ID_PREFIX ="ROOT_PLACED_MANAGER_PANEL_";
    /**
     * ukuran cache. berapa widget yang di keep dalam cache
     */
    private int numberOfWidgetToCache = 20 ;
    
    
    /**
     * widget yang ada dalam cache
     */
    private ArrayList<IRootPlacedWidget> cachedWidgets = new ArrayList<IRootPlacedWidget>();
    
    
    
    
    /**
     * id pada root panel di mana data akan di taruh
     **/
    private String rootElementIdForPanelManager ; 
    
    
    
   



	/**
     * widget yang sedang dalam posisi aktiv
     **/
    private IRootPlacedWidget currentWidget ;  
    
    /**
     * id pada root panel di mana data akan di taruh
     **/
    public String getRootElementIdForPanelManager() {
		return rootElementIdForPanelManager;
	}
    /**
     * id pada root panel di mana data akan di taruh
     **/
	public void setRootElementIdForPanelManager(String rootElementIdForPanelManager) {
		this.rootElementIdForPanelManager = rootElementIdForPanelManager;
	}
    /**
     * ukuran cache. berapa widget yang di keep dalam cache
     * @return the numberOfWidgetToCache
     */
    public int getNumberOfWidgetToCache() {
        return numberOfWidgetToCache;
    }

    /**
     * ukuran cache. berapa widget yang di keep dalam cache
     * @param numberOfWidgetToCache the numberOfWidgetToCache to set
     */
    public void setNumberOfWidgetToCache(int numberOfWidgetToCache) {
        this.numberOfWidgetToCache = numberOfWidgetToCache;
        
    }
    
    
    /**
     * ini untuk aktivasi widget
     */
	public void activateWidget(final ApplicationMenu<?> applicationMenu) {

		try {
			GWT.log("activating widget untuk menu dengan id :"
					+ applicationMenu.getMenuId() + ", command >>"
					+ applicationMenu.getActionCommand());

			if (currentWidget != null) {
				if ((currentWidget.getWidgetSecurityGroupId()
						+ IRootPlacedWidgetGeneratorGroup.GENERATOR_GROUP_SEPARATOR + currentWidget
							.getWidgetSecurityCode()).equals(applicationMenu
						.getActionCommand())) {
					// yang mau di aktivkan menu yang sama, jadi abaikan saja
					return;
				}
			}
			emptyCurrentPanel();

			// cek dari cache

			GWT.log("kosongkan dulu, can pada existing");
			for (IRootPlacedWidget scn : cachedWidgets) {
				if ((scn.getWidgetSecurityGroupId()
						+ IRootPlacedWidgetGeneratorGroup.GENERATOR_GROUP_SEPARATOR + scn
							.getWidgetSecurityCode()).equals(applicationMenu
						.getActionCommand())) {
					actualActivateWorker(applicationMenu, scn);
					GWT.log("Ok widget di perlukan dalam cache, pasang ini untuk root placed panel");
					return;
				}
			}

			// none found, skr kita musti cek ke generator
			final Element imgElement = AbstractImagePrototype
					.create(CommonResourceBundle.getResources()
							.iconLoadingWheel()).createImage().getElement();
			final Element spanElem = DOM.createSpan();

			spanElem.setInnerHTML(I18Utilities.getInstance()
					.getInternalitionalizeText(getLoadingPageMessageI18NKey(),
							"Please wait, while loading page"));

			put(imgElement);
			put(spanElem);

			NativeJsUtilities
					.getInstance()
					.writeToBrowserConsole(
							"tidak ada dalam cache, berarti proses akan digging ke dalam provider. memasang loading image skr");
			getWidgetProvider()
					.getGenerator(
							applicationMenu.getActionCommand(),
							new AsyncCallback<IRootPlacedWidgetGenerator<? extends IRootPlacedWidget>>() {

								@Override
								public void onFailure(Throwable caught) {
									NativeJsUtilities
											.getInstance()
											.writeToBrowserConsole(
													"maaf, gagal mencari menu. error di laporkan : "
															+ caught.getMessage());
									GWT.log("maaf, gagal mencari menu. error di laporkan : "
											+ caught.getMessage(), caught);
									removeLoadingMarker();
								}

								@Override
								public void onSuccess(
										final IRootPlacedWidgetGenerator<? extends IRootPlacedWidget> generator) {

									try {
										NativeJsUtilities
												.getInstance()
												.writeToBrowserConsole(
														"success get generator, remove image space holder firsrt");
										try {
											removeLoadingMarker();
										} catch (Exception e) {
											NativeJsUtilities
													.getInstance()
													.writeToBrowserConsole(
															"gagal remove marker, error di laporkan -> "
																	+ e.getMessage());
										}

										if (generator == null) {
											if (!GWT.isProdMode()) {
												Window.alert("command >>"
														+ applicationMenu
																.getActionCommand()
														+ ", blm di definisikan");

											}
											GWT.log("sory, no generator found for :"
													+ applicationMenu
															.getActionCommand());
											NativeJsUtilities
													.getInstance()
													.writeToBrowserConsole(
															"sory, no generator found for :"
																	+ applicationMenu
																			.getActionCommand());
											return;
										}
										NativeJsUtilities
												.getInstance()
												.writeToBrowserConsole(
														"remove image done, generator not null, skr generate widget.generator  class :"
																+ generator
																		.getClass());

										final IRootPlacedWidget w = generator
												.instantiate();
										;

										GWT.log("survive dan menemukan class generator :"
												+ generator.getClass()
												+ ",instantiated class :"
												+ w.getClass());
										NativeJsUtilities
												.getInstance()
												.writeToBrowserConsole(
														"survive dan menemukan class generator :"
																+ generator
																		.getClass()
																+ ",instantiated class :"
																+ w.getClass());
										w.setWidgetSecurityGroupId(generator
												.getGroupId());

										actualActivateWorker(applicationMenu, w);

										// setWidget((Widget)w);
									} catch (Exception exc) {
										NativeJsUtilities
												.getInstance()
												.writeToBrowserConsole(
														"gagal render widget>>"
																+ exc.getMessage());
										GWT.log("gagal render widget>>"
												+ exc.getMessage(), exc);
										try {
											imgElement.removeFromParent();
											spanElem.removeFromParent();
										} catch (Exception e) {
											
										}

									}

								}

								private void removeLoadingMarker() {
									GWT.log("mengosongkan inner HTML dari container");
									NativeJsUtilities
											.getInstance()
											.writeToBrowserConsole(
													"mengosongkan inner HTML dari container");
									try {
										remove(spanElem);
										remove(imgElement);
									} catch (Exception e) {
										GWT.log("gagal remove space holder"
												+ e.getMessage(), e);
										NativeJsUtilities
												.getInstance()
												.writeToBrowserConsole(
														"gagal remove space holder"
																+ e.getMessage());
									}

								}

							});

		} catch (Exception exc) {
			GWT.log("gagal activate widget dengan id :"
					+ applicationMenu.getMenuId() + ",command >>"
					+ applicationMenu.getActionCommand() + ",error message :"
					+ exc.getMessage(), exc);
		}

	}
    
    
    
    /**
     * widget yang perlu dai aktivasi.
     **/
    protected void actualActivateWorker (final ApplicationMenu<?> applicationMenu ,  IRootPlacedWidget widgetToActivate) {
    	if ( cachedWidgets.contains(widgetToActivate)){
    		cachedWidgets.remove(widgetToActivate);
    	}
    		
    	widgetToActivate.switchToDefaultState(applicationMenu.getAdditionalData());
    	putWidget(widgetToActivate);
    	cachedWidgets.add(widgetToActivate);
    }
    
    /**
     * mengosongkan panel . sebelum semuanya di taruh
     **/
    protected void emptyCurrentPanel ()  {
    	RootPanel pnl = RootPanel.get(this.rootElementIdForPanelManager);
    	int count = pnl.getWidgetCount(); 
    	for ( int i=0;i<count;i++){
    		pnl.remove(i);
    	}
    	
    	try {
    		int elCnt =  pnl.getElement().getChildCount();
    		if ( elCnt>0){
        		Element holder []= new Element[elCnt];
        		for ( int i=0;i< elCnt;i++){
        			holder[i]=(Element)pnl.getElement().getChild(i);
        		}
        		for ( Element el : holder){
        			el.removeFromParent();
        		}
        	}
		} catch (Exception e) {
			GWT.log("bermasalah dalam clear panel.error message :" + e.getMessage() , e);
		}
    	
    	
    }
    
    
    protected void putWidget (Widget widget) {
    	RootPanel.get(this.rootElementIdForPanelManager).add(widget);
    }
    /**
     * menaruh widget ke dalam container
     **/
    protected void putWidget (IRootPlacedWidget widget) {
    	RootPanel.get(this.rootElementIdForPanelManager).add((Widget)widget);
    	
    }
    
    
    
    /**
     * menaruh element dalam widget
     **/
    protected void put (Element element) {
    	RootPanel.get(this.rootElementIdForPanelManager).getElement().appendChild(element);
    }
    
    
    protected void remove(Element element) {
    	RootPanel.get(this.rootElementIdForPanelManager).getElement().removeChild(element);
    }
    /**
     * hapus widget dari root container
     **/
    protected void removeCurrentWidget () {
    	RootPanel.get(this.rootElementIdForPanelManager).remove((Widget)currentWidget);
    }
    
    /**
     * menaruh widget ke dalam stack. ini include pekerjaan dispose widget
     */
    public void pushWidget(IRootPlacedWidget widget) {
        if ( cachedWidgets.size()>numberOfWidgetToCache){
            IRootPlacedWidget disposed = cachedWidgets.get(0);
            cachedWidgets.remove(0);
            try{
                 disposed.dispose();
            }
            catch( Exception exc){
                GWT.log("gagal dispose panel>>" + exc.getMessage(), exc);
            }
           
        }
        cachedWidgets.add(widget);
    }
    
    
    
    
    /**
     * ini merupakan key untuk message saat panel akan di load ke dalam panel. message nya misal nya : pls wait, while loading page
     */
    protected abstract  String getLoadingPageMessageI18NKey () ; 
    
    
    
    
    /**
     * ini key i18 key , pesan kalau panel gagal di load. 
     */
    protected abstract  String getLoadingPageFailMessageI18NKey () ; 
    
    
    
    /**
     * provider widget. di implementasikan masing-masing
     */
    protected abstract  IRootPlacedWidgetProvider getWidgetProvider() ; 
    
}
