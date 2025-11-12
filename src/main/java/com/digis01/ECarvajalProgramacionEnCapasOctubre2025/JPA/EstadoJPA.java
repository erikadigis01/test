package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ESTADO")
public class EstadoJPA {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestado", nullable = false)
    private int IdEstado;
    
    @Column(name = "nombre", nullable = false)
    private String Nombre;
    
    @ManyToOne
    @JoinColumn(name = "idpais")
    public PaisJPA Pais;
    
    public EstadoJPA(){
    
    }
    
    public EstadoJPA(int IdEstado, String Nombre){
    
        this.IdEstado = IdEstado;
        this.Nombre = Nombre;
    }
    
    public void setIdEstado(int IdEstado){
    
        this.IdEstado = IdEstado;
    }
    public int getIdEstado(){
        return IdEstado;
    }
    
    public void setNombre(String Nombre){
    
        this.Nombre = Nombre;
    }
    public String getNombre(){
        return Nombre;
    }

    public PaisJPA getPais() {
        return Pais;
    }

    public void setPais(PaisJPA Pais) {
        this.Pais = Pais;
    }
    
}
