package app.com.CATE;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import app.com.CATE.requests.CommentRequest;
import app.com.youtubeapiv3.R;

public class AccountCommentListActivity extends AppCompatActivity {

    //선언부
   int size;
   ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_comment_list);

        Intent intent = new Intent(this.getIntent());
        String s=intent.getStringExtra("text");
        TextView textView=(TextView)findViewById(R.id.account_sub_list_title);
        textView.setText(s);

        //if s equals 내가 쓴 댓글
        listview = (ListView) findViewById(R.id.mList_account);


        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    if (response.startsWith("ï»¿")) {
                        response = response.substring(3);
                    }
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

                        CommentModel commentModel = new CommentModel(author,_index, desc,writetime,commentLike,commentDisLike,status);
                        cListData.add(commentModel);
                    }
                    if(cListData.isEmpty()) size = 0;
                    else size = cListData.size();

                    CommentAdapter adapter = new CommentAdapter(cListData);
                    listview.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        final CommentRequest commentRequest = new CommentRequest(37, "a" ,responseListener);
        RequestQueue queue = Volley.newRequestQueue(AccountCommentListActivity.this);
        queue.add(commentRequest);

    }


}