package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.JPA.UsuarioJPA;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Roll;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA{
    
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() {
        
        Result result = new Result();
        
        try {
            
            TypedQuery<UsuarioJPA> queryUsuario = entityManager.createQuery("FROM UsuarioJPA", UsuarioJPA.class);
            
            List<UsuarioJPA> usuariosJPA = queryUsuario.getResultList();
            
            List<Usuario> usuariosML = new ArrayList<>();
            
            result.objects = new ArrayList<>();
            
            for(UsuarioJPA usuarioJPA : usuariosJPA) {
            
                Usuario usuarioML = new Usuario();
                usuarioML.setIdUsuario(usuarioJPA.getIdUsuario());
                usuarioML.setUserName(usuarioJPA.getUserName());
                usuarioML.setNombre(usuarioJPA.getNombre());
                usuarioML.setApellidoPaterno(usuarioJPA.getApellidoPaterno());
                usuarioML.setApellidoMaterno(usuarioJPA.getApellidoMaterno());
                usuarioML.setEmail(usuarioJPA.getEmail());
                usuarioML.setPassword(usuarioJPA.getPassword());
                usuarioML.setFechaNacimiento(usuarioJPA.getFechaNacimiento());
                usuarioML.setSexo(usuarioJPA.getSexo());
                usuarioML.setTelefono(usuarioJPA.getTelefono());
                usuarioML.setCelular(usuarioJPA.getCelular());
                usuarioML.setCurp(usuarioJPA.getCurp());
                usuarioML.setImagen(usuarioJPA.getImagen());
                
                if(usuarioJPA.getRollJPA() == null) {
                    usuarioML.Roll = null;
                } else {
                    usuarioML.Roll = new Roll();
                    usuarioML.Roll.setIdRoll(usuarioJPA.getRollJPA().getIdRoll());
                }
                
                result.objects.add(usuarioML);
           
            }
            
            
            result.correct = true;
            
        } catch (Exception ex) {
        
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
        
    }

}
