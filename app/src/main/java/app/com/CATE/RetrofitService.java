package app.com.CATE;

import com.google.gson.JsonArray;
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

    @FormUrlEncoded
    @POST("fow_allvideo.php")
    Call<JsonObject> All_video(
            @Field("Id") String Id
    );
    @FormUrlEncoded
    @POST("fow_post.php")
    Call<JsonObject> Signup(
            @Field("Id") String Id,
            @Field("Pw") String Pw,
            @Field("Name") String Name
    );
    @FormUrlEncoded
    @POST("fow_MakeLikeTable.php")
    Call<JsonObject> MakeLikeTable(
            @Field("userName") String userName,
            @Field("video_id") int video_id
    );
    @FormUrlEncoded
    @POST("fow_updatelikes.php")
    Call<JsonObject> updatelikes(
            @Field("username") String username,
            @Field("videoid") String videoid,
            @Field("target") String target
    );
    @FormUrlEncoded
    @POST("fow_updatecommentlikes.php")
    Call<JsonObject> updatecommentlikes(
            @Field("username") String username,
            @Field("videoid") String videoid,
            @Field("_index") String _index,
            @Field("target") String target
    );
}


