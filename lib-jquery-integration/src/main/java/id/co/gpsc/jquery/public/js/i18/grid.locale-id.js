;(function($){
/**
 * JQ grid versi bhs indonesia
**/
$.jgrid = $.jgrid || {};
$.extend($.jgrid,{
	defaults : {
		recordtext: "Tampilkan {0} - {1} dari {2}",
		emptyrecords: "Tidak ada data",
		loadtext: "Loading...",
		pgtext : "Page {0} dari {1}"
	},
	search : {
		caption: "Mencari...",
		Find: "Cari",
		Reset: "Reset",
		odata : ['sama dengan', 'tidak sama', 'kurang', 'kurang atau sama','lebih dari','lebih dari atau sama', 'diawali dengan','tidak diawali dengan','ada dalam','tidak ada dalam','diakhiri','tidak di akhiri','ada','tidak ada'],
		groupOps: [	{ op: "AND", text: "semua" },	{ op: "OR",  text: "manapun" }	],
		matchText: " cocok",/*match*/
		rulesText: " aturan"
	},
	edit : {
		addCaption: "Tambah Data",
		editCaption: "Edit Data",
		bSubmit: "Submit",
		bCancel: "Batal",
		bClose: "Tutup",/*Close*/
		saveData: "Data berubah! Simpan Perubahan?",
		bYes : "Ya",
		bNo : "Tidak",
		bExit : "Batal",
		msg: {
			required:"Field harus di isi",
			number:"Mohon masukan angka yang benar",
			minValue:"nilai harus lebih besar atau sema dengan",
			maxValue:"nilai harus kurang dari atau sema dengan",
			email: "bukan alamat e-mail yang benar",
			integer: "Mohon masukan,bilangan bulat",
			date: "Mohon masukan tgl yang valid",
			url: "bukan URL yang benar. Harus di awali ('http://' or 'https://')",
			nodefined : " tidak di definisikan!",
			novalue : " return value diperlukan!",
			customarray : "Custom function harus return array!",
			customfcheck : "Custom function harus di buat dalam kasus custom checkng!"
			
		}
	},
	view : {
		caption: "Tampilkan data",
		bClose: "Tutup"
	},
	del : {
		caption: "Hapus",
		msg: "Hapus data yg di pilih?",
		bSubmit: "Hapus",
		bCancel: "Batal"
	},
	nav : {
		edittext: "",
		edittitle: "Edit baris di pilih",
		addtext:"",
		addtitle: "Tambah baris baru",
		deltext: "",
		deltitle: "Hapus baris yg dipilih",
		searchtext: "",
		searchtitle: "Cari data",
		refreshtext: "",
		refreshtitle: "Reload Grid",
		alertcap: "Perhatian",
		alerttext: "Mohon pilih baris terlebih dahulu",
		viewtext: "",
		viewtitle: "Tampilkan Row di pilih"
	},
	col : {
		caption: "Select columns",
		bSubmit: "Ok",
		bCancel: "Batal"
	},
	errors : {
		errcap : "Error",
		nourl : "tidak ada URL yg di set",
		norecords: "Tidak ada data untuk di proses",
		model : "Panjang  dari colNames <> colModel!"
	},
	formatter : {
		integer : {thousandsSeparator: ".", defaultValue: '0'},
		number : {decimalSeparator:",", thousandsSeparator: ".", decimalPlaces: 2, defaultValue: '0.00'},
		currency : {decimalSeparator:",", thousandsSeparator: ".", decimalPlaces: 2, prefix: "", suffix:"", defaultValue: '0.00'},
		date : {
			dayNames:   [
				"Mng", "Sen", "Sls", "Rab", "Kms", "Jmt", "Sbt",
				"Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"
			],
			monthNames: [
				"Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des",
				"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"
			],
			AmPm : ["am","pm","AM","PM"],
			S: function (j) {return j < 11 || j > 13 ? ['st', 'nd', 'rd', 'th'][Math.min((j - 1) % 10, 3)] : 'th';},
			srcformat: 'Y-m-d',
			newformat: 'j/n/Y',
			masks : {
				// see http://php.net/manual/en/function.date.php for PHP format used in jqGrid
				// and see http://docs.jquery.com/UI/Datepicker/formatDate
				// and https://github.com/jquery/globalize#dates for alternative formats used frequently
				// one can find on https://github.com/jquery/globalize/tree/master/lib/cultures many
				// information about date, time, numbers and currency formats used in different countries
				// one should just convert the information in PHP format
				ISO8601Long:"Y-m-d H:i:s",
				ISO8601Short:"Y-m-d",
				// short date:
				//    n - Numeric representation of a month, without leading zeros
				//    j - Day of the month without leading zeros
				//    Y - A full numeric representation of a year, 4 digits
				// example: 3/1/2012 which means 1 March 2012
				ShortDate: "j/n/Y", // in jQuery UI Datepicker: "M/d/yyyy"
				// long date:
				//    l - A full textual representation of the day of the week
				//    F - A full textual representation of a month
				//    d - Day of the month, 2 digits with leading zeros
				//    Y - A full numeric representation of a year, 4 digits
				LongDate: "l, F d, Y", // in jQuery UI Datepicker: "dddd, MMMM dd, yyyy"
				// long date with long time:
				//    l - A full textual representation of the day of the week
				//    F - A full textual representation of a month
				//    d - Day of the month, 2 digits with leading zeros
				//    Y - A full numeric representation of a year, 4 digits
				//    g - 12-hour format of an hour without leading zeros
				//    i - Minutes with leading zeros
				//    s - Seconds, with leading zeros
				//    A - Uppercase Ante meridiem and Post meridiem (AM or PM)
				FullDateTime: "l, F d, Y g:i:s A", // in jQuery UI Datepicker: "dddd, MMMM dd, yyyy h:mm:ss tt"
				// month day:
				//    F - A full textual representation of a month
				//    d - Day of the month, 2 digits with leading zeros
				MonthDay: "F d", // in jQuery UI Datepicker: "MMMM dd"
				// short time (without seconds)
				//    g - 12-hour format of an hour without leading zeros
				//    i - Minutes with leading zeros
				//    A - Uppercase Ante meridiem and Post meridiem (AM or PM)
				ShortTime: "g:i A", // in jQuery UI Datepicker: "h:mm tt"
				// long time (with seconds)
				//    g - 12-hour format of an hour without leading zeros
				//    i - Minutes with leading zeros
				//    s - Seconds, with leading zeros
				//    A - Uppercase Ante meridiem and Post meridiem (AM or PM)
				LongTime: "g:i:s A", // in jQuery UI Datepicker: "h:mm:ss tt"
				SortableDateTime: "Y-m-d\\TH:i:s",
				UniversalSortableDateTime: "Y-m-d H:i:sO",
				// month with year
				//    Y - A full numeric representation of a year, 4 digits
				//    F - A full textual representation of a month
				YearMonth: "F, Y" // in jQuery UI Datepicker: "MMMM, yyyy"
			},
			reformatAfterEdit : false
		},
		baseLinkUrl: '',
		showAction: '',
		target: '',
		checkbox : {disabled:true},
		idName : 'id'
	}
});
})(jQuery);
