package vn.itplus.projectjava.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.itplus.projectjava.model.Order;

public interface OrderService {

    @GET("get-all")
    Call<List<Order>> getOrder();

    @GET("get-all-desc")
    Call<List<Order>> getOrderDESC();

    @GET("get-by-user-id/{user_id}")
    Call<List<Order>> getOrderById(@Path("user_id") int user_id);

    @GET("get-by-user-id-desc/{user_id}")
    Call<List<Order>> getOrderByIdDESC(@Path("user_id") int user_id);

    @POST("insert")
    Call<Order> addOrder(@Body Order order);
}
