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

import com.booki.ai.R;
import com.booki.ai.activity.OnboardingActivity;
import com.booki.ai.adapter.InfiniteSnippetsAdapter;
import com.booki.ai.model.InfiniteSnippetsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

import java.util.ArrayList;

public class Onboarding_SnippetFragment extends Fragment {
    CardStackView cardStackView;
    InfiniteSnippetsAdapter adapter;
    ArrayList<InfiniteSnippetsModel> arrayList = new ArrayList<>();
    CardStackListener listener;
    int top_snippet_position = 0;
    static ArrayList<String> snippet_tags = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding__snippet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener = new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
            }

            @Override
            public void onCardSwiped(Direction direction) {
                OnboardingActivity.Enable_next_btn(true);
                if(direction.toString().equals("Right")) {
                    snippet_tags.addAll(arrayList.get(top_snippet_position).getTags());
                    for(String i:snippet_tags){
                        System.out.println(i);
                    }
                }
            }

            @Override
            public void onCardRewound() {

            }

            @Override
            public void onCardCanceled() {

            }

            @Override
            public void onCardAppeared(View view, int position) {
                top_snippet_position = position;
            }

            @Override
            public void onCardDisappeared(View view, int position) {

            }
        };

        cardStackView = view.findViewById(R.id.fragment_onboardingsnippet_cardstackview);
        adapter = new InfiniteSnippetsAdapter(getContext(), arrayList);
        cardStackView.setLayoutManager(new CardStackLayoutManager(getContext(), listener));
        cardStackView.setAdapter(adapter);

        fetchdata();
    }


    private void fetchdata() {
        //Define Database and Query
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("snippets");
        Query snippets_query = dbReference.limitToFirst(5);

        //Fetching data from database
        snippets_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    ArrayList<String> tags_arraylist = new ArrayList<>();
                    for(DataSnapshot tags:snap.child("ztags").getChildren()){
                        tags_arraylist.add(tags.getKey());
                    }
                    String key = snap.getKey();
                    String heading = snap.child("heading").getValue(String.class);
                    String body = snap.child("body").getValue(String.class);
                    StorageReference imagesrc = FirebaseStorage.getInstance().getReference("snippets").child("images").child(key+".png");
                    InfiniteSnippetsModel snippet = new InfiniteSnippetsModel(key, heading, body, imagesrc, tags_arraylist);
                    arrayList.add(snippet);
                    adapter.notifyItemInserted(arrayList.size() - 1);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static ArrayList<String> getSnippet_tags() {
        return snippet_tags;
    }
}