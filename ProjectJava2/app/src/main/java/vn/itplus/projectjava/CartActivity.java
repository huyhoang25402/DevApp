package vn.itplus.projectjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import vn.itplus.projectjava.model.Cart;
import vn.itplus.projectjava.model.EventBus.ProductPriceTotal;
import vn.itplus.projectjava.remote.APIUtils;

public class CartActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView tvCartEmpty, tvPriceTotal;
    Button btnBuyProduct;
    ListView lvCart;
    CartAdapter cartAdapter;
    List<Cart> cartList;
    ImageView btnBackProductCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tvCartEmpty = findViewById(R.id.tvCartEmpty);
        lvCart = findViewById(R.id.lvCart);
        tvPriceTotal = findViewById(R.id.tvPriceTotal);
        btnBackProductCart = findViewById(R.id.btnBackProductCart);
        btnBuyProduct = findViewById(R.id.btnBuyProduct);

        SessionManagement sessionManagement = new SessionManagement(this);


        if (APIUtils.listCart.size() == 0){
            tvPriceTotal.setText("0");
        }else{
            NumberFormat formatter = new DecimalFormat("###,###,###");
            int totalPrice = getAllProductPrice();
            String totalPriceFormat = formatter.format(totalPrice);
            tvPriceTotal.setText(totalPriceFormat);
        }

        if (APIUtils.listCart.size() == 0){
            tvCartEmpty.setVisibility(View.VISIBLE);
        }else{
            cartAdapter = new CartAdapter(getApplicationContext(), R.layout.item_cart, APIUtils.listCart);
            lvCart.setAdapter(cartAdapter);
        }

        btnBackProductCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnBuyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sessionManagement.getSession() != -1){
                    if (getAllProductPrice() == 0){
                        Toast.makeText(CartActivity.this, "Giỏ hàng trống, không thể mua hàng", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent i = new Intent(CartActivity.this, PaymentActivity.class);
                        startActivity(i);
                    }
                }else{
                    Toast.makeText(CartActivity.this, "Vui lòng đăng nhập để đặt hàng", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CartActivity.this, LoginActivity.class));
                }
            }
        });

    }

    public int getAllProductPrice(){
        int price = 0;
        for (int i = 0; i < APIUtils.listCart.size(); i++){
            price+= (APIUtils.listCart.get(i).getQty() * APIUtils.listCart.get(i).getPrice());
        }
        return price;
    }

    public int getAllProductQty(){
        int qty = 0;
        for (int i = 0; i < APIUtils.listCart.size(); i++){
            qty+= APIUtils.listCart.get(i).getQty();
        }
        return qty;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventProductPriceTotal(ProductPriceTotal event){
        if (event != null){
            NumberFormat formatter = new DecimalFormat("###,###,###");
            int totalPrice = getAllProductPrice();
            String totalPriceFormat = formatter.format(totalPrice);
            tvPriceTotal.setText(totalPriceFormat);
        }
    }
}