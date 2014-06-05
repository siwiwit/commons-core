/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.gpsc.common.client.app;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * provider 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface IRootPlacedWidgetProvider {
    
    
    
    /**
     * provider generator. di akses dengan widget code. widget code di dapat dari menu
     * @param  widgetCode  kode widget yang perlu di akses
     */
    public void getGenerator (String widgetCode , AsyncCallback<IRootPlacedWidgetGenerator<? extends  IRootPlacedWidget>> handlerOnComplete) ; 
    
}
