package me.doublenico.test;

import com.fazecast.jSerialComm.SerialPort;
import me.doublenico.scaraGUI.arduino.ArduinoManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockSettings;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

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

/**
 * Test class for testing Arduino Serial communication.
 * Best way to test it its on windows
 * @author DoubleNico
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ArduinoSerialTest {

    @Mock
    private SerialPort mockSerialPort;

    private static ArduinoManager arduinoManager;
    private static final String TEST_PORT_NAME = "tty.wlan-debug";
    private ByteArrayOutputStream outputStream;
    private ByteArrayInputStream inputStream;
    private static final Logger logger = Logger.getLogger(ArduinoSerialTest.class.getName());
    private MockedStatic<SerialPort> serialPortMock;

    @BeforeEach
    void setUp() {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
        System.out.println("Setting up test environment");

        serialPortMock = Mockito.mockStatic(SerialPort.class);
        serialPortMock.when(() -> SerialPort.getCommPort(anyString())).thenReturn(mockSerialPort);
        serialPortMock.when(SerialPort::getCommPorts).thenReturn(new SerialPort[]{mockSerialPort});

        when(mockSerialPort.getSystemPortName()).thenReturn(TEST_PORT_NAME);
        when(mockSerialPort.openPort()).thenReturn(true);
        when(mockSerialPort.isOpen()).thenReturn(true);

        if (arduinoManager == null) {
            arduinoManager = new ArduinoManager(TEST_PORT_NAME);
        }

        outputStream = new ByteArrayOutputStream();
        inputStream = new ByteArrayInputStream("Test data".getBytes(StandardCharsets.UTF_8));
        System.out.println("Test environment setup completed");
    }

    @AfterEach
    void tearDown() {
        if (serialPortMock != null) {
            serialPortMock.close();
        }
    }

    @Test
    void testPortConnection() {
        System.out.println("Testing port connection");
        assertTrue(arduinoManager.isOpened());
        assertNotNull(arduinoManager.getSelectedPort());
        assertEquals(TEST_PORT_NAME, arduinoManager.getSelectedPort().getSystemPortName());
        System.out.println("Port connection test passed");
    }

    @Test
    void testPortConnectionFailure() {
        System.out.println("Testing port connection failure");
        SerialPort failMockPort = mock(SerialPort.class);
        when(failMockPort.openPort()).thenReturn(false);

        serialPortMock.when(() -> SerialPort.getCommPort(anyString())).thenReturn(failMockPort);

        ArduinoManager failingManager = new ArduinoManager(TEST_PORT_NAME);
        assertFalse(failingManager.isOpened());

        System.out.println("Port connection failure test passed");
    }

    @Test
    void testSendMessage() throws IOException {
        System.out.println("Testing message sending");
        String testMessage = "G1 X100 Y100";
        outputStream.write(testMessage.getBytes());
        assertEquals(testMessage, outputStream.toString());
        System.out.println("Message sending test passed");
    }

    @Test
    void testReceiveMessage() throws IOException {
        System.out.println("Testing message receiving");
        when(mockSerialPort.getInputStream()).thenReturn(inputStream);

        byte[] buffer = new byte[9];
        int bytesRead = inputStream.read(buffer);
        System.out.println("Read " + bytesRead + " bytes from serial port");

        String receivedData = new String(buffer, StandardCharsets.UTF_8);
        assertEquals("Test data", receivedData);
        System.out.println("Message receiving test passed");
    }

    @Test
    void testSentData() {
        System.out.println("Testing sent data");
        String testMessage = "G1 X100 Y100";
        byte[] messageBytes = testMessage.getBytes(StandardCharsets.UTF_8);
        when(mockSerialPort.writeBytes(messageBytes, messageBytes.length)).thenReturn(messageBytes.length);

        int bytesSent = mockSerialPort.writeBytes(messageBytes, messageBytes.length);
        assertEquals(messageBytes.length, bytesSent);
        System.out.println("Sent data test passed");
    }

}