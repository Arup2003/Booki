package com.booki.ai.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.booki.ai.R;
import com.booki.ai.activity.BookReadActivity;
import com.booki.ai.activity.MainActivity;
import com.booki.ai.model.LibraryBookModel;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class BookLibraryAdapter extends ArrayAdapter<LibraryBookModel> {

    Context context;
    DatabaseReference dbRef;
    public BookLibraryAdapter(@NonNull Context context, ArrayList<LibraryBookModel> arrayList) {
        super(context, 0, arrayList);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.individual_library_book, parent, false);
        }

        LibraryBookModel libraryBookModel = getItem(position);
        ImageView individual_library_book_iv = listitemView.findViewById(R.id.individual_library_book_iv);
        CardView individual_library_book_cv = listitemView.findViewById(R.id.individual_library_book_cv);

        individual_library_book_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = FirebaseAuth.getInstance().getUid();
                dbRef = FirebaseDatabase.getInstance().getReference("Userdata").child(userId).child("books_purchased").child(libraryBookModel.getBook_key());
                dbRef.child("last_read").setValue(ServerValue.TIMESTAMP);


                File book_epub_dir = new File(context.getExternalFilesDir(null),libraryBookModel.getBook_key());
                File book_epub_file = new File(book_epub_dir,"book_epub.epub");

                Intent ii = new Intent(context, BookReadActivity.class);
                ii.putExtra("epubFilePath",book_epub_file.getPath());
                startActivity(context,ii,new Bundle());
            }
        });


        StorageReference bookCoverRef = libraryBookModel.getImgsrc();
        Glide.with(context)
                .load(bookCoverRef)
                .centerCrop()
                .into(individual_library_book_iv);

        return listitemView;
    }
}