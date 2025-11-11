package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.JPA;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name  = "ROLL")
public class RollJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idroll")
    
    private int IdRoll;
    
    @Column(name = "nombreroll", nullable = false)
    private String NombreRoll;
    
//    @OneToMany(mappedBy = "roll", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<UsuarioJPA> usuarios = new ArrayList<>();
//    
    
    //Constructores
     public RollJPA(){
    
    }
    
    public RollJPA(int IdRoll, String NombreRoll){
        
        this.IdRoll = IdRoll;
        this.NombreRoll = NombreRoll;
    }
    
    //Getters y setters
    public void setIdRoll(int IdRoll){
        this.IdRoll = IdRoll;
    }
    public int getIdRoll(){
        return IdRoll;
    }
    
    public void setNombreRoll(String NombreRoll){
        this.NombreRoll = NombreRoll;
    }
    public String getNombreRoll(){
        return NombreRoll;
    }
}
