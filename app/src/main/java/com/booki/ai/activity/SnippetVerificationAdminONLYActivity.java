package com.booki.ai.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.booki.ai.R;
import com.booki.ai.adapter.InfiniteSnippetsAdapter;
import com.booki.ai.adapter.Snippets_VerificationAdapter;
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

public class SnippetVerificationAdminONLYActivity extends AppCompatActivity {
    CardStackView cardStackView;
    Snippets_VerificationAdapter adapter;
    ArrayList<InfiniteSnippetsModel> arrayList = new ArrayList<>();
    CardStackListener listener;
    int top_snippet_position = 0;
    ArrayList<DataSnapshot> top_snap = new ArrayList<>();

    static String edittext_message_inside_snippet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippet_verification_admin_onlyactivity);

        listener = new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
            }

            @Override
            public void onCardSwiped(Direction direction) {
                OnboardingActivity.Enable_next_btn(true);
                int top_snippet_position_saved = top_snippet_position;
                if(direction.toString().equals("Right")) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("snippets");
                    ref.child(top_snap.get(top_snippet_position_saved).getKey()).setValue(top_snap.get(top_snippet_position_saved).getValue());
                    ref.child(top_snap.get(top_snippet_position_saved).getKey()).child("body").setValue(edittext_message_inside_snippet);
                    DatabaseReference ref_to_delete = FirebaseDatabase.getInstance().getReference("Snippets_for_verification").child(top_snap.get(top_snippet_position_saved).getKey());
                    ref_to_delete.removeValue();
                }
                else{
                    DatabaseReference ref_to_delete = FirebaseDatabase.getInstance().getReference("Snippets_for_verification").child(top_snap.get(top_snippet_position_saved).getKey());
                    ref_to_delete.removeValue();
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
                System.out.println(position);
                top_snippet_position = position;
                edittext_message_inside_snippet = arrayList.get(position).getBody();
            }

            @Override
            public void onCardDisappeared(View view, int position) {

            }
        };

        cardStackView = findViewById(R.id.snippet_verification_cardstackview);
        adapter = new Snippets_VerificationAdapter(this, arrayList);
        cardStackView.setLayoutManager(new CardStackLayoutManager(this, listener));
        cardStackView.setAdapter(adapter);
        cardStackView.setItemViewCacheSize(1);


        fetchdata();
    }

    private void fetchdata() {
        System.out.println("FETCHED DATA");
        //Define Database and Query
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("Snippets_for_verification");
        Query snippets_query = dbReference.limitToFirst(100);

        //Fetching data from database
        snippets_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int iteration_count = 0;
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    iteration_count += 1;
                    if(iteration_count == 1){
                        setEdittext_message_inside_snippet(snap.child("body").getValue(String.class));
                    }
                    System.out.println("FETCHED DATA");
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
                    top_snap.add(snap);
                    adapter.notifyItemInserted(arrayList.size() - 1);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void setEdittext_message_inside_snippet(String new_string) {
        edittext_message_inside_snippet = new_string;
    }
}