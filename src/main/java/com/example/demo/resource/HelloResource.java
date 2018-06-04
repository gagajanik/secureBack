package com.example.demo.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by home on 6/4/2018.
 */
@RequestMapping("/rest/hello")
@RestController
public class HelloResource {

    @GetMapping("/all")
    public String hello(){
        return "hello youtube";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/secured/all")
    public String securedHello(){
        return "secured hello";
    }
}
