package com.example.dontfakeit;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import org.w3c.dom.Text;

public class VerifyScreen extends AppCompatActivity {

    private TextView verifyText1, verifyText2;
    private VideoView verifyVideo;
    private boolean isTrue = false;
    private ImageView trueImage, fakeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_screen);

        verifyText1 = findViewById(R.id.verifyText1);
        verifyText2 = findViewById(R.id.verifyText2);
        verifyVideo = findViewById(R.id.videoView);

        trueImage= findViewById(R.id.trueImg);
        fakeImage = findViewById(R.id.fakeImg);

        Uri videoUri = Uri.parse("android.resource://com.example.dontfakeit/" + R.raw.animation);

        verifyVideo.setVideoURI(videoUri);
        verifyVideo.start();

        trueImage.setVisibility(View.INVISIBLE);
        fakeImage.setVisibility(View.INVISIBLE);

        View rootView = findViewById(android.R.id.content);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float touchX = event.getX(); // X coordinate of touch event

                // Check if touch event is on the left half of the screen
                if (touchX < v.getWidth() / 2) {
                    isTrue = true;
                } else {
                    isTrue = false;
                }

                return false; // Do not consume the touch event
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                verifyVideo.setVisibility(View.GONE);
                verifyText1.setVisibility(View.VISIBLE);
                verifyText2.setVisibility(View.VISIBLE);
                if (isTrue) {
                        trueImage.setVisibility(View.VISIBLE);
                        verifyText1.setText("This is True!!");
                        verifyText2.setText("This news article appears to be genuine and based on verified facts. Proceed with confidence in sharing or reading further.");
                        fakeImage.setVisibility(View.INVISIBLE);
                } else {
                    fakeImage.setVisibility(View.VISIBLE);
                    verifyText1.setText("Uh Ohh!!");
                    verifyText2.setText("Caution! This news article seems to be misleading or inaccurate. Verify the information from trusted sources before sharing or believing.");
                    trueImage.setVisibility(View.INVISIBLE);
                }
            }
        }, 5000);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}