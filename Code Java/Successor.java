

import java.util.ArrayList;

/**
 * Created by pierr on 05/03/2018.
 */

public class Successor {
    private int x;
    private int y;
    private double p;

    public Successor(int x, int y, double p) {
        this.x = x;
        this.y = y;
        this.p = p;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getP() {
        return p;
    }

    public NavNod getNod(NavGraph g)
    {
        ArrayList<NavNod> tableau = g.getTableau();
        NavNod a = tableau.get(0);
        for(NavNod n : tableau){
            if(this.x == n.getX() && this.y == n.getY()){
                a = n;
            }
        }
        return a;
    }
}
