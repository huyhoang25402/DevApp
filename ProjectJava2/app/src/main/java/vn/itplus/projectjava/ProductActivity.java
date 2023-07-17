package vn.itplus.projectjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.lucasr.twowayview.TwoWayView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.itplus.projectjava.model.Category;
import vn.itplus.projectjava.model.Product;
import vn.itplus.projectjava.remote.APIUtils;
import vn.itplus.projectjava.remote.CategoryService;
import vn.itplus.projectjava.remote.ProductService;

public class ProductActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    List<Category> listCategory;
    TwoWayView listViewCategory;
    List<Product> productList;
    ListView listViewProduct;
    ProductService productService;
    CategoryService categoryService;
    TextView tvProductPriceDefault, tvProductPriceInc, tvProductPriceDec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setSelectedItemId(R.id.product);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case  R.id.product:
                        return true;
                    case  R.id.account:
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        productService = APIUtils.getProductService();
        categoryService = APIUtils.getCategoryService();
        tvProductPriceDefault = findViewById(R.id.tvProductPriceDefault);
        tvProductPriceInc = findViewById(R.id.tvProductPriceInc);
        tvProductPriceDec = findViewById(R.id.tvProductPriceDec);
        listViewCategory = (TwoWayView) findViewById(R.id.listCategory);
        listViewProduct = findViewById(R.id.lvProduct);


        getCategoryList();

        getProductsList();

        tvProductPriceInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProductsListASC();
            }
        });

        tvProductPriceDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProductsListDESC();
            }
        });

        tvProductPriceDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProductsList();
            }
        });
    }

    public void getCategoryList(){
        Call<List<Category>> callCategory = categoryService.getCategory();
        callCategory.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()){
                    listCategory = response.body();
                    CategoryAdapter categoryAdapter = new CategoryAdapter(ProductActivity.this, R.layout.layout_category, listCategory);
                    listViewCategory.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

        public void getProductsList(){
            Call<List<Product>> callProduct = productService.getProduct();
            callProduct.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()){
                        productList = response.body();
                        ProductAdapter productAdapter = new ProductAdapter(ProductActivity.this, R.layout.bestseller_product,productList);
                        listViewProduct.setAdapter(productAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    Log.e("Error: ", t.getMessage());
                }
            });
        }

    public void getProductsListASC(){
        Call<List<Product>> callProduct = productService.getProductASC();
        callProduct.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    productList = response.body();
                    ProductAdapter productAdapter = new ProductAdapter(ProductActivity.this, R.layout.bestseller_product,productList);
                    listViewProduct.setAdapter(productAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    public void getProductsListDESC(){
        Call<List<Product>> callProduct = productService.getProductDESC();
        callProduct.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    productList = response.body();
                    ProductAdapter productAdapter = new ProductAdapter(ProductActivity.this, R.layout.bestseller_product,productList);
                    listViewProduct.setAdapter(productAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }
}