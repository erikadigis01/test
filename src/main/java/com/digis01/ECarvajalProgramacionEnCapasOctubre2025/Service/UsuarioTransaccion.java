package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Service;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO.UsuarioDAOImplementation;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioTransaccion {
    
    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    
    @Transactional(rollbackFor = Exception.class)
    public void AddUserCargaMasiva(List<Usuario> usuarios){
        
        try {
            int i = 0;
            Result result = new Result();
            do{
            
                Result resultUsuario = new Result();
//                resultUsuario = usuarioDAOImplementation.AddWithoutDireccion(usuarios.get(i));
                
                if(resultUsuario.correct == false) {
                
                    throw new RuntimeException("Hay un error esto hara el rollback.");
                    
                }
                i++;
                result = resultUsuario;
            
            } while (result.correct != false && i < usuarios.size());
            
            System.out.println("Se realizo la transaccion correctamente ");
            
        } catch (RuntimeException e) {
            
            System.out.println("Se atrapó un error: " + e.getMessage());
            throw e; 
        }
    
    }
}
