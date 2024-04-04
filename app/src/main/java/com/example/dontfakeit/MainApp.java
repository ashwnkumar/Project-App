package com.example.dontfakeit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainApp extends AppCompatActivity {

    //declaring variables
    ImageView realImg, fakeImg;
    TextView verifyText1,  verifyText2;
    Button verifyBtnTrue, verifyBtnFalse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_app);

        //fetching respective image and text objects using their IDs
        realImg = findViewById(R.id.real_img);
        fakeImg = findViewById(R.id.fake_img);

        verifyText1= findViewById(R.id.verify_text1);
        verifyText2= findViewById(R.id.verify_text2);

        verifyBtnTrue= findViewById(R.id.verify_true);
        verifyBtnFalse= findViewById(R.id.verify_false);

        //'true' and 'fake' news images disabled at start
        realImg.setVisibility(View.INVISIBLE);
        fakeImg.setVisibility(View.INVISIBLE);

        //function for executing (dummy) output in case of news being true
        verifyBtnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realImg.setVisibility(View.VISIBLE); //making 'true' wala image visible

                //text fields are set with no text by default. 'setText()' function adds text into them
                verifyText1.setText("This is True!!");
                verifyText2.setText("This really happened and this news is accurate.");

                fakeImg.setVisibility(View.INVISIBLE); //disabling 'fake' wala image

            }
        });

        //function for executing (dummy) output in case of news being false
        verifyBtnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fakeImg.setVisibility(View.VISIBLE); //making 'fake' wala image visible
                verifyText1.setText("Uh Oh!!");
                verifyText2.setText("It seems that your news sources have been tampered with. This is fake");
                realImg.setVisibility(View.INVISIBLE); //disabling 'true' wala image

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}