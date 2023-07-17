package vn.itplus.projectjava.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.itplus.projectjava.model.Product;

public interface ProductService {

    @GET("get-all")
    Call<List<Product>> getProduct();

    @GET("get-all-asc")
    Call<List<Product>> getProductASC();

    @GET("get-all-desc")
    Call<List<Product>> getProductDESC();

    @GET("get-by-category/{category_id}")
    Call<List<Product>> getProductByCategory(@Path("category_id") int id);

    @GET("search-product/{name}")
    Call<List<Product>> getProductByName(@Path("name") String name);

    @GET("sort-by-id")
    Call<List<Product>> getNewestProduct();

    @POST("insert")
    Call<Product> addProduct(@Body Product product);

    @DELETE("delete/{id}")
    Call<Product> deleteProduct(@Path("id") int id);

    @PUT("update/{id}")
    Call<Product> updateProduct(@Path("id") int id);
}
