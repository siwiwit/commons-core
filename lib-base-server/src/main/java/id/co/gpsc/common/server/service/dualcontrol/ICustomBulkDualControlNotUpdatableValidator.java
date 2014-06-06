package id.co.gpsc.common.server.service.dualcontrol;

import id.co.gpsc.common.exception.DataDuplicationOnUploadedDataException;


public interface ICustomBulkDualControlNotUpdatableValidator<T> {
	
	public void validateNotUpdatableField(T dbData ,T uploadedData) throws DataDuplicationOnUploadedDataException, Exception;
	
	public Class<T> getHandledClass();
}
