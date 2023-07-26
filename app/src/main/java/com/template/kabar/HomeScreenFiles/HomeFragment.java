package com.template.kabar.HomeScreenFiles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.template.kabar.R;
import com.template.kabar.SupportFiles.Api;
import com.template.kabar.SupportFiles.Articles;
import com.template.kabar.SupportFiles.DataLoadListener;
import com.template.kabar.SupportFiles.NewsItemsModel;
import com.template.kabar.SupportFiles.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements DataLoadListener {

    NewsItemsModel data;
    List<Articles> list;
    RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        list = new ArrayList<>();

        rv = view.findViewById(R.id.homeFragmentRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        sendGetRequest();

        return view;
    }

     private void sendGetRequest() {
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        Api myApi = retrofitClient.getMyApi();

        String query = "all";
        String fromDate = "2023-07-01";
        String sortBy = "popularity";
        int pageSize = 100;
        String apiKey = Api.API_KEY;

        Call<NewsItemsModel> call = myApi.getAllTopHeadlines(query, fromDate, sortBy, pageSize, apiKey);
        Log.d("checking get request", call.request().toString());

// Now you can enqueue the API call to get the response asynchronously
         call.enqueue(new Callback<>() {
             @Override
             public void onResponse(@NonNull Call<NewsItemsModel> call, @NonNull Response<NewsItemsModel> response) {
                 // Handle the response here
                 if (response.isSuccessful()) {
                     data = response.body();
                     onDataLoaded(data); // Notify the listener with the data

                 } else {
                     onDataLoaded(null); // Notify the listener with null when response is unsuccessful
                 }
             }

             @Override
             public void onFailure(@NonNull Call<NewsItemsModel> call, @NonNull Throwable t) {
                 onDataLoaded(null); // Notify the listener with null in case of failure
                 Log.d("checkingGETRequest", t.getLocalizedMessage());
             }
         });
    }

    @Override
    public void onDataLoaded(NewsItemsModel data) {
        if (data != null) {
            Log.d("checkingGETRequest", String.valueOf(data.getArticles().get(0).getSource().get("name")));
            // Process the data as needed
            list.addAll(data.getArticles());

            // Update the RecyclerView with the data

            HomeFragmentAdapter adapter= new HomeFragmentAdapter(getContext(),list);
            rv.setAdapter(adapter);
        } else {
            // Handle unsuccessful response
            Log.d("checkingGETRequest", "failed");
        }
    }
}


@SuppressLint("SimpleDateFormat")
class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.HomeFragmentViewHolder>{
    Context context;
    List<Articles > list;

    public HomeFragmentAdapter(Context context, List<Articles> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_fragment_item_layout,parent,false);
        return new HomeFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFragmentViewHolder holder, int position) {
        String titleText = list.get(holder.getAdapterPosition()).getTitle();
        String name = Objects.requireNonNull(list.get(holder.getAdapterPosition()).getSource().get("name")).toString();
        String imageUrl = list.get(holder.getAdapterPosition()).getUrlToImage();
        Date publishDateText = list.get(holder.getAdapterPosition()).getPublishedAt();

        //setting title and name
        holder.title.setText(titleText);
        holder.name.setText(name);

        //setting date and time
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm\t\tdd-MM-yyyy");
        String dateAndTime =  outputFormat.format(publishDateText);
        holder.publishDate.setText(dateAndTime);

        //setting image
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_icon)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);


        holder.cardView.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("title", titleText);
            editor.putString("name", name);
            editor.putString("imageUrl", imageUrl);
            editor.putString("date", dateAndTime);
            editor.putString("content", list.get(position).getContent());
            editor.putString("author", list.get(position).getAuthor());
            editor.apply();

            Intent intent = new Intent(context, HomeScreenNewsItemActivity.class);
            context.startActivity(intent);
        });

        int bookmarkDrawableId = (int) holder.bookmark.getTag();
        holder.bookmark.setImageResource(bookmarkDrawableId);

//         Set the click listener on the bookmark ImageView
        holder.bookmark.setOnClickListener(view -> {
            // Get the current bookmark drawable resource ID from the ImageView's tag
            int currentBookmarkDrawableId = (int) holder.bookmark.getTag();

            // Toggle the bookmark image based on the current resource ID
            int newBookmarkDrawableId;
            if (currentBookmarkDrawableId == R.drawable.bookmark_outline) {
                newBookmarkDrawableId = R.drawable.bookmark_filled;
            } else {
                newBookmarkDrawableId = R.drawable.bookmark_outline;
            }

            // Update the bookmark image and set the new drawable resource ID as the tag
            holder.bookmark.setImageResource(newBookmarkDrawableId);
            holder.bookmark.setTag(newBookmarkDrawableId);

            // ... Handle bookmark click actions here if needed ...
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class HomeFragmentViewHolder extends RecyclerView.ViewHolder {
        ImageView image, bookmark;
        TextView  title, publishDate,name;
        ConstraintLayout cardView;

        public HomeFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.newsItemCardImage);
            title = itemView.findViewById(R.id.newsItemCardTitle);
            publishDate = itemView.findViewById(R.id.newsItemCardPublishedDateTime);
            name = itemView.findViewById(R.id.newsItemCardName);
            cardView = itemView.findViewById(R.id.newItemCardView);
            bookmark = itemView.findViewById(R.id.bookmark_image);

            bookmark.setImageResource(R.drawable.bookmark_outline);
            bookmark.setTag(R.drawable.bookmark_outline);
        }
    }
}