package com.apifestivos.festivos.core.interfaces.servicios;

import java.util.List;

import com.apifestivos.festivos.core.entidades.Festivo;

public interface IFestivoServicio {

    public String consultar(int day, int month, int year);

    public List<Festivo> listar(int year);
}
