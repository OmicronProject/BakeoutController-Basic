package devices;

import exceptions.WrappedModbusException;
import kernel.modbus.ModbusConnector;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.io.BytesInputStream;
import net.wimpi.modbus.io.BytesOutputStream;
import net.wimpi.modbus.io.ModbusTransaction;
import net.wimpi.modbus.msg.ModbusMessage;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Contains an implementation of the PVCi IGC3 ion pressure gauge
 *
 * @implNote The serial port parameters are hard-coded for now. More
 * customization will come from a proper kernel, once that gets written.
 */
public class PVCiPressureGauge implements PressureGauge {

    /**
     * The application log to which entries about application state are to
     * be written
     */
    private static final Logger log = LoggerFactory.getLogger
            (PVCiPressureGauge.class);

    /**
     * The address (0x9A in hexadecimal) where pressure information is stored
     */
    private static final Integer gaugePressureAddress = 154;

    /**
     * The size of the pressure register. The pressure is stored as a 32-bit
     * IEEE 754 floating point number. The MODBUS protocol uses 16 bit words.
     * Therefore, two words must be read from the device, starting at the
     * register address.
     */
    private static final Integer gaugePressureWordsTorRead = 2;

    /**
     * The device address
     */
    private final Integer address;

    private final ModbusConnector connection;

    public PVCiPressureGauge(Integer address, ModbusConnector connection){
        this.address = address;
        this.connection = connection;
        log.debug(
                "Created PVCi pressure gauge with address {} and conn {}",
                address, connection);
    }

    @Override
    public Float getPressure() throws WrappedModbusException,
            ModbusException {

        ReadInputRegistersRequest pressureRequest = getReadRegisterRequest(
                gaugePressureAddress, gaugePressureWordsTorRead
        );

        ModbusTransaction transaction = connection.getTransactionForRequest(
                pressureRequest);

        log.debug(
                "PVCi Pressure gauge {} executing transaction {}",
                this.toString(), transaction.toString());

        transaction.execute();

        ModbusMessage response = transaction.getResponse();

        log.debug(
                "Received response {} from transaction {}",
                response.getHexMessage(), transaction.toString());

        Float pressure;
        try {
            pressure = parseResponse(response);
        } catch (IOException error) {
            throw new WrappedModbusException(error);
        }

        return pressure;
    }

    @Contract("_, _ -> !null")
    private ReadInputRegistersRequest getReadRegisterRequest(
            Integer registerNumber, Integer numberofWordsToRead){
        ReadInputRegistersRequest request = new ReadInputRegistersRequest
                (registerNumber, numberofWordsToRead);
        request.setUnitID(address);
        request.setHeadless();

        return request;
    }

    @NotNull
    private Float parseResponse(ModbusMessage response) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        DataOutput writer = new DataOutputStream(byteBuffer);

        response.writeTo(writer);

        log.debug("Response is {} long. Message is {}",
                response.getDataLength(), response.getHexMessage());

        DataInput reader = new DataInputStream(
                new ByteArrayInputStream(byteBuffer.toByteArray()));

        return reader.readFloat();
    }
}
