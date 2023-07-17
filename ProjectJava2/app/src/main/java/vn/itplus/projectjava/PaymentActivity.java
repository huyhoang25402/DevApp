package vn.itplus.projectjava;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.itplus.projectjava.model.Order;
import vn.itplus.projectjava.model.OrderDetail;
import vn.itplus.projectjava.model.User;
import vn.itplus.projectjava.remote.APIUtils;
import vn.itplus.projectjava.remote.OrderDetailService;
import vn.itplus.projectjava.remote.OrderService;
import vn.itplus.projectjava.remote.UserService;

public class PaymentActivity extends AppCompatActivity {
    TextView tvPaymentPrice;
    EditText etPaymentPhone, etPaymentAddress;
    ImageView btnBack;
    Button btnPayment;
    OrderService orderService;
    OrderDetailService orderDetailService;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        SessionManagement sessionManagement = new SessionManagement(PaymentActivity.this);
        System.out.println(sessionManagement.getSession());
        orderService = APIUtils.getOrderService();
        orderDetailService = APIUtils.getOrderDetailService();
        userService = APIUtils.getUserService();

        btnBack = findViewById(R.id.btnBackProductPayment);
        tvPaymentPrice = findViewById(R.id.tvPaymentPrice);
        etPaymentPhone = findViewById(R.id.etPaymentPhone);
        etPaymentAddress = findViewById(R.id.etPaymentAddress);
        btnPayment = findViewById(R.id.btnPayment);

        NumberFormat formatter = new DecimalFormat("###,###,###");
        int totalPrice = getAllProductPrice();
        String totalPriceFormat = formatter.format(totalPrice);
        tvPaymentPrice.setText(totalPriceFormat);

        getCurrentUserById(sessionManagement.getSession());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etPaymentAddress.getText().toString().equals("") || etPaymentPhone.getText().toString().equals("")){
                    Toast.makeText(PaymentActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    int orderid = getRandomNumber(1000, 9999);
                    int iduser = sessionManagement.getSession();
                    Order order = new Order();
                    order.setId(orderid);
                    order.setUserId(iduser);
                    order.setPrice(getAllProductPrice());
                    order.setQty(getAllProductQty());
                    order.setAddress(etPaymentAddress.getText().toString());
                    order.setPhone(etPaymentPhone.getText().toString());
                    order.setStatus(1);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    order.setOrderTime(formatter.format(date));
                    createOrder(order);
                    for (int i = 0; i < APIUtils.listCart.size(); i++){
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setOrderId(orderid);
                        orderDetail.setQty(APIUtils.listCart.get(i).getQty());
                        orderDetail.setPrice(APIUtils.listCart.get(i).getPrice() * APIUtils.listCart.get(i).getQty());
                        orderDetail.setName(APIUtils.listCart.get(i).getName());
                        orderDetail.setStatus(1);
                        createOrderDetail(orderDetail);
                    }
                    APIUtils.listCart.clear();
                    Toast.makeText(PaymentActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PaymentActivity.this, MainActivity.class));
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

    public void createOrder(Order order){
        Call<Order> create = orderService.addOrder(order);
        create.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {

            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }

    public void createOrderDetail(OrderDetail orderDetail){
        Call<OrderDetail> create = orderDetailService.addOrderDetail(orderDetail);
        create.enqueue(new Callback<OrderDetail>() {
            @Override
            public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {

            }

            @Override
            public void onFailure(Call<OrderDetail> call, Throwable t) {

            }
        });
    }

    public static int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }

    public void getCurrentUserById(int id){
        Call<User> get = userService.getUserById(id);
        get.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User u = response.body();
                System.out.println(u);
                etPaymentPhone.setText(u.getPhoneNumber());
                etPaymentAddress.setText(u.getAddress());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}