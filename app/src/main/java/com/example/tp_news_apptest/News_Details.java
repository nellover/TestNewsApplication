package com.example.tp_news_apptest;


import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;



import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;

public class News_Details extends AppCompatActivity {

    private String newsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);


        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        ImageView imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String imageUrl = intent.getStringExtra("imageUrl");
        String linkUrl = intent.getStringExtra("linkUrl");

        if (title != null) {
            titleTextView.setText(title);
        } else {
            titleTextView.setText(R.string.default_title);
        }

        if (description != null) {
            descriptionTextView.setText(description);
        } else {
            descriptionTextView.setText(R.string.default_description);
        }

        if (imageUrl != null) {
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.placeholder_image);
        }


    }



    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, newsUrl);
            startActivity(Intent.createChooser(i, "Share via"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
