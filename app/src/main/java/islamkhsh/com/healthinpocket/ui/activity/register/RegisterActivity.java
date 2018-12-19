package islamkhsh.com.healthinpocket.ui.activity.register;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import islamkhsh.com.healthinpocket.R;
import islamkhsh.com.healthinpocket.ui.activity.base.BaseActivity;
import islamkhsh.com.healthinpocket.ui.activity.login.LoginActivity;
import islamkhsh.com.healthinpocket.ui.activity.main.MainActivity;

public class RegisterActivity extends BaseActivity implements OnCompleteListener<AuthResult> {

    @BindView(R.id.reg_display_name)
    public EditText mDisplayName;
    @BindView(R.id.log_email)
    public EditText mEmail;
    @BindView(R.id.log_password)
    public EditText mPassword;
    private RegisterViewModel registerViewModel;
    private ProgressDialog mRegProgress;

    @OnClick(R.id.textViewSignIn)
    public void goToSignin() {
        Intent LoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(LoginIntent);
        finish();
    }

    @OnClick(R.id.login_btn)
    public void signup() {
        if (!TextUtils.isEmpty(mDisplayName.getText()) && !TextUtils.isEmpty(mEmail.getText()) &&
                !TextUtils.isEmpty(mPassword.getText())) {
            mRegProgress.setTitle(getString(R.string.register_dialog_title));
            mRegProgress.setMessage(getString(R.string.register_dialog_msg));
            mRegProgress.setCanceledOnTouchOutside(false);
            mRegProgress.show();

            registerViewModel.register_user(this, mEmail.getText().toString(),
                    mPassword.getText().toString());
        } else
            Toast.makeText(RegisterActivity.this, R.string.register_empty_fields, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mRegProgress = new ProgressDialog(this);
    }

    @Override
    public void setupViewModel() {
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
    }


    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            registerViewModel.setCurrentUser(task.getResult().getUser());

            registerViewModel.getCurrentUser().observe(this, new Observer<FirebaseUser>() {
                @Override
                public void onChanged(@Nullable FirebaseUser firebaseUser) {
                    if (firebaseUser != null) {
                        firebaseUser.updateProfile(new UserProfileChangeRequest.Builder().
                                setDisplayName(mDisplayName.getText().toString()).build());
                        registerViewModel.setCurrentUser(firebaseUser);
                    }

                }
            });

            mRegProgress.dismiss();
            Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();

        } else {
            mRegProgress.hide();
            Toast.makeText(RegisterActivity.this, R.string.signup_error_msg, Toast.LENGTH_SHORT).show();
        }

    }
}
