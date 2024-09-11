package com.smartjob.bci.ejercicio.controlador;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartjob.bci.ejercicio.entidad.User;
import com.smartjob.bci.ejercicio.enums.NombreEntidadEnum;
import com.smartjob.bci.ejercicio.servicio.UserServicio;
import com.smartjob.bci.ejercicio.util.Constantes;
import com.smartjob.bci.ejercicio.util.RespuestaControlador;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class UserControlador extends BaseControladorImpl<User, UUID> implements BaseControlador<User, UUID> {

    @Autowired
    private UserServicio userServicio;
    
    @Autowired
    public UserControlador(UserServicio userServicio) {
        super(userServicio, NombreEntidadEnum.USER.getValor());
    }

    @PostMapping("logeo")
    public ResponseEntity<RespuestaControlador> logeo(@RequestParam("email") String email, @RequestParam("password") String password) {
        RespuestaControlador respuestaControlador;
        HttpStatus httpStatus;
        try {
            respuestaControlador = userServicio.logeo(email, password);
            httpStatus = HttpStatus.OK;
        } catch (Exception exception) {
            logger.error(exception, exception);
            respuestaControlador = respuestaControladorServicio.obtenerRespuestaDeErrorActualizar(NombreEntidadEnum.USER.getValor());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        respuestaControlador.setCodeStatus(String.valueOf(httpStatus.value()));

        return new ResponseEntity<>(respuestaControlador, httpStatus);
    }

}