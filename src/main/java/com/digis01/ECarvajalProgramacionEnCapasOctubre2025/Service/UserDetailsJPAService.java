package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Service;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO.IUsuarioRepositoryDAO;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.JPA.UsuarioJPA;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsJPAService  implements UserDetailsService{
    
    private final IUsuarioRepositoryDAO iUsusuarioRepositoryDAO;
    
    public UserDetailsJPAService (IUsuarioRepositoryDAO iUsusuarioRepositoryDAO){
        this.iUsusuarioRepositoryDAO = iUsusuarioRepositoryDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        UsuarioJPA usuario = iUsusuarioRepositoryDAO.findByUserName(username);
        
         if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        
        return User.withUsername(usuario.getUserName())
                .password(usuario.getPassword())
                .roles(usuario.Roll.getNombreRoll())
                .build();
        
    }

}
