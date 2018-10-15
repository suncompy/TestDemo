package com.example.khy.demo.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 测试返回时响应的http状态
 */
@RestController
public class TestResponseStatusControler {

    /**
     * @ResponseStatus 是标记一个方法或异常类在返回时响应的http状态
     * @ResponseStatus 可以结合 @ResponseBody 一起使用。
     *
     * immutable Objects就是那些一旦被创建，它们的状态就不能被改变的Objects，每次对他们的改变都是产生了新的immutable的对象，
     * 而mutable Objects就是那些创建后，状态可以被改变的Objects.、
     *
     * 举个例子：String和StringBuilder，String是immutable的，每次对于String对象的修改都将产生一个新的String对象，
     * 而原来的对象保持不变，而StringBuilder是mutable，因为每次对于它的对象的修改都作用于该对象本身，并没有产生新的对象。
     * @return
     */
    @RequestMapping("/testResponseBodyFaild")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public Map<String, String> testResponseBodyFaild(){
        return ImmutableMap.of("key", "faild");
    }

    @RequestMapping("/testResponseBody")
    public Map<String, String> testResponseBody(){
        return ImmutableMap.of("key", "value");
    }

    /**
     * 响应http status code(http状态码) 是200
     * @return
     */
    @RequestMapping("/testResponseEntity")
    public ResponseEntity<Map<String, String>> testResponseEntity(){
        Map<String, String> map = ImmutableMap.of("key", "value");
        return ResponseEntity.ok(map);
    }

    /**
     * ResponseEntity 和 @ResponseStatus 一起使用是无效的
     * 响应http status code(http状态码) 是200
     * @return
     */
    @RequestMapping("/testResponseEntityFaild")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ResponseEntity<Map<String, String>> testResponseEntityFaild(){
        Map<String, String> map = ImmutableMap.of("key", "faild");
        return ResponseEntity.ok(map);
    }

    /**
     * 响应http status code(http状态码) 是400
     * @return
     */
    @RequestMapping("/testResponseEntityFaild2")
    public ResponseEntity<Map<String, String>> testResponseEntityFaild2(){
        return ResponseEntity.badRequest().body(ImmutableMap.of("key", "faild"));
    }

    /**
     * 响应http status code(http状态码) 是405
     * @return
     */
    @RequestMapping("/testResponseEntityFaild3")
    public ResponseEntity testResponseEntityFaild3(){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    /**
     * 该方法是错误的操作，将会报服务器异常500
     * @return
     */
    @RequestMapping("/testResponseEntityFaild4")
    public ResponseEntity.BodyBuilder testResponseEntityFaild4(){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE);
    }
}
