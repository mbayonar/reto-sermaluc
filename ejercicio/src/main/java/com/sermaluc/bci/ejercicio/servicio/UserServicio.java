package com.sermaluc.bci.ejercicio.servicio;

import java.util.UUID;

import com.sermaluc.bci.ejercicio.entidad.User;
import com.sermaluc.bci.ejercicio.util.RespuestaControlador;

public interface UserServicio extends BaseServicio<User, UUID> {

	public RespuestaControlador logeo(String login, String contrasena);

    public User obtenerPorCorreo(String email);

}
