package id.co.gpsc.common.server.spreadsheet;

import id.co.gpsc.common.data.InvalidSpreadSheetCellFormatException;

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;

public abstract class StringSpreadSheetColumnDefinition<DATA> extends BaseSpreadSheetColumnDefinition<DATA, String> {

	public String generateNumberWithoutZero(String data) {
		if(!data.contains(".")) return data;
		
		String[] split = data.split("[.]");
		
		Float f = Float.parseFloat("0."+split[1]);
		
		if(f > 0) {
			return data;
		} else {
			return split[0];
		}
	}
	
	@Override
	protected String getValueFromCell(Cell cell) throws InvalidSpreadSheetCellFormatException {
		String val = null;
		
		try {
			val = cell.getStringCellValue();
		} catch (Exception e) {
			try {
				Double d  = cell.getNumericCellValue() ;
				if ( d== null)
					val= null; 
				else
					val = generateNumberWithoutZero(d+"");
					
			} catch (Exception e2) {
				try {
					Date dt = cell.getDateCellValue();
					if ( dt==null)
						val = null ; 
					else
						val = dt.toString();
						
				} catch (Exception e3) {
					try {
						Boolean b = cell.getBooleanCellValue();
						if ( b== null)
							val = null;
						else
							val = b.toString();
					} catch (Exception e4) {
						return null ;
					}					
					
					
				}
			}
			
			
		}
		
		return val;
	}

}
