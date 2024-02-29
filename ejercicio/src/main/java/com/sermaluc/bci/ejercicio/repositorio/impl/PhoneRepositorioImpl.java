package com.sermaluc.bci.ejercicio.repositorio.impl;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.sermaluc.bci.ejercicio.entidad.Phone;
import com.sermaluc.bci.ejercicio.repositorio.PhoneRepositorio;

@Repository
public class PhoneRepositorioImpl extends BaseRepositorioImpl<Phone, UUID> implements PhoneRepositorio {

}
