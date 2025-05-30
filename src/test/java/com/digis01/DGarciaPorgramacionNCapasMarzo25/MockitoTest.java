package com.digis01.DGarciaPorgramacionNCapasMarzo25;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Alumno;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.AlumnoDireccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Colonia;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Direccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Result;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Semestre;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class MockitoTest {
    
    @Mock
    EntityManager entityManager;
    
    @Mock
    JdbcTemplate jdbcTemplate;
    
    @InjectMocks
    AlumnoDAOImplementation alumnoDAOImplementation;
    
    @Test
    public void AlumnoAddMockito() {
        
        AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
        alumnoDireccion.Alumno = new Alumno();
        alumnoDireccion.Alumno.setNombre("Alberto");
        alumnoDireccion.Alumno.setApellidoPaterno("Aguilera");
        alumnoDireccion.Alumno.setPassword("1234");
        alumnoDireccion.Alumno.Semestre = new Semestre();
        alumnoDireccion.Direccion = new Direccion();
        alumnoDireccion.Direccion.setCalle("Ciudad Juárez");
        alumnoDireccion.Direccion.Colonia = new Colonia();
        
        Result result = alumnoDAOImplementation.AddJPA(alumnoDireccion);
        
        Assertions.assertTrue(result.correct, "Mi result.correct viene en false");
        
        Mockito.verify(entityManager, Mockito.times(1)).persist(Mockito.any(com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Alumno.class));
        Mockito.verify(entityManager, Mockito.times(1)).persist(Mockito.any(com.digis01.DGarciaPorgramacionNCapasMarzo25.JPA.Direccion.class));
        
    }
    
}
