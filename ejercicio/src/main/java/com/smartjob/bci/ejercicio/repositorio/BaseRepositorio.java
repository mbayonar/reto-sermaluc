package com.smartjob.bci.ejercicio.repositorio;

import java.util.List;

import org.hibernate.Session;

import com.smartjob.bci.ejercicio.util.Criterio;


public interface BaseRepositorio<Entidad, TipoLlave> {
    
    Entidad obtener(TipoLlave id);

    void crear(Entidad entidad);

    void actualizar(Entidad entidad);

    void grabarTodos(List<Entidad> list);
    
    Session getCurrentSession();

    List<Entidad> obtenerTodos();
    
    Entidad obtenerPorCriterio(Criterio filtro);

    Entidad obtenerInclusoEliminado(TipoLlave id);

    public List<Entidad> buscarPorCriteria(Criterio filtro);

    public List<Entidad> buscarPorCriteriaSinProyecciones(Criterio filtro);
    
    @SuppressWarnings("rawtypes")
    List proyeccionPorCriteria(Criterio filtro, Class resultado);
    
    public Object obtenerConResultSet(Criterio filtro, Class resultado);
    
}
