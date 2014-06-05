package id.co.gpsc.common.client.control.i18;

/**
 * Data yg akan tampil pd list panel i18 Text Editor
 * @author I Gede Mahendra
 * @since Sep 18, 2012, 2:01:56 PM
 * @version $Id
 */
public class I18DataListPanel {
	
	private Integer no;
	private String key;
	private String label;
	private String locale;
	private String groupId;
	private Integer version;
	
	public I18DataListPanel(){};
	
	public I18DataListPanel(Integer no, String key, String label, String locale, String groupId, Integer version) {
		super();
		this.no = no;
		this.key = key;
		this.label = label;
		this.locale = locale;
		this.groupId = groupId;
		this.version = version;
	}
	
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}		
}