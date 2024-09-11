package com.smartjob.bci.ejercicio.servicio.impl;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.smartjob.bci.ejercicio.entidad.Phone;
import com.smartjob.bci.ejercicio.entidad.User;
import com.smartjob.bci.ejercicio.excepcion.EntidadDuplicadaExcepcion;
import com.smartjob.bci.ejercicio.repositorio.PhoneRepositorio;
import com.smartjob.bci.ejercicio.repositorio.UserRepositorio;
import com.smartjob.bci.ejercicio.servicio.impl.UserServicioImpl;
import com.smartjob.bci.ejercicio.util.RespuestaControlador;
import com.smartjob.bci.ejercicio.util.RespuestaControladorServicio;

@ExtendWith(MockitoExtension.class)
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
        when(userRepositorio.buscarPorCriteriaSinProyecciones(any())).thenReturn(new ArrayList<User>());

        List<User> listaUsers = userServicioImpl.obtenerTodos();
        assertEquals(0, listaUsers.size());
    }

    @Test
    public void crearUsuarioExitosoTest() throws EntidadDuplicadaExcepcion {
        User usuario = new User();
        usuario.setName("Marcos Mariano");
        usuario.setEmail("prueba@gmail.com");
        usuario.setPassword("M@rcos123456");
        usuario.setPhones(new ArrayList<>());

        RespuestaControlador respuestaEsperada = new RespuestaControlador(HttpStatus.OK.getReasonPhrase(), String.valueOf(HttpStatus.OK.value()));

        doNothing().when(userRepositorio).crear(any(User.class));
        when(respuestaControladorServicio.obtenerRespuestaDeExitoCrearConData(anyString(), any(User.class))).thenReturn(respuestaEsperada);

        RespuestaControlador respuestaActual = userServicioImpl.crear(usuario);

        assertSame(respuestaEsperada, respuestaActual);
        assertEquals(HttpStatus.OK.getReasonPhrase(), respuestaActual.getEstado());
        assertEquals(String.valueOf(HttpStatus.OK.value()), respuestaActual.getCodeStatus());
    }

    @Test
    public void crearUsuarioConEmailInvalidoTest() throws EntidadDuplicadaExcepcion {
        User usuario = new User();
        usuario.setEmail("correoIncorrecto");
        usuario.setPassword("123456");
        usuario.setPhones(new ArrayList<>());

        RespuestaControlador respuestaErrorEsperada = new RespuestaControlador(HttpStatus.OK.getReasonPhrase(), String.valueOf(HttpStatus.OK.value()));

        when(respuestaControladorServicio.obtenerRespuestaDeErrorCrearConMensaje(anyString(), anyString())).thenReturn(respuestaErrorEsperada);

        RespuestaControlador respuestaActual = userServicioImpl.crear(usuario);

        assertSame(respuestaErrorEsperada, respuestaActual);
        assertEquals(HttpStatus.OK.getReasonPhrase(), respuestaActual.getEstado());
        assertEquals(String.valueOf(HttpStatus.OK.value()), respuestaActual.getCodeStatus());
    }
}
