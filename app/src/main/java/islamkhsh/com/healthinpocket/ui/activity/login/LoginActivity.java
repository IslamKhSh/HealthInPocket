package islamkhsh.com.healthinpocket.ui.activity.login;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import islamkhsh.com.healthinpocket.R;
import islamkhsh.com.healthinpocket.ui.activity.base.BaseActivity;
import islamkhsh.com.healthinpocket.ui.activity.main.MainActivity;
import islamkhsh.com.healthinpocket.ui.activity.register.RegisterActivity;

public class LoginActivity extends BaseActivity implements OnCompleteListener<AuthResult> {

    @BindView(R.id.log_email)
    public EditText mLoginEmail;
    @BindView(R.id.log_password)
    public EditText mLoginPassword;
    private LoginViewModel loginViewModel;
    private ProgressDialog mLoginProgress;

    @OnClick(R.id.login_btn)
    public void logIn() {
        String email = mLoginEmail.getText().toString();
        String password = mLoginPassword.getText().toString();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mLoginProgress.setTitle(getString(R.string.login_dialog_title));
            mLoginProgress.setMessage(getString(R.string.login_dialog_msg));
            mLoginProgress.setCanceledOnTouchOutside(false);
            mLoginProgress.show();

            loginViewModel.loginUser(LoginActivity.this, email, password);
        } else
            Toast.makeText(this, R.string.login_empty_fields, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.textViewSignUp)
    public void goToSignUp() {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        mLoginProgress = new ProgressDialog(this);
    }

    @Override
    public void setupViewModel() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            mLoginProgress.dismiss();
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();

        } else {
            mLoginProgress.hide();
            Toast.makeText(LoginActivity.this, R.string.login_fail_msg, Toast.LENGTH_SHORT).show();
        }

    }
}
