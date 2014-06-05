package id.co.gpsc.jquery.client.enums;



/**
 * animation effect yang
 **/
public enum AnimationEffect {
	blind ("blind") ,
	bounce("bounce") ,
	clip("clip") ,
	drop("drop") ,
	explode("explode") ,
	fold("fold") ,
	plusate("pulsate") ,
	scale ("scale"),
	size("size") ,
	shake("shake") ,
	slide("slide"),
	transfer("transfer");
	private String internalRepresentaion ;
	private AnimationEffect(String internalRepresentaion){
		this.internalRepresentaion=internalRepresentaion;
	}
	@Override
	public String toString() {
		return internalRepresentaion;
	}
	//'blind', 'bounce', 'clip', 'drop', 'explode', 'fold', 'highlight', 'puff', 'pulsate', 'scale', 'shake', 'size', 'slide', 'transfer'.

}
