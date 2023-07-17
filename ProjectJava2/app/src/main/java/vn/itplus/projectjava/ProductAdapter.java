package vn.itplus.projectjava;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.bumptech.glide.Glide;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import vn.itplus.projectjava.model.Product;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private int idLayout;
    private List<Product> productArrayList;

    public ProductAdapter(Context context, int resource, List<Product> list) {
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
        Picasso.get().load(product.getImage()).networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(viewHolder.imgProduct);
//        Glide.with(context).load(product.getImage()).into(viewHolder.imgProduct);
        viewHolder.textProductName.setText(product.getName());
        NumberFormat formatter = new DecimalFormat("###,###,###");
        int price = product.getPrice();
        String price_format = formatter.format(price);
        viewHolder.textProductPrice.setText(price_format);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProduct.class);
                intent.putExtra("product_id", String.valueOf(product.getId()));
                intent.putExtra("product_name", String.valueOf(product.getName()));
                intent.putExtra("product_price", String.valueOf(product.getPrice()));
                intent.putExtra("product_desc", String.valueOf(product.getDescription()));
                intent.putExtra("product_avatar", String.valueOf(product.getImage()));
                intent.putExtra("product_category_id", String.valueOf(product.getCategoryId()));
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        private ImageView imgProduct;
        private TextView textProductName, textProductPrice;
    }
}
