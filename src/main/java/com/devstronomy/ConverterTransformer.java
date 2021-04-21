package com.devstronomy;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reads data from planets https://nssdc.gsfc.nasa.gov/planetary/factsheet/  and insert them into .csv file.
 */
@Component
@Order(2)
final class ConverterTransformer implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(ConverterTransformer.class);

    @Autowired
    private YAMLConfig myConfig;

    private static final String PLANETS_CSV_PATH = "./data/raw/";
    private static final String PYTHON_SCRIPT_NAME = "planets-nasa-from-scraper-to-transformer.py";

    @Override
    public void run(String... args) throws Exception {
        transform();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private void transform() throws Exception {
        try {
            LOG.info("Creating Process Builder");
            ProcessBuilder processBuilder = new ProcessBuilder(
                    myConfig.getPathLocalPython(),
                    resolvePythonScriptPath(PYTHON_SCRIPT_NAME)).inheritIO();
            processBuilder.redirectErrorStream(true);
            LOG.info("ProcessBuilder in Transformer Starting ");
            Process process = processBuilder.start();
            process.waitFor();
            LOG.info("ProcesBuilder in Transformer  Finished");
        } catch (
                FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e ) {
            LOG.error("Missing absolute path for python in application.yml file");
        }
    }

    private List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                    .collect(Collectors.toList());
        }
    }

    private String resolvePythonScriptPath(String filename) {
        File file = new File(PLANETS_CSV_PATH + filename);
        return file.getAbsolutePath();
    }
}
