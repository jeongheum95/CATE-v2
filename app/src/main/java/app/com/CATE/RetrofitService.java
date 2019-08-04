package app.com.CATE;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitService {
    static final String URL = "http://ghkdua1829.dothome.co.kr/fow/";

    @FormUrlEncoded
    @POST("fow_loginReq.php")
    Call<JsonObject> Login_cate(
            @Field("userID") String userID,
            @Field("userPassword") String userPassword
    );

    @FormUrlEncoded
    @POST("fow_tag.php")
    Call<JsonObject> all_category(
            @Field("Id") String Id
            );
}


