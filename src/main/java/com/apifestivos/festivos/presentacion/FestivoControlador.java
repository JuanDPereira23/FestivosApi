package com.apifestivos.festivos.presentacion;

import org.springframework.web.bind.annotation.RestController;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apifestivos.festivos.core.entidades.Festivo;
import com.apifestivos.festivos.core.interfaces.servicios.IFestivoServicio;

import java.util.List;

@RestController
@RequestMapping("/api/festivos")
public class FestivoControlador {
    
    private IFestivoServicio servicio;

    public FestivoControlador(IFestivoServicio servicio){
        this.servicio = servicio;
    }

    @RequestMapping(value="/listar", method=RequestMethod.GET)
    public List<Festivo> listar(@RequestParam int year) {
        return servicio.listar(year);      
    }

    @GetMapping("/consultar/{day}/{month}/{year}")
    public String consultar(@PathVariable int day, @PathVariable int month, @PathVariable int year) {
        return servicio.consultar(day, month, year);
    }

}
