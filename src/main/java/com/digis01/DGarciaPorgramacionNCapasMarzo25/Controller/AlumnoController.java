package com.digis01.DGarciaPorgramacionNCapasMarzo25.Controller;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Alumno;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.AlumnoDireccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Colonia;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Direccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Result;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Semestre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String Form(@PathVariable int IdAlumno, Model model) {
        if (IdAlumno == 0) { // Agregar
            AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
            alumnoDireccion.Alumno = new Alumno();
            alumnoDireccion.Alumno.Semestre = new Semestre();
            alumnoDireccion.Direccion = new Direccion();
            alumnoDireccion.Direccion.Colonia = new Colonia();
                    
            model.addAttribute("alumnoDireccion", alumnoDireccion);
            return "AlumnoForm";
        } else { // Editar
            System.out.println("Voy a editar");
            return "AlumnoView";
        }

    }

    @PostMapping("Form")
    public String Form(@ModelAttribute AlumnoDireccion alumnoDireccion) {
        alumnoDireccion.Alumno.Semestre = new Semestre();
        alumnoDireccion.Alumno.Semestre.setIdSemestre(10);
        alumnoDAOImplementation.Add(alumnoDireccion);
        
        return "AlumnoIndex";
    }

}
