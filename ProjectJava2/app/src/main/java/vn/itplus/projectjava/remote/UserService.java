package vn.itplus.projectjava.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.itplus.projectjava.model.User;

public interface UserService {

    @GET("get-all")
    Call<List<User>> getUser();

    @GET("get-by-id/{id}")
    Call<User> getUserById(@Path("id") int id);

    @GET("get-by-name/{name}")
    Call<User> getUserByName(@Path("name") String name);

    @GET("loginAuth/{username}&{password}")
    Call<User> checkLogin(@Path("username") String username, @Path("password") String password);

    @POST("insert")
    Call<User> addUser(@Body User user);

    @PUT("update/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);

    @PUT("changePassword/{password}&{id}")
    Call<User> changePassword(@Path("password") String password, @Path("id") int id);
}
