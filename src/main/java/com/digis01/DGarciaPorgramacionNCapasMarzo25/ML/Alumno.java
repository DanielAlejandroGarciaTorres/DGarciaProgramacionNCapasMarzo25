package com.digis01.DGarciaPorgramacionNCapasMarzo25.ML;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Alumno {

    private int IdAlumno;

    @NotNull
    @NotEmpty(message = "Ingresa información,")
    @Size(min = 3, max = 50, message = "Entre 3 y 50 caracteres")
    private String Nombre;

    @NotNull(message = "No se permiten nulos")
    private String ApellidoPaterno;
    private String ApellidoMaterno;
    public Semestre Semestre;

    @NotNull(message = "No se permiten nulos")
    private String Username;
    private String Email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date FechaNacimiento;
    private String Imagen;

    private int Status;
    
    public Alumno() {

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

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

}
