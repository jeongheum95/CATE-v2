package app.com.CATE.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import app.com.CATE.models.CategoryModel;
import app.com.CATE.MainActivity;
import app.com.youtubeapiv3.R;
import app.com.CATE.adapters.CategoryAdapter;
import app.com.CATE.adapters.HorizontalCategoryAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    //// private CategoryAdapter adapter = null;
//// private ListView listView;
//// private List<CategoryModel> categoryList;
//// private MainActivity mainActivity;
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
//
//
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