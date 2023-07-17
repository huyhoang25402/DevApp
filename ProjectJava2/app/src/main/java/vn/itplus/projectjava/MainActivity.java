package vn.itplus.projectjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.itplus.projectjava.model.Category;
import vn.itplus.projectjava.model.Product;
import vn.itplus.projectjava.remote.APIUtils;
import vn.itplus.projectjava.remote.CategoryService;
import vn.itplus.projectjava.remote.ProductService;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    EditText etSearch;
    TextView tvProductNameTitile, tvCartQtyHome;
    ImageSlider imageSlider;
    List<Category> listCategory;
    TwoWayView listViewCategory;
    List<Product> newestProductList;
    ListView listViewNewestProduct;
    ProductService productService;
    CategoryService categoryService;
    ImageView btnCartHome;
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
                    case  R.id.product:
                        startActivity(new Intent(getApplicationContext(), ProductActivity.class));
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

        tvProductNameTitile = findViewById(R.id.tvProductNameTitile);
        etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Toast.makeText(MainActivity.this,"1", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!(etSearch.getText().toString().equals(""))){
                    getProductByName(etSearch.getText().toString());
                    tvProductNameTitile.setText("Sản phẩm với từ khóa " + "'" + etSearch.getText().toString() + "'");
                }else{
                    getNewestProductsList();
                    tvProductNameTitile.setText("Sản phẩm mới nhất");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                Toast.makeText(MainActivity.this,"3", Toast.LENGTH_SHORT).show();
            }
        });

        productService = APIUtils.getProductService();
        categoryService = APIUtils.getCategoryService();
        imageSlider = findViewById(R.id.image_slider);
        listViewCategory = (TwoWayView) findViewById(R.id.listCategory);
        listCategory = new ArrayList<>();
        listViewNewestProduct = findViewById(R.id.lvOrderHistory);
        newestProductList = new ArrayList<>();
        btnCartHome = findViewById(R.id.btnCartHome);
        tvCartQtyHome = findViewById(R.id.tvCartQtyHome);

        try {
            tvCartQtyHome.setText(String.valueOf(getAllQtyProductCart()));
        }catch (Exception e){

        }

        if (APIUtils.listCart != null){
            tvCartQtyHome.setText(String.valueOf(getAllQtyProductCart()));
        }else{
            tvCartQtyHome.setText("0");
        }

        btnCartHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });

        if (APIUtils.listCart != null){
            tvCartQtyHome.setText(String.valueOf(getAllQtyProductCart()));
        }

        if (APIUtils.listCart == null){
            APIUtils.listCart = new ArrayList<>();
        }
        //Get Category ListView
        getCategoryList();

        //Get Newest Product ListView
        getNewestProductsList();

        //Slide
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.slide1, null));
        imageList.add(new SlideModel(R.drawable.slide2, null));
        imageList.add(new SlideModel(R.drawable.slide3, null));
        imageSlider.setImageList(imageList);




    }

    @Override
    protected void onResume() {
        super.onResume();
        if (APIUtils.listCart != null){
            tvCartQtyHome.setText(String.valueOf(getAllQtyProductCart()));
        }else{
            tvCartQtyHome.setText("0");
        }
    }

    public int getAllQtyProductCart(){
        int qty = 0;
        for (int i = 0; i < APIUtils.listCart.size(); i++){
            qty+= APIUtils.listCart.get(i).getQty();
        }
        return qty;
    }


    public void getCategoryList(){
        Call<List<Category>> callCategory = categoryService.getCategory();
        callCategory.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()){
                    listCategory = response.body();
                    CategoryAdapter categoryAdapter = new CategoryAdapter(MainActivity.this, R.layout.layout_category, listCategory);
                    listViewCategory.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    public void getNewestProductsList(){
        Call<List<Product>> callProduct = productService.getNewestProduct();
        callProduct.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    newestProductList = response.body();
                    ProductAdapter productAdapter = new ProductAdapter(MainActivity.this, R.layout.bestseller_product,newestProductList);
                    listViewNewestProduct.setAdapter(productAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    public void getProductByName(String name){
        Call<List<Product>> callProduct = productService.getProductByName(name);
        callProduct.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    newestProductList = response.body();
                    ProductAdapter productAdapter = new ProductAdapter(MainActivity.this, R.layout.bestseller_product,newestProductList);
                    listViewNewestProduct.setAdapter(productAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }
}