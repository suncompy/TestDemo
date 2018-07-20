package com.khy.jwt.vo;

import java.util.Map;


/**
 * 通用接口返回结果,泛型
 *
 * @author Vincent
 */
public class RestfulResponse<T> {
    private Boolean success;
    private String message;
    private Integer code;
    private Map<String, String> errors;
    private T data;

    public RestfulResponse() {
        this.success = true;
        this.code = 200;
        this.message = "success";
    }

    public RestfulResponse(T data) {
        this.success = true;
        this.code = 200;
        this.message = "success";
        this.data = data;
    }


    public RestfulResponse(Boolean success, String message, Integer code, T data) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
