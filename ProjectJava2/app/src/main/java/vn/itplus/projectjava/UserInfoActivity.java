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
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.itplus.projectjava.model.User;
import vn.itplus.projectjava.remote.APIUtils;
import vn.itplus.projectjava.remote.UserService;

public class UserInfoActivity extends AppCompatActivity {
    EditText etName, etDate, etPhone, etAddress, etEmail;
    Button btnEdit, btnChangePassword;
    ImageView btnBack;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        SessionManagement sessionManagement = new SessionManagement(UserInfoActivity.this);
        int id = sessionManagement.getSession();

        userService = APIUtils.getUserService();
        btnBack = findViewById(R.id.btnBack);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        btnEdit = findViewById(R.id.btnEdit);

        Bundle extras = getIntent().getExtras();

        etName.setText(extras.getString("name"));
        etDate.setText(extras.getString("birthday"));
        etPhone.setText(extras.getString("phone"));
        etAddress.setText(extras.getString("address"));
        etEmail.setText(extras.getString("email"));


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setId(id);
                user.setUsername(extras.getString("username"));
                user.setPassword(extras.getString("password"));
                user.setName(etName.getText().toString());
                user.setBirthDay(etDate.getText().toString());
                user.setPhoneNumber(etPhone.getText().toString());
                user.setAddress(etAddress.getText().toString());
                user.setEmail(etEmail.getText().toString());
                user.setStatus(1);
                editUser(id, user);
                Toast.makeText(UserInfoActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserInfoActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserInfoActivity.this, ChangePasswordActivity.class);
                i.putExtra("edit_name", extras.getString("name"));
                i.putExtra("edit_username", extras.getString("username"));
                i.putExtra("edit_password", extras.getString("password"));
                i.putExtra("edit_birthday", extras.getString("birthday"));
                i.putExtra("edit_phone", extras.getString("phone"));
                i.putExtra("edit_address", extras.getString("address"));
                i.putExtra("edit_email",extras.getString("email"));
                startActivity(i);
            }
        });
    }

    public void editUser(int id,User user){
        Call<User> update = userService.updateUser(id,user);
        update.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }
}