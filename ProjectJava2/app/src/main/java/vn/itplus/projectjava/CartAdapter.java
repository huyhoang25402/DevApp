package vn.itplus.projectjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import vn.itplus.projectjava.model.Cart;
import vn.itplus.projectjava.model.EventBus.ProductPriceTotal;
import vn.itplus.projectjava.remote.APIUtils;

public class CartAdapter extends ArrayAdapter<Cart> {

    private Context context;
    private int idLayout;
    private List<Cart> cartArrayList;


    public CartAdapter(Context context, int resource, List<Cart> list) {
        super(context, resource, list);
        this.context = context;
        this.idLayout = resource;
        this.cartArrayList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imgProductCart = convertView.findViewById(R.id.imgProductCart);
            viewHolder.tvProductName = convertView.findViewById(R.id.tvProductNameCart);
            viewHolder.tvProductPrice = convertView.findViewById(R.id.tvProductPrice);
            viewHolder.etProductNumber = convertView.findViewById(R.id.etProductNumber);
            viewHolder.tvProductPriceTotal = convertView.findViewById(R.id.tvProductPriceTotal);
            viewHolder.btnProductInc = convertView.findViewById(R.id.btnProductInc);
            viewHolder.btnProductDec = convertView.findViewById(R.id.btnProductDec);
        }else{
            viewHolder = (ViewHolder)  convertView.getTag();
        }
        Cart cart = cartArrayList.get(position);
        Picasso.get().load(cart.getImage()).networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(viewHolder.imgProductCart);
        viewHolder.tvProductName.setText(cart.getName());
        viewHolder.tvProductPrice.setText(Integer.toString(cart.getPrice()));
        viewHolder.etProductNumber.setText(Integer.toString(cart.getQty()));
        viewHolder.tvProductPriceTotal.setText(Integer.toString(cart.getQty()*cart.getPrice()));
        ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnProductDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalViewHolder.buttonClickListener.onButtonClick(view, position, 1);
            }
        });
        viewHolder.btnProductInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalViewHolder.buttonClickListener.onButtonClick(view, position, 2);
            }
        });
        viewHolder.setButtonClickListener(new ButtonClickListener() {
            @Override
            public void onButtonClick(View view, int position, int value) {
                if (value == 1){
                    if (cart.getQty() > 1){
                        int newQty = cart.getQty()-1;
                        cart.setQty(newQty);
                        finalViewHolder.etProductNumber.setText(Integer.toString(cart.getQty()));
                        finalViewHolder.tvProductPriceTotal.setText(Integer.toString(cart.getQty()*cart.getPrice()));
                        EventBus.getDefault().postSticky(new ProductPriceTotal());
                    }else if (cart.getQty() == 1){
                        APIUtils.listCart.remove(position);
                        notifyDataSetChanged();
                        finalViewHolder.etProductNumber.setText(Integer.toString(cart.getQty()));
                        finalViewHolder.tvProductPriceTotal.setText(Integer.toString(cart.getQty()*cart.getPrice()));
                        EventBus.getDefault().postSticky(new ProductPriceTotal());
                    }
                }else if (value ==2){
                    int newQty = cart.getQty()+1;
                    cart.setQty(newQty);
                    finalViewHolder.etProductNumber.setText(Integer.toString(cart.getQty()));
                    finalViewHolder.tvProductPriceTotal.setText(Integer.toString(cart.getQty()*cart.getPrice()));
                    EventBus.getDefault().postSticky(new ProductPriceTotal());
                }
            }
        });

        return convertView;
    }

    public static class ViewHolder implements View.OnClickListener{
        EditText etProductNumber;
        TextView tvProductName, tvProductPrice, tvProductPriceTotal;
        ImageView imgProductCart;
        Button btnProductDec, btnProductInc;
        ButtonClickListener buttonClickListener;

        public void setButtonClickListener(ButtonClickListener buttonClickListener) {
            this.buttonClickListener = buttonClickListener;
        }

        @Override
        public void onClick(View view){

        }
    }
}
