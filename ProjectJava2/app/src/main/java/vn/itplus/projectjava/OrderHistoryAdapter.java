package vn.itplus.projectjava;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import vn.itplus.projectjava.model.Order;

public class OrderHistoryAdapter extends ArrayAdapter<Order> {

    private Context context;
    private int idLayout;
    private List<Order> orderList;
    public OrderHistoryAdapter(Context context, int resource, List<Order> orderList) {
        super(context, resource, orderList);
        this.context = context;
        this.idLayout = resource;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_history_order, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvOrderId = convertView.findViewById(R.id.tvOrderId);
            viewHolder.tvOrderAddress = convertView.findViewById(R.id.tvOrderAddress);
            viewHolder.tvOrderTime = convertView.findViewById(R.id.tvOrderTime);
            viewHolder.tvOrderPhone = convertView.findViewById(R.id.tvOrderPhone);
            viewHolder.tvOrderQty = convertView.findViewById(R.id.tvOrderQty);
            viewHolder.tvOrderPrice = convertView.findViewById(R.id.tvOrderPrice);
            viewHolder.tvOrderStatus = convertView.findViewById(R.id.tvOrderStatus);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Order order = orderList.get(position);
        viewHolder.tvOrderId.setText(Integer.toString(order.getId()));
        viewHolder.tvOrderAddress.setText(order.getAddress());
        viewHolder.tvOrderTime.setText(order.getOrderTime());
        viewHolder.tvOrderPhone.setText(order.getPhone());
        viewHolder.tvOrderQty.setText(Integer.toString(order.getQty()));
        NumberFormat formatter = new DecimalFormat("###,###,###");
        int totalPrice = order.getPrice();
        String totalPriceFormat = formatter.format(totalPrice);
        viewHolder.tvOrderPrice.setText(totalPriceFormat);
        if (order.getStatus() == 1){
            viewHolder.tvOrderStatus.setText("Chưa giao hàng");
        }else{
            viewHolder.tvOrderStatus.setText("Đã giao hàng");
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, OrderDetailActivity.class);
                i.putExtra("order_id", Integer.toString(order.getId()));
                i.putExtra("order_address", order.getAddress());
                i.putExtra("order_phone", order.getPhone());
                i.putExtra("order_time", order.getOrderTime());
                i.putExtra("order_status", Integer.toString(order.getStatus()));
                context.startActivity(i);
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        private TextView tvOrderId, tvOrderAddress, tvOrderTime, tvOrderPhone, tvOrderQty, tvOrderPrice, tvOrderStatus;
    }
}
