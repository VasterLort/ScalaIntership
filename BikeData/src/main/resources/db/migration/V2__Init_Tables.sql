CREATE TABLE user_info(
	user_id BIGSERIAL,
	gender Gender NOT NULL,
	user_type usertype NOT NULL,
	year_of_birth VARCHAR (10),
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
	trip_id BIGSERIAL,
	trip_duration BIGINT,
	station_start_id BIGINT,
	station_end_id BIGINT,
	user_id BIGSERIAL,
	bike_id BIGINT,
	start_time VARCHAR (20),
	end_time VARCHAR (20),
	PRIMARY KEY (trip_id),
	FOREIGN KEY (station_start_id) REFERENCES station(station_id),
	FOREIGN KEY (station_end_id) REFERENCES station(station_id),
	FOREIGN KEY (user_id) REFERENCES user_info(user_id),
	FOREIGN KEY (bike_id) REFERENCES bike(bike_id)
);