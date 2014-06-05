package id.co.sigma.common.server.service.dualcontrol;

import id.co.sigma.common.exception.DataDuplicationOnUploadedDataException;


public interface ICustomBulkDualControlNotUpdatableValidator<T> {
	
	public void validateNotUpdatableField(T dbData ,T uploadedData) throws DataDuplicationOnUploadedDataException, Exception;
	
	public Class<T> getHandledClass();
}
