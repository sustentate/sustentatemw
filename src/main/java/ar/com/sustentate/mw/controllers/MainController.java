package ar.com.sustentate.mw.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @RequestMapping("/")
    public String keepAlive() {
        return "Alive";
    }
}
