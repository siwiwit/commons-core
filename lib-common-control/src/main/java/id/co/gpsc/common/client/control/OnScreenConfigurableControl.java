package id.co.gpsc.common.client.control;




/**
 * control yang bisa di configurasi di screen langsung
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public interface OnScreenConfigurableControl {
	/**
	 * enable/disable tombol untuk edit label. ini seharusnya hanya berlaku untuk admin user
	 * @param show display editor
	 **/
	public void showHideEditConfigButton(boolean show) ; 
	
	/**
	 * set ID group, ini di inject oleh form
	 **/
	public String getGroupId () ; 
	
	/**
	 * set ID group, ini di inject oleh form
	 **/
	public void setGroupId(String groupId);
	
	/**
	 * plug id configuration. ini untuk kemudahan menyimpan dalam database
	 * @param configurationId id konfigurasi
	 **/
	public void setOnScreenConfigurationId (String configurationId) ; 
	
	/**
	 * gettter, on screen configuration id
	 **/
	public String getOnScreenConfigurationId();
	
	
	
	/**
	 *  id dari form di mana dia di taruh. ini di pergunakan untuk load/save. ini di inject oleh container
	 **/
	public void setParentFormConfigurationId (String parentFormId );
	
	
	/**
	 * set id dari form di mana dia di taruh. ini di pergunakan untuk load/save. ini di inject oleh container
	 **/
	public String getParentFormConfigurationId ( );
	
	/**
	 * Get group form configuration 
	 */
	public String getGroupFormConfiguration();
	
	/**
	 * set on screen group form configuration
	 */
	public void setOnScreenGroupFormConfiguration(String groupId);
	
	/**
	 * penanda kontrol apakah boleh di konfigurasi atau tidak
	 * @param state true : boleh diconfig, false : tidak boleh
	 */
	public void setOnScreenEditable(boolean state);
	
	/**
	 * penanda kontrol apakah boleh di konfigurasi atau tidak
	 * @return state true : boleh diconfig, false : tidak boleh
	 */
	public boolean isOnScreenEditable();
	
	
	
	
	/**
	 * assign data form configuration ke dalam control
	 * @param formConfiguration data form configuration
	 **/
	public void assignConfigurationData(IFormElementConfiguration formConfiguration);
}
