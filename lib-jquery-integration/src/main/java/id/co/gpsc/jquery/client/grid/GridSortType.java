package id.co.gpsc.jquery.client.grid;



/**
 * sort type. column yang di click di representasikan sebagai apa
 **/
public enum GridSortType {
	intSort("int") , 
	dateSort("date") , 
	stringSort("text") , 
	floatSort("float") ;
	
	private String internalRepresentation ; 
	private GridSortType(String rep){
		this.internalRepresentation = rep ;
	}
	
	@Override
	public String toString() {
		return internalRepresentation;
	}

}
