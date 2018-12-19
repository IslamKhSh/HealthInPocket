package islamkhsh.com.healthinpocket.ui.fragment.check_up;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import islamkhsh.com.healthinpocket.R;
import islamkhsh.com.healthinpocket.common.Constants;
import islamkhsh.com.healthinpocket.data.model.MeasuredItem;
import islamkhsh.com.healthinpocket.ui.activity.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckUpFragment extends BaseFragment {

    private static String fragmentTitle;
    @BindView(R.id.measure_type)
    public TextView measureType;
    @BindView(R.id.measure_value)
    public TextView measureValue;
    @BindView(R.id.measure_symbol)
    public TextView measureSymbol;
    private CheckUpViewModel checkUpViewModel;
    private int measuredItemType;
    private BluetoothAdapter bluetoothAdapter;

    public CheckUpFragment() {
        // Required empty public constructor
    }

    public static CheckUpFragment newInstance(String title) {
        CheckUpFragment fragment = new CheckUpFragment();
        fragmentTitle = title;

        return fragment;
    }

    @OnClick(R.id.save_btn)
    public void saveValue() {
        if (!measureValue.getText().toString().isEmpty()) {
            checkUpViewModel.pushValue(new MeasuredItem(measuredItemType, measureValue.getText().toString()));
            measureValue.setText("");
        } else
            Toast.makeText(getContext(), R.string.save_empty_value, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.share_btn)
    public void shareValue() {
        if (!measureValue.getText().toString().isEmpty()) {
            String message = "Email: " + checkUpViewModel.getCurrentUser().getValue().getEmail() + ".\n" +
                    measureType.getText() + ": " + measureValue.getText() + " " + measureSymbol.getText() + ".";

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else
            Toast.makeText(getContext(), R.string.share_empty_value_msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.clear_btn)
    public void loadDefault() {
        measuredItemType = Constants.HEART_RATE;
        measureType.setText(R.string.heart_rate);
        measureSymbol.setText(R.string.heart_rate_unit);
    }

    @OnClick({R.id.heart_rate_btn, R.id.heart_signal_btn, R.id.blood_pressure_btn, R.id.temperature_btn})
    public void startMeasure(View view) {
        switch (view.getId()) {
            case R.id.heart_rate_btn:
                loadDefault();

            case R.id.heart_signal_btn:
                measuredItemType = Constants.HEART_SIGNAL;
                measureType.setText(R.string.heart_signal);
                measureSymbol.setText("");

            case R.id.blood_pressure_btn:
                measuredItemType = Constants.BLOOD_PRESSURE;
                measureType.setText(R.string.blood_pressure);
                measureSymbol.setText(R.string.blood_pressure_unit);

            case R.id.temperature_btn:
                measuredItemType = Constants.TEMPERATURE;
                measureType.setText(R.string.temperature);
                measureSymbol.setText(R.string.temperature_unit);

            default:
                accessBluetooth();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_check_up, container, false);
        ButterKnife.bind(this, root);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        checkUpViewModel.getCurrentUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(@Nullable FirebaseUser firebaseUser) {
                if (firebaseUser != null)
                    checkUpViewModel.setDatabaseRef(firebaseUser);
            }
        });

        checkUpViewModel.getResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (!s.isEmpty())
                    measureValue.setText(s);
            }
        });

        return root;
    }


    @Override
    public void onStop() {
        super.onStop();

        checkUpViewModel.cancelConnectTask();
        if (bluetoothAdapter.isEnabled())
            bluetoothAdapter.disable();
    }


    @Override
    public void setupViewModel() {
        checkUpViewModel = ViewModelProviders.of(this).get(CheckUpViewModel.class);
    }

    private void accessBluetooth() {
        if (bluetoothAdapter == null) {
            Toast.makeText(getActivity(), R.string.bluetooth_not_supported_msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!bluetoothAdapter.isEnabled())
            bluetoothAdapter.enable();

        findPairedDevice();
    }

    private void findPairedDevice() {
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        if (bondedDevices.isEmpty()) {
            Toast.makeText(getActivity(), R.string.no_paired_devices_found, Toast.LENGTH_SHORT).show();
            //TODO: scan for available devices
            return;
        } else {
            for (BluetoothDevice iterator : bondedDevices) {
                if (iterator.getAddress().equalsIgnoreCase(Constants.DEVICE_ADDRESS)) {
                    connectToDevice(iterator);
                    return;
                }
            }
            Toast.makeText(getActivity(), R.string.please_pair_your_device, Toast.LENGTH_SHORT).show();
        }
    }


    private synchronized void connectToDevice(BluetoothDevice bluetoothDevice) {
        checkUpViewModel.connectToDevice(getContext(), bluetoothDevice, measuredItemType);
    }


    @Override
    public String toString() {
        return fragmentTitle;
    }


}
