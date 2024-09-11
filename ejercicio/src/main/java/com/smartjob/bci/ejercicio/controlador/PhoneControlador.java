package com.smartjob.bci.ejercicio.controlador;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smartjob.bci.ejercicio.entidad.Phone;
import com.smartjob.bci.ejercicio.enums.NombreEntidadEnum;
import com.smartjob.bci.ejercicio.servicio.PhoneServicio;

@RestController
@RequestMapping("/phone")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class PhoneControlador extends BaseControladorImpl<Phone, UUID> implements BaseControlador<Phone, UUID> {

    @Autowired
    private PhoneServicio phoneServicio;
    
    @Autowired
    public PhoneControlador(PhoneServicio phoneServicio) {
        super(phoneServicio, NombreEntidadEnum.PHONE.getValor());
    }

}