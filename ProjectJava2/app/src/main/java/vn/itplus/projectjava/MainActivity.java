package vn.itplus.projectjava;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}