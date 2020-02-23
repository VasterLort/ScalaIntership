CREATE VIEW trip_view AS
SELECT trip_id, trip_duration, station_start_id, station_end_id, ui.user_type, ui.gender, ui.year_of_birth, bike_id, start_time, end_time
FROM Trip
LEFT JOIN user_info ui ON ui.user_id = trip.user_id;