package islamkhsh.com.healthinpocket.ui.fragment.check_up;

import android.arch.lifecycle.MutableLiveData;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by ESLAM on 12/19/2018.
 */

public class ExchangeDataTask extends AsyncTask<BluetoothSocket, Void, Void> {
    private BluetoothSocket bluetoothSocket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private String sendData;
    private MutableLiveData<String> result;

    public ExchangeDataTask(int sendData) {
        this.sendData = "" + sendData;
        this.result = new MutableLiveData<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(BluetoothSocket... bluetoothSockets) {
        this.bluetoothSocket = bluetoothSockets[0];
        byte[] buffer = new byte[1024];
        int begin = 0;
        int bytes = 0;

        try {
            inputStream = bluetoothSocket.getInputStream();
            outputStream = bluetoothSocket.getOutputStream();

            outputStream.write((sendData).getBytes());

            while (true) {
                bytes += inputStream.read(buffer, bytes, buffer.length - bytes);
                for (int i = begin; i < bytes; i++) {
                    if (buffer[i] == "#".getBytes()[0]) {
                        handleMessage(begin, i, buffer);
                        begin = i + 1;
                        if (i == bytes - 1) {
                            bytes = 0;
                            begin = 0;
                        }
                    }
                }
            }
        } catch (IOException e) {
        }
        return null;
    }

    public void handleMessage(int begin, int end, byte[] writeBuf) {
        String resultMeasure = new String(writeBuf);
        resultMeasure = resultMeasure.substring(begin, end);
        this.result.postValue(resultMeasure);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        try {
            inputStream.close();
            outputStream.close();
            bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<String> getResult() {
        return result;
    }
}
