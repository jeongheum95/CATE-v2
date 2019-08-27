package app.com.CATE;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import app.com.youtubeapiv3.R;

public class AccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Intent intent = getIntent();






    }

    public void likeVideoListPressed(View view) {
        TextView video_list_like_text = (TextView)findViewById(R.id.videoListLike);
        Intent intent=new Intent(AccountActivity.this, AccountVideoListActivity.class);
        intent.putExtra("text",String.valueOf(video_list_like_text.getText()));
        startActivity(intent);
    }

    public void disLikeVideoListPressed(View view) {
        TextView video_list_dislike_text = (TextView)findViewById(R.id.videoListDislike);
        Intent intent=new Intent(AccountActivity.this, AccountVideoListActivity.class);
        intent.putExtra("text",String.valueOf(video_list_dislike_text.getText()));
        startActivity(intent);
    }

    public void commentListPressed(View view) {
        TextView comment_list_text = (TextView)findViewById(R.id.commentList);
        Intent intent=new Intent(AccountActivity.this, AccountCommentListActivity.class);
        intent.putExtra("text",String.valueOf(comment_list_text.getText()));
        startActivity(intent);
    }
}