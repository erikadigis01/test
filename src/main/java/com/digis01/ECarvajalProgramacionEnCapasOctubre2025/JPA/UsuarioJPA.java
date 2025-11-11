package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.JPA;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Roll;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "usuario")
public class UsuarioJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idalumno")
    private int IdUsuario;
    
    @Column(name = "usernombre", nullable = false, unique = true)
    private String UserName;
    
    @Column(name = "nombre", nullable = false)
    private String Nombre;
    
    @Column(name = "apellidoPaterno", nullable = false)
    private String ApellidoPaterno;
    
    @Column(name = "apellidomaterno", nullable = true)
    private String ApellidoMaterno;
    
    @Column(name = "email", nullable = false, unique = true)
    private String Email;
    
    @Column(name = "password", nullable = false)
    private String Password;
    
    @Column(name = "fechanacimiento", nullable =  false)
    private Date FechaNacimiento;
    
    @Column(name = "sexo", nullable = false)
    private char Sexo;
    
    @Column(name = "telefono", nullable = false)
    private String Telefono;
    
    @Column(name = "celular", nullable = true)
    private String Celular;
    
    @Column(name = "curp", nullable = true)
    private String Curp;
    
    @Column(name = "imagen", nullable = true)
    private String Imagen;
    
    @ManyToOne
    @JoinColumn(name = "idroll")
    public Roll Roll;
//    public List<Direccion> Direccion;
    
}
