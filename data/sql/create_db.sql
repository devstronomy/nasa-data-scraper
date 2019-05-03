-- Prepares empty 'devstronomy' database.
--
-- 'jconverter' tool currently depends on the empty database being prepared.

CREATE DATABASE devstronomy;

USE devstronomy;

CREATE TABLE planet (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  mass DECIMAL(16,6) NOT NULL,
  diameter DECIMAL(16,6) NOT NULL,
  density DECIMAL(16,6) NOT NULL,
  gravity DECIMAL(16,6) NOT NULL,
  escape_velocity DECIMAL(16,6) NOT NULL,
  rotation_period DECIMAL(16,6) NOT NULL,
  length_of_day DECIMAL(16,6) NOT NULL,
  distance_from_sun DECIMAL(16,6) NOT NULL,
  perihelion DECIMAL(16,6) NOT NULL,
  aphelion DECIMAL(16,6) NOT NULL,
  orbital_period DECIMAL(16,6) NOT NULL,
  orbital_velocity DECIMAL(16,6) NOT NULL,
  orbital_inclination DECIMAL(16,6) NOT NULL,
  orbital_eccentricity DECIMAL(16,6) NOT NULL,
  obliquity_to_orbit DECIMAL(16,6) NOT NULL,
  mean_temperature DECIMAL(16,6) NOT NULL,
  surface_pressure DECIMAL(16,6) NULL,
  number_of_moons INT NOT NULL,
  has_ring_system BOOLEAN NOT NULL,
  has_global_magnetic_field BOOLEAN NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE satellite (
  id INT NOT NULL AUTO_INCREMENT,
  planet_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  gm DECIMAL(16,6) NOT NULL,
  radius DECIMAL(16,6) NOT NULL,
  density DECIMAL(16,6) NULL,
  magnitude DECIMAL(16,6) NULL,
  albedo DECIMAL(16,6) NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (planet_id)
    REFERENCES planet(id)
    ON DELETE CASCADE
);
