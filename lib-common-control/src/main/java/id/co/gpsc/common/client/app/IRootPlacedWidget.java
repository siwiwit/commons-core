/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.gpsc.common.client.app;

import id.co.gpsc.common.form.DisposeablePanel;

/**
 * interface object yang bisa di taruh di root
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface IRootPlacedWidget extends  DisposeablePanel{
    
    
    /**
     * switch panel ke state default. editor harus di tutup etc
     * @param  additionalData  data tambahan. kandidat paling tepat adalah json, jadi data tertentu di pass over json
    */
    public void switchToDefaultState(String additionalData) ; 
    
    
    /**
     * kode dari widget. ini untuk pengenal, sebelum widget di taruh di dalam root panel
     */
    public String getWidgetSecurityCode () ; 
    
    /**
     * settter id security group
     **/
    public void setWidgetSecurityGroupId(String groupId); 
    
    /**
     * getter, id security group
     **/
    public String getWidgetSecurityGroupId(); 
    
    
}
