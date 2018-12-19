package islamkhsh.com.healthinpocket.ui.activity.register;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

import islamkhsh.com.healthinpocket.ui.activity.base.BaseViewModel;

/**
 * Created by ESLAM on 12/18/2018.
 */

public class RegisterViewModel extends BaseViewModel {

    public void register_user(OnCompleteListener<AuthResult> onCompleteListener, String email, String password) {
        getmAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener(onCompleteListener);
    }
}
