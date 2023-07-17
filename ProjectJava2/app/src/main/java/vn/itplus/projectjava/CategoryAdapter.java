package vn.itplus.projectjava;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.itplus.projectjava.model.Category;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private Context context;
    private int idLayout;
    private List<Category> categoryArrayList;

    public CategoryAdapter(Context context, int idLayout, List<Category> list) {
        super(context, idLayout, list);
        this.context = context;
        this.idLayout = idLayout;
        this.categoryArrayList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_category,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imgCate = (ImageView) convertView.findViewById(R.id.imgCate);
            viewHolder.textCate = (TextView) convertView.findViewById(R.id.textCate);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Category category = categoryArrayList.get(position);
//        viewHolder.imgCate.setBackgroundResource(category.getImage());
        Picasso.get().load(category.getImage()).networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(viewHolder.imgCate);
        viewHolder.textCate.setText(category.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ProductByCategory.class);
                i.putExtra("category_id", String.valueOf(category.getId()));
                i.putExtra("category_image", String.valueOf(category.getImage()));
                i.putExtra("category_name", String.valueOf(category.getName()));
                context.startActivity(i);
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        private ImageView imgCate;
        private TextView textCate;
    }
}
