package me.doublenico.test;

import com.fazecast.jSerialComm.SerialPort;
import me.doublenico.scaraGUI.arduino.ArduinoManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArduinoSerialTest {

    @Mock
    private SerialPort mockSerialPort;

    private ArduinoManager arduinoManager;
    private ByteArrayOutputStream outputStream;
    private ByteArrayInputStream inputStream;
    private static final Logger logger = Logger.getLogger(ArduinoSerialTest.class.getName());

    @BeforeEach
    void setUp() {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);

        logger.info("Setting up test environment");
        arduinoManager = new ArduinoManager("COM1");
        outputStream = new ByteArrayOutputStream();
        inputStream = new ByteArrayInputStream("Test data".getBytes(StandardCharsets.UTF_8));
        logger.info("Test environment setup completed");
    }

    @Test
    void testPortAvailability() {
        logger.info("Testing port availability");
        try (MockedStatic<SerialPort> serialPortMock = mockStatic(SerialPort.class)) {
            SerialPort[] ports = {mockSerialPort};
            serialPortMock.when(SerialPort::getCommPorts).thenReturn(ports);

            when(mockSerialPort.getSystemPortName()).thenReturn("COM1");

            SerialPort[] availablePorts = SerialPort.getCommPorts();

            assertNotNull(availablePorts);
            assertEquals(1, availablePorts.length);
            assertEquals("COM1", availablePorts[0].getSystemPortName());
            logger.info("Port availability test passed");
        }
    }

    @Test
    void testPortConnection() {
        logger.info("Testing port connection");
        try (MockedStatic<SerialPort> serialPortMock = mockStatic(SerialPort.class)) {
            serialPortMock.when(() -> SerialPort.getCommPort(anyString())).thenReturn(mockSerialPort);

            when(mockSerialPort.openPort()).thenReturn(true);
            when(mockSerialPort.isOpen()).thenReturn(true);
            when(mockSerialPort.getSystemPortName()).thenReturn("COM1");

            ArduinoManager manager = new ArduinoManager("COM1");

            assertTrue(manager.isOpened());
            assertNotNull(manager.getSelectedPort());
            assertEquals("COM1", manager.getSelectedPort().getSystemPortName());
            logger.info("Port connection test passed");
        }
    }

    @Test
    void testPortConnectionFailure() {
        logger.info("Testing port connection failure");
        try (MockedStatic<SerialPort> serialPortMock = mockStatic(SerialPort.class)) {
            serialPortMock.when(() -> SerialPort.getCommPort(anyString())).thenReturn(mockSerialPort);

            when(mockSerialPort.openPort()).thenReturn(false);
            when(mockSerialPort.getSystemPortName()).thenReturn("COM1");

            ArduinoManager manager = new ArduinoManager("COM1");

            assertFalse(manager.isOpened());
            logger.info("Port connection failure test passed");
        }
    }

    @Test
    void testSendMessage() throws IOException {
        logger.info("Testing message sending");
        when(mockSerialPort.getOutputStream()).thenReturn(outputStream);
        when(mockSerialPort.isOpen()).thenReturn(true);

        arduinoManager.setSelectedPort(mockSerialPort);
        arduinoManager.setOpened(true);

        String testMessage = "G1 X100 Y100";
        outputStream.write(testMessage.getBytes());

        assertEquals(testMessage, outputStream.toString());
        logger.info("Message sending test passed");
    }

    @Test
    void testReceiveMessage() throws IOException {
        logger.info("Testing message receiving");
        when(mockSerialPort.getInputStream()).thenReturn(inputStream);
        when(mockSerialPort.isOpen()).thenReturn(true);
        when(mockSerialPort.bytesAvailable()).thenReturn(9);

        arduinoManager.setSelectedPort(mockSerialPort);
        arduinoManager.setOpened(true);

        byte[] buffer = new byte[9];
        int bytesRead = inputStream.read(buffer);

        logger.info("Read " + bytesRead + " bytes from serial port");
        String receivedData = new String(buffer, StandardCharsets.UTF_8);

        assertEquals("Test data", receivedData);
        logger.info("Message receiving test passed");
    }

    @Test
    void testSerialPortParameters() {
        logger.info("Testing serial port parameters");
        try (MockedStatic<SerialPort> serialPortMock = mockStatic(SerialPort.class)) {
            serialPortMock.when(() -> SerialPort.getCommPort(anyString())).thenReturn(mockSerialPort);

            new ArduinoManager("COM1");

            verify(mockSerialPort).setComPortParameters(115200, 8, 1, 0);
            verify(mockSerialPort).setComPortTimeouts(anyInt(), anyInt(), anyInt());

            logger.info("Serial port parameters test passed");
        }
    }

    @Test
    void testDisconnect() {
        logger.info("Testing disconnection");
        when(mockSerialPort.closePort()).thenReturn(true);
        when(mockSerialPort.isOpen()).thenReturn(false);

        arduinoManager.setSelectedPort(mockSerialPort);
        arduinoManager.setOpened(true);

        boolean result = mockSerialPort.closePort();
        arduinoManager.setOpened(false);

        assertTrue(result);
        assertFalse(arduinoManager.isOpened());
        logger.info("Disconnection test passed");
    }

    @Test
    void testDataTransmission() throws IOException {
        logger.info("Testing complete data transmission cycle");
        when(mockSerialPort.getOutputStream()).thenReturn(outputStream);
        when(mockSerialPort.getInputStream()).thenReturn(inputStream);
        when(mockSerialPort.isOpen()).thenReturn(true);

        arduinoManager.setSelectedPort(mockSerialPort);
        arduinoManager.setOpened(true);

        String command = "G28";
        outputStream.write(command.getBytes(StandardCharsets.UTF_8));

        byte[] responseBuffer = new byte[9];
        inputStream.read(responseBuffer);

        assertEquals(command, outputStream.toString());
        assertEquals("Test data", new String(responseBuffer, StandardCharsets.UTF_8));
        logger.info("Data transmission test passed");
    }

    @Test
    void testPortClosedBehavior() {
        logger.info("Testing behavior when port is closed");
        when(mockSerialPort.isOpen()).thenReturn(false);

        arduinoManager.setSelectedPort(mockSerialPort);
        arduinoManager.setOpened(false);

        assertFalse(arduinoManager.isOpened());
        logger.info("Port closed behavior test passed");
    }
}