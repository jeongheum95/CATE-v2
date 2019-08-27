package app.com.CATE;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import app.com.youtubeapiv3.R;

public class AccountVideoListActivity extends AppCompatActivity {

    //선언부


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_comment_list);

        Intent intent = new Intent(this.getIntent());
        String s = intent.getStringExtra("text");
        TextView textView = (TextView) findViewById(R.id.account_sub_list_title);
        textView.setText(s);

    }

}