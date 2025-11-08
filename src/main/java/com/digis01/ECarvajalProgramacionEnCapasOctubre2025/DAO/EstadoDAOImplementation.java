package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Estado;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementation implements IEstadoDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetByIdPais(int IdPais) {
        return  jdbcTemplate.execute("{CALL EstadosGetByPais(?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();
            try{
            
                callableStatement.setInt(1, IdPais);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                result.objects = new ArrayList<>();
                
                while(resultSet.next()){
                
                    Estado estado = new Estado();
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre(resultSet.getString("Nombre"));
                    result.objects.add(estado);
                
                }
                result.correct = true;
            
            }catch(Exception ex){
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            
            }
            
            return result;
        });
    }

}
