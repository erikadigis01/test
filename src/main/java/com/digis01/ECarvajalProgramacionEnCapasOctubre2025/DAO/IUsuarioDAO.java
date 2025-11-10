package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO;


import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Direccion;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Usuario;
import java.util.List;




public interface IUsuarioDAO {
    
    Result GetAll();
    Result GetById(int IdUsuario);
    Result Add(Usuario usuario);
    Result Update(Usuario usuario);
    Result Delete(int IdUsuario);
    Result AddDireccion(Direccion direccion, int idUsuario);
    Result UpdateDireccion(Direccion direccion, int idUsuario);
    Result DeleteDireccion(int idDireccion);
    Result GetByIdDireccion(int IdDireccion);
    Result UpdateImagen(Usuario usuario);
    Result AddAll(List<Usuario> usuarios);
    }
;