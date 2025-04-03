
package com.digis01.DGarciaPorgramacionNCapasMarzo25.ML;

import jakarta.validation.Valid;
import java.util.List;

public class AlumnoDireccion {
    
    @Valid
    public Alumno Alumno;
    public List<Direccion> Direcciones;
    public Direccion Direccion;

    
    public Alumno getAlumno() {
        return Alumno;
    }

    public void setAlumno(Alumno Alumno) {
        this.Alumno = Alumno;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }

    public Direccion getDireccion() {
        return Direccion;
    }

    public void setDireccion(Direccion Direccion) {
        this.Direccion = Direccion;
    }
    
    
    
}
