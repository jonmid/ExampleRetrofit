package com.example.exampleretrofit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.exampleretrofit.Adapter.MoviesAdapter;
import com.example.exampleretrofit.Model.Movie;
import com.example.exampleretrofit.Model.MovieResponse;
import com.example.exampleretrofit.R;
import com.example.exampleretrofit.Rest.ApiClient;
import com.example.exampleretrofit.Rest.ApiInterface;
import com.example.exampleretrofit.Util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // insert your themoviedb.org API KEY here
    private final static String API_KEY = "f6bc48e1a74b403aa448efe1703d19f9";

    RecyclerView recyclerView;

    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;
        }

        // ***** Configuracion RecyclerView *****
        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Movie movie = movieList.get(position);
                nextActivity(movie.getTitle(), movie.getVoteCount(), movie.getOverview());
                //Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                //
            }
        }));

        getDataApi();
    }

    // ***** Programacion Retrofit para el consumo de una API *****
    public void getDataApi() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse>call, Response<MovieResponse> response) {
                movieList = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movieList, R.layout.list_item_movie, getApplicationContext()));
                Log.d(TAG, "Number of movies received: " + movieList.size());
            }

            @Override
            public void onFailure(Call<MovieResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    public void nextActivity(String title, Integer voteCount, String overView){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("voteCount", voteCount);
        intent.putExtra("overView", overView);
        startActivity(intent);
    }
}
