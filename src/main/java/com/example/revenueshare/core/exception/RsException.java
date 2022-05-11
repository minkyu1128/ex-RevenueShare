package com.example.revenueshare.core.exception;

public class RsException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private ErrCd errCd;

    private Object data;

    public RsException(ErrCd errCd) {
        super(errCd.getCodeNm());
        this.errCd = errCd;
    }

    public RsException(ErrCd errCd, String message) {
        super(String.format("%s", message));
        this.errCd = errCd;
    }

    public RsException(ErrCd errCd, String message, Object data) {
        super(String.format("%s", message));
        this.errCd = errCd;
        this.data = data;
    }

    public RsException(ErrCd errCd, String message, Throwable cause) {
        super(String.format("%s", message), cause);
        this.errCd = errCd;
    }

    public ErrCd getErrCd() {
        return errCd;
    }

    public Object getData() { return data; }


}

