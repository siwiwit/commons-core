package id.co.gpsc.common.client.cache;

import java.util.Collection;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;



/**
 * backend cache dengan menaruh hidden pada root elemen. ini di pergunakan kalau session storage tidak di kenali
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class HiddenElementBackedCacheWrapper implements BaseActualClientCacheWorker{
	
	
	
	/**
	 * prefix tambahan untuk data cache. ini menghindar kemunkinan bentrok dengan data existing
	 **/
	protected static final String HIDDEN_ID_PREFIX ="session_storage_emulation_hidden________________________________________________gps_rock__";

	@Override
	public void submitToCache(String key, String dataToCache) {
		String domId = HIDDEN_ID_PREFIX + key ; 
		Element e =  DOM.getElementById(domId);
		if ( e==null){
			e  = DOM.createInputText(); 
			e.setPropertyString("type", "hidden");
			RootPanel.get().getElement().appendChild(e);
		}
		e.setPropertyString("value", dataToCache);
		
	}

	@Override
	public String get(String key) {
		String domId = HIDDEN_ID_PREFIX + key ; 
		Element e =  DOM.getElementById(domId);
		if (e==null)
			return null; 
		return e.getPropertyString("value");
	}

	@Override
	public void remove(String key) {
		String domId = HIDDEN_ID_PREFIX + key ; 
		Element e =  DOM.getElementById(domId);
		if (e==null)
			return ; 
		e.removeFromParent();
		
	}

	@Override
	public void removes(Collection<String> keys) {
		if ( keys==null||keys.isEmpty())
			return ; 
		for ( String key : keys){
			remove(key);
		}
		
	}

}
