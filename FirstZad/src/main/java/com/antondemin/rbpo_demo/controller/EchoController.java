package com.antondemin.rbpo_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

    @GetMapping("/api/echo")
    public String echo(@RequestParam(defaultValue = "world") String q) {
        return "Echo: " + q;
    }
}
