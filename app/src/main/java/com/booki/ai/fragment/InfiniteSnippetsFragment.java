package com.booki.ai.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.appgozar.fadeoutparticle.FadeOutParticleFrameLayout;
import com.booki.ai.adapter.InfiniteSnippetsAdapter;
import com.booki.ai.model.InfiniteSnippetsModel;
import com.booki.ai.R;
import com.factor.bouncy.BouncyRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Random;

public class InfiniteSnippetsFragment extends Fragment {
    boolean is_fetching_data = false;
    boolean newItemAdded = false;
    BouncyRecyclerView recycle_main;
    InfiniteSnippetsAdapter adapter;
    ArrayList<InfiniteSnippetsModel> infinite_snippets_arraylist = new ArrayList<>();
    ArrayList<String> feed_already_loaded = new ArrayList<>();
    LottieAnimationView loader;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_infinite_snippets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Setting up recyclerview
        recycle_main = view.findViewById(R.id.infinite_snippets_recycler);
        adapter = new InfiniteSnippetsAdapter(getContext(), infinite_snippets_arraylist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recycle_main.setLayoutManager(manager);
        recycle_main.setAdapter(adapter);

        loader = view.findViewById(R.id.infinite_snippets_loader);
        loader.setVisibility(View.GONE);

        recycle_main.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        fetchdata();

        recycle_main.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                manager.getItemCount();
                if(manager.findLastVisibleItemPosition() == manager.getItemCount() - 1){
                    if(!is_fetching_data){
                        fetchdata();
                    }
                }
            }
        });

    }

    private void fetchdata() {

        newItemAdded=false;
        System.out.println("FETCHING NEW SNIPPETS!!");
        is_fetching_data = true;
        loader.setVisibility(View.VISIBLE);

        //Define Database and Query
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("Snippets");
        Query snippets_query = dbReference.orderByChild("priority").limitToFirst(10);

        //Fetching data from database
        snippets_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    FirebaseDatabase.getInstance().getReference("Snippets").child(snap.getKey()).child("priority").setValue((int)(Math.random()*10000)+1);
                    if(!feed_already_loaded.contains(snap.getKey())){
                        ArrayList<String> tags_arraylist = new ArrayList<>();
                        for(DataSnapshot tags:snap.child("tags").getChildren()){
                            tags_arraylist.add(tags.getKey());
                        }
                        String key = snap.getKey();
                        String body = snap.child("body").getValue(String.class);
                        String bookid = snap.child("book_id").getValue(String.class);
                        String bookname = snap.child("book_name").getValue(String.class);
                        String author = snap.child("author").getValue(String.class);
                        StorageReference imagesrc = FirebaseStorage.getInstance().getReference("Marketplace").child("Books").child(bookid).child("book_cover");

                        System.out.println(imagesrc);
                        InfiniteSnippetsModel snippet = new InfiniteSnippetsModel(key, body, imagesrc, tags_arraylist, bookid, author, bookname);
                        infinite_snippets_arraylist.add(snippet);
                        adapter.notifyItemInserted(infinite_snippets_arraylist.size() - 1);
                        feed_already_loaded.add(snap.getKey());
                        newItemAdded=true;
                    }
                }

                is_fetching_data = false;

                if(newItemAdded)
                {
                    loader.setVisibility(View.GONE);
                }
                else
                {
                    fetchdata();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}