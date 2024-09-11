package com.smartjob.bci.ejercicio.util;

import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;

public class TablasH2 {

	public static void crearTablaUser(JdbcTemplate template) {
		String sqlTablaUser;

		sqlTablaUser = "DROP TABLE user IF EXISTS;";
		sqlTablaUser = "CREATE TABLE user(";
		sqlTablaUser = sqlTablaUser + "id UUID DEFAULT RANDOM_UUID() PRIMARY KEY, ";
		sqlTablaUser = sqlTablaUser + "name VARCHAR(100), ";
		sqlTablaUser = sqlTablaUser + "email VARCHAR(100), ";
		sqlTablaUser = sqlTablaUser + "password VARCHAR(100), ";
		sqlTablaUser = sqlTablaUser + "created TIMESTAMP DEFAULT CURRENT_TIMESTAMP, ";
		sqlTablaUser = sqlTablaUser + "modified TIMESTAMP NULL, ";
		sqlTablaUser = sqlTablaUser + "last_login TIMESTAMP, ";
		sqlTablaUser = sqlTablaUser + "isactive boolean DEFAULT true";
		sqlTablaUser = sqlTablaUser + ");";
		template.execute(sqlTablaUser);
	}

	public static void insertarRegistrosTablaUser(JdbcTemplate template) {
		String sqlInsertarMoneda;
		String[] listaUuid = { "1c5cea0e-c0b3-4e9d-838e-c05e80d40591", "772fc8d8-363f-4e4c-8b42-b50b93e1779f", "cadbb456-0f3d-4269-b218-206f9fd0daea"};
		String[] listaUsuarios = { "Juan Rodríguez", "Marcos Bayona", "Jeremy Agurto" };
		String[] listaCorreos = { "juan@rodriguez.org", "marcos280798br@gmail.com", "jeremy@gmail.com" };
		String[] listaContraseñas = { "hunter2", "marcosbr", "123456" };

		for (int i = 1; i < listaUsuarios.length; i++) {
			sqlInsertarMoneda = "";
			sqlInsertarMoneda = sqlInsertarMoneda + "INSERT INTO user (id, name, email, password, created, modified, last_login) VALUES (";
			sqlInsertarMoneda = sqlInsertarMoneda + "'" + listaUuid[i] + "', '" + listaUsuarios[i] + "', '" + listaCorreos[i] + "', '" + listaContraseñas[i] + "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";
			//sqlInsertarMoneda = sqlInsertarMoneda + "INSERT INTO user (name, email, password, created, modified, last_login) VALUES (";
			//sqlInsertarMoneda = sqlInsertarMoneda + "'" + listaUsuarios[i] + "', '" + listaCorreos[i] + "', '" + listaContraseñas[i] + "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";
			template.update(sqlInsertarMoneda);
		}
	}

	public static void crearTablaPhone(JdbcTemplate template) {
		String sqlTablaPhone;

		sqlTablaPhone = "DROP TABLE phone IF EXISTS;";
		sqlTablaPhone = "CREATE TABLE phone(";
		sqlTablaPhone = sqlTablaPhone + "id UUID DEFAULT RANDOM_UUID() PRIMARY KEY, ";
		sqlTablaPhone = sqlTablaPhone + "id_user uuid, ";
		sqlTablaPhone = sqlTablaPhone + "number VARCHAR(9), ";
		sqlTablaPhone = sqlTablaPhone + "citycode INTEGER(11), ";
		sqlTablaPhone = sqlTablaPhone + "countrycode INTEGER(11), ";
		sqlTablaPhone = sqlTablaPhone + "created TIMESTAMP DEFAULT CURRENT_TIMESTAMP, ";
		sqlTablaPhone = sqlTablaPhone + "modified TIMESTAMP NULL, ";
		sqlTablaPhone = sqlTablaPhone + "isactive boolean DEFAULT true, ";
		sqlTablaPhone = sqlTablaPhone + "FOREIGN KEY (id_user) references user(id)";
		sqlTablaPhone = sqlTablaPhone + ");";
		template.execute(sqlTablaPhone);
	}

	public static void insertarRegistrosTablaPhone(JdbcTemplate template) {
		String sqlInsertarPhone;

		sqlInsertarPhone = TablasH2.queryInsertarValoresFijos();

		template.update(sqlInsertarPhone);
	}

	public static String queryInsertarValoresFijos() {
		String sqlInsertarPhone, user1, user2, user3, phone1, phone2, phone3, phone4;

		user1 = "1c5cea0e-c0b3-4e9d-838e-c05e80d40591";
		user2 = "772fc8d8-363f-4e4c-8b42-b50b93e1779f";
		user3 = "cadbb456-0f3d-4269-b218-206f9fd0daea";

		phone1 = "1234567";
		phone2 = "947181319";
		phone3 = "987987987";
		phone4 = "911222333";

		sqlInsertarPhone = "";
/*
		sqlInsertarPhone = sqlInsertarPhone + "INSERT INTO phone(id_user, number, citycode, countrycode, created, modified) VALUES('";
		sqlInsertarPhone = sqlInsertarPhone + user1 + "', '" + phone1 + "', 1, 57, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";
*/
		sqlInsertarPhone = sqlInsertarPhone + "INSERT INTO phone(id_user, number, citycode, countrycode, created, modified) VALUES('";
		sqlInsertarPhone = sqlInsertarPhone + user2 + "', '" + phone2 + "', 1, 57, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";

		sqlInsertarPhone = sqlInsertarPhone + "INSERT INTO phone(id_user, number, citycode, countrycode, created, modified) VALUES('";
		sqlInsertarPhone = sqlInsertarPhone + user3 + "', '" + phone3 + "', 1, 57, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";

		sqlInsertarPhone = sqlInsertarPhone + "INSERT INTO phone(id_user, number, citycode, countrycode, created, modified) VALUES('";
		sqlInsertarPhone = sqlInsertarPhone + user3 + "', '" + phone4 + "', 1, 57, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";
		
		return sqlInsertarPhone;
	}

	public static String queryValoresBD() {
		String cabeceraInsert, sqlInsertarPhone, user1, user2, user3, phone1, phone2, phone3, phone4;
		
		cabeceraInsert = "INSERT INTO phone(id_user, number, citycode, countrycode, created, modified) ";

		user1 = "FROM user WHERE email='juan@rodriguez.org' LIMIT 1; ";
		user2 = "FROM user WHERE email='marcos280798br@gmail.com' LIMIT 1; ";
		user3 = "FROM user WHERE email='jeremy@gmail.com' LIMIT 1; ";

		phone1 = "1234567";
		phone2 = "947181319";
		phone3 = "999555111";
		phone4 = "987456123";

		sqlInsertarPhone = cabeceraInsert + "SELECT id, '" + phone1 + "', 1, 57, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP ";
		sqlInsertarPhone = sqlInsertarPhone + user1;
		
		sqlInsertarPhone = cabeceraInsert + "SELECT id, '" + phone2 + "', 1, 57, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP ";
		sqlInsertarPhone = sqlInsertarPhone + user2;
		
		sqlInsertarPhone = cabeceraInsert + "SELECT id, '" + phone3 + "', 1, 57, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP ";
		sqlInsertarPhone = sqlInsertarPhone + user2;
		
		sqlInsertarPhone = cabeceraInsert + "SELECT id, '" + phone4 + "', 1, 57, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP ";
		sqlInsertarPhone = sqlInsertarPhone + user3;

		return sqlInsertarPhone;
	}
}
