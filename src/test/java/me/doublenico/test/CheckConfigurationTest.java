package me.doublenico.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import me.doublenico.scaraGUI.configuration.application.ApplicationModel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CheckConfigurationTest {

    @Test
    public void testLoadConfiguration() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File yamlFile = new File("src/test/resources/test-application.yml");
        ApplicationModel application = mapper.readValue(yamlFile, ApplicationModel.class);
        application.validate();

        assertNotNull(application, "Application should not be null");
        assertEquals("ScaraGui", application.getName(), "Application name should be 'ScaraGui'");
        assertNotNull(application.getOperations(), "Operations should not be null");
        assertEquals(2, application.getOperations().size(), "There should be two operations");
    }

    @Test
    public void testInvalidConfiguration() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File invalidYamlFile = new File("src/test/resources/invalid-application.yml");

        assertThrows(MismatchedInputException.class, () -> {
            ApplicationModel application = mapper.readValue(invalidYamlFile, ApplicationModel.class);
            application.validate();
        }, "Expected a MismatchedInputException to be thrown for invalid YAML");
    }

    @Test
    public void testMissingFields() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File missingFieldsYamlFile = new File("src/test/resources/missing-fields-application.yml");

        assertThrows(IllegalArgumentException.class, () -> {
            ApplicationModel application = mapper.readValue(missingFieldsYamlFile, ApplicationModel.class);
            application.validate();
        }, "Expected an IllegalArgumentException to be thrown for missing fields");
    }

    @Test
    public void testIncorrectDataTypes() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File incorrectDataTypesYamlFile = new File("src/test/resources/incorrect-data-types-application.yml");

        assertThrows(InvalidFormatException.class, () -> {
            ApplicationModel application = mapper.readValue(incorrectDataTypesYamlFile, ApplicationModel.class);
            application.validate();
        }, "Expected an InvalidFormatException to be thrown for incorrect data types");
    }

    @Test
    public void testEmptyConfiguration() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File emptyYamlFile = new File("src/test/resources/empty-application.yml");

        assertThrows(MismatchedInputException.class, () -> {
            ApplicationModel application = mapper.readValue(emptyYamlFile, ApplicationModel.class);
            application.validate();
        }, "Expected a MismatchedInputException to be thrown for empty configuration");
    }
}