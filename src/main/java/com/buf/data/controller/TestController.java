package com.buf.data.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mawenguang on 2019/5/13.
 */
@RestController
@RequestMapping(value = "/test")
public class TestController
{

    @RequestMapping(value = "/helloWorld")
    public String Test(){

        return "sadd阿斯顿";
    }

}
