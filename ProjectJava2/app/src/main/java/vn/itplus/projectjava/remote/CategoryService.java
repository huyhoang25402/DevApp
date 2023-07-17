package vn.itplus.projectjava.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.itplus.projectjava.model.Category;

public interface CategoryService {

    @GET("get-all")
    Call<List<Category>> getCategory();
}
