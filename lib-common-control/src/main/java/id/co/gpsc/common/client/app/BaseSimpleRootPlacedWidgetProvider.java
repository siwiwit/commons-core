
package id.co.gpsc.common.client.app;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;




import id.co.gpsc.jquery.client.util.NativeJsUtilities;

import java.util.HashMap;

/**
 * base class untuk simple root placed generator
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public abstract class BaseSimpleRootPlacedWidgetProvider implements  IRootPlacedWidgetProvider{
    
    /**
     * ini generator yang sudah melewati 
     */
    private HashMap<String , IRootPlacedWidgetGeneratorGroup> indexedAlreadyCreatedGeneratorGroup  = new HashMap<String, IRootPlacedWidgetGeneratorGroup>(); 

   
    private HashMap<String , IRootPlacedWidgetGenerator<? extends IRootPlacedWidget> > indexedGenerators = new HashMap<String, IRootPlacedWidgetGenerator<? extends IRootPlacedWidget>>(); 

    @Override
    public void getGenerator(final String widgetCode,final AsyncCallback<IRootPlacedWidgetGenerator<? extends IRootPlacedWidget>> handlerOnComplete) {
       
        
        if ( widgetCode==null||!widgetCode.contains(IRootPlacedWidgetGeneratorGroup.GENERATOR_GROUP_SEPARATOR)){
        	GWT.log("widget code >>" + widgetCode +", tidak comply karena tidak di temukan " + IRootPlacedWidgetGeneratorGroup.GENERATOR_GROUP_SEPARATOR +",dalam widget code") ;
        	NativeJsUtilities.getInstance().writeToBrowserConsole("widget code >>" + widgetCode +", tidak comply karena tidak di temukan " + IRootPlacedWidgetGeneratorGroup.GENERATOR_GROUP_SEPARATOR +",dalam widget code");
        	handlerOnComplete.onFailure(new Exception("Invalid widget command passed for navigation >" + widgetCode));
        	return ; 
        }
        
        
        String arrOfCmd[] = widgetCode.split("\\" + IRootPlacedWidgetGeneratorGroup.GENERATOR_GROUP_SEPARATOR);
        final String grpId = arrOfCmd[0];
        final String actualCommand=arrOfCmd[1];
        
        
        
        if ( indexedAlreadyCreatedGeneratorGroup.containsKey(widgetCode)){
        	NativeJsUtilities.getInstance().writeToBrowserConsole("thx god, generator available untuk widget code :" + widgetCode);
        	GWT.log("thx god, generator available untuk widget code :" + widgetCode);
            actualGetGeneratorHandler(actualCommand, indexedAlreadyCreatedGeneratorGroup.get(grpId), handlerOnComplete);
        }else{
            instantiateGeneratorGroup(grpId , new AsyncCallback<IRootPlacedWidgetGeneratorGroup>() {
                @Override
                public void onFailure(Throwable caught) {
                	NativeJsUtilities.getInstance().writeToBrowserConsole("gagal mencari generator untuk command :" + widgetCode + ",error message :" + caught.getMessage());
                	GWT.log("gagal mencari generator untuk command :" + widgetCode + ",error message :" + caught.getMessage());
                   handlerOnComplete.onFailure(caught);
                }
                @Override
                public void onSuccess(IRootPlacedWidgetGeneratorGroup result) {
                	if ( result==null){
                		handlerOnComplete.onSuccess(null);
                		NativeJsUtilities.getInstance().writeToBrowserConsole("sayang, generator untuk >>" + widgetCode +",blm di definisikan");
                		GWT.log("sayang, generator untuk >>" + widgetCode +",blm di definisikan");
                		return ; 
                	}
                   indexedAlreadyCreatedGeneratorGroup.put(result.getWidgetCodePrefix(), result);
                   actualGetGeneratorHandler(actualCommand, result, handlerOnComplete);
                }
            }); 
        }
        
       
    }
    
    
    /**
     * worker untuk redirect ke caller dari generate group requestor
     * 
     */
    protected void actualGetGeneratorHandler(final String actualWidgetCode , final IRootPlacedWidgetGeneratorGroup generatorGroup , final AsyncCallback<IRootPlacedWidgetGenerator<? extends IRootPlacedWidget>> handlerOnComplete){
        handlerOnComplete.onSuccess(generatorGroup.getGenerator(actualWidgetCode));
    
    }
  
    
    
    
    /**
     * register generators ke dalam indexer.<br/>
     * 
     *  pendekatan nya di sini, anda harus menggenerate semua generator yang ada, berdasarkan ID group yang di minta<br/>
     *  Jadinya sesuai dengan parameter <i>groupId</i>, anda perlu instantiate  <i>{@link IRootPlacedWidgetGeneratorGroup}</i>yang di perlukan. sebagai catatan , <i>groupId</i> kurang lebih akan sama dengan  <i>{@link IRootPlacedWidgetGeneratorGroup#getWidgetCodePrefix()}</i> 
     *  pergunakan #registerGenerator, dan dalam GWT.runAsync.
     * 
     *  
     *  sample code :<br/> <br/> 
     * <div style="border:solid green 1px;width:600px">
     * <code>
     *if ( "MDA".equalsIgnoreCase(groupId)){<br/>
	*&nbsp;&nbsp;GWT.runAsync(new RunAsyncCallback() {<br/><br/>
	*&nbsp;&nbsp;&nbsp;
	*&nbsp;&nbsp;&nbsp;public void onSuccess() {<br/>
	*&nbsp;&nbsp;&nbsp;&nbsp;id.co.gpsc.cam.client.generator.MDADataGenerator a = new id.co.gpsc.cam.client.generator.MDADataGenerator();<br/> 
	*&nbsp;&nbsp;&nbsp;&nbsp;callback.onSuccess(a);<br/>
	*&nbsp;&nbsp;&nbsp;}<br/><br/>
	*&nbsp;&nbsp;&nbsp;
	*&nbsp;&nbsp;&nbsp;public void onFailure(Throwable exc) {<br/>
	*&nbsp;&nbsp;&nbsp;&nbsp;callback.onFailure(exc);<br/>
	*&nbsp;&nbsp;&nbsp;}<br/>
	*&nbsp;&nbsp;});<br/>
	*&nbsp;&nbsp;return ;<br/> 
	*&nbsp;}
     * 
     * </code>
     * </div>
     * pergunakan <i>full qualified class name(fqcn)</i>, untuk menghindari adanya import, sebab kalau import, ini akan menyebabkan code spliting kurang sempurna
     * @param  groupId  id group yang perlu di generate.mohon hasilnya di return dengan callback
     * @param  callback  callback untuk me return generator group
     * @see getAllAvailableGroups
     */
    protected abstract  void instantiateGeneratorGroup(String groupId , AsyncCallback<IRootPlacedWidgetGeneratorGroup> callback); 
    
    
    
    
    /**
     * membaca semua groups yang ada dalam generator ini, ini di pergunakan untuk register ke database, browse data , auomatic instantiate all etc. 
     * data ini di ambil dari <i>{@link IRootPlacedWidgetGeneratorGroup#getWidgetCodePrefix()}</i>
     * 
     **/
    protected abstract String[] getAllAvailableGroups() ; 
    /**
     * register generator ke dalam indexer, ini unuk memudahkan akses
     */
    protected void registerGenerator(IRootPlacedWidgetGenerator<? extends  IRootPlacedWidget>[] generators) {
        if ( generators==null||generators.length==0){
            return ;
        }    
        for (IRootPlacedWidgetGenerator<? extends  IRootPlacedWidget> scn : generators ){
            indexedGenerators.put(scn.getWidgetCode(), scn);
        }
    }
    
}
