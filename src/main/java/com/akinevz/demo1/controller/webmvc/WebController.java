package com.akinevz.demo1.controller.webmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class WebController {
    @RequestMapping(value = "")
    public String index() {
        return "index";
    }
}
