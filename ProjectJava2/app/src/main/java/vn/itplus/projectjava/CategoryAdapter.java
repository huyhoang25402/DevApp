package vn.itplus.projectjava;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private Context context;
    private int idLayout;
    private ArrayList<Category> categoryArrayList;

    public CategoryAdapter(Context context, int idLayout, ArrayList<Category> list) {
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
        viewHolder.imgCate.setBackgroundResource(category.getImage());
        viewHolder.textCate.setText(category.getName());
        return convertView;
    }

    public static class ViewHolder {
        private ImageView imgCate;
        private TextView textCate;
    }
}
