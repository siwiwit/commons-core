package id.co.gpsc.jquery.client.grid;


/**
 * 
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @since 09 Aug 2012
 **/
public enum GridSummaryType {
	
	count	("count") , 
	sum 	("sum") ;
	//,
	//avg 	(""),
	//none 	("");
	
	
	
	private String internalRepresentation;
	
	private GridSummaryType(String code){
		this.internalRepresentation = code;
	}
	
	@Override
	public String toString() {
		return internalRepresentation;
	}
	

}
