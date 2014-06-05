package id.co.gpsc.jquery.client.enums;

public enum AnimationSpeed {



	/**
	 * animasi lamban
	 **/
	slow("slow") ,
	/**
	 * speed normal
	 * !!warning.versi ini belum teruji pada date picker
	 **/
	normal("normal"),
	/**
	 * animasi cepat
	 **/
	fast("fast") ,
	/**
	 * langsung muncul
	 **/
	immediately("");
	private String internalRepresentation ;
	private AnimationSpeed(String internalRepresentation){
		this.internalRepresentation=internalRepresentation;
	}
	@Override
	public String toString() {
		return internalRepresentation;
	}

}
