package cn.hdu.HDU_Minitor.service.ex;

public class BindException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BindException() {
	}

	public BindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BindException(String message, Throwable cause) {
		super(message, cause);
	}

	public BindException(String message) {
		super(message);
	}

	public BindException(Throwable cause) {
		super(cause);
	}

}
