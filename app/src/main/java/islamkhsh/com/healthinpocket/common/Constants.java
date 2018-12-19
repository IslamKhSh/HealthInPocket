package islamkhsh.com.healthinpocket.common;

import java.util.UUID;

/**
 * Created by ESLAM on 12/18/2018.
 */

public abstract class Constants {

    //bluetooth module MAC address
    public static final String DEVICE_ADDRESS = "98:D3:32:20:BF:ED";

    //data to be sent
    public static final int HEART_RATE = 1;
    public static final int HEART_SIGNAL = 2;
    public static final int BLOOD_PRESSURE = 3;
    public static final int TEMPERATURE = 4;

    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
}
