package com.booki.ai.activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.booki.ai.R;
import com.booki.ai.adapter.AI_ChatbotAdapter;
import com.booki.ai.model.AI_chatbot_model;
import com.eyalbira.loadingdots.LoadingDots;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public class AI_ChatbotActivity extends AppCompatActivity {
    AI_ChatbotAdapter adapter;
    ConstraintLayout main_constraint;
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    OkHttpClient client;
    ArrayList<AI_chatbot_model> chat_array = new ArrayList<>();
    RecyclerView recyclerView;
    CardView send_btn, loading_dots_image;
    EditText editText;
    TextView ai_chatbot_name;
    String thread_id, user_message;
    String book_id;
    String vector_id, assistant_id, character_name;
    Float total_price_of_thread = 0.0F;
    LoadingDots typing_indicator;
    BlurView top_blur;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_chatbot);

        book_id = getIntent().getStringExtra("book_id");

        fetch_assistant_data();

        send_btn = findViewById(R.id.ai_chatbot_send_card);
        recyclerView = findViewById(R.id.ai_chatbot_recyclerview);
        editText = findViewById(R.id.ai_chatbot_edittext);
        ai_chatbot_name = findViewById(R.id.ai_chatbot_name);
        main_constraint = findViewById(R.id.ai_chatbot_main_constraint);
        typing_indicator = findViewById(R.id.ai_chatbot_typing_indicator);
        top_blur = findViewById(R.id.ai_chatbot_top_blurview);
        loading_dots_image = findViewById(R.id.ai_chatbot_typing_image);
        typing_indicator.setVisibility(View.GONE);
        loading_dots_image.setVisibility(View.GONE);

        adapter = new AI_ChatbotAdapter(this, chat_array);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


        View decor = getWindow().getDecorView();
        ViewGroup decorview = decor.findViewById(R.id.ai_chatbot_main_constraint);
        Drawable windowBackground = decor.getBackground();
        top_blur.setupWith(decorview, new RenderScriptBlur(getApplicationContext()))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(10);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isblank(s.toString())){
                    send_btn.setVisibility(View.GONE);
                }
                else{
                    send_btn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        builder.connectTimeout(1, TimeUnit.MINUTES);
        builder.callTimeout(1, TimeUnit.MINUTES);
        client = builder.build();

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_message = editText.getText().toString();
                editText.setText("");
                chat_array.add(new AI_chatbot_model(false, user_message, character_name, false));
                adapter.notifyItemInserted(chat_array.size() - 1);
                recyclerView.scrollToPosition(chat_array.size() - 1);
                typing_indicator.setVisibility(View.VISIBLE);
                loading_dots_image.setVisibility(View.VISIBLE);


                if(thread_id == null) {
                    RequestBody create_thread_body = RequestBody.create("", JSON);
                    Request create_thread_request = new Request.Builder()
                            .url("https://api.openai.com/v1/threads")
                            .header("Content-type", "application/json")
                            .header("Authorization", "Bearer sk-proj-48xOwVbkwfJNlnBZqlALT3BlbkFJRoxe2pdZyPQGzBjTo2Yx")
                            .header("OpenAI-Beta", "assistants=v2")
                            .post(create_thread_body)
                            .build();
                    client.newCall(create_thread_request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            System.out.println("CREATING THREAD FAILED");
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            System.out.println("CREATING THREAD SUCCESS");
                            JSONObject thread_response_json = null;
                            try {
                                thread_response_json = new JSONObject(response.body().string());
                                System.out.println(thread_response_json);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                thread_id = thread_response_json.getString("id");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            generate_response();
                        }
                    });
                }
                else{
                    generate_response();
                }

            }
        });
    }

    private void fetch_assistant_data() {
        DatabaseReference chatbot_ref = FirebaseDatabase.getInstance().getReference("AI_characters").child(book_id);
        chatbot_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){
                    if(!Objects.equals(snap.getKey(), "vector_id")){
                        character_name = snap.getKey();
                        assistant_id = snap.child("assistant_id").getValue(String.class);
                        ai_chatbot_name.setText(character_name);
                        chat_array.add(new AI_chatbot_model(true, "Hey there! I am " + character_name, character_name, true));
                        adapter.notifyItemInserted(chat_array.size() - 1);
                        recyclerView.scrollToPosition(chat_array.size() - 1);
                    }
                }
                Toast.makeText(AI_ChatbotActivity.this, "READY TO CHAT", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void generate_response() {
        //FIRST API CALL TO ADD MESSAGE TO THE THREAD
        JSONObject message_to_add = new JSONObject();

        try {
            message_to_add.put("role", "user");
            message_to_add.put("content", user_message);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        RequestBody add_message_to_thread_body = RequestBody.create(message_to_add.toString(), JSON);
        Request add_message_to_thread_request = new Request.Builder()
                .url("https://api.openai.com/v1/threads/" + thread_id + "/messages")
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer sk-proj-48xOwVbkwfJNlnBZqlALT3BlbkFJRoxe2pdZyPQGzBjTo2Yx")
                .header("OpenAI-Beta", "assistants=v2")
                .post(add_message_to_thread_body)
                .build();
        System.out.println("REQUESTING ADDITION OF MESSAGE");

        client.newCall(add_message_to_thread_request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //SECOND API CALL TO RUN THE THREAD AND RETRIEVE THE RESPONSE
                System.out.println("ADDED MESSAGE TO THREAD");

                JSONObject get_response_object = new JSONObject();
                try {
                    get_response_object.put("assistant_id", assistant_id);
                    get_response_object.put("stream", true);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                RequestBody get_response_body = RequestBody.create(get_response_object.toString(), JSON);
                Request get_response_request = new Request.Builder()
                        .url("https://api.openai.com/v1/threads/" + thread_id + "/runs")
                        .header("Content-type", "application/json")
                        .header("Authorization", "Bearer sk-proj-48xOwVbkwfJNlnBZqlALT3BlbkFJRoxe2pdZyPQGzBjTo2Yx")
                        .header("OpenAI-Beta", "assistants=v2")
                        .post(get_response_body)
                        .build();

                System.out.println("NOW REQUESTING RESPONSE");

                client.newCall(get_response_request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        System.out.println("RESPONSE RECEIVED");
                        Source source = response.body().source();
                        BufferedSource bufferedSource = Okio.buffer(source);
                        String line, old_line = "{\"nothing\":\"nothing\"}", tokens = null;

                        while ((line = bufferedSource.readUtf8Line()) != null) {
                            if (line.contains("\"type\":\"text\"")){
                                old_line = line;
                                System.out.println(line);
                            }
                            else{
                                System.out.println(line);
                            }
                            if (line.contains("\"completion_tokens\"") && line.contains("\"prompt_tokens\"")){
                                tokens = line;

                            }

                        }

                        try {
                            JSONObject response_data = new JSONObject(old_line.substring(6));
                            JSONObject token_data = new JSONObject(tokens.substring(6));
                            System.out.println("TOKENNNNNNN");
                            System.out.println(token_data);

                            String prompt_tokens = token_data.getJSONObject("usage").getString("prompt_tokens");
                            String completion_tokens = token_data.getJSONObject("usage").getString("completion_tokens");

                            String status = response_data.getString("status");
                            String message = response_data.getJSONArray("content").getJSONObject(0).getJSONObject("text").getString("value");
                            System.out.println("PARSED DATA:");
                            System.out.println(message);
                            System.out.println(status);
                            System.out.println(thread_id);
                            System.out.println(prompt_tokens);
                            System.out.println(completion_tokens);
                            Float final_price = (float) (((Float.parseFloat(prompt_tokens)/1000)*0.005 + (Float.parseFloat(completion_tokens)/1000)*0.015))*83;
                            System.out.println(final_price);
                            total_price_of_thread += final_price;

                            if(status.equals("completed")){
                                chat_array.add(new AI_chatbot_model(true, message, character_name, true));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AI_ChatbotActivity.this, "TOTAL PRICE: " + total_price_of_thread.toString(), Toast.LENGTH_LONG).show();
                                        adapter.notifyItemInserted(chat_array.size() - 1);
                                        recyclerView.scrollToPosition(chat_array.size() - 1);
                                        typing_indicator.setVisibility(View.GONE);
                                        loading_dots_image.setVisibility(View.GONE);
                                    }

                                });
                            }

                        }
                        catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });



            }
        });
    }

    //This checks if entered message is blank or not
    private boolean isblank(String toString) {
        for(int i = 0; i < toString.length(); i++){
            if(toString.charAt(i) == ' '){
            }
            else{
                return false;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}