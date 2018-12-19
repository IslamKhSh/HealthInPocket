package islamkhsh.com.healthinpocket.ui.activity.base;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by ESLAM on 12/18/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private BaseViewModel baseViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public void setupViewModel() {
        baseViewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
    }


}
