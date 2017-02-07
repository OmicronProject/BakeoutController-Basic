package unit.devices;

import devices.PowerSupply;
import org.junit.Before;
import unit.UnitTestCase;

import java.io.*;

/**
 * Base class for all unit tests of {@link devices}
 */
public abstract class DevicesTestCase extends UnitTestCase {
    protected DeviceCommunicator communicatorForDevice;

    @Before
    public void setUpDevicesTestCase() throws IOException {
        initializeDeviceCommunicator();
    }

    private void initializeDeviceCommunicator(){
        this.communicatorForDevice = new CommunicationMonitor();
    }

    protected class CommunicationMonitor implements DeviceCommunicator {
        private InputStream inputStream;
        private OutputStream outputStream;

        protected CommunicationMonitor(){
            this.inputStream = new ByteArrayInputStream(
                PowerSupply.OK_RESPONSE.getBytes()
            );
            this.outputStream = new ByteArrayOutputStream();
        }

        @Override public InputStream getInputStream(){
            return this.inputStream;
        }

        @Override public OutputStream getOutputStream(){
            return this.outputStream;
        }

        @Override public void setInputStreamData(String dataForDeviceToRead){
            this.inputStream = new ByteArrayInputStream(
                dataForDeviceToRead.getBytes()
            );
        }

        @Override public String getOutputStreamData(){
            String data = this.outputStream.toString();
            this.outputStream = new ByteArrayOutputStream();
            return data;
        }

        @Override public void clear(){
            this.inputStream = new ByteArrayInputStream(
                    PowerSupply.OK_RESPONSE.getBytes()
            );
            this.outputStream = new ByteArrayOutputStream();
        }
    }
}
