package com.booki.ai.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.booki.ai.R;
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
    ArrayList<DataSnapshot> arraylist_of_snap = new ArrayList<>();
    ImageView snippet_image;
    EditText snippet_text;
    TextView authorname, bookname;
    CardView verified_btn, notverified_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippet_verification_admin_onlyactivity);

        snippet_image = findViewById(R.id.snippet_verification_imageview);
        snippet_text = findViewById(R.id.snippet_verification_body);
        authorname = findViewById(R.id.snippet_verification_authorname);
        bookname = findViewById(R.id.snippet_verification_bookname);
        verified_btn = findViewById(R.id.snippet_verification_verified);
        notverified_btn = findViewById(R.id.snippet_verification_notverified);


        verified_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Snippets");
                ref.child(arraylist_of_snap.get(0).getKey()).setValue(arraylist_of_snap.get(0).getValue());
                ref.child(arraylist_of_snap.get(0).getKey()).child("priority").setValue(Math.random());

                ref.child(arraylist_of_snap.get(0).getKey()).child("body").setValue(snippet_text.getText().toString());


                DatabaseReference ref_to_delete = FirebaseDatabase.getInstance().getReference("Snippets_for_verification").child(arraylist_of_snap.get(0).getKey());
                ref_to_delete.removeValue();

                arraylist_of_snap.remove(0);
                if(arraylist_of_snap.size() == 0){
                    snippet_text.setText("LOADING DATA");
                    fetchdata();
                }
                else{
                    snippet_text.setText(arraylist_of_snap.get(0).child("body").getValue(String.class));
                    authorname.setText("by " + arraylist_of_snap.get(0).child("author").getValue(String.class));
                    bookname.setText(arraylist_of_snap.get(0).child("book_name").getValue(String.class));                }
            }
        });

        notverified_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref_to_delete = FirebaseDatabase.getInstance().getReference("Snippets_for_verification").child(arraylist_of_snap.get(0).getKey());
                ref_to_delete.removeValue();
                arraylist_of_snap.remove(0);
                if(arraylist_of_snap.size() == 0){
                    fetchdata();
                    snippet_text.setText("LOADING DATA");
                }
                else{
                    snippet_text.setText(arraylist_of_snap.get(0).child("body").getValue(String.class));
                    authorname.setText("by " + arraylist_of_snap.get(0).child("author").getValue(String.class));
                    bookname.setText(arraylist_of_snap.get(0).child("book_name").getValue(String.class));                }
            }
        });

//        }
//        else{
//            DatabaseReference ref_to_delete = FirebaseDatabase.getInstance().getReference("Snippets_for_verification").child(top_snap.get(top_snippet_position_saved).getKey());
//            ref_to_delete.removeValue();
//        }

        fetchdata();
    }

    private void fetchdata() {
        verified_btn.setEnabled(false);
        notverified_btn.setEnabled(false);
        System.out.println("FETCHED DATA");
        //Define Database and Query
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("Snippets_for_verification");
        Query snippets_query = dbReference.limitToFirst(5);

        //Fetching data from database
        snippets_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() == 0) {
                    snippet_text.setText("NO VERIFICATIONS TO BE DONE!");
                    verified_btn.setEnabled(false);
                    notverified_btn.setEnabled(false);
                } else {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        ArrayList<String> tags_arraylist = new ArrayList<>();
                        for (DataSnapshot tags : snap.child("tags").getChildren()) {
                            tags_arraylist.add(tags.getKey());
                        }
                        String key = snap.getKey();
                        String body = snap.child("body").getValue(String.class);
                        StorageReference imagesrc = FirebaseStorage.getInstance().getReference("snippets").child("images").child(key + ".png");
                        String bookid = snap.child("book_id").getValue(String.class);
                        String bookname = snap.child("book_name").getValue(String.class);
                        String author = snap.child("author").getValue(String.class);

                        InfiniteSnippetsModel snippet = new InfiniteSnippetsModel(key, body, imagesrc, tags_arraylist, bookid, author, bookname);
                        arraylist_of_snap.add(snap);
                    }
                    snippet_text.setText(arraylist_of_snap.get(0).child("body").getValue(String.class));
                    authorname.setText("by " + arraylist_of_snap.get(0).child("author").getValue(String.class));
                    bookname.setText(arraylist_of_snap.get(0).child("book_name").getValue(String.class));

                    verified_btn.setEnabled(true);
                    notverified_btn.setEnabled(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}