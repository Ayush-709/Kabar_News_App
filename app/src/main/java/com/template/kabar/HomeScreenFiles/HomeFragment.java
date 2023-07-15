package com.template.kabar.HomeScreenFiles;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.template.kabar.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        List<String> list = new ArrayList<>();
        list.add("12a");
        list.add("12a");
        list.add("12a");
        list.add("12a");
        list.add("12a");
        list.add("12a");
        RecyclerView rv = view.findViewById(R.id.homeFragmentRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(view.getContext(),list);
        rv.setAdapter(adapter);


        return view;
    }
}


class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.HomeFragmentViewHolder>{
    Context context;
    List<String > list;

    public HomeFragmentAdapter(Context context, List<String> list) {
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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeFragmentViewHolder extends RecyclerView.ViewHolder {

        public HomeFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

class HomeFragmentDataItems{
    String author;
    String title;
    String description;
    String urlToImage;
    String publishedAt;
    String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}