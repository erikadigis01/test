package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO;


import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.JPA.UsuarioJPA;
import org.springframework.data.jpa.repository.JpaRepository;


public interface  IUsuarioRepositoryDAO extends JpaRepository< UsuarioJPA , Integer> {
    
    UsuarioJPA findByUserName(String userName);//metodo de consulta automatica
    
}
