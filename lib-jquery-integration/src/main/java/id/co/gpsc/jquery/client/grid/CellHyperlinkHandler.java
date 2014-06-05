package id.co.gpsc.jquery.client.grid;



/**
 * celll button berupa hyperlink
 **/
public abstract class CellHyperlinkHandler<DATA> extends CellGenericHandler<DATA>{
	
	
	
	public CellHyperlinkHandler(){
		super(); 
	}
	
	 

	@Override
	public String generateHTMLNode(DATA data,
			String clickHandlerMethodNameAlsoWithParam) {
		String css = getCSSForLink(data) ;
		if ( css==null||css.length()==0){
			css = "" ; 
		}else{
			css = "class='" + css + "'";
		}
		if ( clickHandlerMethodNameAlsoWithParam.indexOf("\"")!= -1){
			clickHandlerMethodNameAlsoWithParam = clickHandlerMethodNameAlsoWithParam.replaceAll("\"", "'"); 
		}
		return "<a " +css+" href=\"javascript:" + clickHandlerMethodNameAlsoWithParam + "\">"+generateHyperlinkLabel(data)+"</a>";
	}
	
	
	
	
	/**
	 * get label unutk hyperlink
	 **/
	protected abstract String generateHyperlinkLabel (DATA data);  
	
	
	/**
	 * css untuk label
	 **/
	protected String getCSSForLink (DATA data) {
		return "" ;
	}

}
