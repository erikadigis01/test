package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Repository;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.JPA.UsuarioJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioJPA, Long>{

}
