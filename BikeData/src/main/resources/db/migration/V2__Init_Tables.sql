CREATE TABLE user_type(
	user_type_id BIGSERIAL,
	name_user_type VARCHAR (20) NOT NULL,
	description_name_user_type VARCHAR (50) NOT NULL,
	PRIMARY KEY (user_type_id)
);

CREATE TABLE user_info(
	user_id BIGSERIAL,
	gender_id gender NOT NULL ,
	user_type_id BIGINT NOT NULL,
	year_of_birth INT NOT NULL,
	PRIMARY KEY (user_id),
	FOREIGN KEY (user_type_id) REFERENCES user_type(user_type_id)
);

CREATE TABLE station(
	station_id BIGINT NOT NULL,
	name_station VARCHAR (50) NOT NULL,
	latitude DOUBLE PRECISION NOT NULL,
	longitude DOUBLE PRECISION NOT NULL,
	PRIMARY KEY (station_id)
);

CREATE TABLE bike(
	bike_id BIGSERIAL,
	date_of_appearance DATE NOT NULL,
	last_usage DATE,
	PRIMARY KEY (bike_id)
);

CREATE TABLE trip(
	trip_id BIGSERIAL,
	trip_duration bigint,
	station_start_id bigint,
	station_end_id bigint,
	user_id bigint,
	bike_id bigint,
	PRIMARY KEY (trip_id),
	FOREIGN KEY (station_start_id) REFERENCES station(station_id),
	FOREIGN KEY (station_end_id) REFERENCES station(station_id),
	FOREIGN KEY (user_id) REFERENCES user_info(user_id),
	FOREIGN KEY (bike_id) REFERENCES bike(bike_id)
);