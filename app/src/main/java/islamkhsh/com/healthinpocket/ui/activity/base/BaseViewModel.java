package islamkhsh.com.healthinpocket.ui.activity.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ESLAM on 12/18/2018.
 */

public class BaseViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private MutableLiveData<FirebaseUser> currentUser;

    public BaseViewModel() {
        this.mAuth = FirebaseAuth.getInstance();
        this.currentUser = new MutableLiveData<>();
        this.currentUser.postValue(mAuth.getCurrentUser());
    }

    public MutableLiveData<FirebaseUser> getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(FirebaseUser currentUser) {
        this.currentUser.postValue(currentUser);
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }
}
