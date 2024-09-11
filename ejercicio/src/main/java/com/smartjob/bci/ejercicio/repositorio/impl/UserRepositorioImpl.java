package com.smartjob.bci.ejercicio.repositorio.impl;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.smartjob.bci.ejercicio.entidad.User;
import com.smartjob.bci.ejercicio.repositorio.UserRepositorio;

@Repository
public class UserRepositorioImpl extends BaseRepositorioImpl<User, UUID> implements UserRepositorio {

}