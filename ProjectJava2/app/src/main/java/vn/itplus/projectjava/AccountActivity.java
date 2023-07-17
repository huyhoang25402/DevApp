package vn.itplus.projectjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.itplus.projectjava.model.User;
import vn.itplus.projectjava.remote.APIUtils;
import vn.itplus.projectjava.remote.UserService;

public class AccountActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    UserService userService;
    Button btnLogin, btnRegister, btnLogOut;
    TextView tvUserName,tvUserInfo, tvHistory, tvWarranty, tvDelivery, tvContact;
    User userSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        SessionManagement sessionManagement = new SessionManagement(this);


        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setSelectedItemId(R.id.account);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case  R.id.product:
                        startActivity(new Intent(getApplicationContext(), ProductActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case  R.id.account:
                        return true;
                }
                return false;
            }
        });
        userService = APIUtils.getUserService();
        tvUserInfo = findViewById(R.id.tvUserInfo);
        tvUserName = findViewById(R.id.tvUserName);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogOut = findViewById(R.id.btnLogOut);

        tvHistory = findViewById(R.id.tvHistoryOrder);
        tvWarranty = findViewById(R.id.tvWarranty);
        tvDelivery = findViewById(R.id.tvDelivery);
        tvContact = findViewById(R.id.tvContact);

        tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManagement.getSession() != -1){
                    startActivity(new Intent(AccountActivity.this, OrderHistoryActivity.class));
                }else{
                    Toast.makeText(AccountActivity.this,"Cần đăng nhập để sử dụng chức năng này", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(AccountActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });

        tvWarranty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, WarrantyActivity.class));
            }
        });

        tvDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, DeliveryActivity.class));
            }
        });


        tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, ContactActivity.class));
            }
        });
        int userId = sessionManagement.getSession();
        if (userId != -1){
            getUserById(userId);
            tvUserName.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
            btnRegister.setVisibility(View.GONE);
            btnLogOut.setVisibility(View.VISIBLE);
        }else{
            tvUserName.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.VISIBLE);
            btnLogOut.setVisibility(View.GONE);
        }



        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnReg = findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManagement sessionManagement = new SessionManagement(AccountActivity.this);
                sessionManagement.removeSession();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        tvUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserByIdToShowInfo(userId);
            }
        });
    }

    public void getUserByIdToShowInfo(int id){
        SessionManagement sessionManagement = new SessionManagement(AccountActivity.this);
        int userId = sessionManagement.getSession();
        Call<User> callUser = userService.getUserById(id);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    if (userId != -1){
                        userSession = response.body();
                        Intent i = new Intent(AccountActivity.this, UserInfoActivity.class);
                        i.putExtra("id", userSession.getId());
                        i.putExtra("name", userSession.getName());
                        i.putExtra("username", userSession.getUsername());
                        i.putExtra("password", userSession.getPassword());
                        i.putExtra("phone", userSession.getPhoneNumber());
                        i.putExtra("address", userSession.getAddress());
                        i.putExtra("email", userSession.getEmail());
                        i.putExtra("birthday", userSession.getBirthDay());
                        startActivity(i);
                    }else{
                        Toast.makeText(AccountActivity.this,"Cần đăng nhập để sử dụng chức năng này", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(AccountActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                }else{
                    userSession = null;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    public void getUserById(int id){
        Call<User> callUser = userService.getUserById(id);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    userSession = response.body();
                    tvUserName.setText(userSession.getName());
                }else{
                    userSession = null;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }
}