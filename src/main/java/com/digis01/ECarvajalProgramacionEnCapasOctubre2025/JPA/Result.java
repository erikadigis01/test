package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;


//Clase para personalizar el mensaje devuelto por el servidor, cuando regresa el status
public class Result {
    
    public boolean correct;
    public String errorMessage;
    public Object object;
    public Object objects;
    public Exception ex;
    
    @JsonIgnore /// para ignorar el status y configurarse manualmente
    public int status;

}
