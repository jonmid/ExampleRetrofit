package com.example.exampleretrofit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.exampleretrofit.R;

public class DetailActivity extends AppCompatActivity {

    private String title;
    private Integer voteCount;
    private String overView;

    TextView textViewTitle, textViewVoteCount, textViewOverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        voteCount = bundle.getInt("voteCount");
        overView = bundle.getString("overView");

        textViewTitle = (TextView) findViewById(R.id.detail_title);
        textViewVoteCount = (TextView) findViewById(R.id.detail_votecount);
        textViewOverView = (TextView) findViewById(R.id.detail_overview);

        setTextViewData();
    }

    public void setTextViewData(){
        textViewTitle.setText(title);
        textViewVoteCount.setText(String.valueOf(voteCount));
        textViewOverView.setText(overView);
    }

    public void onClickButtonHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
