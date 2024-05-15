package com.apifestivos.festivos.core.interfaces.servicios;

import java.util.List;

import com.apifestivos.festivos.core.entidades.Tipo;

public interface ITipoServicio {
    
    public List<Tipo> listar();

    public Tipo obtener(int id);

    public Tipo agregar(Tipo tipo);

    public Tipo modificar(Tipo tipo);

    public boolean eliminar(int id);
}
