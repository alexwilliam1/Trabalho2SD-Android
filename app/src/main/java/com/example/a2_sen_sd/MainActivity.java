package com.example.a2_sen_sd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ativar = (Button) findViewById(R.id.angry_btn);
        final TextView status = (TextView) findViewById(R.id.status);

        ativar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //status.setText("Modo de segurança ativado!");
                Intent i = new Intent(MainActivity.this, TelaModoSeguranca.class);
                startActivity(i);
            }
        });
    }
}
