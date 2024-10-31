package com.clouddevops.damienspetitions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PetitionController {

        @GetMapping("/")
        public String home() {
            return "home";
        }
}