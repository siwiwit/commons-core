package id.co.gpsc.jquery.client.form;

public abstract class LOVExtractor<POJO> {
	/**
	 * mengambil label
	 * @param data data yang akan di extract
	 **/
	public abstract String getLabel (POJO data); 
	/**
	 * membaca description dari POJO
	 **/
	public abstract String getDescription (POJO data) ; 
	
	/**
	 * value textbox
	 **/
	public abstract String getValue (POJO data) ; 
	
	/**
	 * generate HTML node untuk auto complete. Override ini kalaau anda mengiginkan custom label untuk auto complete
	 **/
	public String generateHTMLStringForAutocompleteNode (POJO data) {
		 return "<a>" + getLabel(data)   + "<br>" + getDescription(data)  + "</a>";
	}

}
