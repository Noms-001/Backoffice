package com.example.backoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/resident")
public class ResidentController {
    
    @GetMapping
    public ModelAndView showCarteAndVisa() {
        ModelAndView modelAndView = new ModelAndView("resident/piece");
        return modelAndView;
    }
}
