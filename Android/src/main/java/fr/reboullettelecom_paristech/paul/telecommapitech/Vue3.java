package fr.reboullettelecom_paristech.paul.telecommapitech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class Vue3 extends Activity {

    private ImageView logo;
    private EditText position;
    private EditText destination;
    private Button ok;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout3);

        logo = findViewById(R.id.logo);
        position = findViewById(R.id.position);
        destination = findViewById(R.id.destination);
        ok = findViewById(R.id.ok);


        String textQ = this.getIntent().getExtras().getString("plan");

        String QRString = this.getIntent().getExtras().getString("txtResult");

        String[] Qr = QRString.split(";");

        String URL = Qr[0];

        String[] urlDecomposed = URL.split("/");

        int size = urlDecomposed.length;

        final String out = urlDecomposed[size-2]+"/"+urlDecomposed[size-1];

        getPlan(URL+".png",out+".png");
        getPlan(URL+".graph", out+".graph");


        /*
        String textPosition = this.getIntent().getExtras().getString("txtResult");
        if(textPosition != null) {
            position.setText(textPosition);
        }
        */

        position.setText(Qr[1]);
        destination.setText("");

        final Intent intent3 = new Intent(Vue3.this, Vue4.class);
        intent3.putExtra("plan", textQ);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent3.putExtra("positionId", position.getText().toString());
                intent3.putExtra("file", out);

                intent3.putExtra("destinationId", destination.getText().toString());
                startActivity(intent3);
            }
        });
    }

    private boolean readStream(InputStream in, String output) {
        Log.d("In readStream", output);
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), output);
        boolean success = false;
        try {
            OutputStream outStream = new FileOutputStream(file);

            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            success = true;
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return success;
        }
    }

    public void getPlan(final String Url,final String outFile) {

        new Thread(new Runnable() {
            public void run() {
                boolean done = false;
                try {
                    URL url = new URL(Url);
                    Log.d("In getPlan", Url);
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    con.setConnectTimeout(5000);
                    con.setReadTimeout(10000);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Downloading plan... ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    done = readStream(con.getInputStream(),outFile);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(done) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Successfully downloaded plan",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Failed to download plan",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();




    }
}
