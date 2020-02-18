CREATE TABLE user_info(
	user_id BIGSERIAL,
	gender gender NOT NULL ,
	user_type user_type NOT NULL,
	year_of_birth VARCHAR (8),
	PRIMARY KEY (user_id)
);

CREATE TABLE station(
	station_id BIGINT NOT NULL,
	name_station VARCHAR (50) NOT NULL,
	latitude DOUBLE PRECISION NOT NULL,
	longitude DOUBLE PRECISION NOT NULL,
	PRIMARY KEY (station_id)
);

CREATE TABLE bike(
	bike_id BIGINT NOT NULL,
	date_of_appearance VARCHAR (50),
	last_usage VARCHAR (50),
	PRIMARY KEY (bike_id)
);

CREATE TABLE trip(
	trip_id BIGINT NOT NULL,
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