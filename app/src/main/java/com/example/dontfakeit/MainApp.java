package com.example.dontfakeit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainApp extends AppCompatActivity {

    //declaring variables
    Button verifyBtn;
    private EditText inputText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_app);

        inputText = findViewById(R.id.textBox);

        verifyBtn = findViewById(R.id.verifyButton);


        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String input = inputText.getText().toString().trim();
                if (input.isEmpty()) {
                    Toast.makeText(MainApp.this, "Please Enter News First", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isSentence(input)) {
                    Intent intent = new Intent(MainApp.this, VerifyScreen.class);
                    startActivity(intent);

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