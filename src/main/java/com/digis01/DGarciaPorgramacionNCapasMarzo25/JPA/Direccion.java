
package com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddireccion")
    private int IdDireccion;
   
    @Column(name = "calle")
    private String Calle;
    
    @Column(name = "numerointerior")
    private String NumeroInterior;
    
    @Column(name = "numeroexterior")
    private String NumeroExterior;
    
    @JoinColumn(name = "idcolonia")
    @ManyToOne
    public Colonia Colonia; //Propiedad de navegación

    @JoinColumn(name = "idalumno")
    @ManyToOne
    public Alumno Alumno;
    
    /*
    @Column( name = "idalumno")
    private int IdAlumno;
    */
    
    
    public int getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(int IdDireccion) {
        this.IdDireccion = IdDireccion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String NumeroInterior) {
        this.NumeroInterior = NumeroInterior;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }

    public Colonia getColonia() {
        return Colonia;
    }

    public void setColonia(Colonia Colonia) {
        this.Colonia = Colonia;
    }
    
    
}
