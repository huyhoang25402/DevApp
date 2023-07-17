package vn.itplus.projectjava;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.itplus.projectjava.model.Product;
import vn.itplus.projectjava.remote.APIUtils;
import vn.itplus.projectjava.remote.ProductService;

public class ProductByCategory extends AppCompatActivity {
    TextView categoryName;
    ImageView btnBack;
    List<Product> productList;
    ListView listViewProduct;
    ProductService productService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_category);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        productService = APIUtils.getProductService();
        btnBack = findViewById(R.id.btnBack);
        categoryName = findViewById(R.id.categoryName);
        listViewProduct = findViewById(R.id.lvProduct);

        Bundle extras = getIntent().getExtras();
        getProductListByCategory(Integer.parseInt(extras.getString("category_id")));
        categoryName.setText(extras.getString("category_name"));


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getProductListByCategory(int category_id) {
        Call<List<Product>> callProduct = productService.getProductByCategory(category_id);
        callProduct.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    productList = response.body();
                    ProductAdapter productAdapter = new ProductAdapter(ProductByCategory.this, R.layout.bestseller_product,productList);
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