package com.gaoap.opf.common.core.http;

/**
 * HTTP结果封装
 */
public class HttpResult<T> {

    private int code = HttpStatus.OK.value();
    private String msg;
    private T data;

    public static HttpResult error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
    }

    public static HttpResult error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    /**
     * 未登录返回结果
     */
    public static <T> HttpResult<T> unauthorized(T data) {
        HttpResult r = new HttpResult();
        r.setCode(ResultCode.UNAUTHORIZED.getCode());
        r.setMsg(ResultCode.UNAUTHORIZED.getMessage());
        r.data = data;
        return r;
    }

    /**
     * 未授权返回结果
     */
    public static <T> HttpResult<T> forbidden(T data) {
        HttpResult r = new HttpResult();
        r.setCode(ResultCode.FORBIDDEN.getCode());
        r.setMsg(ResultCode.FORBIDDEN.getMessage());
        r.data = data;
        return r;
    }

    public static HttpResult error(int code, String msg) {
        HttpResult r = new HttpResult();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static HttpResult ok(String msg) {
        HttpResult r = new HttpResult();
        r.setMsg(msg);
        return r;
    }

    public static <T> HttpResult<T> ok(T data) {
        HttpResult r = new HttpResult();
        r.setData(data);
        return r;
    }

    public static HttpResult ok() {
        return new HttpResult();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
