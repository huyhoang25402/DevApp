package vn.itplus.projectjava.remote;

import java.util.List;

import vn.itplus.projectjava.model.Cart;

public class APIUtils {

    public static final String API_URL = "http://172.20.10.5:8080/";
    public static List<Cart> listCart;

    public static CategoryService getCategoryService() {
        return RetrofitClient2.getClient(API_URL+"category/").create(CategoryService.class);
    }

    public static ProductService getProductService() {
        return RetrofitClient.getClient(API_URL+"product/").create(ProductService.class);
    }

    public static UserService getUserService() {
        return RetrofitClient3.getClient(API_URL+"user/").create(UserService.class);
    }

    public static OrderService getOrderService(){
        return RetrofitClient4.getClient(API_URL+"order/").create(OrderService.class);
    }

    public static OrderDetailService getOrderDetailService(){
        return RetrofitClient5.getClient(API_URL+"orderDetail/").create(OrderDetailService.class);
    }

}
