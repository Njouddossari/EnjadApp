package com.example.lenovo.enjad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hom_page);
        Button ReportsB = (Button) findViewById(R.id.Reportsbutton);
        Button ProfileB = (Button) findViewById(R.id.Profilebutton);
        Button ChatsB = (Button) findViewById(R.id.Chatsbutton);

        ProfileB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent R = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(R);
            }
        });

        ChatsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chls = new Intent(getApplicationContext(), chatlistActivity.class);
                startActivity(chls);
            }
        });
    }

    public void profileonclick(View view) {

        Intent profile = new Intent (this,HomPageActivity.class);

        startActivity(profile);


    }
}
