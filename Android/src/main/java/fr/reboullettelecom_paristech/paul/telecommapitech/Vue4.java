package fr.reboullettelecom_paristech.paul.telecommapitech;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class Vue4 extends Activity{

    private PhotoView nav;
    private TextView position;
    Bitmap bitmap;
    Bitmap mutable;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout4);

        NavGraph navGraph = new NavGraph(this);

        nav = findViewById(R.id.nav);
        position = findViewById(R.id.position);

        String text2 = this.getIntent().getExtras().getString("positionId");
        String text3 = this.getIntent().getExtras().getString("destinationId");
        String text4 = text2+" to "+text3;

        position.setText(text4);





        File sdCard = Environment.getExternalStorageDirectory();

        File directory = new File (sdCard.getAbsolutePath() + "/Documents");

        File file = new File(directory, getIntent().getExtras().getString("file")+".png"); //or any other format supported
        Log.d("Vue 4", file.getAbsolutePath().toString());
        FileInputStream streamIn = null;
        try {
            streamIn = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeStream(streamIn); //This gets the image

        try {
            streamIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //bitmap = ((BitmapDrawable) drawable).getBitmap();
        mutable = bitmap.copy(Bitmap.Config.ARGB_8888,true);

        /*for(int i=1; i<mutable.getWidth()/2;i=i+2) {
            for(int j=1; j<mutable.getHeight()/2;j=j+2) {
                mutable.setPixel(i, j, RED);
            }
        }*/


        int xStart = Integer.parseInt(this.getIntent().getExtras().get("positionId").toString().split(",")[0]);
        int yStart = Integer.parseInt(this.getIntent().getExtras().get("positionId").toString().split(",")[1]);
        int xFinish = Integer.parseInt(this.getIntent().getExtras().get("destinationId").toString().split(",")[0]);
        int yFinish = Integer.parseInt(this.getIntent().getExtras().get("destinationId").toString().split(",")[1]);
        Log.d("In Vue4","Starting init from text file");
        navGraph.initWithFile(getIntent().getExtras().getString("file")+".graph");
        Log.d("In Vue4","Finishing init from text file");
        Log.d("In Vue 4",navGraph.getyMax()+";"+navGraph.getxMax());
        Log.d("In Vue4","Starting dijkstra");
        Previous parents = Dijkstra.dijkstra(navGraph,xStart,yStart);
        Log.d("In Vue4","Finishing dijkstra");
        NavNod arrival = Dijkstra.projection(xFinish,yFinish,navGraph);
        Log.d("In Vue4", "Projection on squeleton : "+arrival.getX()+";"+arrival.getY());
        ArrayList<NavNod> chemin = parents.getPath(arrival);


        Log.d("In Vue4", "Size of chemin = "+chemin.size());


        for(NavNod n : chemin){
            int x1 = n.getX();
            int y1 = n.getY();
            //int taille = chemin.size();
            //int pos = chemin.indexOf(n);

            mutable.setPixel(y1,x1,RED);

            /*
            if ((pos != taille-3)&(pos < taille-1)){
                //Log.d("In Vue4", "pos " + Integer.toString(pos)+"; taille "+Integer.toString(taille));
                int x2 = chemin.get(pos+1).getX();
                int y2 = chemin.get(pos+1).getY();
                int dx = x2-x1;
                int dy = y2-y1;
                Log.d("In Vue4", "x1,y1;x2,y2 = "+x1+","+y1+";"+x2+","+y2);



                if (dx == 0){
                    if(dy > 0){
                        for(int i = 1; i<dy;i++){
                            Log.d("In Vue4", "mute a pixel");
                            mutable.setPixel(y1+i,x1, RED);
                        }
                    }
                    if(dy < 0){
                        for(int i = 1; i<-dy;i++){
                            Log.d("In Vue4", "mute a pixel");
                            mutable.setPixel(y1-i,x1,RED);
                        }
                    }
                }
                if (dx > 0){
                    if(dy > 0){
                        for(int i = 1; i<dy;i++){
                            Log.d("In Vue4", "mute a pixel");
                            mutable.setPixel(y1+i,x1+i, RED);
                        }
                    }
                    if(dy < 0){
                        for(int i = 1; i<-dy;i++){
                            Log.d("In Vue4", "mute a pixel");
                            mutable.setPixel(y1-i,x1+i,RED);
                        }
                    }
                    if(dy == 0){
                        for(int i = 1; i<dx;i++){
                            Log.d("In Vue4", "mute a pixel");
                            mutable.setPixel(y1,x1+i,RED);
                        }
                    }
                }
                if (dx < 0){
                    if(dy > 0){
                        for(int i = 1; i<dy;i++){
                            Log.d("In Vue4", "mute a pixel");
                            mutable.setPixel(y1+i,x1-i, RED);
                        }
                    }
                    if(dy < 0){
                        for(int i = 1; i<-dy;i++){
                            mutable.setPixel(y1-i,x1-i,RED);
                        }
                    }
                    if(dy == 0){
                        for(int i = 1; i<-dx;i++){
                            Log.d("In Vue4", "mute a pixel");
                            mutable.setPixel(y1,x1-i,RED);
                        }
                    }
                }


            }
            */

        }


        Drawable d = new BitmapDrawable(this.getResources(),mutable);

        nav.setImageDrawable(d);

    }
}
