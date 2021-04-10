package python;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class JavaPythonScraperUnitTest {

    private static final String LOCAL_PYTHON = "C:\\Users\\X5\\AppData\\Local\\Programs\\Python\\Python37\\python ";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenPythonScript_whenPythonProcessInvoked_thenSuccess2() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(
                LOCAL_PYTHON,
                resolvePythonScriptPath("testing-transpose.py"));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        List<String> results = readProcessOutput(process.getInputStream());

        assertThat("Results should not be empty", results, is(not(empty())));
        assertThat("Results should contain output of script: ", results, hasItem(containsString(
                "[Unnamed: 0, MERCURY, VENUS, EARTH, MOON, MARS, JUPITER, SATURN, URANUS, NEPTUNE, PLUTO]")));
        int exitCode = process.waitFor();
        assertEquals("No errors should be detected", 0, exitCode);
    }


    @Test
    public void givenPythonScript_whenPythonProcessInvoked_thenSuccess() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(
                LOCAL_PYTHON,
                resolvePythonScriptPath("testing-transpose.py"));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        List<String> results = readProcessOutput(process.getInputStream());
        assertThat("Results should not be empty", results, is(not(empty())));
        assertThat("Results should contain output of script: ", results, hasItem(containsString(
                "Unnamed: 0, MERCURY, VENUS, EARTH, MOON, MARS, JUPITER, SATURN, URANUS, NEPTUNE, PLUTO")));
        int exitCode = process.waitFor();
        assertEquals("No errors should be detected", 0, exitCode);
    }

    @Test
    public void givenPythonInterpreter_whenPrintExecuted_thenOutputDisplayed() {
        try (PythonInterpreter pyInterp = new PythonInterpreter()) {
            StringWriter output = new StringWriter();
            pyInterp.setOut(output);

            pyInterp.exec("print('Hello Devstronomers!!')");
            assertEquals("Should contain script output: ", "Hello Devstronomers!!", output.toString()
                .trim());
        }
    }

    @Test
    public void givenPythonInterpreter_whenNumbersAdded_thenOutputDisplayed() {
        try (PythonInterpreter pyInterp = new PythonInterpreter()) {
            pyInterp.exec("x = 10+10");
            PyObject x = pyInterp.get("x");
            assertEquals("x: ", 20, x.asInt());
        }
    }

    @Test
    public void givenPythonInterpreter_whenErrorOccurs_thenExceptionIsThrown() {
        thrown.expect(PyException.class);
        thrown.expectMessage("ImportError: No module named syds");

        try (PythonInterpreter pyInterp = new PythonInterpreter()) {
            pyInterp.exec("import syds");
        }
    }

    @Test
    public void givenPythonScript_whenPythonProcessExecuted_thenSuccess() throws ExecuteException, IOException {
        // String line = "python " + resolvePythonScriptPath("hello.py");
        String line = LOCAL_PYTHON + resolvePythonScriptPath("hello.py");
        CommandLine cmdLine = CommandLine.parse(line);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(streamHandler);

        int exitCode = executor.execute(cmdLine);
        assertEquals("No errors should be detected", 0, exitCode);
        assertEquals("Should contain script output: ", "Hello Devstronomers!!", outputStream.toString()
            .trim());
    }

    private List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                .collect(Collectors.toList());
        }
    }

    private String resolvePythonScriptPath(String filename) {
        File file = new File("src/test/resources/" + filename);
        return file.getAbsolutePath();
    }

}
