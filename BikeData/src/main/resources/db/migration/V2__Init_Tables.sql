CREATE TABLE user_type(
	id_user_type BIGSERIAL,
	name_user_type VARCHAR (20) NOT NULL,
	description_name_user_type VARCHAR (50) NOT NULL,
	PRIMARY KEY (id_user_type)
);

CREATE TABLE user_info(
	id_user BIGSERIAL,
	id_gender gender NOT NULL ,
	id_user_type BIGINT NOT NULL,
	year_of_birth INT NOT NULL,
	PRIMARY KEY (id_user),
	FOREIGN KEY (id_user_type) REFERENCES user_type(id_user_type)
);

CREATE TABLE station(
	id_station BIGSERIAL,
	name_station VARCHAR (50) NOT NULL,
	latitude DOUBLE PRECISION NOT NULL,
	longitude DOUBLE PRECISION NOT NULL,
	PRIMARY KEY (id_station)
);

CREATE TABLE bike(
	id_bike BIGSERIAL,
	date_of_appearance DATE NOT NULL,
	last_usage DATE,
	PRIMARY KEY (id_bike)
);

CREATE TABLE trip(
	id_trip BIGSERIAL,
	trip_duration bigint,
	id_station_start bigint,
	id_station_end bigint,
	id_user bigint,
	id_bike bigint,
	PRIMARY KEY (id_trip),
	FOREIGN KEY (id_station_start) REFERENCES station(id_station),
	FOREIGN KEY (id_station_end) REFERENCES station(id_station),
	FOREIGN KEY (id_user) REFERENCES user_info(id_user),
	FOREIGN KEY (id_bike) REFERENCES bike(id_bike)
);