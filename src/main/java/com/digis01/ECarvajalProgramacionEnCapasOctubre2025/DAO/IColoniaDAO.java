package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;


public interface IColoniaDAO {
    Result GetByIdMunicipio(int IdMunicipio);
    Result CodigoPostalByColonia(int IdColonia);
}
