package com.example.dontfakeit;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainApp extends AppCompatActivity {

    //declaring variables
    private ImageView realImg, fakeImg;
    private TextView verifyText1,  verifyText2;
    private Button verifyBtnTrue;
    private View rootView;
    private boolean isTrue = false;
    private EditText inputText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_app);


        realImg = findViewById(R.id.real_img);
        fakeImg = findViewById(R.id.fake_img);
        rootView = findViewById(android.R.id.content);

        verifyText1= findViewById(R.id.verify_text1);
        verifyText2= findViewById(R.id.verify_text2);
        inputText = findViewById(R.id.textBox);

        verifyBtnTrue = findViewById(R.id.verifyButton);

        //'true' and 'fake' news images disabled at start
        realImg.setVisibility(View.INVISIBLE);
        fakeImg.setVisibility(View.INVISIBLE);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float touchX = event.getX();

                if(touchX < v.getWidth() / 2) {
                    isTrue = true;
                } else {
                    isTrue = false;
                }
                return false;
            }
        });

        verifyBtnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input = inputText.getText().toString().trim();
                if (input.isEmpty()) {
                    Toast.makeText(MainApp.this, "Please Enter News First", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isSentence(input)) {
                    if (isTrue) {
                        realImg.setVisibility(View.VISIBLE);
                        verifyText1.setText("This is True!!");
                        verifyText2.setText("This really happened and this news is accurate.");
                        fakeImg.setVisibility(View.INVISIBLE);
                    } else {
                        fakeImg.setVisibility(View.VISIBLE);
                        verifyText1.setText("Uh Oh!!");
                        verifyText2.setText("It seems that your news sources have been tampered with. This is fake");
                        realImg.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Toast.makeText(MainApp.this, "Please enter a valid sentence", Toast.LENGTH_SHORT).show();
                }

            }

            private boolean isSentence (String text) {
                return text.matches(".*\\s+.*") && text.matches(".*[a-zA-Z]+.*");
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}