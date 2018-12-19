package islamkhsh.com.healthinpocket.ui.activity.login;


import com.google.android.gms.tasks.OnCompleteListener;

import islamkhsh.com.healthinpocket.ui.activity.base.BaseViewModel;

/**
 * Created by ESLAM on 12/18/2018.
 */

public class LoginViewModel extends BaseViewModel {

    public void loginUser(OnCompleteListener onCompleteListener, String email, String password) {
        getmAuth().signInWithEmailAndPassword(email, password).
                addOnCompleteListener(onCompleteListener);
    }
}
