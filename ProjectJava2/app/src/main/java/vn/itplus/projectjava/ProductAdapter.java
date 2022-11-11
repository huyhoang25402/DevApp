package vn.itplus.projectjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private int idLayout;
    private ArrayList<Product> productArrayList;

    public ProductAdapter(Context context, int resource, ArrayList<Product> list) {
        super(context, resource, list);
        this.context = context;
        this.idLayout = resource;
        this.productArrayList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.bestseller_product,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imgProduct = (ImageView) convertView.findViewById(R.id.imgBestSellerProduct);
            viewHolder.textProductName = (TextView) convertView.findViewById(R.id.tvBestSellerProductName);
            viewHolder.textProductPrice = (TextView) convertView.findViewById(R.id.tvBestSellerProductPrice);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = productArrayList.get(position);
        viewHolder.imgProduct.setBackgroundResource(product.getImage());
        viewHolder.textProductName.setText(product.getName());
        viewHolder.textProductPrice.setText(String.valueOf(product.getPrice()));
        return convertView;
    }

    public static class ViewHolder {
        private ImageView imgProduct;
        private TextView textProductName, textProductPrice;
    }
}
