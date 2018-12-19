package islamkhsh.com.healthinpocket.ui.fragment.check_up;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import islamkhsh.com.healthinpocket.data.model.MeasuredItem;
import islamkhsh.com.healthinpocket.ui.activity.base.BaseViewModel;

/**
 * Created by ESLAM on 12/18/2018.
 */

public class CheckUpViewModel extends BaseViewModel {
    private ConnectTask connectTask;
    private MediatorLiveData<String> result;
    private DatabaseReference mRef;

    public CheckUpViewModel() {
        result = new MediatorLiveData<>();
    }

    public void connectToDevice(Context context, BluetoothDevice bluetoothDevice, int measuredItemType) {
        cancelConnectTask();
        connectTask = new ConnectTask(context, measuredItemType);
        connectTask.doInBackground(bluetoothDevice);
        result.addSource(connectTask.getSource(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // do something
            }
        });
    }

    public void cancelConnectTask() {
        if (connectTask != null)
            connectTask.cancel(true);
    }

    public MediatorLiveData<String> getResult() {
        return result;
    }

    public void pushValue(MeasuredItem measuredItem) {
        mRef.push().setValue(measuredItem);
    }

    public void setDatabaseRef(FirebaseUser firebaseUser) {
        mRef = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid());
    }
}
