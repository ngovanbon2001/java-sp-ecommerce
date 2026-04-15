package ihanoi.ihanoi_backend.exception;

public class BizException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BizException(String s) {
        super(s);
    }

	public BizException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}