package com.booki.ai.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.booki.ai.R;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ncorti.slidetoact.SlideToActView;


public class LoginActivity extends AppCompatActivity {

    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private static final int LOGIN_CLOSED = 0;  // 0 = Closed popup without login
    private SignInClient oneTapClient;
    private BeginSignInRequest signUpRequest;

    SlideToActView slider_sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        slider_sign_in = findViewById(R.id.activity_login_slider);
        slider_sign_in.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                oneTapClient = Identity.getSignInClient(LoginActivity.this);
                signUpRequest = BeginSignInRequest.builder()
                        .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                // Your server's client ID, not your Android client ID.
                                .setServerClientId("363160883230-0ccqvghg6e34vmku0n7ja3c0pn9kpmat.apps.googleusercontent.com")
                                // Show all accounts on the device.
                                .setFilterByAuthorizedAccounts(false)
                                .build())
                        .build();

                oneTapClient.beginSignIn(signUpRequest).addOnSuccessListener(new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                        try {
                            startIntentSenderForResult(
                                    result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                                    null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Toast.makeText(LoginActivity.this, "Can't load sign in", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e);
                    }
                });

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("RESULT CODE IS: " + resultCode);
        if (resultCode == LOGIN_CLOSED){
            System.out.println("Login window closed");
            slider_sign_in.setCompleted(false, true);
        }
        else if (requestCode == REQ_ONE_TAP){
            try {
                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                String idToken = credential.getGoogleIdToken();
                if (idToken !=  null) {
                    // Got an ID token from Google. Use it to authenticate
                    // with Firebase.
                    AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                    FirebaseAuth.getInstance().signInWithCredential(firebaseCredential)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        DatabaseReference user_check = FirebaseDatabase.getInstance().getReference("Userdata").child(FirebaseAuth.getInstance().getUid());
                                        user_check.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.child("gender").getValue() == null){
                                                    Intent intent = new Intent(LoginActivity.this, OnboardingActivity.class);
                                                    startActivity(intent);
                                                    LoginActivity.this.finish();
                                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                                                }
                                                else{
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    LoginActivity.this.finish();
                                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Sign in failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            } catch (ApiException e) {
                Toast.makeText(LoginActivity.this, "Sign in failed", Toast.LENGTH_LONG).show();
                // ...
            }
        }
    }
}