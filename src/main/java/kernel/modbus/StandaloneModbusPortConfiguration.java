package kernel.modbus;

import net.wimpi.modbus.util.SerialParameters;

/**
 * Contains methods required to configure a MODBUS serial port
 */
public class StandaloneModbusPortConfiguration implements
        ModbusPortConfiguration {

    private String portName;

    private int stopBits;

    private int parityBits;

    private int dataBits;

    private int baudRate;

    private String encoding = ModbusPortConfiguration.ASCII_ENCODING;

    @Override
    public String getPortName(){
        return this.portName;
    }

    @Override
    public void setPortName(String portName){
        this.portName = portName;
    }

    @Override
    public int getStopBits(){
        return this.stopBits;
    }

    @Override
    public void setStopBits(int stopBits){
        this.stopBits = stopBits;
    }

    @Override
    public int getParityBits(){
        return this.parityBits;
    }

    @Override
    public void setParityBits(int parityBits){
        this.parityBits = parityBits;
    }

    @Override
    public int getDataBits(){
        return this.dataBits;
    }

    @Override
    public void setDataBits(int dataBits){
        this.dataBits = dataBits;
    }

    @Override
    public int getBaudRate(){
        return this.baudRate;
    }

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


    @Override
    public String getEncoding(){
        return this.encoding;
    }

    @Override
    public void setEncoding(String encoding){
        this.encoding = encoding;
    }

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
