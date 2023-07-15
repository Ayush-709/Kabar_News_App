package com.template.kabar.HomeScreenFiles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        String lang = "en";

        Call<NewsItemsModel> call = myApi.getAllTopHeadlines(query, fromDate, sortBy, pageSize, apiKey, lang);

// Now you can enqueue the API call to get the response asynchronously
         call.enqueue(new Callback<NewsItemsModel>() {
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
        String titleText = list.get(position).getTitle();
        String name = Objects.requireNonNull(list.get(position).getSource().get("name")).toString();
        String imageUrl = list.get(position).getUrlToImage();
        Date publishDateText = list.get(position).getPublishedAt();

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
                .override(120, 120)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class HomeFragmentViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView  title, publishDate,name;

        public HomeFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.newsItemCardImage);
            title = itemView.findViewById(R.id.newsItemCardTitle);
            publishDate = itemView.findViewById(R.id.newsItemCardPublishedDateTime);
            name = itemView.findViewById(R.id.newsItemCardName);
        }
    }
}