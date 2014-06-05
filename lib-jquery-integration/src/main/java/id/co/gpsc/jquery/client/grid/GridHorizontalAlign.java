package id.co.gpsc.jquery.client.grid;


/**
 * horizontal align
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @since 9 August 2012
 **/
public enum GridHorizontalAlign {
	
	left ("left"), 
	right("right") , 
	center("center"); 
	
	private String internalRepresentation ; 
	private GridHorizontalAlign(String rep){
		this.internalRepresentation = rep ;
	}
	
	@Override
	public String toString() {
		return internalRepresentation;
	}


}
