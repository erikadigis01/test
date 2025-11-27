package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String Login(){
    
        return "Login";
    
    }
    
}
