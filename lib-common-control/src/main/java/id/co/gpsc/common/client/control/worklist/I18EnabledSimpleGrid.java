package id.co.gpsc.common.client.control.worklist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

import id.co.gpsc.common.client.form.ResourceBundleEnableContainer;
import id.co.gpsc.common.control.ResourceBundleConfigurableControl;


/**
 * Grid Simple dengan i18 enabled support
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 28-aug-2012
 **/
public abstract class I18EnabledSimpleGrid<DATA> extends PagedSimpleGridPanel<DATA>{
	
	
	/**
	 * rposes scan di batasi berapa level. karena ndak mungkin scan infinite
	 **/
	protected static int MAX_PARENT_SCAN_DEPTH=1000;
	/**
	 * container i18 dari grid. di scan pada saat attached
	 */
	private ResourceBundleEnableContainer resourceBundleEnableContainer ; 
	
	/**
	 * definisi internalization configuration untuk columns. Ini berpasangan dengan column yang di definisikan untuk {@link #getColumnDefinitions()}. <br/>
	 * Column-column yang configurable(via form configuration), musti di daftarkan dalam variable ini
	 **/
	public abstract I18ColumnDefinition<DATA>[] getI18ColumnDefinitions();
	
	
	
	public I18EnabledSimpleGrid(){
		super(); 
	}
	
	public I18EnabledSimpleGrid(boolean multiple){
		super(multiple); 
	}
	
	
	public I18EnabledSimpleGrid(boolean multiple, boolean attachOnLoad){
		super(multiple,attachOnLoad); 
	}

	@Override
	protected void renderJQWidgetOnAttachWorker() {
		
		super.renderJQWidgetOnAttachWorker();
		
			ResourceBundleConfigurableControl[] defs= getI18ColumnDefinitions();
			if (defs==null||defs.length==0 )
				return ;
			int currentDepth =0;
			Widget w=this ;
			do{
				w=w.getParent();
				if ( w instanceof ResourceBundleEnableContainer){
					this.resourceBundleEnableContainer= (ResourceBundleEnableContainer)w;
					this.resourceBundleEnableContainer.registerResourceBundleEnabledNodes(defs);
					return ;
				}
				currentDepth++;
				if(currentDepth>=MAX_PARENT_SCAN_DEPTH){
					GWT.log("giving up scan for " + ResourceBundleEnableContainer.class.getName() + ",stlh " + currentDepth);
					return ;
				}
			}while(w!=null);
		
	}
	
	@Override
	protected void onDetach() {
		if ( this.resourceBundleEnableContainer!=null){
			ResourceBundleConfigurableControl[] defs= getI18ColumnDefinitions();
			this.resourceBundleEnableContainer=null ;
			if ( defs!=null&&defs.length>0){
				for (ResourceBundleConfigurableControl scn : defs ){
					resourceBundleEnableContainer.unregisterResourceBundleEnabledNode(scn);
				}
			}
		}
		super.onDetach();
	}

}
