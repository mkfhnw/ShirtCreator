package com.example.shirtcreator.ShirtCreator.Presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PresentationController {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

}
