package devices;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.io.ModbusTransaction;
import com.ghgande.j2mod.modbus.msg.ModbusMessage;
import com.ghgande.j2mod.modbus.msg.ModbusRequest;
import com.ghgande.j2mod.modbus.msg.ReadWriteMultipleRequest;
import exceptions.WrappedModbusException;
import kernel.modbus.ModbusConnector;
import org.jetbrains.annotations.Contract;
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

    private static final Integer unitIDAddress = 0;

    private static final Integer unitIDNumberofWords = 2;
    
    /**
     * The device address
     */
    private final Integer address;

    /**
     * The helper that manages Modbus connections.
     */
    private final ModbusConnector connection;

    /**
     * @param address The device address, numbered from 1 to 99. MODBUS
     *                requests are sent to the device with this address.
     * @param connection The connection manager to use.
     */
    public PVCiPressureGauge(Integer address, ModbusConnector connection)
            throws IOException {
        this.address = address;
        this.connection = connection;
        log.debug(
                "Created PVCi pressure gauge with address {} and conn {}",
                address, connection);

        try {
            this.checkUnitID();
        } catch (Exception error){
            throw new IOException(error);
        }
    }

    /**
     *
     * @return The current pressure, in millibars.
     * @throws WrappedModbusException If the getter to get the transaction
     * for the request fails
     * @throws ModbusException If the transaction to get the pressure cannot
     * be executed
     * @throws IOException If the result of the transaction to get pressure
     * cannot be parsed
     */
    @Override
    public Float getPressure() throws WrappedModbusException,
            ModbusException, IOException {
        log.debug("Method to get pressure was called");

        ModbusRequest pressureRequest = getReadRegisterRequest(
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

        return connection.parseFloatFromResponse(response);
    }

    /**
     * Create a request to read the desired register
     * @param registerNumber The number of the register that must be read
     * @param numberofWordsToRead The number of 16-bit words to read,
     *                            starting at the register number
     * @return A request to read the registers, that jamod can parse.
     */
    @Contract("_, _ -> !null")
    private ReadWriteMultipleRequest getReadRegisterRequest(
            Integer registerNumber, Integer numberofWordsToRead){
        ReadWriteMultipleRequest request = new
                ReadWriteMultipleRequest();

        request.setReadReference(registerNumber);
        request.setReadWordCount(numberofWordsToRead);
        request.setWriteReference(0);
        request.setWriteWordCount(0);

        request.setUnitID(address);
        request.setHeadless();

        return request;
    }

    private void checkUnitID() throws WrappedModbusException,
            ModbusException, IOException {
        ModbusRequest request = getReadRegisterRequest(
            unitIDAddress, unitIDNumberofWords
        );

        ModbusTransaction transaction = connection.getTransactionForRequest(
                request);

        log.info("Checking for device. Attempting to read unit ID using " +
                "transaction {}", transaction);

        transaction.execute();

        log.info("Check Unit ID transaction successfully completed");

        ModbusMessage response = transaction.getResponse();

        log.debug("Received response {} from transaction {}",
                response.getHexMessage(), transaction.toString());

        if (connection.parseStringFromResponse(response) == null){
            throw new IOException(
                    "Unable to create pressure gauge. No response from unit"
            );
        }
    }
}
