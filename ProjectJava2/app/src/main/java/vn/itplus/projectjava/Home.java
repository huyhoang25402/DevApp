package vn.itplus.projectjava;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import org.lucasr.twowayview.TwoWayView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageSlider imageSlider;
    ArrayList<Category> list;
    CategoryAdapter categoryAdapter;
    TwoWayView  listView;
    ArrayList<Product> bestSellerProductList;
    ProductAdapter productAdapter;
    ListView listBestSellerProduct;
    ArrayList<Product> newestProductList;
    ListView listNewestProduct;



    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Slider
        imageSlider = view.findViewById(R.id.image_slider);
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.slide1, null));
        imageList.add(new SlideModel(R.drawable.slide2, null));
        imageList.add(new SlideModel(R.drawable.slide3, null));
        imageSlider.setImageList(imageList);

        //ListView category
        listView = (TwoWayView) view.findViewById(R.id.listCategory);
        list = new ArrayList<>();
        list.add(new Category(R.drawable.cable,"Củ, cáp"));
        list.add(new Category(R.drawable.headphone,"Tai nghe"));
        list.add(new Category(R.drawable.phonecase,"Ốp lưng"));
        list.add(new Category(R.drawable.screen,"Cường lực"));
        list.add(new Category(R.drawable.power,"Sạc dự phòng"));
        list.add(new Category(R.drawable.speaker,"Loa"));
        categoryAdapter = new CategoryAdapter(getContext(), R.layout.layout_category, list);
        listView.setAdapter(categoryAdapter);

        //ListView best seller product
        listBestSellerProduct = view.findViewById(R.id.lvBestSellerProduct);
        bestSellerProductList = new ArrayList<>();
        bestSellerProductList.add(new Product(150000,R.drawable.sac,"Cáp USB-A to Lightning Choetech MFi"));
        bestSellerProductList.add(new Product(149000,R.drawable.cusac1,"Sạc nhanh Anker Powerport III Nano 20W A2633"));
        bestSellerProductList.add(new Product(150000,R.drawable.tainghe,"Tai nghe Xiaomi Mi Basic Chính hãng"));
        bestSellerProductList.add(new Product(440000,R.drawable.sacduphong,"Pin sạc dự phòng Aukey PB-N83S 20W PD"));
        Log.d( "onCreateView: ", String.valueOf(bestSellerProductList));
        productAdapter = new ProductAdapter(getContext(), R.layout.bestseller_product, bestSellerProductList);
        listBestSellerProduct.setAdapter(productAdapter);

        //ListView newest seller product
        listNewestProduct = view.findViewById(R.id.lvNewestProduct);
        newestProductList = new ArrayList<>();
        newestProductList.add(new Product(150000,R.drawable.sac,"Cáp USB-A to Lightning Choetech MFi"));
        newestProductList.add(new Product(149000,R.drawable.cusac1,"Sạc nhanh Anker Powerport III Nano 20W A2633"));
        newestProductList.add(new Product(150000,R.drawable.tainghe,"Tai nghe Xiaomi Mi Basic Chính hãng"));
        newestProductList.add(new Product(440000,R.drawable.sacduphong,"Pin sạc dự phòng Aukey PB-N83S 20W PD"));
        productAdapter = new ProductAdapter(getContext(), R.layout.bestseller_product, newestProductList);
        listNewestProduct.setAdapter(productAdapter);
        return view;
    }
}