package id.co.gpsc.common.client.form;



/**
 * penyedia value. ini untuk mempergunakan anonym class dalam constructor. karena dalam constructor, (yang di panggil bersama statement super), 
 * tidak di mungkinkan memanggil variable dalam outer class. bridge semacam ini mungkin di perlukan
 **/
public interface ValueProvider<VALUE> {

	
	public VALUE getValue(); 
}
