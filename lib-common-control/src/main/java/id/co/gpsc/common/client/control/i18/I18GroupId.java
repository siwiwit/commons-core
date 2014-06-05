package id.co.gpsc.common.client.control.i18;

/**
 * Group ID. Ini hanya sementara saja. Klo LOV Custom sudah fix,
 * enum ini sudah tidak di pakai lagi
 * @author I Gede Mahendra
 * @since Sep 19, 2012, 9:41:25 AM
 * @version $Id
 */
public enum I18GroupId {
	
	FORM_SAMPLE1("i18TextSample1"), FORM_SAMPLE2("i18TextSample2"), FORM_SAMPLE3("i18TextSample3");
	
    private String formSample;
 
    private I18GroupId(String formSample) {
    	this.formSample = formSample;
    }

    public String getI18GroupId() {
    	return formSample;
    } 
}