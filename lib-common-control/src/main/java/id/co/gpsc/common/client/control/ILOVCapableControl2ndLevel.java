package id.co.gpsc.common.client.control;

import com.google.gwt.user.client.Command;

import id.co.gpsc.common.data.lov.Common2ndLevelLOVHeader;
import id.co.gpsc.common.data.lov.StrongTyped2ndLevelLOVID;

/**
 * interface control yang LOV enabled. versi ini yang 2nd level. jadinya ada parent nya.Lookup di request berdasarkan value dari parent
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public interface ILOVCapableControl2ndLevel {
	
	
	/**
	 * render data lookup
	 **/
	public void renderLookupData (Common2ndLevelLOVHeader lookupData); 
	
	/**
	 * parent control, 
	 * kepada yang mana control ini bergantung. pekerjaan di sini : 
	 * <ol>
	 * <li>bind ke event change listener</li>
	 * <li>proses otomatis request for LOV update kalau misal nya ada perubahan pada parent</li>
	 * </ol>
	 **/
	public void assignParentControl (IParentLOVEnableControl parentControl) ; 
	
	
	/**
	 * set value. versi ini dengan asumsi data parent di ketahui. jadinya lebih sederhana, request data LOV (ntah cache atau RPC) dengan berdasar pada parent id, dan set control pada posisi selected sesuai data 
	 * @param myValue nilai untuk control
	 * @param parentValue nilai untuk parent. ini untuk meload list data
	 * @param afterSetValueDone method ini akan di trigger kalau set value selesai di lakukan. jangan lupa, proses di sini bisa asyn, jadinya anda harus sadar, kalau dat alookup bisa jadi belum selesai di load. 
	 * Kalau ada pekerjaan berantai setelah set value, anda wajib mentrigger setelah  proses LOB selesai di laksanakan
	 **/
	public void setValue(String myValue , String parentValue, Command afterSetValueDone);
	
	
	
	
	/**
	 * Set value ke dalam kontrol. ini pekerjaan nya lebih ribet. karena perlu memeriksa dulu, parent nya ada di mana. langkah nya kira-kira spt ini : <ol>
	 * <li>Cari dulu parent node</li>
	 * <li>Cari array data, sesuai parent node yang di dapat pada step berikutnya</li>
	 * <li>Load lookup ke dalam control</li>
	 * </ol>
	 * @param lookupValue value dari lookup
	 * @param afterSetValueDone task setelah render selesai di lakukan
	 **/
	public void setValue(String lookupValue, Command afterSetValueDone) ; 
	/**
	 * set LOV Id dari control
	 **/
	public void setLovParameterId(StrongTyped2ndLevelLOVID lovId);
	
	
	/**
	 * nilai pada arent Lookup
	 **/
	public String getParentLookupValue(); 
	
	
	/**
	 * getter, Parameter ID
	 **/
	public StrongTyped2ndLevelLOVID getLovParameterId(); 

}
