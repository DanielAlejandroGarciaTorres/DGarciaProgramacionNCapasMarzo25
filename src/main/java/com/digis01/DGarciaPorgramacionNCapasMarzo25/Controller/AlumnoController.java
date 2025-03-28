package com.digis01.DGarciaPorgramacionNCapasMarzo25.Controller;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Alumno")
public class AlumnoController {

    @Autowired
    private AlumnoDAOImplementation alumnoDAOImplementation;
    // Model y ModelAttribute
    
    @GetMapping
    public String Index(Model model) {

        Result result = alumnoDAOImplementation.GetAll();
        model.addAttribute("listaAlumnos", result.objects);
        
        return "AlumnoIndex";
    }

    @GetMapping("Form/{IdAlumno}")
    public String Form(@PathVariable int IdAlumno) {
        if(IdAlumno == 0){ // Agregar
            System.out.println("Voy a agregar");
        } else { // Editar
            System.out.println("Voy a editar");
        }
        return "FormAlumno";
    }

}
