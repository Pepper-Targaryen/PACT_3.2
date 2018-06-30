package fr.reboullettelecom_paristech.paul.telecommapitech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Vue2 extends Activity {

    private ImageView logo;
    private Button QR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);

        logo = findViewById(R.id.logo);
        QR = findViewById(R.id.QR);

        final Intent intentQ = new Intent(Vue2.this, VueQR.class);

        QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentQ);
            }
        });


    }




}
