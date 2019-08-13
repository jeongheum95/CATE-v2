package app.com.CATE.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import app.com.CATE.DetailsActivity;
import app.com.CATE.RetrofitService;
import app.com.CATE.TwitchActivity;
import app.com.CATE.adapters.VideoPostAdapter;
import app.com.CATE.interfaces.OnItemClickListener;
import app.com.CATE.models.CategoryModel;
import app.com.CATE.MainActivity;
import app.com.CATE.models.YoutubeDataModel;
import app.com.youtubeapiv3.R;
import app.com.CATE.adapters.CategoryAdapter;
import app.com.CATE.adapters.HorizontalCategoryAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    SearchView searchview;
    RecyclerView recyclerView;
    private VideoPostAdapter adapter = null;
    ArrayList<YoutubeDataModel> listData = new ArrayList<>();
    private ProgressBar progressBar;
    //// private CategoryAdapter adapter = null;
//// private ListView listView;
//// private List<CategoryModel> categoryList;
    private MainActivity mainActivity;
    //
//
////
// public void onActivityCreated(@Nullable Bundle savedInstanceState) {
// super.onActivityCreated(savedInstanceState);
// LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true);
// }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchview=(SearchView)view.findViewById(R.id.searching);
        recyclerView=(RecyclerView)view.findViewById(R.id.searchlist);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        searchview.setSubmitButtonEnabled(true);
        mainActivity = (MainActivity)getActivity();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount();
                Log.e("sdsd","ssss : "+lastVisibleItemPosition);
                if (lastVisibleItemPosition == itemTotalCount-1) {
                    progressBar.setVisibility(View.VISIBLE);
                    //리스트 마지막(바닥) 도착!!!!! 다음 페이지 데이터 로드!!
                    All_video(lastVisibleItemPosition+1);
                }
            }
        });
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String target) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });

        All_video();



// // Adapter 생성 및 Adapter 지정.
// adapter = new CategoryAdapter();
// mainActivity = (MainActivity)getActivity();
// // Toast.makeText(mainActivity, mainActivity.channel, Toast.LENGTH_SHORT).show();
// categoryList=new ArrayList<CategoryModel>();
// adapter.addItem("1",
// "음악" , "인기트랙 - 한국", "PLFgquLnL59alGJcdc0BEZJb2p7IgkL0Oe") ;
// adapter.addItem("2",
// "음악" , "인기트랙 - 리히텐슈타인", "PLFgquLnL59al_vjBToIrYqC2l-CiO78U6") ;
// setListAdapter(adapter);
//
//// try{
//// //intent로 값을 가져옵니다 이때 JSONObject타입으로 가져옵니다
//// JSONObject jsonObject = new JSONObject(mainActivity.channel);
////
////
//// //List.php 웹페이지에서 response라는 변수명으로 JSON 배열을 만들었음..
//// JSONArray jsonArray = jsonObject.getJSONArray("response");
//// int count = 0;
////
//// String cateId, cateName, cateDetail, cateKey;
////
//// //JSON 배열 길이만큼 반복문을 실행
//// while(count < jsonArray.length()){
//// //count는 배열의 인덱스를 의미
//// JSONObject object = jsonArray.getJSONObject(count);
////
//// cateId = object.getString("id");//여기서 ID가 대문자임을 유의
//// cateName = object.getString("title");
//// cateDetail = object.getString("url");
//// cateKey = object.getString("tag");
////
//// //값들을 User클래스에 묶어줍니다
//// CategoryModel CategoryModel = new CategoryModel(cateId, cateName, cateDetail, cateKey);
//// categoryList.add(CategoryModel);//리스트뷰에 값을 추가해줍니다
//// adapter.addItem(cateId,cateName,cateDetail,cateKey);
//// count++;
//// }
//// setListAdapter(adapter) ;
////
////
//// }catch(Exception e){
//// e.printStackTrace();
//// }
//
//
        return view;
    }

    private void initList(ArrayList<YoutubeDataModel> mListData) {
        adapter = new VideoPostAdapter(getActivity(), mListData, new OnItemClickListener() {
            @Override
            public void onItemClick(YoutubeDataModel item) {
                YoutubeDataModel youtubeDataModel = item;
                if (youtubeDataModel.getVideo_kind().equals("YOUTUBE")) { //유튜브 플레이어
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra(YoutubeDataModel.class.toString(), youtubeDataModel);
                    intent.putExtra("userID", MainActivity.strId);
                    intent.putExtra("video_index", youtubeDataModel.getVideo_index());
                    startActivity(intent);
                }
                if (youtubeDataModel.getVideo_kind().equals("TWITCH")) {
                    Intent intent = new Intent(getActivity(), TwitchActivity.class); //트위치 플레이어
                    intent.putExtra(YoutubeDataModel.class.toString(), youtubeDataModel);
                    intent.putExtra("userID", MainActivity.strId);
                    intent.putExtra("video_index", youtubeDataModel.getVideo_index());
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public void All_video(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        Call<JsonObject> call=retrofitService.All_video(MainActivity.strId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject=response.body();
                try {

                    JsonArray jsonArray = jsonObject.get("response").getAsJsonArray();


                    int count = 0;

                    while (count < 3) {
                        JsonObject object = jsonArray.get(count).getAsJsonObject();

                        YoutubeDataModel youtubeObject = new YoutubeDataModel();
                        String thumbnail = "";
                        String video_id = "";
                        String cateName, video_kind, cateDetail;
                        int video_index;

                        cateName = object.get("title").getAsString();
                        video_kind = object.get("kind").getAsString();
                        cateDetail = object.get("url").getAsString();
                        video_index = Integer.parseInt(object.get("id").getAsString());

                        if (video_kind.equals("YOUTUBE")) {
                            video_id = cateDetail.substring(cateDetail.indexOf("=") + 1);
                            thumbnail = "https://i.ytimg.com/vi/" + video_id + "/hqdefault.jpg";
                        }
                        if (video_kind.equals("TWITCH")) {
                            String[] split = cateDetail.split("/");
//                            video_id = split[4];
                            thumbnail = "https://static-cdn.jtvnw.net/jtv_user_pictures/twitch-profile_image-8a8c5be2e3b64a9a-300x300.png";
                        }

                        youtubeObject.setVideo_index(video_index);
                        youtubeObject.setTitle(cateName);
                        youtubeObject.setThumbnail(thumbnail);
                        youtubeObject.setVideo_id(video_id);
                        youtubeObject.setVideo_kind(video_kind);

                        count++;
                        listData.add(youtubeObject);
                    }
                    initList(listData);
//                    mainActivity.listData = listData;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    public void All_video(final int start){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(RetrofitService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        Call<JsonObject> call=retrofitService.All_video(MainActivity.strId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject=response.body();
                try {

                    JsonArray jsonArray = jsonObject.get("response").getAsJsonArray();


                    int count = start;

                    while (count < start+3) {
                        JsonObject object = jsonArray.get(count).getAsJsonObject();

                        YoutubeDataModel youtubeObject = new YoutubeDataModel();
                        String thumbnail = "";
                        String video_id = "";
                        String cateName, video_kind, cateDetail;
                        int video_index;

                        cateName = object.get("title").getAsString();
                        video_kind = object.get("kind").getAsString();
                        cateDetail = object.get("url").getAsString();
                        video_index = Integer.parseInt(object.get("id").getAsString());

                        if (video_kind.equals("YOUTUBE")) {
                            video_id = cateDetail.substring(cateDetail.indexOf("=") + 1);
                            thumbnail = "https://i.ytimg.com/vi/" + video_id + "/hqdefault.jpg";
                        }
                        if (video_kind.equals("TWITCH")) {
                            String[] split = cateDetail.split("/");
//                            video_id = split[4];
                            thumbnail = "https://static-cdn.jtvnw.net/jtv_user_pictures/twitch-profile_image-8a8c5be2e3b64a9a-300x300.png";
                        }

                        youtubeObject.setVideo_index(video_index);
                        youtubeObject.setTitle(cateName);
                        youtubeObject.setThumbnail(thumbnail);
                        youtubeObject.setVideo_id(video_id);
                        youtubeObject.setVideo_kind(video_kind);

                        count++;
                        listData.add(youtubeObject);
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    },1000);
//                    initList(listData);
//                    mainActivity.listData = listData;
                } catch(IndexOutOfBoundsException ea){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "더이상 동영상이 없습니다.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    },1000);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
//
// //아이템 클릭 이벤트
// @Override
// public void onListItemClick (ListView l, View v, int position, long id) {
// // get TextView's Text.
// CategoryModel item = (CategoryModel) l.getItemAtPosition(position) ;
// mainActivity.PLAYLIST_ID = item.getKey();
// mainActivity.PLAYLIST_GET_URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + mainActivity.PLAYLIST_ID + "&maxResults=20&key=" + mainActivity.GOOGLE_YOUTUBE_API_KEY + "";
// String titleStr = item.getName() ;
// String descStr = item.getDetail() ;
// String iconDrawable = item.getId() ;
// String channelStr = item.getKey() ;
// // TODO : use item data.
// }
//
// public void addItem(String icon, String title, String desc, String channel) {
// adapter.addItem(icon, title, desc, channel) ;
// }
//
//
// private View.OnClickListener onClickItem = new View.OnClickListener() {
// @Override
// public void onClick(View v) {
// }
// };

}