package com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.AlumnoDireccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Result;


public interface IAlumnoDAO {
 
    Result GetAll(); //método abstracto 
    
    Result Add(AlumnoDireccion alumnoDireccion);
    
    Result direccionesByIdUsuario(int IdUsuario);
}
