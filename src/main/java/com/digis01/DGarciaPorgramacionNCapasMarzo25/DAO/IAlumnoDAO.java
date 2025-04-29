package com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Alumno;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.AlumnoDireccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Result;


public interface IAlumnoDAO {
 
    Result GetAll(); //método abstracto 
    
    Result Add(AlumnoDireccion alumnoDireccion);
    
    Result direccionesByIdUsuario(int IdUsuario);
    
    Result GetById(int IdAlumno);
    
    Result Update(Alumno alumno);
    
    Result GetAllDinamico(Alumno alumno);
    
    Result GetAllJPA();
    
    Result AddJPA(AlumnoDireccion alumnoDireccion);
    
    Result GetAllDinamicoJPA(Alumno alumno);
    
    Result DeleteJPA(int IdAlumno);
    
    Result UpdateAlumnoJPA(Alumno alumno);
    
    Result DireccionUsuarioByIdAlumnoJPA(int IdAlumno);
}
