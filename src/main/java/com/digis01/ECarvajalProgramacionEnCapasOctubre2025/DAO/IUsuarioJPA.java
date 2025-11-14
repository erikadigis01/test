package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Direccion;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Usuario;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;


public interface IUsuarioJPA {

    Result GetAll();
    Result Add(Usuario usuario);
    Result GetById(int Id);
    Result Update(Usuario usuario);
    Result Delete(int Id);
    Result UpdateImagenUsuario(Usuario usuario);
    Result AddDireccion(Direccion direccion, int idusuario);
    Result UpdateDireccion(Direccion direccion, int idUsuario);
    Result GetByIdDireccion(int IdDireccion);
    Result DeleteDireccion(int IdDireccion);
    
}
