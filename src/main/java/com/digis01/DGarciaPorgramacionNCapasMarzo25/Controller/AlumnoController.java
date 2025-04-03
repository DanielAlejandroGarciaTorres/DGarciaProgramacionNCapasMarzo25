package com.digis01.DGarciaPorgramacionNCapasMarzo25.Controller;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Alumno;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.AlumnoDireccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Colonia;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Direccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Result;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Semestre;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            Result result = alumnoDAOImplementation.direccionesByIdUsuario(IdAlumno);
            model.addAttribute("alumnoDirecciones", result.object);
            return "AlumnoDetail";
        }

    }
    
    @GetMapping("/FormEditable")
    public String FormEditable(Model model, @RequestParam int IdAlumno, @RequestParam(required = false) Integer IdDireccion ){
        
        if(IdAlumno > 0 && IdDireccion == 0){
            // agrega direccion
        } else if (IdAlumno > 0 && IdDireccion > 0) {
            // editar direccion
        } else {    
            // editar alumno
        }
        
        return "AlumnoForm";
    }

    @PostMapping("Form")
    public String Form(@Valid @ModelAttribute AlumnoDireccion alumnoDireccion, BindingResult BindingResult, Model model) {
        
        if (BindingResult.hasErrors()) {
            
            model.addAttribute("alumnoDireccion", alumnoDireccion);
            return "AlumnoForm";
        }
        
        alumnoDireccion.Alumno.Semestre = new Semestre();
        alumnoDireccion.Alumno.Semestre.setIdSemestre(10);
        alumnoDAOImplementation.Add(alumnoDireccion);
        
        return "AlumnoIndex";
    }

    
}
