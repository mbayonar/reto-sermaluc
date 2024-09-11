package com.smartjob.bci.ejercicio.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "phone")
@DynamicUpdate(value = true)
@DynamicInsert(value = true)
@SelectBeforeUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Phone extends AuditoriaEntidad {

	@Column(name = "number", nullable = false)
	private String number;

	@Column(name = "citycode")
	private String citycode;

	@Column(name = "countrycode")
	private String countrycode;

	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

	public Phone() {
	}
}