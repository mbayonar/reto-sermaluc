package com.sermaluc.bci.ejercicio.servicio.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sermaluc.bci.ejercicio.entidad.User;
import com.sermaluc.bci.ejercicio.excepcion.EntidadDuplicadaExcepcion;
import com.sermaluc.bci.ejercicio.repositorio.PhoneRepositorio;
import com.sermaluc.bci.ejercicio.repositorio.UserRepositorio;
import com.sermaluc.bci.ejercicio.util.RespuestaControlador;
import com.sermaluc.bci.ejercicio.util.RespuestaControladorServicio;

import junit.framework.Assert;

public class UserServicioImplTest {

	@InjectMocks
    UserServicioImpl userServicioImpl;

    @Mock
    UserRepositorio userRepositorio;

    @Mock
    PhoneRepositorio phoneRepositorio;

    @Mock
    RespuestaControladorServicio respuestaControladorServicio;

	AutoCloseable closeable;

	@BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        closeable = MockitoAnnotations.openMocks(this);
    }

	@Test
	public void getUsersTest() {
		//doReturn(new ArrayList<User>()).when(userRepositorio).buscarPorCriteriaSinProyecciones(any());
		when(userRepositorio.buscarPorCriteriaSinProyecciones(any()))
        .thenReturn(new ArrayList<User>());

		List<User> listaUsers = userServicioImpl.obtenerTodos();
		Assert.assertEquals(0, listaUsers.size());
	}

}
