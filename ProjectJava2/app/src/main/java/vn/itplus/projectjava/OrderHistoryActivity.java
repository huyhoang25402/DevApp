package vn.itplus.projectjava;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.itplus.projectjava.model.Order;
import vn.itplus.projectjava.remote.APIUtils;
import vn.itplus.projectjava.remote.OrderService;

public class OrderHistoryActivity extends AppCompatActivity {
    ImageView btnBackOrder;
    OrderService orderService;
    ListView lvOrder;
    List<Order> listOrder;
    OrderHistoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        orderService = APIUtils.getOrderService();
        SessionManagement sessionManagement = new SessionManagement(this);

        btnBackOrder = findViewById(R.id.btnBackHistory);
        lvOrder = findViewById(R.id.lvOrderHistory);
        listOrder = null;
        getOrderHistory(sessionManagement.getSession());

        btnBackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void getOrderHistory(int id){
        Call<List<Order>> show = orderService.getOrderByIdDESC(id);
        show.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {

                listOrder = response.body();
                adapter = new OrderHistoryAdapter(OrderHistoryActivity.this, R.layout.item_history_order, listOrder);
                lvOrder.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }

}