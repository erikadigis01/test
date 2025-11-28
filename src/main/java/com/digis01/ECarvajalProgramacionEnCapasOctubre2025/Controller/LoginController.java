package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Controller;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String Login(){
    
        return "Login";
    
    }
    @GetMapping("/login/success")
    public String loginSuccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String url = "redirect:/login";
        
        if (authentication != null && authentication.isAuthenticated()) {
           
            if (authentication.getPrincipal() instanceof Usuario) {
                Usuario userDetails = (Usuario) authentication.getPrincipal();
                
                if("Administrador".equals(userDetails.Roll.getNombreRoll()) || "Maestro".equals(userDetails.Roll.getNombreRoll()) ) {
                
                    url = "redirect:/usuario";
                    
                } else {
                
                    url = "redirect:/usuario/detail/" + userDetails.getIdUsuario();
                }
            }
        }
        
      
        return url; 
    }
}
