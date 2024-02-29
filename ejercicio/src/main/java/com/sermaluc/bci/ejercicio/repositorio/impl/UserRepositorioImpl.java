package com.sermaluc.bci.ejercicio.repositorio.impl;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.sermaluc.bci.ejercicio.entidad.User;
import com.sermaluc.bci.ejercicio.repositorio.UserRepositorio;

@Repository
public class UserRepositorioImpl extends BaseRepositorioImpl<User, UUID> implements UserRepositorio {

}