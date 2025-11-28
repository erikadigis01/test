package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Configuration;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO.UsuarioJPADAOImplementation;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    
    
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        
        String url = "";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_Alumno")) {
                
                String userName = authentication.getName(); 
                
                
                url = "/usuario/detailUserName/" + userName;
                
            } else if (grantedAuthority.getAuthority().equals("ROLE_Administrador")) {
                url =  "/usuario"; 
            }
        }

        return url; 
    }

   
}
