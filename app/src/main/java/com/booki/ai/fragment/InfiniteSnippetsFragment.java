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

import com.booki.ai.adapter.InfiniteSnippetsAdapter;
import com.booki.ai.model.InfiniteSnippetsModel;
import com.booki.ai.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class InfiniteSnippetsFragment extends Fragment {
    RecyclerView recycle_main;
    InfiniteSnippetsAdapter adapter;
    ArrayList<InfiniteSnippetsModel> infinite_snippets_arraylist = new ArrayList<>();


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

        recycle_main.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        fetchdata();

    }

    private void fetchdata() {
        //Define Database and Query
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("Snippets");
        Query snippets_query = dbReference.orderByPriority().limitToFirst(5);


        //Fetching data from database
        snippets_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    dbReference.child(snap.getKey()).setPriority(Math.random());

                    ArrayList<String> tags_arraylist = new ArrayList<>();
                    for(DataSnapshot tags:snap.child("tags").getChildren()){
                        tags_arraylist.add(tags.getKey());
                    }
                    String key = snap.getKey();
                    String body = snap.child("body").getValue(String.class);
                    StorageReference imagesrc = FirebaseStorage.getInstance().getReference("snippets").child("images").child(key+".png");
                    String bookid = snap.child("book_id").getValue(String.class);
                    String bookname = snap.child("book_name").getValue(String.class);
                    String author = snap.child("author").getValue(String.class);


                    System.out.println(imagesrc);
                    InfiniteSnippetsModel snippet = new InfiniteSnippetsModel(key, body, imagesrc, tags_arraylist, bookid, author, bookname);
                    infinite_snippets_arraylist.add(snippet);
                    adapter.notifyItemInserted(infinite_snippets_arraylist.size() - 1);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}