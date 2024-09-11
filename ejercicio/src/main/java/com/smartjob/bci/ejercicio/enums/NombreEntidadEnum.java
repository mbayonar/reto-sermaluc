package com.smartjob.bci.ejercicio.enums;

public enum NombreEntidadEnum {
    
    USER("User"),
    PHONE("Phone");
    
    private String valor;

    private NombreEntidadEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
}