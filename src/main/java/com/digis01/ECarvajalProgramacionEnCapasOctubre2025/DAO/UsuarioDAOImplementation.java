package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.DAO;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Colonia;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Direccion;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Estado;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Municipio;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Pais;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Roll;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Usuario;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;



@Repository
public class UsuarioDAOImplementation implements IUsuarioDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAll() {
    
        Result result = jdbcTemplate.execute("{CALL UsuarioDireccionGetAll(?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result resultSP = new Result();
            try{
            
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                resultSP.objects = new ArrayList<>();
                
                int IdUsuario = 0;
                
                while(resultSet.next()){
                
                    IdUsuario = resultSet.getInt("IdUsuario");
                    
                    if(!resultSP.objects.isEmpty() && IdUsuario == ((Usuario) (resultSP.objects.get(resultSP.objects.size()-1))).getIdUsuario()){
                    
                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        
                        Usuario usuario = ((Usuario)(resultSP.objects.get(resultSP.objects.size()-1)));
                        usuario.Direccion.add(direccion);
                    } else {
                    
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuario.setNombre(resultSet.getString("Nombre"));
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario.setCelular(resultSet.getString("Celular"));
                        usuario.setTelefono(resultSet.getString("Telefono"));
                        usuario.setEmail(resultSet.getString("Email"));
                        
                        usuario.Roll = new Roll();
                        usuario.Roll.setIdRoll(resultSet.getInt("IdRoll"));
                        usuario.Roll.setNombreRoll(resultSet.getString("NombreRoll"));
                        usuario.setImagen(resultSet.getString("Imagen"));
                        
                        usuario.Direccion = new ArrayList<>();
                        
                        Direccion direccion = new Direccion();
                        
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        
                        usuario.Direccion.add(direccion);
                        resultSP.objects.add(usuario);
                    }
                }
            
                resultSP.correct =  true;
                
            }catch(Exception ex) {
            
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            
            return resultSP;
        });
        
        return result;
    
    }
    
   

    @Override
    public Result GetById( int id) {
        Result result = jdbcTemplate.execute("{CALL UsuarioDireccionGetById(?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result resultSP = new Result();
            try{
                
                
            
               callableStatement.setInt(1, id);
            
               callableStatement.registerOutParameter(2, Types.REF_CURSOR);
               callableStatement.registerOutParameter(3, Types.REF_CURSOR);
               
                callableStatement.execute();
                
                ResultSet resultSetUsuario = (ResultSet) callableStatement.getObject(2);
                
                
                if(resultSetUsuario.next()){
               
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(id);
                    usuario.setNombre(resultSetUsuario.getString("Nombre"));
                    usuario.setUserName(resultSetUsuario.getString("UserName"));
                    usuario.setApellidoPaterno(resultSetUsuario.getString("ApellidoPaterno"));
                    usuario.setApellidoMaterno(resultSetUsuario.getString("ApellidoMaterno"));
                    usuario.setEmail(resultSetUsuario.getString("Email"));
                    usuario.setPassword(resultSetUsuario.getString("Password"));
                    usuario.setFechaNacimiento(resultSetUsuario.getDate("FechaNacimiento"));

                    char caracter;
                    caracter = resultSetUsuario.getString("Sexo").charAt(0);
                    usuario.setSexo(caracter);

                    usuario.setTelefono(resultSetUsuario.getString("Telefono"));
                    usuario.setCelular(resultSetUsuario.getString("Celular"));
                    usuario.setCurp(resultSetUsuario.getString("Curp"));

                    usuario.Roll = new Roll();
                    usuario.Roll.setIdRoll(resultSetUsuario.getInt("IdRoll"));
                    usuario.Roll.setNombreRoll(resultSetUsuario.getString("NombreRoll"));
                    usuario.setImagen(resultSetUsuario.getString("Imagen"));
                    
                     ResultSet resultSetUsuarioDirecciones = (ResultSet) callableStatement.getObject(3);
                
                    usuario.Direccion = new ArrayList<>();

                    while(resultSetUsuarioDirecciones.next()){

                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSetUsuarioDirecciones.getInt("IdDireccion"));
                        direccion.setCalle(resultSetUsuarioDirecciones.getString("Calle"));
                        direccion.setNumeroInterior(resultSetUsuarioDirecciones.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSetUsuarioDirecciones.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSetUsuarioDirecciones.getInt("ColoniaID"));
                        direccion.Colonia.setNombre(resultSetUsuarioDirecciones.getString("Colonia"));
                        direccion.Colonia.setCodigoPostal(resultSetUsuarioDirecciones.getString("CP"));

                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSetUsuarioDirecciones.getInt("MunicipioID"));
                        direccion.Colonia.Municipio.setNombre(resultSetUsuarioDirecciones.getString("Municipio"));

                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSetUsuarioDirecciones.getInt("EstadoID"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSetUsuarioDirecciones.getString("Estado"));

                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSetUsuarioDirecciones.getInt("PaisID"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSetUsuarioDirecciones.getString("Pais"));


                        usuario.Direccion.add(direccion);
                }
                    resultSP.object = usuario;
                    resultSP.correct =  true;
                    
                }
                
                
            }catch(Exception ex) {
            
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            
            return resultSP;
        });
        
        return result;
    }
    
    @Override
    public Result Add(Usuario usuario) {
         Result result = jdbcTemplate.execute("{CALL UsuarioDireccionAdd(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result resultSP = new Result();
            try{
                
                callableStatement.setString(1, usuario.getUserName());
                callableStatement.setString(2, usuario.getNombre());
                callableStatement.setString(3, usuario.getApellidoPaterno());
                callableStatement.setString(4, usuario.getApellidoMaterno());
                callableStatement.setString(5, usuario.getEmail());
                callableStatement.setString(6, usuario.getPassword());



                callableStatement.setDate(7, new java.sql.Date(usuario.getFechaNacimiento().getTime()));

                String sexo ;
                sexo = Character.toString(usuario.getSexo());

                callableStatement.setString(8, sexo);
                callableStatement.setString(9, usuario.getTelefono());
                callableStatement.setString(10, usuario.getCelular());
                callableStatement.setString(11, usuario.getCurp());
                callableStatement.setInt(12, usuario.Roll.getIdRoll());
                callableStatement.setString(13, usuario.Direccion.get(0).getCalle());
                callableStatement.setString(14,usuario.Direccion.get(0).getNumeroInterior());
                callableStatement.setString(15, usuario.Direccion.get(0).getNumeroExterior());
                callableStatement.setInt(16, usuario.Direccion.get(0).Colonia.getIdColonia());
                callableStatement.setString(17, usuario.getImagen());
                
                
                int rowAffected = callableStatement.executeUpdate();
            
                if(rowAffected > 0){

                    resultSP.correct = true;
                    System.out.println("Se inserto correctamente");
                   
                }else{

                    resultSP.correct = false;
                    resultSP.errorMessage = "No se pudo insertar el usuario y direccion";
                    System.out.println("Se no inserto correctamente");
                }
            
            
                
            }catch(Exception ex) {
            
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            
            return resultSP;
        });
        
        return result;
    }

    @Override
    public Result Update(Usuario usuario) {
        Result result = jdbcTemplate.execute("{CALL UsuarioUpdate(?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Result>) callableStatement ->{
            Result resultSP = new Result();
            
            try{
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2, usuario.getUserName());
                callableStatement.setString(3, usuario.getNombre());
                callableStatement.setString(4, usuario.getApellidoPaterno());
                callableStatement.setString(5, usuario.getApellidoMaterno());
                callableStatement.setString(6, usuario.getEmail());
                
                callableStatement.setDate(7, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                
                String sexo;
                sexo = Character.toString(usuario.getSexo());
                
                callableStatement.setString(8, sexo);
                callableStatement.setString(9, usuario.getTelefono());
                callableStatement.setString(10, usuario.getCelular());
                callableStatement.setString(11, usuario.getCurp());
                callableStatement.setInt(12, usuario.Roll.getIdRoll());
                
                int rowAffected = callableStatement.executeUpdate();
            
                if(rowAffected > 0){

                    resultSP.correct = true;
                    System.out.println("Se actualizo correctamente");
                    System.out.print(usuario.getIdUsuario()+usuario.getUserName()+usuario.getNombre()+usuario.getApellidoPaterno()+usuario.getApellidoMaterno()+usuario.getEmail()+usuario.getFechaNacimiento()
                    +usuario.getTelefono()+usuario.getCelular()+usuario.getCurp()+usuario.Roll.getIdRoll());
                   
                }else{

                    resultSP.correct = false;
                    resultSP.errorMessage = "No se pudo insertar el usuario y direccion";
                    System.out.println("Se no inserto correctamente");
                }
                
            }catch(Exception ex){
            
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            
            return resultSP;
        });
        return result;
    }

    @Override
    public Result Delete(int IdUsuario) {
            
        Result result = jdbcTemplate.execute("{CALL UsuarioDelete(?)}", (CallableStatementCallback<Result>) callableStatement ->{
            
            Result resultSP = new Result();
            
            try{
                
                callableStatement.setInt(1, IdUsuario);
                int rowAffected = callableStatement.executeUpdate();
                System.out.print("Id usuario" + IdUsuario);

                if(rowAffected > 0) {

                    resultSP.correct = true;
                    resultSP.object ="Usuarios eliminado correctanente";
                    System.out.print("Se elimino correctamente");

                } else {

                    resultSP.correct = false;
                    resultSP.errorMessage = "No se elimino el usuario";
                    System.out.print("No se elimino correctamente");
                }
            
            
            }catch(Exception ex){
            
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            
            }
            
            
            return resultSP;
        });
        
        return result;
        
    }

    @Override
    public Result  AddDireccion(Direccion direccion, int idUsuario)  {
        Result result = jdbcTemplate.execute("{CALL DireccionAddByUsuario(?,?,?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            Result resultSP = new Result();
            try{
                
                callableStatement.setInt(1, idUsuario);
                callableStatement.setString(2, direccion.getCalle());
                callableStatement.setString(3,direccion.getNumeroInterior());
                callableStatement.setString(4, direccion.getNumeroExterior());
                callableStatement.setInt(5, direccion.Colonia.getIdColonia());
                
                
                int rowAffected = callableStatement.executeUpdate();
            
                if(rowAffected > 0){

                    resultSP.correct = true;
                    System.out.println("Se inserto correctamente");
                   
                }else{

                    resultSP.correct = false;
                    resultSP.errorMessage = "No se pudo insertar el usuario y direccion";
                    System.out.println("Se no inserto correctamente");
                }
            
            
                
            }catch(Exception ex) {
            
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            
            return resultSP;
        });
        
        return result;
    }

    @Override
    public Result UpdateDireccion(Direccion direccion, int idUsuario) {
        Result result = jdbcTemplate.execute("{CALL DireccionUpdateByUsuario(?,?,?,?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
            
            Result resultSP = new Result();
            
            try {
                
                callableStatement.setInt(1, direccion.getIdDireccion());
                callableStatement.setInt(2, idUsuario);
                callableStatement.setString(3, direccion.getCalle());
                callableStatement.setString(4, direccion.getNumeroInterior());
                callableStatement.setString(5, direccion.getNumeroExterior());
                callableStatement.setInt(6, direccion.Colonia.getIdColonia());
                
                int rowAffected = callableStatement.executeUpdate();
                
                if(rowAffected > 0){
                
                    resultSP.correct = true;
                } else {
                
                    resultSP.correct = false;
                    resultSP.errorMessage = "No se pudo actualizar la direccion";
                    
                }
            
            } catch (Exception ex){
            
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            
            return resultSP;
            
        });
        return result;
    }

    @Override
    public Result GetByIdDireccion(int IdDireccion) {
        
        Result result = jdbcTemplate.execute("{CALL DireccionGetById(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
        
            Result resultSP = new Result();
            
            try {
                
                callableStatement.setInt(1, IdDireccion);
                callableStatement.registerOutParameter(2, Types.VARCHAR);
                callableStatement.registerOutParameter(3, Types.VARCHAR);
                callableStatement.registerOutParameter(4, Types.VARCHAR);
                
                callableStatement.registerOutParameter(5, Types.NUMERIC);
                callableStatement.registerOutParameter(6, Types.NUMERIC);
                
                callableStatement.registerOutParameter(7, Types.VARCHAR);
                callableStatement.registerOutParameter(8, Types.VARCHAR);
                 
                callableStatement.registerOutParameter(9, Types.NUMERIC);
                
                callableStatement.registerOutParameter(10, Types.VARCHAR);
                
                callableStatement.registerOutParameter(11, Types.NUMERIC);
                
                callableStatement.registerOutParameter(12, Types.VARCHAR);
                
                callableStatement.registerOutParameter(13, Types.NUMERIC);
                
                callableStatement.registerOutParameter(14, Types.VARCHAR);
                 
                
                callableStatement.execute();
                
                
                Direccion direccion = new Direccion();
                
                direccion.setCalle(callableStatement.getString(2));
                direccion.setNumeroInterior(callableStatement.getString(3));
                direccion.setNumeroExterior(callableStatement.getString(4));
                direccion.Colonia = new Colonia();
                direccion.Colonia.setIdColonia(callableStatement.getInt(5));
                direccion.Colonia.setCodigoPostal(callableStatement.getString(7));
                direccion.Colonia.setNombre(callableStatement.getString(8));
                
                direccion.Colonia.Municipio = new Municipio();
                direccion.Colonia.Municipio.setIdMunicipio(callableStatement.getInt(9));
                direccion.Colonia.Municipio.setNombre(callableStatement.getString(10));
                
                direccion.Colonia.Municipio.Estado = new Estado();
                direccion.Colonia.Municipio.Estado.setIdEstado(callableStatement.getInt(11));
                direccion.Colonia.Municipio.Estado.setNombre(callableStatement.getString(12));
                
                direccion.Colonia.Municipio.Estado.Pais = new Pais();
                direccion.Colonia.Municipio.Estado.Pais.setIdPais(callableStatement.getInt(13));
                direccion.Colonia.Municipio.Estado.Pais.setNombre(callableStatement.getString(14));
                
                resultSP.object = direccion;
                resultSP.correct = true;
                
            
            } catch (Exception ex) {
            
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            
            return resultSP;
            
        });
        
        return result;
    
    }

    @Override
    public Result UpdateImagen(Usuario usuario) {
        Result result = jdbcTemplate.execute("{CALL UpdateImagenUsuario(?,?)}", (CallableStatementCallback<Result>) callableStatement -> {
        
            Result resultSP = new Result();
            
            try {
                
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2, usuario.getImagen());
                
                int rowAffected = callableStatement.executeUpdate();
                
                if(rowAffected > 0){
                
                    resultSP.correct = true;
                } else {
                
                    resultSP.correct = false;
                    resultSP.errorMessage = "No se pudo actualizar la direccion";
                    
                }
            
            } catch (Exception ex) {
            
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            
            return resultSP;
        
        });
        
        return result;
    }

    @Override
    public Result DeleteDireccion(int idDireccion) {
        Result result = jdbcTemplate.execute("{CALL DireccionDelete(?)}", (CallableStatementCallback<Result>) callableStatement -> {
        
            Result resultSP = new Result();
            
            try {
                
                callableStatement.setInt(1, idDireccion);
                int rowAffected = callableStatement.executeUpdate();
                

                if(rowAffected > 0) {

                    resultSP.correct = true;
                    resultSP.object ="Direccion eliminada correctanente";
                    System.out.print("Se elimino correctamente");

                } else {

                    resultSP.correct = false;
                    resultSP.errorMessage = "No se elimino la direccion";
                    System.out.print("No se elimino correctamente");
                }
            
            
            } catch (Exception ex){
            
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            
            return resultSP;
            
        });
        
        return result;
    }
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result AddAll(List<Usuario> usuarios) {
        Result result = new Result();

        try{

          jdbcTemplate.batchUpdate("{CALL UsuarioAdd(?,?,?,?,?,?,?,?,?,?,?,?)}",
                  usuarios,
                  usuarios.size(),
                  (callableStatement, usuario) -> {

                      callableStatement.setString(1, usuario.getUserName());
                      callableStatement.setString(2, usuario.getNombre());
                      callableStatement.setString(3, usuario.getApellidoPaterno());
                      callableStatement.setString(4, usuario.getApellidoMaterno());
                      callableStatement.setString(5, usuario.getEmail());
                      callableStatement.setString(6, usuario.getPassword());



                      callableStatement.setDate(7, new java.sql.Date(usuario.getFechaNacimiento().getTime()));

                      String sexo ;
                      sexo = Character.toString(usuario.getSexo());

                      callableStatement.setString(8, sexo);
                      callableStatement.setString(9, usuario.getTelefono());
                      callableStatement.setString(10, usuario.getCelular());
                      callableStatement.setString(11, usuario.getCurp());
                      callableStatement.setInt(12, usuario.Roll.getIdRoll());

                  });

                  result.correct =  true;


        }catch(Exception ex) {

          result.correct = false;
          result.errorMessage = ex.getLocalizedMessage();
          result.ex = ex;
          }

        return result;          
    }
    
    
}
