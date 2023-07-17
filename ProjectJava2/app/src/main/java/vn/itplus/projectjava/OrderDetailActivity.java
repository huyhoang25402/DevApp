package vn.itplus.projectjava;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.itplus.projectjava.model.OrderDetail;
import vn.itplus.projectjava.remote.APIUtils;
import vn.itplus.projectjava.remote.OrderDetailService;

public class OrderDetailActivity extends AppCompatActivity {
    TextView tvOrderDetailId, tvOrderDetailAddress, tvOrderDetailPhone, tvOrderDetailTime,  tvDetailPrice, tvOrderDetailStatus;
    ImageView btnBack;
    ListView lvOrderDetail;
    List<OrderDetail> list;
    OrderDetailService orderDetailService;
    OrderDetailAdapter orderDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Bundle extras = getIntent().getExtras();
        orderDetailService = APIUtils.getOrderDetailService();
        tvOrderDetailId = findViewById(R.id.tvOrderDetailId);
        tvOrderDetailAddress = findViewById(R.id.tvOrderDetailAddress);
        tvOrderDetailPhone = findViewById(R.id.tvOrderDetailPhone);
        tvOrderDetailTime = findViewById(R.id.tvOrderDetailTime);
        tvDetailPrice = findViewById(R.id.tvDetailPrice);
        lvOrderDetail = findViewById(R.id.lvOrderDetail);
        tvOrderDetailStatus = findViewById(R.id.tvOrderDetailStatus);
        tvOrderDetailId.setText(extras.getString("order_id"));
        tvOrderDetailAddress.setText(extras.getString("order_address"));
        tvOrderDetailPhone.setText(extras.getString("order_phone"));
        tvOrderDetailTime.setText(extras.getString("order_time"));
        if (extras.getString("order_status").equals("1")){
            tvOrderDetailStatus.setText("Chưa giao hàng");
        }else {
            tvOrderDetailStatus.setText("Đã giao hàng");
        }
        getOrderDetail(Integer.parseInt(extras.getString("order_id")));
        btnBack = findViewById(R.id.btnBackHistory);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getOrderDetail(int id){
        Call<List<OrderDetail>> get = orderDetailService.getByOrderDetail(id);
        get.enqueue(new Callback<List<OrderDetail>>() {
            @Override
            public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                list = response.body();
                orderDetailAdapter = new OrderDetailAdapter(OrderDetailActivity.this, R.layout.item_order_detail, list);
                lvOrderDetail.setAdapter(orderDetailAdapter);
                int price = 0;
                for(int i = 0; i < list.size(); i++){
                    price += list.get(i).getPrice();
                }
                NumberFormat formatter = new DecimalFormat("###,###,###");
                String totalPriceFormat = formatter.format(price);
                tvDetailPrice.setText(totalPriceFormat);
            }

            @Override
            public void onFailure(Call<List<OrderDetail>> call, Throwable t) {

            }
        });
    }
}