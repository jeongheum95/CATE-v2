package app.com.CATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.CATE.adapters.CommentAdapter;
import app.com.CATE.models.CommentModel;
import app.com.CATE.models.YoutubeDataModel;
import app.com.CATE.requests.CommentInsertRequest;
import app.com.CATE.requests.CommentRequest;
import app.com.youtubeapiv3.R;

public class TwitchActivity extends AppCompatActivity {
    private YoutubeDataModel youtubeDataModel = null;
    WebView web;
    TextView title;
    ListView listview;
    int size, video_index;
    String userID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitch);
        youtubeDataModel = getIntent().getParcelableExtra(YoutubeDataModel.class.toString());
        String videoId = youtubeDataModel.getVideo_id();
//        String twitch_URL = "https://www.twitch.tv/videos/442053839";                                           //트위치 URL 예
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        video_index = intent.getIntExtra("video_index", 0);

        web = (WebView) findViewById(R.id.twitchPlayer);
        title = (TextView) findViewById(R.id.twichTitle);
        listview = (ListView) findViewById(R.id.commentList);

        web.getSettings().setJavaScriptEnabled(true);
        web.setWebChromeClient(new FullscreenableChromeClient(TwitchActivity.this));                //전체화면
        web.setWebViewClient(new WebClient());                                                       //다른 웹브라우저로 여는 것을 방지

//        web.getSettings().setPluginState(WebSettings.PluginState.ON);             //플러그인 설정
//        web.getSettings().setSupportMultipleWindows(true);                        //웹뷰에서 여러 개의 윈도우를 사용가능하게함
//        web.setLayerType(View.LAYER_TYPE_HARDWARE, null);                         //뷰 가속 - 영상 재생 가능하게 설정
//        if (android.os.Build.VERSION.SDK_INT >= 11) {                             //GPU를 가속
//            getWindow().addFlags(16777216);
//        }


        final String html1 = "<iframe frameborder=\"0\"\n" +
                "scrolling=\"no\"\n" +
                "id=\"chat_embed\"\n" +
                "allowfullscreen=\"true\"\n" +
                "src=\"https://player.twitch.tv/?autoplay=false&video=v";
        final String html2 = "\"\n" +
                "height=\"200\"\n" +
                "width=\"400\">\n" +
                "</iframe>";

//        String[] split = twitch_URL.split("/");
        web.loadData(html1 + videoId + html2, "text/html", null);

        title.setText(youtubeDataModel.getTitle());

        //댓글 기능
        CommentAdapter adapter = new CommentAdapter();
//        listview.setAdapter(adapter);

        final EditText descText = (EditText) findViewById(R.id.descText);
        Button insertButton = (Button) findViewById(R.id.insertButton);

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ArrayList<CommentModel> cListData = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject commentObject = jsonArray.getJSONObject(i);
                        String author = commentObject.getString("author");
                        String desc = commentObject.getString("desc");
                        String _index = commentObject.getString("_index");
                        String writetime = commentObject.getString("writetime");
                        String commentLike = commentObject.getString("commentLike");
                        String commentDisLike = commentObject.getString("commentDisLike");
                        String status = commentObject.getString("status");

                        CommentModel commentModel = new CommentModel(author,_index, desc,writetime,commentLike,commentDisLike,status);
                        cListData.add(commentModel);
                    }
                    if(cListData.isEmpty()) size = 0;
                    else size = cListData.size();

//                    CommentAdapter adapter = new CommentAdapter(cListData);
//                    listview.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        final CommentRequest commentRequest = new CommentRequest(video_index,userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(TwitchActivity.this);
        queue.add(commentRequest);

        insertButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String author = "test";
                String desc = descText.getText().toString();

                final Response.Listener<String> responseListener1 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<CommentModel> cListData = new ArrayList<>();
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i<jsonArray.length(); i++) {
                                JSONObject commentObject = jsonArray.getJSONObject(i);
                                String author = commentObject.getString("author");
                                String _index = commentObject.getString("_index");
                                String desc = commentObject.getString("desc");
                                String writetime = commentObject.getString("writetime");
                                String commentLike = commentObject.getString("commentLike");
                                String commentDisLike = commentObject.getString("commentDisLike");
                                String status = commentObject.getString("status");

                                CommentModel commentModel = new CommentModel(author, _index,desc,writetime,commentLike,commentDisLike,status);
                                cListData.add(commentModel);
                            }
                            if(cListData.isEmpty()) size = 0;
                            else size = cListData.size();

//                            CommentAdapter adapter = new CommentAdapter(cListData);
//                            listview.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                CommentInsertRequest commentInsertRequest = new CommentInsertRequest(video_index, size+1, userID, desc,userID, responseListener1);
                RequestQueue queue = Volley.newRequestQueue(TwitchActivity.this);
                queue.add(commentInsertRequest);

                descText.setText(null);
            }
        });

    }
}

class WebClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }
}

