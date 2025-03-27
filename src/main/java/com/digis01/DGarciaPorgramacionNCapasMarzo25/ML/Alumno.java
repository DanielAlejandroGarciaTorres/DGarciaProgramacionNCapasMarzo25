package com.digis01.DGarciaPorgramacionNCapasMarzo25.ML;


import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Alumno {
    
    private int IdAlumno;
    private String Nombre;
    private String ApellidoPaterno;
    private String ApellidoMaterno;
    public Semestre Semestre;
    
    public Alumno(){
        
    }
    
    public Alumno(String Nombre, String ApellidoPaterno) {
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
    }
    
    
    public int getIdAlumno() {
        return IdAlumno;
    }

    public void setIdAlumno(int IdAlumno) {
        this.IdAlumno = IdAlumno;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public Semestre getSemestre() {
        return Semestre;
    }

    public void setSemestre(Semestre Semestre) {
        this.Semestre = Semestre;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }
    
}
