package id.co.gpsc.common.server.rpc;

import javax.annotation.PostConstruct;

//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Service;


//@Service
//@Lazy(value=false)
public class CommonServerHintBean {

	
	@PostConstruct
	public void postConstruct(){
		System.out.println("Welcome developers, ");
		System.out.println("=======================================================================");
		System.out.println("Anda mempergunakan common server. dalam aplikasi anda. ada beberapa konfigurasi yang anda perlu recheck agar library di sini bisa berjalan sesuai spesifikasi");
		System.out.println("1. library ini memerlukan servlet dengan class : org.springframework.web.servlet.DispatcherServlet. container yang belum support servlet 3.0 specs. Library ini tidak menyertakan servlet dengan tipe ini yang driven dengan annotation" +
				"\njadinya pilihan yang tersedia adalah mendaftarkan manual dalam web.xml anda. contohnya kurang lebih spt ini : \n" +
				"\n<servlet>\n"+
				"\t<description>GWT RPC Dispatch. agar tidak register manual servlet RPC</description>\n"+
				"\t<servlet-name>app-rpc-dispatch</servlet-name>\n"+
				"\t<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>\n"+
				"\t<init-param>\n"+
				"\t\t<param-name>load-on-startup</param-name>\n"+
				"\t\t<param-value>1</param-value>\n"+
				"\t</init-param>\n"+
				"</servlet>\n");
		System.out.println("2. servlet ini minimal musti handle url dengan *.app-rpc. RPC yang sudah predefined di definisikan dengan RPC url. Di sarankan anda juga mempergunakan ini untuk RPC anda");
		System.out.println("3. karena diperlukan org.springframework.web.servlet.DispatcherServlet, anda juga memerlukan spring web-mvc file(sesuai dengan nama servlet anda dalam sample di atas namanya app-rpc-dispatch)--(ini bisa hanya file kosong) dengan nama file: nama_servlet_anda-servlet.xml(contoh saya di sini nama file nya menjadi : app-rpc-dispatch-servlet.xml) ");
		System.out.println("4. anda wajib juga menyediakan implementasi direct to table LOV reader(service class dengan implement interface id.co.gpsc.common.server.dao.DirectTableLookupProviderDao )\nId dari dao bean harus : common-lov.directTableProvider ");
		System.out.println("mohon di recek kembali ke aplikasi anda kala ada sesuatu yang tidak berjalan sesuai dengan rencana");
	}
}
