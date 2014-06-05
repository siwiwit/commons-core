/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.gpsc.common.client.app;



/**
 * interface generator widget. ini untuk generate widget yang akan di taruh di atas root
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface IRootPlacedWidgetGenerator<W extends  IRootPlacedWidget>{
    
    
    
    
    /**
     * ini worker untuk instantiate widget
     */
    public abstract W instantiate() ; 
    
    
    
    /**
     * kode dari widget. ini di pakai ama menu 
     */
    public String getWidgetCode () ; 
    
    
    
    /**
     * getter, group dari generator
     **/
    public String getGroupId () ; 
    
    /**
     * getter, group dari generator
     **/
    public void setGroupId (String groupId) ;
    
    /**
     * deskripsi ringkas dari task, ini untuk kemudahan manajemen menu
     **/
    public String getBriefTaskDescription() ; 
}
