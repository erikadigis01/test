package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.JPA.DireccionJPA;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.JPA.UsuarioJPA;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Colonia;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Direccion;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Estado;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Municipio;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Pais;
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
                
                if(usuarioJPA.getDireccionesJPA() == null) {
                    usuarioML.Direccion = null;
                } else {
                    
                    usuarioML.Direccion = new ArrayList<Direccion>();
                    int i = 0;
                    
                    for(DireccionJPA direccionJPA : usuarioJPA.getDireccionesJPA()){
                        
                        Direccion direccionML =  new Direccion();
                        direccionML.Colonia = new Colonia();
                        direccionML.Colonia.Municipio = new Municipio();
                        direccionML.Colonia.Municipio.Estado = new Estado();
                        direccionML.Colonia.Municipio.Estado.Pais = new Pais();
                        usuarioML.Direccion.add(direccionML);
                        usuarioML.Direccion.get(i).setIdDireccion(direccionJPA.getIdDireccion());
                        usuarioML.Direccion.get(i).setCalle(direccionJPA.getCalle());
                        usuarioML.Direccion.get(i).setNumeroInterior(direccionJPA.getNumeroInterior());
                        usuarioML.Direccion.get(i).setNumeroExterior(direccionJPA.getNumeroExterior());
                        usuarioML.Direccion.get(i).Colonia.setIdColonia(direccionJPA.getColoniaJPA().getIdColonia());
                        usuarioML.Direccion.get(i).Colonia.setNombre(direccionJPA.getColoniaJPA().getNombre());
                        usuarioML.Direccion.get(i).Colonia.setCodigoPostal(direccionJPA.getColoniaJPA().getCodigoPostal());
                        usuarioML.Direccion.get(i).Colonia.Municipio.setIdMunicipio(direccionJPA.getColoniaJPA().getMunicipioJPA().getIdMunicipio());
                        usuarioML.Direccion.get(i).Colonia.Municipio.setNombre(direccionJPA.getColoniaJPA().getMunicipioJPA().getNombre());
                        usuarioML.Direccion.get(i).Colonia.Municipio.Estado.setIdEstado(direccionJPA.getColoniaJPA().getMunicipioJPA().getEstadoJPA().getIdEstado());
                        usuarioML.Direccion.get(i).Colonia.Municipio.Estado.setNombre(direccionJPA.getColoniaJPA().getMunicipioJPA().getEstadoJPA().getNombre());
                        
                        usuarioML.Direccion.get(i).Colonia.Municipio.Estado.Pais.setIdPais(direccionJPA.getColoniaJPA().getMunicipioJPA().getEstadoJPA().getPaisJPA().getIdPais());
                        usuarioML.Direccion.get(i).Colonia.Municipio.Estado.Pais.setNombre(direccionJPA.getColoniaJPA().getMunicipioJPA().getEstadoJPA().getPaisJPA().getNombre());
                        
                        
                        
                        i++;
                        
                    }
                
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
