package com.smartjob.bci.ejercicio.util;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class RespuestaControlador implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String estado;

    private String codeStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mensaje;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public String getEstado() {
        return estado;
    }

    public RespuestaControlador(String estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public RespuestaControlador(String estado, Object data) {
        this.estado = estado;
        this.data = data;
    }

    public static RespuestaControlador obtenerRespuestaDeError(String mensaje) {
        return new RespuestaControlador(Constantes.RESPUESTA_CONTROLADOR.ESTADO_ERROR, mensaje);
    }

    public static RespuestaControlador obtenerRespuestaDeExito(String mensaje) {
        return new RespuestaControlador(Constantes.RESPUESTA_CONTROLADOR.ESTADO_EXITO, mensaje);
    }

    public static Boolean esRespuestaDeError(RespuestaControlador respuestaControlador) {
        return respuestaControlador == null || respuestaControlador.getEstado().equals(Constantes.RESPUESTA_CONTROLADOR.ESTADO_ERROR);
    }

    public static Boolean esRespuestaDeExito(RespuestaControlador respuestaControlador) {
        return respuestaControlador != null && respuestaControlador.getEstado().equals(Constantes.RESPUESTA_CONTROLADOR.ESTADO_EXITO);
    }

    public static RespuestaControlador obtenerRespuestaExitoConData(Object extraInfo) {
        return new RespuestaControlador(Constantes.RESPUESTA_CONTROLADOR.ESTADO_EXITO, extraInfo);
    }

}
