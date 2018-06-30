package fr.reboullettelecom_paristech.paul.telecommapitech;
import java.util.ArrayList;
import java.io.BufferedReader;

import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import android.content.res.AssetManager;
import java.io.*;
import java.lang.Object;
import android.content.Context;
/**
 * Created by pierr on 05/03/2018.
 */

public class NavGraph {
    private Context context;
    private ArrayList<NavNod> tableau;
    int xMax;
    int yMax;


    public NavGraph(Context current) {
        this.context = current;
    }

    public double getP(NavNod n1, NavNod n2){
        ArrayList<Successor> successors = n1.getSuccessors();
        double p = -1;
        int goodX = n2.getX();
        int goodY = n2.getY();
        for(Successor s : successors){
            if(s.getX() == goodX && s.getY() == goodY){
                p = s.getP();
            }
        }
        return p;
    }

    public ArrayList<NavNod> getTableau() {
        return tableau;
    }

    public final void initWithFile(String file) {
        BufferedReader in = null;
        try {
            /* Modifier les deux lignes qui suivent pour obtenir quelque chose qui lit
            les fichiers sur la mémoire du téléphone. Il faudra éventuellement ajouter un argument
            Attention : faire de même aux lignes 65 et 66!!!
            */
            File sdCard = Environment.getExternalStorageDirectory();

            File directory = new File (sdCard.getAbsolutePath() + "/Documents/");



            in = new BufferedReader(new FileReader(directory.getAbsolutePath().toString()+"/"+file));
            yMax = in.readLine().length();

            if (yMax == 0)
                throw new IOException();

            int line = 1;
            while (in.readLine() != null)
                line++;
            xMax = line;
            tableau = new ArrayList<NavNod>();

            in = new BufferedReader(new FileReader(directory.getAbsolutePath().toString()+"/"+file));
            //yMax = in.readLine().length();


            // reset the BufferedReader and success of reading the file

            line = 0;

            for (String x = in.readLine(); x != null; x = in.readLine()) {

                if (yMax != x.length()) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    throw new IOException();
                }


                for (int i = 0; i < xMax; i++) {
                    switch (x.charAt(i)) {
                        case '1':
                            // Empty


                            ArrayList<Successor> successors = new ArrayList<>();
                            NavNod n = new NavNod(line, i, "E",successors,this);
                            tableau.add(n);
                            break;
                        case '0':
                            // Wall

                            /*
                            ArrayList<Successor> successors2 = new ArrayList<>();
                            NavNod n2 = new NavNod(line, i, "W",successors2,this);
                            tableau.add(n2);
                            */

                            break;

                    }
                }

                line++;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("In NavGraph", "Buffered Reader is null");
            }
        }
    }

    public NavNod getNod(int x, int y) {
        for(NavNod n :tableau) {
            if((n.getX()==x)&&(n.getY()==y)) {
                return n;
            }
        }
        return null;
    }

    public int getxMax() {
        return xMax;
    }

    public int getyMax() {
        return yMax;
    }
}
