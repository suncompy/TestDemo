package com.khy.auth2server.vo;

/**
 * 响应
 * @param <T>
 */
public class SimpleResponse<T> {

    private Integer code;
    private String msg;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

    public SimpleResponse success(Object object) {
        SimpleResponse result = new SimpleResponse();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public SimpleResponse success() {
        return success(null);
    }

    public static SimpleResponse error(Integer code, String msg) {
        SimpleResponse result = new SimpleResponse();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
