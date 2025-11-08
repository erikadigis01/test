package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Roll;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RollDAOImplementation implements IRollDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = jdbcTemplate.execute("{CALL RollGetAll(?)}", (CallableStatementCallback<Result>) callableStatement ->{
            Result resultSP = new Result();
            try{
                
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                resultSP.objects = new ArrayList<>();
                
                while(resultSet.next()){
                
                    Roll roll = new Roll();
                    
                    roll.setIdRoll(resultSet.getInt("IdRoll"));
                    roll.setNombreRoll(resultSet.getString("NombreRoll"));
                    resultSP.objects.add(roll);
                
                }
                
                resultSP.correct = true;
            
            }catch(Exception ex){
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            return resultSP;
        
        });
        return result ;
    }

}
