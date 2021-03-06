package com.example.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.*;

@Controller
@RequestMapping("/")
public class BaseController {
    @GetMapping("/")
    public ModelAndView redirectToSwaggerUi(ModelMap model) {
        return new ModelAndView("redirect:/swagger-ui.html", model);
    }
}