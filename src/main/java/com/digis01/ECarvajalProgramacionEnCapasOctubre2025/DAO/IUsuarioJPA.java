package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Usuario;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;


public interface IUsuarioJPA {

    Result GetAll();
    Result Add(Usuario usuario);
    Result GetById(int Id);
    Result Update(Usuario usuario);
    Result Delete(int Id);
    
}
