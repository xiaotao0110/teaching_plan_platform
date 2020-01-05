package com.hnieu.crtvn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class TestEndPointController {

    @RequestMapping(value = "/login")
    public String index(){
        return "sys/login";
    }

    @ResponseBody
    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
        return "product id : " + id;
    }

}