package com.digis01.DGarciaPorgramacionNCapasMarzo25.RestController;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Alumno;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demoapi")
public class DemoRestController {
    
    @GetMapping("saludo")
    public String Saludo(@RequestParam String nombre){
        return "HolaMundo " + nombre;
    }
    
    @GetMapping("saludo2")
    public ResponseEntity Saludo2(){
        Alumno alumno = new Alumno();
        alumno.setNombre("Ximena");
        alumno.setApellidoPaterno("Fragoso");
        alumno.setApellidoMaterno("Cortes");
        
        return ResponseEntity.accepted().body(alumno);
    }
    
}
