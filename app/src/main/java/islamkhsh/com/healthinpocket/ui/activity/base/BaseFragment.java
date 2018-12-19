package islamkhsh.com.healthinpocket.ui.activity.base;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by ESLAM on 12/18/2018.
 */

public abstract class BaseFragment extends Fragment {
    private BaseViewModel baseViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
    }

    public void setupViewModel() {
        baseViewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
    }

}
