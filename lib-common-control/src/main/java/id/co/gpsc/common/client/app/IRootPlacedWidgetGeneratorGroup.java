/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.gpsc.common.client.app;

/**
 * kelompok generator
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface IRootPlacedWidgetGeneratorGroup {
    
    
    
    /**
     * separator antara nama group dengan actual command
     */
    public static final String GENERATOR_GROUP_SEPARATOR ="-::-";
    
    /**
     * generators.sebaiknya di kelompokan ke dalam kelmpok nya masing-masing
     */
    public IRootPlacedWidgetGenerator<? extends  IRootPlacedWidget> getGenerator(String actualWidgetId) ; 
    
    
    
    /**
     * prefix dari kode widget. ini di pergunakan untuk enhance proses lazy load. Semua Generator, akan di dalam generator ini akan di mulai dengan prefix ini + separator
     */
    public String getWidgetCodePrefix() ; 
    
    
    
    
   
    
}
