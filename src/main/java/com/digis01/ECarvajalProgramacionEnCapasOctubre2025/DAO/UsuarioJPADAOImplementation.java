package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.JPA.DireccionJPA;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.JPA.RollJPA;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.JPA.UsuarioJPA;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Colonia;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Direccion;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Estado;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Municipio;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Pais;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Roll;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Usuario;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA{
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetAll() {
        
        Result result = new Result();
        
        try {
            
            TypedQuery<UsuarioJPA> queryUsuario = entityManager.createQuery("FROM UsuarioJPA", UsuarioJPA.class);
            
            //Resultado del JPA
            List<UsuarioJPA> usuariosJPA = queryUsuario.getResultList();
            
            //en donde se debe guardar
            result.objects = new ArrayList<>();
            
            for(UsuarioJPA usuarioJPA : usuariosJPA) {
            
                Usuario usuarioML = modelMapper.map(usuarioJPA, Usuario.class);
                usuarioML.Direccion =  new ArrayList<Direccion>();
                
                for(DireccionJPA direccionJPA : usuarioJPA.getDireccion()){
                 
                    Direccion direccionML =  new Direccion();
                    
                    direccionML = modelMapper.map(direccionJPA, Direccion.class);
                    
                    usuarioML.Direccion.add(direccionML);
                 
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

    @Override
    @Transactional
    public Result Add(Usuario usuarioML) {
     Result result =  new Result();
     
     EntityTransaction entityTransaction = null;
     
     try {
         
        UsuarioJPA usuarioJPA =  new UsuarioJPA();
        
        usuarioJPA = modelMapper.map(usuarioML, UsuarioJPA.class);
        usuarioJPA.Direccion.get(0).UsuarioJPA = usuarioJPA;
        entityManager.persist(usuarioJPA);
        
        
        result.correct =  true;
         
    
          
     } catch(Exception ex) {
     
         result.correct = false;
         result.errorMessage = ex.getLocalizedMessage();
         result.ex = ex;
     
     }
     
     return result;
    }

    @Override
    public Result GetById(int Id) {
        
        Result result = new Result();
         
        try {
        
            TypedQuery<UsuarioJPA> queryUsuario = entityManager.createQuery("FROM UsuarioJPA  WHERE IdUsuario = :Id", UsuarioJPA.class);
            
            queryUsuario.setParameter("Id", Id);
            
            try {
            
                UsuarioJPA usuarioJPA = queryUsuario.getSingleResult();
                
                Usuario usuarioML = modelMapper.map(usuarioJPA, Usuario.class);
                usuarioML.Direccion =  new ArrayList<Direccion>();
                
                for(DireccionJPA direccionJPA : usuarioJPA.getDireccion()){
                 
                    Direccion direccionML =  new Direccion();
                    
                    direccionML = modelMapper.map(direccionJPA, Direccion.class);
                    
                    usuarioML.Direccion.add(direccionML);
                 
                }
                
                result.object = usuarioML;
                
                result.correct = true;
                
            
            } catch (NoResultException ex) {
            
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }
        
        } catch (Exception ex) {
        
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            
        }
        
        return result;
        
    }
    
    @Transactional
    @Override
    public Result Update(Usuario usuarioML) {
        
        Result result = new Result();
        
        int id = usuarioML.getIdUsuario();
        
        try {
            
            UsuarioJPA usuarioJPA  = entityManager.find(UsuarioJPA.class, id);
            
            if(usuarioJPA != null) {
            
                String password = usuarioJPA.getPassword();
                String imagen = usuarioJPA.getImagen();
                List<DireccionJPA> direccionesJPA = usuarioJPA.getDireccion();
                
                
                
                modelMapper.map(usuarioML, usuarioJPA);
                
                 
                //Atributos y propiedades que se preservan 
                usuarioJPA.setPassword(password);
                usuarioJPA.setImagen(imagen);
                usuarioJPA.setDireccion(direccionesJPA);
                
               
                try{
                    entityManager.merge(usuarioJPA);
                    
                    Usuario usuarioUpdate = modelMapper.map(usuarioJPA, Usuario.class);
                    
                    result.object =  usuarioUpdate;
                    result.correct = true;
                    
                } catch (PersistenceException ex) {
                    
                    result.correct = false;
                    result.errorMessage = ex.getLocalizedMessage();
                    result.ex = ex;
                
                }
                
            } else {
            
                result.correct =  false;
                result.errorMessage = "No se encontro un usuario";
            }
//                    modelMapper.map(usuarioML, UsuarioJPA.class);
            
        
        
        } catch (Exception ex) {
        
            result.correct =  false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            
        }
        
        return result;
    
    }

}
