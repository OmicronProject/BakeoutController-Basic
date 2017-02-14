package kernel.modbus;

import net.wimpi.modbus.util.SerialParameters;

/**
 * Contains methods required to configure a MODBUS serial port
 */
public class StandaloneModbusPortConfiguration implements
        ModbusPortConfiguration {

    /**
     * The current port name
     */
    private String portName;

    /**
     * The number of stop bits
     */
    private int stopBits;

    /**
     * The number of parity bits
     */
    private int parityBits;

    /**
     * The number of data bits
     */
    private int dataBits;

    /**
     * The baud rate
     */
    private int baudRate;

    /**
     * The character encoding. This value defaults to ASCII encoding.
     */
    private String encoding = ModbusPortConfiguration.ASCII_ENCODING;

    /**
     * @return The port name
     */
    @Override
    public String getPortName(){
        return this.portName;
    }

    /**
     * @param portName The port name to use
     */
    @Override
    public void setPortName(String portName){
        this.portName = portName;
    }

    /**
     * @return The number of stop bits
     */
    @Override
    public int getStopBits(){
        return this.stopBits;
    }

    /**
     * @param stopBits The number of stop bits in the RS232 connection
     */
    @Override
    public void setStopBits(int stopBits){
        this.stopBits = stopBits;
    }

    /**
     * @return The number of parity bits
     */
    @Override
    public int getParityBits(){
        return this.parityBits;
    }

    /**
     * @param parityBits The number of parity bits
     */
    @Override
    public void setParityBits(int parityBits){
        this.parityBits = parityBits;
    }

    /**
     * @return The desired number of parity bits
     */
    @Override
    public int getDataBits(){
        return this.dataBits;
    }

    /**
     * @param dataBits The data bits in an RS232 frame
     */
    @Override
    public void setDataBits(int dataBits){
        this.dataBits = dataBits;
    }

    /**
     * @return The desired baud rate
     */
    @Override
    public int getBaudRate(){
        return this.baudRate;
    }

    /**
     * @param baudRate The desired Baud rate
     */
    @Override
    public void setBaudRate(int baudRate){
        if (baudRate <= 0){
            throw new IllegalArgumentException(
                    "Attempted to set a baud rate less than or equal to 0. " +
                            "This is not allowed"
            );
        }
        this.baudRate = baudRate;
    }

    /**
     * @return The current character encoding
     */
    @Override
    public String getEncoding(){
        return this.encoding;
    }

    /**
     * @param encoding The string encoding to use
     */
    @Override
    public void setEncoding(String encoding){
        this.encoding = encoding;
    }

    /**
     * @return An instance of {@link SerialParameters} that contains the
     * desired connection parameters.
     */
    @Override
    public SerialParameters getSerialParameters(){
        SerialParameters parameters = new SerialParameters();

        parameters.setPortName(portName);
        parameters.setStopbits(stopBits);
        parameters.setParity(parityBits);
        parameters.setDatabits(dataBits);
        parameters.setBaudRate(baudRate);
        parameters.setEncoding(encoding);

        return parameters;
    }
}
