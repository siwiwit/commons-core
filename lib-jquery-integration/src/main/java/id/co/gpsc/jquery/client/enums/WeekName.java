package id.co.gpsc.jquery.client.enums;

/**
 * nama minggu
 **/
public enum WeekName {
	sunday(0),
	monday(1),
	tuesday(2),
	wednesday(3) ,
	thursday(4) ,
	friday(5),
	saturday(6);

	private int internalRepresentation ;

	private WeekName(int internalRepresentation){
		this.internalRepresentation=internalRepresentation;
	}

	/**
	 * internal representation dari hari
	 **/
	public int getInternalRepresentation() {
		return internalRepresentation;
	}

}
