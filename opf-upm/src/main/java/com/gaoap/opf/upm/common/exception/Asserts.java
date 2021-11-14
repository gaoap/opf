package com.gaoap.opf.upm.common.exception;


import com.gaoap.opf.upm.common.http.IErrorCode;

/**
 * 用于抛出和http异常
 */
public class Asserts {
    public static void fail(String message) {
        throw new HttpException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new HttpException(errorCode);
    }
}
