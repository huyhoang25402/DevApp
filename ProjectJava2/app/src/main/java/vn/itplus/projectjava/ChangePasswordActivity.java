package vn.itplus.projectjava;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.itplus.projectjava.model.User;
import vn.itplus.projectjava.remote.APIUtils;
import vn.itplus.projectjava.remote.UserService;

public class ChangePasswordActivity extends AppCompatActivity {
    ImageView btnBack;
    EditText etCurrentPassword, etNewPassword, etConfirmPassword;
    Button btnChangePasswordSubmit;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Bundle extras = getIntent().getExtras();
        SessionManagement sessionManagement = new SessionManagement(ChangePasswordActivity.this);
        userService = APIUtils.getUserService();
        btnBack = findViewById(R.id.btnBackChangePassword);
        btnChangePasswordSubmit = findViewById(R.id.btnChangePasswordSubmmit);
        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnChangePasswordSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etCurrentPassword.getText().toString().equals("") || etNewPassword.getText().toString().equals("") ||
                    etConfirmPassword.getText().toString().equals("")){
                    Toast.makeText(ChangePasswordActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if (!etCurrentPassword.getText().toString().equals(extras.getString("edit_password"))){
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu hiện tại không đúng", Toast.LENGTH_SHORT).show();
                }else if (!etNewPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                }else{
                    User user = new User();
                    user.setId(sessionManagement.getSession());
                    user.setUsername(extras.getString("edit_username"));
                    user.setPassword(etNewPassword.getText().toString());
                    user.setName(extras.getString("edit_name"));
                    user.setBirthDay(extras.getString("edit_birthday"));
                    user.setPhoneNumber(extras.getString("edit_phone"));
                    user.setAddress(extras.getString("edit_address"));
                    user.setEmail(extras.getString("edit_email"));
                    user.setStatus(1);
                    changePassword(sessionManagement.getSession(), user);
                    Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ChangePasswordActivity.this, AccountActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    public void changePassword(int id, User user){
        Call<User> change = userService.updateUser(id,user);
        change.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}