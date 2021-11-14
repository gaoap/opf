package com.gaoap.opf.upm.common.exception;


import com.gaoap.opf.upm.common.http.IErrorCode;

/**
 * 自定义http异常
 */
public class HttpException extends RuntimeException {
    private IErrorCode errorCode;

    public HttpException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
