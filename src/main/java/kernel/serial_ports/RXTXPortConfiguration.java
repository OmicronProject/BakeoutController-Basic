package kernel.serial_ports;

/**
 * Data structure to hold configuration parameters for a given Serial Port
 */
public final class RXTXPortConfiguration implements PortConfiguration {
    /**
     * The data transfer rate in bits per second
     */
    private final int baudRate;

    /**
     * The number of bits needed to signal an end of message
     */
    private final int stopBits;

    /**
     * The number of data bits in a byte of data
     */
    private final int dataBits;

    /**
     * The number of parity bits in a byte of data
     */
    private final int parityBits;

    /**
     * @param baudRate the desired baud rate
     * @param stopBits the number of stop bits
     * @param dataBits the number of data bits
     * @param parityBits the number of parity bits
     */
    public RXTXPortConfiguration(int baudRate, int stopBits, int dataBits,
                                 int parityBits){
        this.baudRate = baudRate;
        this.stopBits = stopBits;
        this.dataBits = dataBits;
        this.parityBits = parityBits;
    }

    /**
     * @return The desired Baud rate (bits per second)
     */
    @Override public int getBaudRate(){
        return this.baudRate;
    }

    /**
     * @return The number of stop bits
     */
    @Override public int getStopBits(){
        return this.stopBits;
    }

    /**
     * @return The number of data bits
     */
    @Override public int getDataBits(){
        return this.dataBits;
    }

    /**
     * @return The number of parity bits
     */
    @Override public int getParityBits(){
        return this.parityBits;
    }
}
