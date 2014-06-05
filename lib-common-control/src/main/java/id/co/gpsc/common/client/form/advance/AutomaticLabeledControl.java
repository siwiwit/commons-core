package id.co.gpsc.common.client.form.advance;

public interface AutomaticLabeledControl {
	
	
	
	/**
	 * add css ke dalam label
	 * @param cssName nama css
	 **/
	public void addCssToLabel (String cssName) ; 
		
	/**
	 * remove css dari label
	 **/
	
	public void removeCssFromLabel (String cssName) ;
	
	/**
	 * css untuk :(kalau ada)
	 **/
	public void addCssToPoint (String cssName) ;
	
	/**
	 * remove css klo ada
	 * @param cssName
	 */
	public void removeCssFromPoint (String cssName) ;
	
	/**
	 * Get CSS label
	 * @return cssName
	 */
	public String getCssToLabel();
	
	/**
	 * Set CSS label
	 * @param cssName
	 */
	public void setCssToLabel(String cssName);
	
	/**
	 * Get CSS Point (titik dua)
	 * @return cssName
	 */
	public String getCssToPoint();
	
	/**
	 * Set CSS Point (titik dua)
	 * @param cssName
	 */
	public void setCssToPoint(String cssName);
}
