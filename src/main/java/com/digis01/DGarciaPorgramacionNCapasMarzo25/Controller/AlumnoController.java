package com.digis01.DGarciaPorgramacionNCapasMarzo25.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Alumno")
public class AlumnoController {

    // Model y ModelAttribute
    @GetMapping
    public String Index(Model model) {

        String nombre = "Jesus";
        model.addAttribute("nombre", nombre);
        return "AlumnoIndex";
    }

    @GetMapping("Add")
    public String Form() {
        return "FormAlumno";
    }

}
