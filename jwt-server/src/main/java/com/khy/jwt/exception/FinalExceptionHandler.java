/*
package com.khy.jwt.exception;

import com.alibaba.fastjson.JSON;
import com.khy.jwt.vo.RestfulResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RestController;

*/
/**
 * 请求刚到达时发生的异常，譬如请求一个不存在的地址
 *//*

@RestController
public class FinalExceptionHandler implements ErrorController {
    @Override
    @SuppressWarnings("unchecked")
    public String getErrorPath() {
        return JSON.toJSONString(new RestfulResponse(false, "改地址不存在", 404, null));
    }
}
*/
