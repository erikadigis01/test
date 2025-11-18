package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.RestController;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.JPA.Result;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController //por que regresara un cuerpo de texto y no una pagina
@RequestMapping("api/demo")
public class DemoRestController {
    
    @GetMapping("saludo")
    public String Saludo() {
    
        return "Hola mundo, soy erika";
    
    }
    
     @GetMapping("division") //Response Entity porque devolveresmos la respuesta del servidor modificada
    public ResponseEntity Division(@RequestParam("NumeroUno") int numeroUno, @RequestParam("NumeroDos") int numeroDos) {//Request param para los paramentros que mandamos en la url
        Result result = new Result();//Result declarado en JPA donde se ignora el status
        try {
            if (numeroDos == 0) {//caso para evitar el server error 500
                result.correct = false;
                result.errorMessage = "Syntax Error :c";
                result.status = 400;
            } else { // division
                int division = numeroUno / numeroDos;
                result.correct = true;
                result.errorMessage = "Se realizo la division";
                result.status = 200;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }
    
    @GetMapping ("multiplicacion")
    public ResponseEntity Multiplicacion(@RequestParam("NumeroUno") int numeroUno, @RequestParam("NumeroDos") int numeroDos){
        Result result = new Result();
        
        try {
            
            if(numeroUno  != 0 && numeroDos != 0) {
                
                int resultado = numeroUno * numeroDos;
                result.correct = true;
                result.errorMessage = "Se realizo la multiplicacion";
                result.status = 200;
                result.object = resultado;
            
            
            } else {
            
                result.correct = false;
                result.errorMessage = "No se puede multiplicar por cero";
                result.status = 400;
            
            }
        
        
        } catch (Exception ex) {
        
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        
        return ResponseEntity.status(result.status).body(result);
        
        
    }
    
    @GetMapping("saludoAlguien")
    public String SaludoAlguien(@RequestParam ("nombre") String nombre) {
        
        return "Hola " + nombre + " :D";
    
    
    }
    
   
    
    @GetMapping("sumarNumerosBody") 
    public ResponseEntity SumarNumerosBody(@RequestBody List<String> numeros){
        
        Result result = new Result();
        
        try {
            
            int suma = 0;
            int auxiliar = 0;
            for (String numero : numeros) {
                
                try {
                
                    auxiliar = Integer.parseInt(numero);
                    suma = suma + auxiliar;
                    result.correct = true;
                    result.errorMessage = "Se realizo la suma de los numeros";
                    result.status = 200;
                    result.object = suma;
                
                } catch (NumberFormatException ex) {
                    
                    result.correct = false;
                    result.errorMessage = "introdujo un caracter no numerico";
                    result.ex = ex;
                    result.status = 400;
                
                }
               
            }
        
        
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        
        }
        
        return ResponseEntity.status(result.status).body(result);

    }
    
    @GetMapping("sumarNumeros")
    public ResponseEntity SumarNumeros(@RequestParam ("numeros") List<String> numeros) {
    
        Result result = new Result();
        
        try {
            
            int suma = 0;
            int auxiliar = 0;
            for (String numero : numeros) {
                
                try {
                
                    auxiliar = Integer.parseInt(numero);
                    suma = suma + auxiliar;
                    result.correct = true;
                    result.errorMessage = "Se realizo la suma de los numeros";
                    result.status = 200;
                    result.object = suma;
                
                } catch (NumberFormatException ex) {
                    
                    result.correct = false;
                    result.errorMessage = "introdujo un caracter no numerico";
                    result.ex = ex;
                    result.status = 400;
                
                }
               
            }
        
        
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        
        }
        
        return ResponseEntity.status(result.status).body(result);
      
    
    }

}
