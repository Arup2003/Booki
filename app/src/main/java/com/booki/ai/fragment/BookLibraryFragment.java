package com.booki.ai.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.booki.ai.R;
import com.booki.ai.activity.MainActivity;
import com.booki.ai.adapter.BookLibraryAdapter;
import com.booki.ai.model.LibraryBookModel;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

public class BookLibraryFragment extends Fragment {

    GridView book_library_grid;
    ArrayList<LibraryBookModel> libraryBooks = new ArrayList<>();
    StorageReference bookCoverRef;
    BookLibraryAdapter bookLibraryAdapter;
    DatabaseReference dbRef;
    String userId;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context=getContext();

        book_library_grid = view.findViewById(R.id.book_library_grid);


        userId = FirebaseAuth.getInstance().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference("Userdata").child(userId).child("books_purchased");
        bookCoverRef = FirebaseStorage.getInstance().getReference("Marketplace").child("Books");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                libraryBooks.clear();
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    String book_key = snap.getKey();
                    libraryBooks.add(new LibraryBookModel(bookCoverRef.child(book_key).child("book_cover"),book_key));
                }

                bookLibraryAdapter = new BookLibraryAdapter(context,libraryBooks);
                book_library_grid.setAdapter(bookLibraryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}