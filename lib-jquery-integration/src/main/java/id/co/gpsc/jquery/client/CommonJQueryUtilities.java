package id.co.gpsc.jquery.client;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public final  class CommonJQueryUtilities {

	private static CommonJQueryUtilities instance;


	/**
	 * singleton instance
	 **/
	public static CommonJQueryUtilities getInstance() {
		if (instance==null)
			instance=new CommonJQueryUtilities();

		return instance;
	}

	private final DateTimeFormat isoDateFormatter;

	private final DateTimeFormat yearFormatter;

	private final DateTimeFormat monthFormatter;



	private final DateTimeFormat dateFormatter;




	/**
	 * jquery dalam kondisi yang standar, cenderung terlalu lebar. jadinya kalau tidak di define di level css, perlu level coding untuk mersize widget.
	 * field ini akan menentukan resize atau tidak widget setelah render. set variable ini sebaiknya pada saat on load
	 **/
	private boolean resizeJqueryWidgetProgrammatically = false ;




	/**
	 * gunakan legacy view. ini untuk masa transisi
	 **/
	private boolean useLegacyView = true ;




	/**
	 * kalau ini true. maka dialog akan di set 100 % untuk size nya
	 **/
	private boolean maximizeDialogPanelInnerPanel = false ;




	/**
	 * berapa persen size resize
	 **/
	private float widgetResizePercentage = 62.5F;







	/**
	 * dalam beberapa kasus widget yang di resize container luarnya, container dalamnya perlu di expand malah.
	 * misalnya
	 **/
	private final float widgetReverseSizePercentage = 110F;



	private CommonJQueryUtilities(){
		isoDateFormatter=DateTimeFormat.getFormat("yyyy-MM-dd");
		yearFormatter=DateTimeFormat.getFormat("yyyy");
		dateFormatter=DateTimeFormat.getFormat("dd");
		monthFormatter=DateTimeFormat.getFormat("MM");
	}
	/**
	 * month formatter. berbasis 1. januari = 1 dst
	 **/
	public int getDate(Date theDate){
		return Integer.parseInt(dateFormatter.format(theDate));
	}

	/**
	 * full year. 4 digit
	 **/
	public int getFullYear(Date theDate){
		return Integer.parseInt(yearFormatter.format(theDate));
	}



	/**
	 * iso date formatter
	 **/
	public DateTimeFormat getIsoDateFormatter() {
		return isoDateFormatter;
	}

	/**
	 * month formatter. berbasis 1. januari = 1 dst
	 **/
	public int getMonthBased1Index(Date theDate){
		return Integer.parseInt(monthFormatter.format(theDate));
	}


	/**
	 * berapa persen size resize
	 **/
	public float getWidgetResizePercentage() {
		return widgetResizePercentage;
	}

	/**
	 * dalam beberapa kasus widget yang di resize container luarnya, container dalamnya perlu di expand malah.
	 * misalnya pada dialog, dialod di resize 62.5 pct, content di di dalamnya perlu di resize menjadi 110% misalnya
	 **/
	public float getWidgetReverseSizePercentage() {
		return widgetReverseSizePercentage;
	}


	/**
	 * kalau ini true. maka dialog akan di set 100 % untuk size nya
	 **/
	public boolean isMaximizeDialogPanelInnerPanel() {
		return maximizeDialogPanelInnerPanel;
	}

	/**
	 * jquery dalam kondisi yang standar, cenderung terlalu lebar. jadinya kalau tidak di define di level css, perlu level coding untuk mersize widget.
	 * field ini akan menentukan resize atau tidak widget setelah render. set variable ini sebaiknya pada saat on load
	 **/
	public boolean isResizeJqueryWidgetProgrammatically() {
		return resizeJqueryWidgetProgrammatically;
	}


	/**
	 * gunakan legacy view. ini untuk masa transisi
	 **/
	public boolean isUseLegacyView() {
		return useLegacyView;
	}


	/**
	 * kalau ini true. maka dialog akan di set 100 % untuk size nya
	 **/
	public void setMaximizeDialogPanelInnerPanel(
			boolean maximizeDialogPanelInnerPanel) {
		this.maximizeDialogPanelInnerPanel = maximizeDialogPanelInnerPanel;
	}
	/**
	 * jquery dalam kondisi yang standar, cenderung terlalu lebar. jadinya kalau tidak di define di level css, perlu level coding untuk mersize widget.
	 * field ini akan menentukan resize atau tidak widget setelah render. set variable ini sebaiknya pada saat on load
	 **/
	public void setResizeJqueryWidgetProgrammatically(
			boolean resizeJqueryWidgetProgrammatically) {
		this.resizeJqueryWidgetProgrammatically = resizeJqueryWidgetProgrammatically;
	}


	/**
	 * gunakan legacy view. ini untuk masa transisi
	 **/
	public void setUseLegacyView(boolean useLogecyView) {
		this.useLegacyView = useLogecyView;
	}


	/**
	 * berapa persen size resize. default value 62.5. sebaiknya ini di set pada saat on load
	 **/
	public void setWidgetResizePercentage(float widgetResizePercentage) {
		this.widgetResizePercentage = widgetResizePercentage;
	}

}
