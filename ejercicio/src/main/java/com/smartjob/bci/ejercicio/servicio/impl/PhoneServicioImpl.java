package com.smartjob.bci.ejercicio.servicio.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartjob.bci.ejercicio.entidad.Phone;
import com.smartjob.bci.ejercicio.enums.NombreEntidadEnum;
import com.smartjob.bci.ejercicio.excepcion.EntidadDuplicadaExcepcion;
import com.smartjob.bci.ejercicio.repositorio.PhoneRepositorio;
import com.smartjob.bci.ejercicio.servicio.PhoneServicio;
import com.smartjob.bci.ejercicio.util.Criterio;
import com.smartjob.bci.ejercicio.util.RespuestaControlador;
import com.smartjob.bci.ejercicio.util.RespuestaControladorServicio;

@Service
public class PhoneServicioImpl extends BaseServicioImpl<Phone, UUID> implements PhoneServicio {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private RespuestaControladorServicio respuestaControladorServicio;

    @Autowired
    private PhoneRepositorio phoneRepositorio;

    @Autowired
    protected SessionFactory sessionFactory;

    @Autowired
    public PhoneServicioImpl(PhoneRepositorio phoneRepositorio) {
        super(phoneRepositorio);
    }

    @Override
    public RespuestaControlador crear(Phone phone) throws EntidadDuplicadaExcepcion {
    	Timestamp fechaActual = Timestamp.from(Instant.now());
    	phone.setCreated(fechaActual);
        this.phoneRepositorio.crear(phone);
        return this.respuestaControladorServicio.obtenerRespuestaDeExitoCrearConData(NombreEntidadEnum.PHONE.getValor(), phone.getId());
    }

    @Override
    public RespuestaControlador actualizar(Phone phone) throws EntidadDuplicadaExcepcion {
    	Timestamp fechaActual = Timestamp.from(Instant.now());
    	phone.setModified(fechaActual);
        this.phoneRepositorio.actualizar(phone);
        return respuestaControladorServicio.obtenerRespuestaDeExitoActualizar(NombreEntidadEnum.PHONE.getValor());
    }

    @Override
    public RespuestaControlador eliminar(UUID phoneId) {
        RespuestaControlador respuesta;
        Phone phone;
        Boolean puedeEliminar;
        Timestamp fechaActual = Timestamp.from(Instant.now());

        puedeEliminar = true;

        if (puedeEliminar == null || !puedeEliminar) {
            respuesta = RespuestaControlador.obtenerRespuestaDeError("El " + NombreEntidadEnum.PHONE.getValor().toLowerCase() + " ha sido asignado a uno o varios usuarios y no se puede eliminar");
        } else {
            phone = phoneRepositorio.obtener(phoneId);
        	phone.setModified(fechaActual);
            phone.setIsactive(Boolean.FALSE);
            phoneRepositorio.actualizar(phone);
            respuesta = respuestaControladorServicio.obtenerRespuestaDeExitoEliminar(NombreEntidadEnum.PHONE.getValor());
        }

        return respuesta;
    }

    @Override
    public Phone obtener(UUID id) {
    	Phone phone;
        Criterio filtro = Criterio.forClass(Phone.class);
        filtro.setFetchMode("user", FetchMode.JOIN);
        filtro.add(Restrictions.eq("isactive", Boolean.TRUE));
        filtro.add(Restrictions.eq("id", id));
        phone = phoneRepositorio.obtenerPorCriterio(filtro);
        return phone;
    }

    @Override
    public List<Phone> obtenerTodos() {
        Criterio filtro = Criterio.forClass(Phone.class);
        //filtro.setFetchMode("user", FetchMode.JOIN);
        filtro.add(Restrictions.eq("isactive", Boolean.TRUE));

        return phoneRepositorio.buscarPorCriteriaSinProyecciones(filtro);
    }

}
