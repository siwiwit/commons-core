package id.co.gpsc.common.client.widget;

public interface IEditorPanel<DATA> {

	/**
	 * Append object baru(ke memory dulu. ke database kalau request ke server sudah di panggil)<br/>
	 * add data dan render ke dalam kontrol
	 * @param data base object untuk di edit. data yang di perlukan di isikan oleh parent(misal : kode unit dll)
	 *  
	 **/
	public abstract void addAndEditNewData(DATA data);

	/**
	 * switch editor ke dalam edit exiting mode. render data ke dalam kontrol dan siap-siap issue update stetement kalau misalnya di perintahkan save
	 * @param data data yang di edit
	 **/
	public abstract void editExistingData(DATA data);
	
	
	
	/**
	 * menampilkan data dalam posisi read only
	 **/
	public abstract void viewDataAsReadOnly(DATA data);
	

}