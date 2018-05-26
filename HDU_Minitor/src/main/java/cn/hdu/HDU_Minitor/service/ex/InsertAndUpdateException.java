package cn.hdu.HDU_Minitor.service.ex;

public class InsertAndUpdateException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public InsertAndUpdateException() {
		super();
	}

	public InsertAndUpdateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InsertAndUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public InsertAndUpdateException(String message) {
		super(message);
	}

	public InsertAndUpdateException(Throwable cause) {
		super(cause);
	}
	
}
