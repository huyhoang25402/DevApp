package vn.itplus.projectjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageSlider imageSlider;
    ArrayList<Category> list;
    CategoryAdapter categoryAdapter;
    TwoWayView listView;
    ArrayList<Product> bestSellerProductList;
    ProductAdapter productAdapter;
    ListView listBestSellerProduct;
    ArrayList<Product> newestProductList;
    ListView listNewestProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case  R.id.cart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case  R.id.account:
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        imageSlider = findViewById(R.id.image_slider);
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.slide1, null));
        imageList.add(new SlideModel(R.drawable.slide2, null));
        imageList.add(new SlideModel(R.drawable.slide3, null));
        imageSlider.setImageList(imageList);

        listView = (TwoWayView) findViewById(R.id.listCategory);
        list = new ArrayList<>();
        list.add(new Category(R.drawable.cable,"Củ, cáp"));
        list.add(new Category(R.drawable.headphone,"Tai nghe"));
        list.add(new Category(R.drawable.phonecase,"Ốp lưng"));
        list.add(new Category(R.drawable.screen,"Cường lực"));
        list.add(new Category(R.drawable.power,"Sạc dự phòng"));
        list.add(new Category(R.drawable.speaker,"Loa"));
        categoryAdapter = new CategoryAdapter(MainActivity.this, R.layout.layout_category, list);
        listView.setAdapter(categoryAdapter);

        listBestSellerProduct = findViewById(R.id.lvBestSellerProduct);
        bestSellerProductList = new ArrayList<>();
        bestSellerProductList.add(new Product(150000,R.drawable.sac,"Cáp USB-A to Lightning Choetech MFi"));
        bestSellerProductList.add(new Product(149000,R.drawable.cusac1,"Sạc nhanh Anker Powerport III Nano 20W A2633"));
        bestSellerProductList.add(new Product(150000,R.drawable.tainghe,"Tai nghe Xiaomi Mi Basic Chính hãng"));
        bestSellerProductList.add(new Product(440000,R.drawable.sacduphong,"Pin sạc dự phòng Aukey PB-N83S 20W PD"));
        productAdapter = new ProductAdapter(MainActivity.this, R.layout.bestseller_product, bestSellerProductList);
        listBestSellerProduct.setAdapter(productAdapter);

        listNewestProduct = findViewById(R.id.lvNewestProduct);
        newestProductList = new ArrayList<>();
        newestProductList.add(new Product(150000,R.drawable.sac,"Cáp USB-A to Lightning Choetech MFi"));
        newestProductList.add(new Product(149000,R.drawable.cusac1,"Sạc nhanh Anker Powerport III Nano 20W A2633"));
        newestProductList.add(new Product(150000,R.drawable.tainghe,"Tai nghe Xiaomi Mi Basic Chính hãng"));
        newestProductList.add(new Product(440000,R.drawable.sacduphong,"Pin sạc dự phòng Aukey PB-N83S 20W PD"));
        productAdapter = new ProductAdapter(MainActivity.this, R.layout.bestseller_product, newestProductList);
        listNewestProduct.setAdapter(productAdapter);
    }
}