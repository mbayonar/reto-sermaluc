package com.smartjob.bci.ejercicio.repositorio.impl;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.smartjob.bci.ejercicio.entidad.Phone;
import com.smartjob.bci.ejercicio.repositorio.PhoneRepositorio;

@Repository
public class PhoneRepositorioImpl extends BaseRepositorioImpl<Phone, UUID> implements PhoneRepositorio {

}
