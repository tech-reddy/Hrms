package com.reddy.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class SecurityController {
    @GetMapping
    public String hello(HttpServletRequest req) {
        return "Hello World!" + req.getSession().getId();
    }

    @GetMapping("csrf")
    public CsrfToken csrfToken(HttpServletRequest req) {
        return (CsrfToken) req.getAttribute("_csrf");
    }
}
