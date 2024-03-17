package com.booki.ai;

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

        //Temporary hardcoded data
        infinite_snippets_arraylist.add(new InfiniteSnippetsModel("The Great Gatsby", getString(R.string.teststrings), R.drawable.testimg4));
        infinite_snippets_arraylist.add(new InfiniteSnippetsModel("Harry Potter and the Philosopher's Stone", getString(R.string.teststring2), R.drawable.testimg3));
        infinite_snippets_arraylist.add(new InfiniteSnippetsModel("Lmao uwu qt", getString(R.string.teststring3), R.drawable.testimg2));

        //Setting up recyclerview
        recycle_main = view.findViewById(R.id.infinite_snippets_recycler);
        adapter = new InfiniteSnippetsAdapter(getContext(), infinite_snippets_arraylist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recycle_main.setLayoutManager(manager);
        recycle_main.setAdapter(adapter);

        //Snaphelper snaps the recyclerview in place
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recycle_main);
    }
}