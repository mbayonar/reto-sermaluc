-- TABLA USER

DROP TABLE user IF EXISTS;
CREATE TABLE user(
	id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
	name VARCHAR(100),
	email VARCHAR(100),
	password VARCHAR(100),
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	modified TIMESTAMP NULL,
	last_login TIMESTAMP,
	isactive boolean DEFAULT true
);

INSERT INTO user (id, name, email, password, created, modified, last_login) VALUES ('1c5cea0e-c0b3-4e9d-838e-c05e80d40591', 'Juan Rodríguez', 'juan@rodriguez.org', 'hunter2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO user (id, name, email, password, created, modified, last_login) VALUES ('772fc8d8-363f-4e4c-8b42-b50b93e1779f', 'Marcos Bayona', 'marcos280798br@gmail.com', 'marcosbr', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO user (id, name, email, password, created, modified, last_login) VALUES ('cadbb456-0f3d-4269-b218-206f9fd0daea', 'Jeremy Agurto', 'jeremy@gmail.com', '123456', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- TABLA PHONE

DROP TABLE phone IF EXISTS;
CREATE TABLE phone(
	id UUID DEFAULT RANDOM_UUID() PRIMARY KEY, 
	id_user uuid, 
	number VARCHAR(9), 
	citycode INTEGER(11), 
	countrycode INTEGER(11), 
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
	modified TIMESTAMP NULL, 
	isactive boolean DEFAULT true, 
	FOREIGN KEY (id_user) references user(id)
);

INSERT INTO phone(id_user, number, citycode, countrycode, created, modified) VALUES('1c5cea0e-c0b3-4e9d-838e-c05e80d40591', '1234567', 1, 57, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO phone(id_user, number, citycode, countrycode, created, modified) VALUES('772fc8d8-363f-4e4c-8b42-b50b93e1779f', '947181319', 1, 57, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO phone(id_user, number, citycode, countrycode, created, modified) VALUES('cadbb456-0f3d-4269-b218-206f9fd0daea', '987987987', 1, 57, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO phone(id_user, number, citycode, countrycode, created, modified) VALUES('cadbb456-0f3d-4269-b218-206f9fd0daea', '911222333', 1, 57, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);