package com.booki.ai.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.booki.ai.R;
import com.booki.ai.fragment.CustomiseTextBottomSheet;
import com.booki.ai.fragment.PageNumberBottomSheet;
import com.booki.ai.model.CustomWebView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.sidesheet.SideSheetBehavior;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;


import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;

public class BookReadActivity extends Activity implements View.OnTouchListener, Handler.Callback  {

    static CustomWebView book_read_main_tv, book_read_secondary_tv, book_read_tertiary_tv;

    int web_view_displayed_on_screen = 0;
    static int currentScrollY = 0;
    static TextView book_read_page_no;
    ImageView book_read_edit_text_icon;
    LinearLayout book_read_chapter_inidex;
    public CardView book_read_pill_panel;
    public ConstraintLayout book_read_top_panel;
    LinearLayout book_read_side_sheet;
    StorageReference epubRef;
    ImageView book_read_page_icon;
    ImageView book_read_chapter_menu;
    public static int totalPages;
    public static int seekbarProgress=0;
    public static int pageColorPosition=0;
    public static int fontStylePosition=0;

    private static final int CLICK_ON_WEBVIEW = 1;

    public static String webData;
    public static int scrollX = 0;
    public static int scrollY = 0;
    private final Handler handler = new Handler(this);
    private GestureDetector gestureDetector;
    private SideSheetBehavior<View> sideSheetBehavior;
    List<TOCReference> tocReferences;
    public static String head;
    public static String htmlStyle;
    static String htmlStyleEnd;
    private static int currPage=1;
    StringBuilder bookContent;
    private int totalCharsScrolled = 0;
    static int pageLength=1500;
    Intent parserIntent;

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
        book_read_chapter_menu = findViewById(R.id.book_read_chapter_menu);
        book_read_side_sheet = findViewById(R.id.book_read_side_sheet);
        book_read_chapter_inidex = findViewById(R.id.book_read_chapter_index);
        book_read_secondary_tv = findViewById(R.id.book_read_secondary_tv);
        book_read_tertiary_tv = findViewById(R.id.book_read_tertiary_tv);
//
//        PageSurfaceView  pageSurfaceView = new PageSurfaceView(this);
//        String[] asset_res_array=null;
//        asset_res_array=  new String[]{"cat1.png", "cat2.png", "cat3.png"};
//        PageCurlAdapter pageCurlAdapter=new PageCurlAdapter(asset_res_array);
//        pageSurfaceView.setPageCurlAdapter(pageCurlAdapter);
//        setContentView(pageSurfaceView);


        sideSheetBehavior = SideSheetBehavior.from(book_read_side_sheet);

        book_read_top_panel.setVisibility(View.INVISIBLE);
        book_read_pill_panel.setVisibility(View.INVISIBLE);


        // Enable JavaScript
        WebSettings webSettings = book_read_main_tv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);


        // Set a WebViewClient to handle page navigation
        book_read_main_tv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                view.setScrollY(scrollY);
                view.setScrollX(scrollX);

                calculateTotalCharacters();

                System.out.println("🫂🫂🫂"+currPage);
                scrollToNextPage();

//                scrollToPage(currPage);

            }
        });
        book_read_secondary_tv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                view.setScrollY(scrollY);
                view.setScrollX(scrollX);

                calculateTotalCharacters();

                System.out.println("🫂🫂🫂"+currPage);
//                scrollToPage(currPage);

            }
        });
        book_read_tertiary_tv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                view.setScrollY(scrollY);
                view.setScrollX(scrollX);

                calculateTotalCharacters();

                System.out.println("🫂🫂🫂"+currPage);
//                scrollToPage(currPage);

            }
        });

        book_read_main_tv.setOnScrollChangedCallback(new CustomWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                calculateCharactersScrolled(t);
            }
        });

        book_read_main_tv.setOnTouchListener(this);
        book_read_main_tv.setHorizontalScrollBarEnabled(false);

        book_read_secondary_tv.setOnScrollChangedCallback(new CustomWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                calculateCharactersScrolled(t);
            }
        });

        book_read_secondary_tv.setOnTouchListener(this);
        book_read_secondary_tv.setHorizontalScrollBarEnabled(false);


        book_read_tertiary_tv.setOnScrollChangedCallback(new CustomWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                calculateCharactersScrolled(t);
            }
        });

        book_read_tertiary_tv.setOnTouchListener(this);
        book_read_tertiary_tv.setHorizontalScrollBarEnabled(false);


        gestureDetector = new GestureDetector(this, new MyGestureListener());



        book_read_page_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PageNumberBottomSheet pageNumberBottomSheet = new PageNumberBottomSheet(BookReadActivity.this,totalPages,currPage);
                pageNumberBottomSheet.show();
            }
        });

        book_read_chapter_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sideSheetBehavior.setState(SideSheetBehavior.STATE_EXPANDED);

                int visibility = View.INVISIBLE;

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



        parserIntent = getIntent();
        String epubFilePath = parserIntent.getStringExtra("epubFilePath");
        epubParser(epubFilePath);

    }


    public void epubParser(String filePath){

        bookContent = new StringBuilder();
        String fullText="";
        try {
            File epubFile = new File(filePath);
            FileInputStream epubInputStream = new FileInputStream(epubFile);
            Book book = (new EpubReader()).readEpub(epubInputStream);

            tocReferences = book.getTableOfContents().getTocReferences();

            List<Resource> contents = book.getContents();

            for (Resource resource : contents) {
                bookContent.append(new String(resource.getData(), StandardCharsets.UTF_8));
            }

            epubInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        webData = bookContent.toString();

        createChapterLinks(tocReferences, 0);

//        System.out.println(webData);


        head = "<html><head><style>body { text-align: center; font-family: serif}</style></head>";
        htmlStyle = "<body><font color='#000000' size='2px'>";
        htmlStyleEnd = "</font></body></html>";

        loadData();
    }

    private static void calculateTotalCharacters() {
        book_read_main_tv.evaluateJavascript(
                "(function() {" +
                        "    var content = document.body.innerText;" +
                        "    return content.length;" +
                        "})()", value -> {
                    try {
                        int totalCharacters = Integer.parseInt(value);
                        // Handle the total character count (e.g., update UI)
                        totalPages = totalCharacters/pageLength;
                        book_read_page_no.setText("Page "+currPage+" of "+totalPages);

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                });
    }

    public static void scrollToPage(int pageNumber) {
        int targetCharIndex = (pageNumber - 1) * pageLength;

        book_read_main_tv.evaluateJavascript(
                "(function() {" +
                        "    var content = document.body.innerText;" +
                        "    var targetCharIndex = " + targetCharIndex + ";" +
                        "    var scrolledLength = 0;" +
                        "    var scrollTop = 0;" +
                        "    var lines = content.split('\\n');" +
                        "    for (var i = 0; i < lines.length; i++) {" +
                        "        scrolledLength += lines[i].length;" +
                        "        if (scrolledLength >= targetCharIndex) {" +
                        "            scrollTop = document.body.scrollHeight * scrolledLength / content.length;" +
                        "            break;" +
                        "        }" +
                        "    }" +
                        "    window.scrollTo(0, scrollTop);" +
                        "    return scrollTop;" +
                        "})()", value -> {
                    try {
                        float scrollY = Float.parseFloat(value);
                        System.out.println("ScrollY for page " + pageNumber + ": " + scrollY);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void createChapterLinks(List<TOCReference> tocReferences, int depth) {
        for (TOCReference tocReference : tocReferences) {
            TextView chapterLink = new TextView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                chapterLink.setTypeface(getResources().getFont(R.font.league_spartan));
            }
            chapterLink.setText(tocReference.getTitle());
            chapterLink.setTextSize(16);
            chapterLink.setOnClickListener(v -> {
                try {
                    displayChapterContent(tocReference.getResource());

                    sideSheetBehavior.setState(SideSheetBehavior.STATE_HIDDEN);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            book_read_chapter_inidex.addView(chapterLink);

            createChapterLinks(tocReference.getChildren(), depth + 1);  // Recursively add child chapters
        }
    }

    private void displayChapterContent(Resource resource) throws IOException {
        InputStream is = resource.getInputStream();
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        is.close();

        String chapterData = new String(buffer);
        int idIndex = chapterData.indexOf(" id=");
        StringBuilder chapterId=new StringBuilder();
        int count=0;
        while(count<2)
        {
            if(chapterData.charAt(idIndex)=='"')
            {
                count++;
            }
            else if(count==1)
            {
                chapterId.append(chapterData.charAt(idIndex));
            }

            idIndex++;
        }

        book_read_main_tv.loadUrl("javascript:document.getElementById('" + chapterId.toString() + "').scrollIntoView();");
    }

    private void calculateCharactersScrolled(int scrollY) {
        book_read_main_tv.evaluateJavascript(
                "(function() {" +
                        "    var content = document.body.innerText;" +
                        "    var viewportHeight = window.innerHeight;" +
                        "    var scrolledText = content.substring(0, Math.floor((document.body.scrollTop) * content.length / document.body.scrollHeight));" +
                        "    return scrolledText.length;" +
                        "})()", value -> {
                    try {
                        int charsScrolled = Integer.parseInt(value);
                        if (charsScrolled != totalCharsScrolled) {
                            totalCharsScrolled = charsScrolled;
                            currPage = (1+charsScrolled/1500);

                            book_read_page_no.setText("Page "+(1+charsScrolled/1500)+" of "+totalPages);

                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                });

    }

    public static void loadData()
    {
        //scrollY = book_read_main_tv.getScrollY();
        book_read_main_tv.loadDataWithBaseURL(null, head+htmlStyle+webData+htmlStyleEnd,"text/html", "UTF-8",null);
        book_read_secondary_tv.loadDataWithBaseURL(null, head+htmlStyle+webData+htmlStyleEnd,"text/html", "UTF-8",null);
        book_read_tertiary_tv.loadDataWithBaseURL(null, head+htmlStyle+webData+htmlStyleEnd,"text/html", "UTF-8",null);

    }

    private static void scrollToNextPage() {
        book_read_main_tv.evaluateJavascript(
                "(function() { return document.body.scrollHeight; })();",
                new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        int contentHeight = Integer.parseInt(value);
                        int webViewHeight = book_read_main_tv.getHeight();

                        if (currentScrollY + webViewHeight < contentHeight) {
                            currentScrollY += webViewHeight;

                            // Ensure no lines are cut off
//                            int additionalScroll = currentScrollY % lineHeight;
//                            currentScrollY -= additionalScroll;

                            book_read_main_tv.scrollTo(0, currentScrollY - webViewHeight);
                            book_read_tertiary_tv.scrollTo(0, currentScrollY);
                            book_read_secondary_tv.scrollTo(0, currentScrollY - 2*webViewHeight);


                        }
                    }
                }
        );
    }







        @Override
    public boolean handleMessage(@NonNull Message msg) {
        if (msg.what == CLICK_ON_WEBVIEW){
//            int visibility = book_read_top_panel.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;
//
//            book_read_top_panel.setVisibility(visibility);
//            book_read_pill_panel.setVisibility(visibility);
            scrollToNextPage();
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }


    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int CLICK_ON_WEBVIEW = 1;

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            handler.sendEmptyMessage(CLICK_ON_WEBVIEW);
            return true;
        }

    }


}


