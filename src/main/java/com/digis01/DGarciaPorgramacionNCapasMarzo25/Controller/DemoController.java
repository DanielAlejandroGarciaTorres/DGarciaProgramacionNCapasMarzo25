package com.digis01.DGarciaPorgramacionNCapasMarzo25.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("saludo")
    public String HolaMundo() {
        return "HolaMundo";
    }

    @GetMapping("saludoPath/{nombre}")
    public String SaludoPath(@PathVariable String nombre) {
        return "HolaMundo";
    }

    @GetMapping("operaciones/{op}/{numeroUno}") //suma resta multiplicación y division
    public void Operaciones(@PathVariable String op, @PathVariable int numeroUno, @RequestParam int numeroDos) {

        switch (op) {
            case "suma":
                System.out.println(numeroUno + numeroDos);
                break;
            default:
                throw new AssertionError();
        }
    }

    @GetMapping("saludoRequest")
    public String SaludoRequest(@RequestParam String nombre) {
        return "Saludo";
    }

}
