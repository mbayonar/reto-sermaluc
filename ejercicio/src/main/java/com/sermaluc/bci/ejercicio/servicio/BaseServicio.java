package com.sermaluc.bci.ejercicio.servicio;

import java.util.List;

import com.sermaluc.bci.ejercicio.excepcion.EntidadDuplicadaExcepcion;
import com.sermaluc.bci.ejercicio.util.RespuestaControlador;

public interface BaseServicio<Entidad, TipoLlave> {

    Entidad obtener(TipoLlave id);

    void grabarTodos(List<Entidad> list) throws EntidadDuplicadaExcepcion;

    List<Entidad> obtenerTodos();

    public RespuestaControlador crear(Entidad entidad) throws EntidadDuplicadaExcepcion;

    public RespuestaControlador actualizar(Entidad entidad) throws EntidadDuplicadaExcepcion;

    public RespuestaControlador eliminar(TipoLlave entidadId);

}
