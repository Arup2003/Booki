package com.booki.ai.model;

import com.google.firebase.storage.StorageReference;

public class LibraryBookModel {

    StorageReference imgsrc;
    String book_key;

    public LibraryBookModel(StorageReference imgsrc, String book_key) {
        this.imgsrc = imgsrc;
        this.book_key = book_key;
    }

    public StorageReference getImgsrc() {
        return imgsrc;
    }
    public void setImgsrc(StorageReference imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getBook_key() {
        return book_key;
    }

    public void setBook_key(String book_key) {
        this.book_key = book_key;
    }
}
