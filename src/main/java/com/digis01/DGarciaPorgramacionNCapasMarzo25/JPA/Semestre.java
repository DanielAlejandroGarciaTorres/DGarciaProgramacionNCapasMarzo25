
package com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Semestre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsemestre")
    private int IdSemestre;
    
    @Column(name = "nombre")
    private String Nombre;

    public int getIdSemestre() {
        return IdSemestre;
    }

    public void setIdSemestre(int IdSemestre) {
        this.IdSemestre = IdSemestre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    
    
}
