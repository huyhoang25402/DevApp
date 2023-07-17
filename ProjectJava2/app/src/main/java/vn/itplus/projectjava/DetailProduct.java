package vn.itplus.projectjava;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import vn.itplus.projectjava.model.Cart;
import vn.itplus.projectjava.remote.APIUtils;

public class DetailProduct extends AppCompatActivity {
    TextView tvProductName, tvProductPrice, tvProductDesc,tvCartQty;
    EditText etProductNumber;
    ImageView btnBackDetail, imgProductDetail, btnCart;
    Button btnProductDec, btnProductInc, btnAddToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductDesc = findViewById(R.id.tvProductDesc);
        tvCartQty = findViewById(R.id.tvCartQty);
        etProductNumber = findViewById(R.id.etProductNumber);
        imgProductDetail = findViewById(R.id.imgProductDetail);
        btnCart = findViewById(R.id.btnCart);
        btnBackDetail = findViewById(R.id.btnBackDetail);
        btnProductDec = findViewById(R.id.btnProductDec);
        btnProductInc = findViewById(R.id.btnProductInc);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        if (APIUtils.listCart != null){
            tvCartQty.setText(String.valueOf(getAllQtyProductCart()));
        }

        Bundle extras = getIntent().getExtras();

        tvProductName.setText(extras.getString("product_name"));
        NumberFormat formatter = new DecimalFormat("###,###,###");
        int priceProduct = Integer.parseInt(extras.getString("product_price"));
        String price_format = formatter.format(priceProduct);
        tvProductPrice.setText(price_format);
        tvProductDesc.setText(extras.getString("product_desc"));
        Glide.with(DetailProduct.this).load(extras.getString("product_avatar")).into(imgProductDetail);
        etProductNumber.setKeyListener(null);

        btnBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnProductDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.parseInt(String.valueOf(etProductNumber.getText()));
                number--;
                if (number <=1){
                    number = 1;
                }
                etProductNumber.setText(String.valueOf(number));
            }
        });

        btnProductInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.parseInt(String.valueOf(etProductNumber.getText()));
                number++;
                etProductNumber.setText(String.valueOf(number));
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
                Toast.makeText(DetailProduct.this, "Thêm vào giỏ hàng thành công !", Toast.LENGTH_SHORT).show();
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailProduct.this, CartActivity.class));
            }
        });
    }

    private void addToCart() {
        if (APIUtils.listCart.size() > 0){
            boolean flag = false;
            Bundle extras = getIntent().getExtras();
            int qty = Integer.parseInt(etProductNumber.getText().toString());
            for (int i = 0; i < APIUtils.listCart.size(); i++){
                if (APIUtils.listCart.get(i).getId() == Integer.parseInt(extras.getString("product_id"))){
                    APIUtils.listCart.get(i).setQty(qty + APIUtils.listCart.get(i).getQty());
                    int price = Integer.parseInt(extras.getString("product_price"));
                    APIUtils.listCart.get(i).setPrice(price);
                    flag = true;
                }
            }
            if (flag == false){
                int price = Integer.parseInt(extras.getString("product_price"));
                Cart cart = new Cart();
                cart.setPrice(price);
                cart.setQty(qty);
                cart.setId(Integer.parseInt(extras.getString("product_id")));
                cart.setName(tvProductName.getText().toString());
                cart.setImage(extras.getString("product_avatar"));
                APIUtils.listCart.add(cart);
            }
        }else{
            Bundle extras = getIntent().getExtras();
            int qty = Integer.parseInt(etProductNumber.getText().toString());
            int price = Integer.parseInt(extras.getString("product_price"));
            Cart cart = new Cart();
            cart.setPrice(price);
            cart.setQty(qty);
            cart.setId(Integer.parseInt(extras.getString("product_id")));
            cart.setName(tvProductName.getText().toString());
            cart.setImage(extras.getString("product_avatar"));
            APIUtils.listCart.add(cart);
        }
        tvCartQty.setText(String.valueOf(getAllQtyProductCart()));
    }
    public int getAllQtyProductCart(){
        int qty = 0;
        for (int i = 0; i < APIUtils.listCart.size(); i++){
            qty+= APIUtils.listCart.get(i).getQty();
        }
        return qty;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (APIUtils.listCart != null){
            tvCartQty.setText(String.valueOf(getAllQtyProductCart()));
        }
    }
}