package id.co.gpsc.common.client.control;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;



/**
 * group of {@link ChildSelfRegisterWorker}. ini scanner ke parent + distribusikan ke actual worker
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id 
 **/
public class ChildSelfRegisterAggregator<CHILD extends Widget> {
	
	
	/**
	 * maksimal berapa level scan ke parent
	 **/
	public static int MAX_SCAN_PARENT_DEPTH =100;
	
	private ChildSelfRegisterWorker<CHILD, ?>[] aggregators;
	
	private CHILD widget; 
	
	
	public ChildSelfRegisterAggregator(ChildSelfRegisterWorker<CHILD, ?>[] aggregators , CHILD widget){
		this.widget = widget ; 
		this.aggregators=aggregators;
		for ( ChildSelfRegisterWorker<CHILD, ?> scn : this.aggregators){
			if ( scn.computeIsReadyToRegisterWorker(widget)){
				
			}
			scn.setWidgetToRegister(this.widget);
		}
		
	}
	
	
	
	
	/**
	 * scan parent node. method ini mencari parent nya(dengan getParent() ). Mohon di catat method ini hanya menscan sedalam {@link #MAX_SCAN_PARENT_DEPTH}(di default : ) {@value #MAX_SCAN_PARENT_DEPTH}
	 **/
	public void registerToParent () {
		Widget cur= this.widget.getParent() ; 
		int matchCount =0 ; 
		int currentDepth = 1 ; 
		
		int readyCount = 0; 
		for (ChildSelfRegisterWorker<CHILD, ?> scn : this.aggregators){
			readyCount+=scn.computeIsReadyToRegister()?1:0;
		}	
		if ( readyCount==0){
			if (! GWT.isProdMode())
				GWT.log("none ready to register, skip proses auto register");
			return  ; 
		}
		while(cur!=null){
			for (ChildSelfRegisterWorker<CHILD, ?> scn : this.aggregators){
				if ( scn.isRegistered()||!scn.isReadyToRegister())
					continue ; 
				boolean hasil = scn.attempRegister(cur);
				if ( hasil){
					matchCount+=hasil?1:0;
				}
			}
			
			cur = cur.getParent();
			if(currentDepth>=MAX_SCAN_PARENT_DEPTH||matchCount==readyCount){
				break ; 
			}
			currentDepth++;
			
		}
		
	}
	
	public void unregisterFromParent (){
		if ( aggregators==null||aggregators.length==0)
			return ;
		for ( ChildSelfRegisterWorker<CHILD, ?> scn : this.aggregators){
			try {
				scn.detachFromParent();
			} catch (Exception e) {
				GWT.log("gagal detach node dari parent.error message :" + e.getMessage() , e);
			}
			 
		}
	}
	
	
	

}
