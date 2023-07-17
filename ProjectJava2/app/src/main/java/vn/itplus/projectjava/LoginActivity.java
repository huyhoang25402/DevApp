package vn.itplus.projectjava;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.itplus.projectjava.model.User;
import vn.itplus.projectjava.remote.APIUtils;
import vn.itplus.projectjava.remote.UserService;

public class LoginActivity extends AppCompatActivity {
    Button btnLoginForm;
    TextView tvLoginToReg,tvError;
    EditText etAccountLogin, etPasswordLogin;
    ImageView btnLoginBack;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        userService = APIUtils.getUserService();
        btnLoginForm = findViewById(R.id.btnLoginForm);
        etAccountLogin = findViewById(R.id.etAccountLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
//        String username = null;
//        String password = null;
//        if(getIntent()!=null && getIntent().getExtras()!=null){
//            Bundle bundle = getIntent().getExtras();
//            if(!bundle.getString("username").equals(null) && bundle.getString("password").equals(null)){
//                username = bundle.getString("username");
//                password = bundle.getString("password");
//                System.out.println(username);
//                System.out.println(password);
//                etAccountLogin.setText(username.toString());
//                etPasswordLogin.setText(password.toString());
//            }
//        }
        tvError = findViewById(R.id.tvError);
        tvLoginToReg = findViewById(R.id.tvLoginToReg);
        btnLoginBack = findViewById(R.id.btnLoginBack);

        btnLoginForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etAccountLogin.getText().toString().equals("") || etPasswordLogin.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    checkLogin(etAccountLogin.getText().toString(), etPasswordLogin.getText().toString());
                }
            }
        });

        tvLoginToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        btnLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void checkLogin(String username, String password){
        Call<User> checkLogin = userService.checkLogin(username, password);
        checkLogin.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body() == null){
//                    tvError.setVisibility(View.VISIBLE);
//                    tvError.setText("Tài khoản hoặc mật khẩu không chính xác");
                }else{
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                    sessionManagement.saveSession(response.body());
                    Intent i = new Intent(LoginActivity.this, AccountActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                Log.e("Error", t.getMessage());
            }
        });
    }
}