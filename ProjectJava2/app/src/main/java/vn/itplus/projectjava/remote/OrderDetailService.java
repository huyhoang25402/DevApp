package vn.itplus.projectjava.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.itplus.projectjava.model.OrderDetail;

public interface OrderDetailService {
    @GET("get-all")
    Call<List<OrderDetail>> getOrderDetail();

    @GET("get-by-order-id/{order_id}")
    Call<List<OrderDetail>> getByOrderDetail(@Path("order_id") int order_id);

    @POST("insert")
    Call<OrderDetail> addOrderDetail(@Body OrderDetail orderDetail);
}
