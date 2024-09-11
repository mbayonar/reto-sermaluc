package com.smartjob.bci.ejercicio.entidad;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Data;

@Data
@MappedSuperclass
@JsonAutoDetect
public class AuditoriaEntidad implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    //@JsonIgnore
    @Column(name = "created", updatable = false)
    private Timestamp created;

    //@JsonIgnore
    @Column(name = "modified")
    private Timestamp modified;

    //@JsonIgnore
    @Column(name = "isactive")
    private Boolean isactive;

    public AuditoriaEntidad() {
    }

    public AuditoriaEntidad(UUID  id) {
        this();
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AuditoriaEntidad other = (AuditoriaEntidad) obj;
        return Objects.equals(this.id, other.id);
    }

}
