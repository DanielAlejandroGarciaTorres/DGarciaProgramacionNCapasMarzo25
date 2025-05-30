package com.digis01.DGarciaPorgramacionNCapasMarzo25;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Alumno;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.AlumnoDireccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Colonia;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Direccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Result;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Semestre;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JUnitTest {

    @Autowired
    private AlumnoDAOImplementation alumnoDAOImplementation;

    @Test
    public void GetAllJUnit() {

        Result result = new Result();
        result = alumnoDAOImplementation.GetAllJPA();
        
        List<AlumnoDireccion> alumnosDireccion = new ArrayList<>();
        
        Assertions.assertNotNull(result, "el objeto result viene nulo");
        Assertions.assertNotNull(result.objects, "result.objects viene nulo");
        Assertions.assertNull(result.ex, "Viene una excepción");
        Assertions.assertNull(result.errorMessage, "Viene un mensaje de error");
        Assertions.assertNull(result.object, "result.object viene nulo");
        //Assertions.assertEquals(alumnosDireccion, result.objects, "La lista no se parece");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
        //Swagger -> Documentar servicios

    }
    
    @Test
    public void AddAlumnoDireccionJUnit() {
        
        AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
        alumnoDireccion.Alumno = new Alumno();
        alumnoDireccion.Alumno.setNombre("Jose Rómulo");
        alumnoDireccion.Alumno.setApellidoPaterno("Sosa");
        alumnoDireccion.Alumno.setApellidoMaterno("Ortiz");
        alumnoDireccion.Alumno.setUsername("Jose Jose");
        alumnoDireccion.Alumno.setEmail("elprincipedelacancion@gmail.com");
        alumnoDireccion.Alumno.setFechaNacimiento(new Date(17021948L));
        alumnoDireccion.Alumno.setImagen(null);
        alumnoDireccion.Alumno.setStatus(0);
        alumnoDireccion.Alumno.setPassword("1234");
        alumnoDireccion.Alumno.Semestre = new Semestre();
        alumnoDireccion.Alumno.Semestre.setIdSemestre(1);
        
        alumnoDireccion.Direccion = new Direccion();
        alumnoDireccion.Direccion.setCalle("Azcapotzalco");
        alumnoDireccion.Direccion.Colonia = new Colonia();
        alumnoDireccion.Direccion.Colonia.setIdColonia(1);
        
        Result result = alumnoDAOImplementation.AddJPA(alumnoDireccion);
        
        Assertions.assertNotNull(result, "el result viene nulo");
        Assertions.assertNull(result.ex, "Viene una excepción");
        Assertions.assertNull(result.errorMessage, "Viene un mensaje de error");
        Assertions.assertNull(result.objects, "viene algo en objects");
        Assertions.assertNull(result.object, "viene algo en object");
        Assertions.assertTrue(result.correct, "result.correct viene false");
        
    }

}
