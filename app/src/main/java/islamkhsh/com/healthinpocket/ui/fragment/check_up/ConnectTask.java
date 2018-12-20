package islamkhsh.com.healthinpocket.ui.fragment.check_up;

import android.app.ProgressDialog;
import android.arch.lifecycle.MutableLiveData;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import islamkhsh.com.healthinpocket.R;
import islamkhsh.com.healthinpocket.common.Constants;

/**
 * Created by ESLAM on 12/19/2018.
 */

public class ConnectTask extends AsyncTask<BluetoothDevice, Void, String> {

    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;
    private ProgressDialog progressDialog;
    private Context context;
    private ExchangeDataTask exchangeDataTask;
    private int sendData;

    public ConnectTask(Context context, int sendData) {
        this.context = context;
        this.sendData = sendData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        exchangeDataTask = new ExchangeDataTask(sendData);
    }

    @Override
    protected String doInBackground(BluetoothDevice... bluetoothDevices) {
        bluetoothDevice = bluetoothDevices[0];
        try {
            bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(Constants.MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                bluetoothSocket.connect();
                publishProgress();
                break;
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        progressDialog.setMessage(context.getString(R.string.connecting_dialog_msg));
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        exchangeDataTask.execute(bluetoothSocket);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        exchangeDataTask.cancel(true);

        try {
            bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<String> getSource() {
        return exchangeDataTask.getResult();
    }
}
