package com.devstronomy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Reads data from planets CSV file and insert them into the SQL database.
 *
 * <p>
 * Runner for {@link Converter}.
 * </p>
 */
@SpringBootApplication
class ConverterRunner {

    public static void main(String[] args) {
        SpringApplication.run(ConverterRunner.class, args);
    }

}
