package com.digis01.DGarciaPorgramacionNCapasMarzo25.Controller;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("Add")
    public String Form() {
        return "FormAlumno";
    }

}
