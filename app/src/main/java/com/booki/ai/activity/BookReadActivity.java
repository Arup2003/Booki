package com.booki.ai.activity;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.booki.ai.R;
import com.booki.ai.fragment.CustomiseTextBottomSheet;
import com.github.mertakdut.BookSection;
import com.github.mertakdut.Reader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import io.hamed.htepubreadr.component.EpubReaderComponent;
import io.hamed.htepubreadr.entity.BookEntity;
import io.hamed.htepubreadr.entity.SubBookEntity;
import io.hamed.htepubreadr.ui.view.EpubView;
import io.hamed.htepubreadr.ui.view.OnHrefClickListener;
import io.hamed.htepubreadr.util.EpubUtil;

public class BookReadActivity extends Activity {

    TextView book_read_main_tv;
    EpubView book_read_main_ev;
    TextView book_read_page_no;
    ImageView book_read_edit_text_icon;
    CardView book_read_pill_panel;
    ConstraintLayout book_read_top_panel;
    StorageReference epubRef;
    ImageView book_read_page_icon;

    public int seekbarProgress=0;
    public int pageColorPosition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_read);

        book_read_main_tv = findViewById(R.id.book_read_main_tv);
        book_read_top_panel = findViewById(R.id.book_read_top_panel);
        book_read_pill_panel = findViewById(R.id.book_read_pill_panel);
        book_read_edit_text_icon = findViewById(R.id.book_read_edit_text_icon);
        book_read_page_no = findViewById(R.id.book_read_page_no);
        book_read_page_icon = findViewById(R.id.book_read_page_icon);

        book_read_top_panel.setVisibility(View.INVISIBLE);
        book_read_pill_panel.setVisibility(View.INVISIBLE);



        book_read_page_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(BookReadActivity.this, BookReadPageViewActivity.class);
                startActivity(ii);
            }
        });

        book_read_main_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int visibility = book_read_top_panel.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;

                book_read_top_panel.setVisibility(visibility);
                book_read_pill_panel.setVisibility(visibility);
            }
        });
        

        book_read_edit_text_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomiseTextBottomSheet customiseTextBottomSheet = new CustomiseTextBottomSheet(BookReadActivity.this, book_read_main_tv, book_read_page_no);
                customiseTextBottomSheet.show();
            }
        });

        epubRef = FirebaseStorage.getInstance().getReference("Marketplace").child("Books").child("-Ny-M3KEfS8LrkhTcS9Y").child("book_epub");

//        epubRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//
//                String url = uri.toString();
//                downloadEpub(BookReadActivity.this,"book_epub",".epub",DIRECTORY_DOWNLOADS,url);
//
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });


        try{
            File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File f = new File(downloadsDirectory,"book_epub.epub");
            EpubReaderComponent epubReader = new EpubReaderComponent(downloadsDirectory+"/book_epub.epub");
            BookEntity bookEntity = epubReader.make(BookReadActivity.this);


            List<String> allPage = bookEntity.getPagePathList();
            // set file path
//            book_read_main_ev.setBaseUrl(epubReader.getAbsolutePath());
            StringBuilder content = new StringBuilder();

//            for(int i=0;i<allPage.size();i++)
            {
                content.append(EpubUtil.getHtmlContent(allPage.get(1)));
            }
//            book_read_main_tv.setUp(content);

//            book_read_main_ev.setUp(content);


            Document document = Jsoup.parse(content.toString());
            StringBuilder text = new StringBuilder();

            // Extract text while preserving paragraphs and headings
            for (Element element : document.body().children()) {
                switch (element.tagName()) {
                    case "h1":
                    case "h2":
                    case "h3":
                    case "h4":
                    case "h5":
                    case "h6":
                        text.append(element.text()).append("\n\n");
                        break;
                    case "p":
                        text.append(element.text()).append("\n\n");
                        break;
                    // Add more cases as needed to handle other elements
                }
            }

            book_read_main_tv.setText(text);




        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Toast.makeText(this, "gggg", Toast.LENGTH_SHORT).show();


//        try {
//
//
//
//            Reader reader = new Reader();
//            reader.setMaxContentPerSection(1000); // Max string length for the current page.
//            reader.setIsIncludingTextContent(true); // Optional, to return the tags-excluded version.
//
//            File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            File f = new File(downloadsDirectory,"book_epub.epub");
//            System.out.println("🙂🙂🙂🙂🙂"+f.exists());
//
//            reader.setFullContent(f.getAbsolutePath()); // Must call before readSection.
//
//            BookSection bookSection = reader.readSection(5);
//            String sectionContent = bookSection.getSectionContent(); // Returns content as html.
//            String sectionTextContent = bookSection.getSectionTextContent(); // Excludes html tags.
//
//            System.out.println("GGGGGGGG");
//
//            book_read_main_tv.setText(sectionTextContent);
//
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void downloadEpub(Context context, String fileName, String fileExtension, String destinationDirectory,String url)
    {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(destinationDirectory,fileName+fileExtension);

        downloadManager.enqueue(request);
    }

}

