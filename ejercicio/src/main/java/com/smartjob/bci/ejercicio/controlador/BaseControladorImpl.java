package com.smartjob.bci.ejercicio.controlador;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.smartjob.bci.ejercicio.excepcion.EntidadDuplicadaExcepcion;
import com.smartjob.bci.ejercicio.servicio.BaseServicio;
import com.smartjob.bci.ejercicio.util.RespuestaControlador;
import com.smartjob.bci.ejercicio.util.RespuestaControladorServicio;
import com.smartjob.bci.ejercicio.util.SistemaUtil;

public abstract class BaseControladorImpl<Entidad, TipoLlave> implements BaseControlador<Entidad, TipoLlave> {

    public final Logger logger = LogManager.getLogger(getClass());

    private final BaseServicio<Entidad, TipoLlave> servicio;

    public final String nombreEntidad;

    @Autowired
    public RespuestaControladorServicio respuestaControladorServicio;

    public BaseControladorImpl(BaseServicio<Entidad, TipoLlave> servicio, String nombreEntidad) {
        this.servicio = servicio;
        this.nombreEntidad = nombreEntidad;
    }

    @PostMapping
    @Override
    public ResponseEntity<RespuestaControlador> crear(@RequestBody Entidad entidad) {
        RespuestaControlador respuestaControlador;
        try {
            respuestaControlador = servicio.crear(entidad);
            if (SistemaUtil.esNoNulo(respuestaControlador) && !SistemaUtil.esNoNulo(respuestaControlador.getCodeStatus())) {
                respuestaControlador.setCodeStatus(String.valueOf(HttpStatus.OK.value()));
            }
        } catch (EntidadDuplicadaExcepcion ede) { // Excepción de entidad duplicada
            respuestaControlador = RespuestaControlador.obtenerRespuestaDeError(ede.getMessage());
            respuestaControlador.setCodeStatus(String.valueOf(HttpStatus.OK.value()));
        } catch (Exception exception) {
            logger.error(exception, exception);
            respuestaControlador = respuestaControladorServicio.obtenerRespuestaDeErrorCrear(nombreEntidad);
            respuestaControlador.setCodeStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
        return new ResponseEntity<>(respuestaControlador, HttpStatus.OK);
    }

    @GetMapping
    @Override
    public ResponseEntity<RespuestaControlador> obtenerTodos() {
        RespuestaControlador respuestaControlador;
        try {
        	List<Entidad> data = servicio.obtenerTodos();
        	if (data.size() > 0) {
        		respuestaControlador = RespuestaControlador.obtenerRespuestaExitoConData(data);
        	} else {
        		respuestaControlador = RespuestaControlador.obtenerRespuestaDeError("No se encontraron registros.");
        	}
            respuestaControlador.setCodeStatus(String.valueOf(HttpStatus.OK.value()));
        } catch (Exception exception) {
            logger.error(exception, exception);
            respuestaControlador = respuestaControladorServicio.obtenerRespuestaDeErrorListar(nombreEntidad);
            respuestaControlador.setCodeStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
        return new ResponseEntity<>(respuestaControlador, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @Override
    public ResponseEntity<RespuestaControlador> obtener(@PathVariable("id") TipoLlave id) {
        RespuestaControlador respuestaControlador;
        try {
        	Entidad entidad = servicio.obtener(id);
        	if (SistemaUtil.esNoNulo(entidad)) {
                respuestaControlador = RespuestaControlador.obtenerRespuestaExitoConData(entidad);
        	} else {
        		respuestaControlador = RespuestaControlador.obtenerRespuestaDeError("No se encontró el registro.");
        	}
            respuestaControlador.setCodeStatus(String.valueOf(HttpStatus.OK.value()));
        } catch (Exception exception) {
            logger.error(exception, exception);
            respuestaControlador = respuestaControladorServicio.obtenerRespuestaDeErrorObtener(nombreEntidad);
            respuestaControlador.setCodeStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
        return new ResponseEntity<>(respuestaControlador, HttpStatus.OK);
    }

    @PutMapping
    @Override
    public ResponseEntity<RespuestaControlador> actualizar(@RequestBody Entidad entidad) {
        RespuestaControlador respuestaControlador;
        try {
            respuestaControlador = servicio.actualizar(entidad);
            respuestaControlador.setCodeStatus(String.valueOf(HttpStatus.OK.value()));
        } catch (EntidadDuplicadaExcepcion ede) {
            respuestaControlador = RespuestaControlador.obtenerRespuestaDeError(ede.getMessage());
            respuestaControlador.setCodeStatus(String.valueOf(HttpStatus.OK.value()));
        } catch (Exception exception) {
            logger.error(exception, exception);
            respuestaControlador = respuestaControladorServicio.obtenerRespuestaDeErrorActualizar(nombreEntidad);
            respuestaControlador.setCodeStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
        return new ResponseEntity<>(respuestaControlador, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    @Override
    public ResponseEntity<RespuestaControlador> eliminar(@PathVariable("id") TipoLlave id) {
        RespuestaControlador respuestaControlador;
        try {
            respuestaControlador = servicio.eliminar(id);
            respuestaControlador.setCodeStatus(String.valueOf(HttpStatus.OK.value()));
        } catch (Exception exception) {
            logger.error(exception, exception);
            respuestaControlador = respuestaControladorServicio.obtenerRespuestaDeErrorEliminar(nombreEntidad);
            respuestaControlador.setCodeStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
        return new ResponseEntity<>(respuestaControlador, HttpStatus.OK);
    }

}
