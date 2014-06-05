/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.gpsc.common.client.app;

/**
 *
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public abstract class PlainRootPlacedWidgetGeneratorGroup {
    
     /**
     * generators yang ada dalam 1 group
     */
    protected abstract IRootPlacedWidgetGenerator<? extends IRootPlacedWidget>[] getGenerators() ; 
    
}
