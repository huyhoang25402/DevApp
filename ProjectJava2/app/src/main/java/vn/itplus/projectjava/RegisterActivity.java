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

public class RegisterActivity extends AppCompatActivity {
    TextView tvRegToLogin;
    EditText etNameReg, etAccountReg, etPasswordReg, etPasswordRegAgain;
    Button btnRegForm;
    ImageView btnRegisterBack;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        userService = APIUtils.getUserService();
        etNameReg = findViewById(R.id.etNameReg);
        etAccountReg = findViewById(R.id.etAccountReg);
        etPasswordReg = findViewById(R.id.etPasswordReg);
        etPasswordRegAgain = findViewById(R.id.etPasswordRegAgain);
        btnRegForm = findViewById(R.id.btnRegForm);
        tvRegToLogin = findViewById(R.id.tvRegToLogin);
        btnRegisterBack = findViewById(R.id.btnRegisterBack);

        tvRegToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        btnRegisterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnRegForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNameReg.getText().toString().equals("") || etAccountReg.getText().toString().equals("") ||
                        etPasswordReg.getText().toString().equals("") || etPasswordRegAgain.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if(!(etPasswordReg.getText().toString().equals(etPasswordRegAgain.getText().toString()))){
                    Toast.makeText(RegisterActivity.this,"Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                }else{
                    User user = new User();
                    user.setName(etNameReg.getText().toString());
                    user.setUsername(etAccountReg.getText().toString());
                    user.setPassword(etPasswordReg.getText().toString());
                    user.setStatus(1);
                    addUser(user);
                }
            }
        });
    }

    public void addUser(User user){
        Call<User> add = userService.addUser(user);
        add.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body() != null){
                    Toast.makeText(RegisterActivity.this,"Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                    etAccountReg.setText("");
                    etNameReg.setText("");
                    etPasswordReg.setText("");
                    etPasswordRegAgain.setText("");
                }else{
                    Toast.makeText(RegisterActivity.this,"Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    public void checkDuplicateUser(String name){
        Call<User> check = userService.getUserByName(name);
        check.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}