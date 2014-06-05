/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.gpsc.common.client.app;

import java.util.HashMap;

/**
 * base class untuk generator group
 *  @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public abstract class BaseRootPlacedWidgetGeneratorGroup implements  IRootPlacedWidgetGeneratorGroup{

    
    
    private HashMap<String ,IRootPlacedWidgetGenerator<? extends IRootPlacedWidget>> indexedGenerators = new HashMap<String, IRootPlacedWidgetGenerator<? extends IRootPlacedWidget>>(); 
    
    
    public BaseRootPlacedWidgetGeneratorGroup() {
        PlainRootPlacedWidgetGeneratorGroup[] arr = getGeneratorGroups(); 
        if ( arr!=null){
            for ( PlainRootPlacedWidgetGeneratorGroup scn : arr){
            	
                IRootPlacedWidgetGenerator<? extends IRootPlacedWidget>[] generators = scn.getGenerators();
                if ( generators==null){
                    continue ;
                }
                for (IRootPlacedWidgetGenerator<? extends IRootPlacedWidget> actualGenerator : generators ){
                      indexedGenerators.put(actualGenerator.getWidgetCode(), actualGenerator);
                      actualGenerator.setGroupId(getWidgetCodePrefix());
                }    
              
            }
        }
    }

    
    
    /**
     * generators yang ada dalam 1 group
     */
    protected abstract PlainRootPlacedWidgetGeneratorGroup[] getGeneratorGroups() ; 
    
    @Override
    public IRootPlacedWidgetGenerator<? extends IRootPlacedWidget> getGenerator(String actualWidgetId) {
        if ( !indexedGenerators.containsKey(actualWidgetId))
        	return null ; 
        return indexedGenerators.get(actualWidgetId);
    }

   
    
    
    
}
