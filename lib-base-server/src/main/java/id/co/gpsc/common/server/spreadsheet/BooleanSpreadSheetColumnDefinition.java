package id.co.gpsc.common.server.spreadsheet;

import id.co.gpsc.common.data.InvalidSpreadSheetCellFormatException;

import org.apache.poi.ss.usermodel.Cell;

public abstract class BooleanSpreadSheetColumnDefinition<DATA> extends BaseSpreadSheetColumnDefinition<DATA, Boolean> {

	@Override
	protected Boolean getValueFromCell(Cell cell) throws InvalidSpreadSheetCellFormatException {
		return cell.getBooleanCellValue();
	}
}
