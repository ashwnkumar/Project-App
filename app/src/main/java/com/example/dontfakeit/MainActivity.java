package com.example.dontfakeit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Variables
    private static int splashScreenDuration = 5000;
    VideoView splashVideo;

    // Animation topAnim, bottomAnim;
    //ImageView image;
    //TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Fetching Animations (top_animation.xml & bottom_animation.xml)
        //topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        //bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Fetching Image and Text
        //image = findViewById(R.id.imageView);
        //text = findViewById(R.id.textView);

        //Applying Animations
        //image.setAnimation(topAnim); //top to bottom animation for image/logo
        //text.setAnimation(bottomAnim); //bottom to top animation for text

        //Fetching splash screen video
        splashVideo = findViewById(R.id.splash_video);

        Uri videoUri = Uri.parse("android.resource://com.example.dontfakeit/" + R.raw.splash_video);

        splashVideo.setVideoURI(videoUri);
        splashVideo.start();

        //function to automatically move to next screen (dashboard screen)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        }, splashScreenDuration);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}