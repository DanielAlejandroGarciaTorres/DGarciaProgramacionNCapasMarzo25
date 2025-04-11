package com.digis01.DGarciaPorgramacionNCapasMarzo25.Controller;

import com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO.AlumnoDAOImplementation;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO.ColoniaDAOImplementation;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO.DireccionDAOImplementation;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO.EstadoDAOImplementation;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO.MunicipioDAOImplementation;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.DAO.SemestreDAOImplementation;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Alumno;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.AlumnoDireccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Colonia;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Direccion;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Result;
import com.digis01.DGarciaPorgramacionNCapasMarzo25.ML.Semestre;
import jakarta.validation.Valid;
import java.util.Base64;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/Alumno")
public class AlumnoController {

    @Autowired
    private AlumnoDAOImplementation alumnoDAOImplementation;
    // Model y ModelAttribute

    @Autowired
    private SemestreDAOImplementation SemestreDAOImplementation;

    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;

    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;

    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;

    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;

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

            model.addAttribute("semestres", SemestreDAOImplementation.GetAll().object);
            model.addAttribute("alumnoDireccion", alumnoDireccion);
            model.addAttribute("estados", estadoDAOImplementation.GetAll().correct ? estadoDAOImplementation.GetAll().objects : null);
            return "AlumnoForm";
        } else { // Editar
            System.out.println("Voy a editar");
            Result result = alumnoDAOImplementation.direccionesByIdUsuario(IdAlumno);
            model.addAttribute("alumnoDirecciones", result.object);
            return "AlumnoDetail";
        }
    }

    @GetMapping("/formEditable")
    public String FormEditable(Model model, @RequestParam int IdAlumno, @RequestParam(required = false) Integer IdDireccion) {

        if (IdDireccion == null) { //Editar Alumno
            AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
//            alumnoDireccion.Alumno.setIdAlumno(1);

            alumnoDireccion = (AlumnoDireccion) alumnoDAOImplementation.GetById(IdAlumno).object;
            alumnoDireccion.Direccion = new Direccion();
            alumnoDireccion.Direccion.setIdDireccion(-1);
            model.addAttribute("alumnoDireccion", alumnoDireccion);

            model.addAttribute("semestres", SemestreDAOImplementation.GetAll().object);
        } else if (IdDireccion == 0) { //Agregar dirección
            AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
            alumnoDireccion.Alumno = new Alumno();
            alumnoDireccion.Alumno.setIdAlumno(1);
            alumnoDireccion.Direccion = new Direccion();
            alumnoDireccion.Direccion.setIdDireccion(0);
            model.addAttribute("alumnoDireccion", alumnoDireccion);
            model.addAttribute("estados", estadoDAOImplementation.GetAll().correct ? estadoDAOImplementation.GetAll().objects : null);
        } else { //Editar dirección
            AlumnoDireccion alumnoDireccion = new AlumnoDireccion();
            alumnoDireccion.Alumno = new Alumno();
            alumnoDireccion.Alumno.setIdAlumno(IdAlumno);
            alumnoDireccion.Direccion = new Direccion();
            alumnoDireccion.Direccion.setIdDireccion(IdDireccion);

            alumnoDireccion.Direccion = (Direccion) direccionDAOImplementation.GetByIdDireccion(IdDireccion).object;

            model.addAttribute("alumnoDireccion", alumnoDireccion);
            model.addAttribute("estados", estadoDAOImplementation.GetAll().correct ? estadoDAOImplementation.GetAll().objects : null);
            // model.addAttribut("municipios", municipioDAOIMplementation.MunicipiosByIdEstado)
        }   // model.addAttribute("colonias", coloniaDAOIMplementation.ColoniasByIdMunicipios)

        return "AlumnoForm";
    }

    @PostMapping("Form")
    public String Form(@Valid @ModelAttribute AlumnoDireccion alumnoDireccion, BindingResult BindingResult, @RequestParam MultipartFile imagenFile, Model model) {

        try {
            if (!imagenFile.isEmpty()) {
                byte[] bytes = imagenFile.getBytes();
                String imgBase64 = Base64.getEncoder().encodeToString(bytes);
                alumnoDireccion.Alumno.setImagen(imgBase64);
            }
        } catch (Exception ex) {

        }

        if (alumnoDireccion.Alumno.getIdAlumno() == 0) { //Agregar
            //Logica para consumir DAO para agregar un nuevo usuario
//            alumnoDireccion.Alumno.Semestre = new Semestre();
//            alumnoDireccion.Alumno.Semestre.setIdSemestre(10);
            System.out.println("Estoy agregando un nuevo usuario y direccion");
            alumnoDireccion.Alumno.setFechaNacimiento(new Date());
            alumnoDAOImplementation.Add(alumnoDireccion);
        } else {
            if (alumnoDireccion.Direccion.getIdDireccion() == -1) { //Editar usuario
                alumnoDAOImplementation.Update(alumnoDireccion.Alumno);
                System.out.println("Estoy actualizando un usuario");
            } else if (alumnoDireccion.Direccion.getIdDireccion() == 0) { //Agregar direccion
                //alumnoDAOImplementation.AddDireccion(alumnoDireccion);
                System.out.println("Estoy agregando direccion");
            } else { //Editar direccion
                //alumnoDAOImplementation.UpdateDireccion(alumnoDireccion);
                System.out.println("Estoy actualizando direccion");
            }
        }

        return "redirect:/Alumno";
    }

    @GetMapping("MunicipioByIdEstado/{IdEstado}")
    @ResponseBody
    public Result MunicipioByIdEstado(@PathVariable int IdEstado) {
        Result result = municipioDAOImplementation.MunicipioByIdEstado(IdEstado);

        return result;
    }

    @GetMapping("ColoniaByIdMunicipio/{IdMunicipio}")
    @ResponseBody
    public Result ColoniaByIdMunicipio(@PathVariable int IdMunicipio) {
        Result result = coloniaDAOImplementation.ColoniaByIdMunicipio(IdMunicipio);

        return result;
    }
}
