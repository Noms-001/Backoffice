package com.example.backoffice.controller;

import com.example.backoffice.service.DbService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final DbService dbService;

    public HomeController(DbService dbService) {
        this.dbService = dbService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("dbStatus", dbService.testConnection());
        return "index";
    }
}