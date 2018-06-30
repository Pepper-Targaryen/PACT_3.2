
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by pierr on 05/03/2018.
 */

public class NavGraph {
    private ArrayList<NavNod> tableau;
    int xMax;
    int yMax;

    public NavGraph() {

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

    public final void initFromSkeleton(String fileName) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(new File(fileName)));
            yMax = in.readLine().length();

            if (yMax == 0)
                throw new IOException();

            int line = 1;
            while (in.readLine() != null)
                line++;
            xMax = line;
            tableau = new ArrayList<NavNod>();

            in = new BufferedReader(new FileReader(new File(fileName)));
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
                            ArrayList<Successor> successors = new ArrayList<Successor>();
                            NavNod n = new NavNod(line, i, "E",successors,this);
                            break;
                        case '0':
                            // Wall
                            ArrayList<Successor> successors2 = new ArrayList<Successor>();
                            tableau.add(new NavNod(line, i, "W",successors2,this));
                            break;
                    }
                }
                line++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    

    public NavNod getNod(int x, int y) {
        return tableau.get((yMax-1)*x+y);
    }

    public int getxMax() {
        return xMax;
    }

    public int getyMax() {
        return yMax;
    }
}
