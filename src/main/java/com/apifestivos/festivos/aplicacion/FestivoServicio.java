package com.apifestivos.festivos.aplicacion;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.apifestivos.festivos.core.entidades.Festivo;
import com.apifestivos.festivos.core.interfaces.repositorios.IFestivoRepositorio;
import com.apifestivos.festivos.core.interfaces.servicios.IFestivoServicio;

@Service
public class FestivoServicio implements IFestivoServicio {


    private IFestivoRepositorio repositorio;

    public FestivoServicio(IFestivoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Festivo> listar(int year) {
        // Implementa la lógica para filtrar los festivos del año dado
        return repositorio.findAll();
    }

    @Override
    public String consultar(int dia, int mes, int año) {
        // Validar si la fecha es válida
        if (!esFechaValida(dia, mes, año)) {
        return "Fecha No Válida";
        }
        Set<MonthDay> festivosFijos = new HashSet<>();
        Set<LocalDate> festivosMovibles = new HashSet<>();
        Set<LocalDate> festivosTrasladables = new HashSet<>();

        // Festivos de fecha fija
        festivosFijos.add(MonthDay.of(1, 1));  // Año Nuevo
        festivosFijos.add(MonthDay.of(5, 1));  // Día del Trabajo
        festivosFijos.add(MonthDay.of(7, 20)); // Día de la Independencia
        festivosFijos.add(MonthDay.of(8, 7));  // Batalla de Boyacá
        festivosFijos.add(MonthDay.of(12, 8)); // Inmaculada Concepción
        festivosFijos.add(MonthDay.of(12, 25)); // Navidad

        // Calcular festivos móviles
        LocalDate pascua = calcularDomingoPascua(año);
        festivosMovibles.add(pascua.minusDays(3)); // Jueves Santo
        festivosMovibles.add(pascua.minusDays(2)); // Viernes Santo
        festivosMovibles.add(pascua.plusDays(43)); // Ascensión de Jesús
        festivosMovibles.add(pascua.plusDays(64)); // Corpus Christi
        festivosMovibles.add(pascua.plusDays(71)); // Sagrado Corazón

        // Calcular festivos trasladables (Ley Emiliani)
        festivosTrasladables.add(trasladarSiNecesario(LocalDate.of(año, 1, 6)));  // Reyes Magos
        festivosTrasladables.add(trasladarSiNecesario(LocalDate.of(año, 3, 19))); // San José
        festivosTrasladables.add(trasladarSiNecesario(LocalDate.of(año, 6, 29))); // San Pedro y San Pablo
        festivosTrasladables.add(trasladarSiNecesario(LocalDate.of(año, 8, 15))); // Asunción de la Virgen
        festivosTrasladables.add(trasladarSiNecesario(LocalDate.of(año, 10, 12))); // Día de la Raza
        festivosTrasladables.add(trasladarSiNecesario(LocalDate.of(año, 11, 1)));  // Todos los Santos
        festivosTrasladables.add(trasladarSiNecesario(LocalDate.of(año, 11, 11))); // Independencia de Cartagena

        LocalDate fechaConsulta = LocalDate.of(año, mes, dia);

        if (festivosFijos.contains(MonthDay.from(fechaConsulta))) {
            return "Es Festivo";
        } else if (festivosMovibles.contains(fechaConsulta)) {
            return "Es Festivo";
        } else if (festivosTrasladables.contains(fechaConsulta)) {
            return "Es Festivo";
        } else {
            return "No Es Festivo";
        }
    }

    private LocalDate calcularDomingoPascua(int año) {
        // Algoritmo de Meeus/Jones/Butcher para calcular el Domingo de Pascua
        int a = año % 19;
        int b = año / 100;
        int c = año % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int mesPascua = (h + l - 7 * m + 114) / 31;
        int diaPascua = ((h + l - 7 * m + 114) % 31) + 1;
        return LocalDate.of(año, mesPascua, diaPascua);
    }

    private LocalDate trasladarSiNecesario(LocalDate fecha) {
        if (fecha.getDayOfWeek() != DayOfWeek.MONDAY) {
            int diasParaElProximoLunes = 8 - fecha.getDayOfWeek().getValue();
            fecha = fecha.plusDays(diasParaElProximoLunes);
        }
        return fecha;
    }

    private boolean esFechaValida(int dia, int mes, int año) {
        try {
            LocalDate.of(año, mes, dia);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
    
}
