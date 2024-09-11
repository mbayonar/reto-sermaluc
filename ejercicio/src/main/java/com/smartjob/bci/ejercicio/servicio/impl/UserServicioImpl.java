package com.smartjob.bci.ejercicio.servicio.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.smartjob.bci.ejercicio.entidad.Phone;
import com.smartjob.bci.ejercicio.entidad.User;
import com.smartjob.bci.ejercicio.enums.NombreEntidadEnum;
import com.smartjob.bci.ejercicio.excepcion.EntidadDuplicadaExcepcion;
import com.smartjob.bci.ejercicio.repositorio.PhoneRepositorio;
import com.smartjob.bci.ejercicio.repositorio.UserRepositorio;
import com.smartjob.bci.ejercicio.servicio.UserServicio;
import com.smartjob.bci.ejercicio.util.Constantes;
import com.smartjob.bci.ejercicio.util.Criterio;
import com.smartjob.bci.ejercicio.util.RespuestaControlador;
import com.smartjob.bci.ejercicio.util.RespuestaControladorServicio;
import com.smartjob.bci.ejercicio.util.SistemaUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServicioImpl extends BaseServicioImpl<User, UUID> implements UserServicio {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private RespuestaControladorServicio respuestaControladorServicio;

    @Autowired
    private PhoneRepositorio phoneRepositorio;

    @Autowired
    private UserRepositorio userRepositorio;

    @Autowired
    protected SessionFactory sessionFactory;

    private String mensajeError;
    
    @Autowired
    public UserServicioImpl(UserRepositorio userRepositorio) {
        super(userRepositorio);
        this.userRepositorio = userRepositorio;
    }

    @Override
    public RespuestaControlador crear(User user) throws EntidadDuplicadaExcepcion {
    	RespuestaControlador respuestaControlador;
    	HttpStatus httpStatus;
    	Timestamp fechaActual = Timestamp.from(Instant.now());
    	this.mensajeError = "";
    	if (validarUsuario(user)) {
    		user.setLast_login(fechaActual);
    		user.setCreated(fechaActual);
    		user.setIsactive(Boolean.TRUE);
	        this.userRepositorio.crear(user);
	        for (Phone phone : user.getPhones()) {
	        	phone.setCreated(fechaActual);
	        	phone.setIsactive(Boolean.TRUE);
	        	phone.setUser(user);
	        	this.phoneRepositorio.crear(phone);
	        }
	    	httpStatus = HttpStatus.OK;
	        respuestaControlador = this.respuestaControladorServicio.obtenerRespuestaDeExitoCrearConData(NombreEntidadEnum.USER.getValor(), user);
    	} else {
        	httpStatus = HttpStatus.OK;
        	respuestaControlador = this.respuestaControladorServicio.obtenerRespuestaDeErrorCrearConMensaje(NombreEntidadEnum.USER.getValor(), mensajeError);
    	}
    	respuestaControlador.setCodeStatus(String.valueOf(httpStatus.value()));
    	return respuestaControlador;
    }

    public boolean validarUsuario(User user) {
    	if (!validarCamposLlenos(user)) {
    		this.mensajeError = "Campos incompletos. " + this.mensajeError;
    		return false;
    	}
    	if (!SistemaUtil.validarEmail(user.getEmail())) {
    		this.mensajeError = "Ingrese un correo válido.";
    		return false;
    	}
    	if (SistemaUtil.esNoNulo(obtenerPorCorreo(user.getEmail()))) {
    		this.mensajeError = "El correo ya ha sido registrado anteriormente.";
    		return false;
    	}
    	if (!SistemaUtil.validarPassword(user.getPassword())) {
    		this.mensajeError = "Ingrese una contraseña válida.";
    		return false;
    	}
    	return true;
    }

    public boolean validarCamposLlenos(User user) {
    	if (!SistemaUtil.esNoNulo(user.getName()) || SistemaUtil.esCadenaVacia(user.getName().trim())) {
    		this.mensajeError = "No ha ingresado nombre.";
    		return false;
    	}
    	if (!SistemaUtil.esNoNulo(user.getEmail()) || SistemaUtil.esCadenaVacia(user.getEmail().trim())) {
    		this.mensajeError = "No ha ingresado correo.";
    		return false;
    	}
    	if (!SistemaUtil.esNoNulo(user.getPassword()) || SistemaUtil.esCadenaVacia(user.getPassword().trim())) {
    		this.mensajeError = "No ha ingresado contraseña.";
    		return false;
    	}
    	return true;
    }

    @Override
    public RespuestaControlador actualizar(User user) throws EntidadDuplicadaExcepcion {
    	Timestamp fechaActual = Timestamp.from(Instant.now());
    	user.setModified(fechaActual);
        this.userRepositorio.actualizar(user);
        return respuestaControladorServicio.obtenerRespuestaDeExitoActualizar(NombreEntidadEnum.USER.getValor());
    }

    @Override
    public RespuestaControlador eliminar(UUID userId) {
        RespuestaControlador respuesta;
        User user;
        Boolean puedeEliminar;

        puedeEliminar = true;

        if (puedeEliminar == null || !puedeEliminar) {
            respuesta = RespuestaControlador.obtenerRespuestaDeError("El " + NombreEntidadEnum.USER.getValor().toLowerCase() + " ha sido asignado a uno o varios usuarios y no se puede eliminar");
        } else {
            user = userRepositorio.obtener(userId);
            if (SistemaUtil.esNoNulo(user)) {
            	user.setIsactive(Boolean.FALSE);
            }
            userRepositorio.actualizar(user);
            respuesta = respuestaControladorServicio.obtenerRespuestaDeExitoEliminar(NombreEntidadEnum.USER.getValor());
        }

        return respuesta;
    }

    @Override
    public User obtener(UUID id) {
    	User user;
        Criterio filtro = Criterio.forClass(User.class);
        filtro.createAlias("phones", "p", JoinType.LEFT_OUTER_JOIN);
        filtro.add(Restrictions.eq("isactive", Boolean.TRUE));
        filtro.add(Restrictions.eq("id", id));
        user = userRepositorio.obtenerPorCriterio(filtro);
        return user;
    }

    @Override
    public User obtenerPorCorreo(String email) {
    	User user;
        Criterio filtro = Criterio.forClass(User.class);
        filtro.createAlias("phones", "p", JoinType.LEFT_OUTER_JOIN);
        filtro.add(Restrictions.eq("isactive", Boolean.TRUE));
        filtro.add(Restrictions.eq("email", email));
        user = userRepositorio.obtenerPorCriterio(filtro);
        return user;
    }

    @Override
    public List<User> obtenerTodos() {
        Criterio filtro = Criterio.forClass(User.class);
        filtro.createAlias("phones", "p", JoinType.LEFT_OUTER_JOIN);
        filtro.add(Restrictions.eq("isactive", Boolean.TRUE));

        return userRepositorio.buscarPorCriteriaSinProyecciones(filtro);
    }

    @Override
	public RespuestaControlador logeo(String login, String contrasena) {
    	RespuestaControlador respuesta = RespuestaControlador.obtenerRespuestaDeError(Constantes.RESPUESTA_CONTROLADOR.MENSAJE_ERROR_AUTENTICACION);
		String token;
		Criterio filtro;

		filtro = Criterio.forClass(User.class);
		filtro.add(Restrictions.eq("email", login));
		filtro.add(Restrictions.eq("password", contrasena));
		filtro.add(Restrictions.eq("isactive", Boolean.TRUE));

		token = "";
		User usuarioSession = userRepositorio.obtenerPorCriterio(filtro);
		if (SistemaUtil.esNoNulo(usuarioSession)) {
			token = this.generarJWToken(login);
            usuarioSession.setToken(token);
            usuarioSession.setLast_login(Timestamp.from(Instant.now()));
            this.userRepositorio.actualizar(usuarioSession);
            respuesta = RespuestaControlador.obtenerRespuestaExitoConData(usuarioSession);
		}
		return respuesta;
	}

	private String generarJWToken(String usuario) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("softtekJWT").setSubject(usuario)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

}