package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name  = "roll")
public class RollJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idroll")
    private int IdRoll;
    
    @Column(name = "nombreroll", nullable = false)
    private String NombreRoll;
    
}
