package vn.itplus.projectjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import vn.itplus.projectjava.model.Order;
import vn.itplus.projectjava.model.OrderDetail;

public class OrderDetailAdapter extends ArrayAdapter<OrderDetail> {
    private Context context;
    private int idLayout;
    private List<OrderDetail> orderDetailListList;
    public OrderDetailAdapter(Context context, int resource, List<OrderDetail> orderDetailListList) {
        super(context, resource, orderDetailListList);
        this.context = context;
        this.idLayout = resource;
        this.orderDetailListList = orderDetailListList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
            if (viewHolder ==null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_order_detail, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tvOrderDetailName = convertView.findViewById(R.id.tvOrderDetailName);
                viewHolder.tvOrderDetaiQty = convertView.findViewById(R.id.tvOrderDetailQty);
                viewHolder.tvOrderDetailPrice = convertView.findViewById(R.id.tvOrderDetailPrice);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            OrderDetail orderDetail = orderDetailListList.get(position);
            viewHolder.tvOrderDetailName.setText(orderDetail.getName());
            viewHolder.tvOrderDetaiQty.setText(Integer.toString(orderDetail.getQty()));
            viewHolder.tvOrderDetailPrice.setText(Integer.toString(orderDetail.getPrice()));

        return convertView;
    }

    public static class ViewHolder{
        TextView tvOrderDetailName, tvOrderDetaiQty, tvOrderDetailPrice;
    }
}
