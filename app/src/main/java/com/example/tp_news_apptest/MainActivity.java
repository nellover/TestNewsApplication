package com.example.tp_news_apptest;

import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_news_apptest.ApiRetr.ApiService;
import com.example.tp_news_apptest.ApiRetr.NewsResponse;
import com.example.tp_news_apptest.NewsArticles.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private String selectedCategory ;
    private String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiKey = getString(R.string.api_key);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
                fetchNewsByCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

    private void fetchNewsByCategory() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService newsApi = retrofit.create(ApiService.class);
        Call<NewsResponse> call = newsApi.getNews(selectedCategory.toLowerCase(), apiKey);

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Article> articles = response.body().getArticles();

                    newsAdapter = new NewsAdapter(MainActivity.this, articles);
                    recyclerView.setAdapter(newsAdapter);
                } else {
                    Log.e("MainActivity", "Response error:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable throwable) {
                Log.e("MainActivity", "Error : " + throwable.getMessage());
                Toast.makeText(MainActivity.this, "Error :  " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
