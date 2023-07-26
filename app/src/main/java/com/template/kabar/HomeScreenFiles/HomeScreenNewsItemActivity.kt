package com.template.kabar.HomeScreenFiles

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.template.kabar.R

class HomeScreenNewsItemActivity : AppCompatActivity(){

    private lateinit var imageIV:ImageView
    private lateinit var authorTV:TextView
    private lateinit var nameTV:TextView
    private lateinit var titleTV:TextView
    private lateinit var contentTv:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen_news_item)
        imageIV = findViewById(R.id.newsDetailTVImage)
        authorTV = findViewById(R.id.newsDetailTVAuthor)
        nameTV = findViewById(R.id.newsDetailTVName)
        titleTV = findViewById(R.id.newsDetailTVTitle)
        contentTv = findViewById(R.id.newsDetailTVContent)

        val backButton:ImageView = findViewById(R.id.homeScreenNewsDetailBackButton)
        backButton.setOnClickListener{finish()}

        val sharedPreferences: SharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)

        Log.println(Log.DEBUG, "checking sharedPrefs", sharedPreferences.getString("name","not entry found").toString())
        val name = sharedPreferences.getString("name","")
        val author = sharedPreferences.getString("author","")
        val title = sharedPreferences.getString("title","")
        val content = sharedPreferences.getString("content","")
        val image = sharedPreferences.getString("imageUrl","")

        //setting image
        Glide.with(applicationContext)
                .load(image)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_icon)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageIV)


        nameTV.text = name
        authorTV.text = author
        contentTv.text = content
        titleTV.text = title



    }
}