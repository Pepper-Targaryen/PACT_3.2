package fr.reboullettelecom_paristech.paul.telecommapitech;
import android.util.Log;

import java.util.ArrayList;
/**
 * Created by pierr on 05/03/2018.
 */



public class Dijkstra {

    private static boolean isInGraph(int x, int y, NavGraph g) {
        return ((0<x)&&(x<g.getxMax())&&(0<y)&&(y<g.getyMax()));
    }

    public static NavNod projection(int x, int y, NavGraph g) {
        int n = 0;
        int newX = x;
        int newY = y;
        boolean found = (g.getNod(x,y) != null);
        while(!found) {
            if(isInGraph(x+n,y,g)&&(g.getNod(x+n,y)!=null)) {
                newX = x+n;
                newY = y;
                found = true;
            } else if(isInGraph(x-n,y,g)&&(g.getNod(x-n,y)!=null)){
                newX = x-n;
                newY = y;
                found = true;
            } else if(isInGraph(x,y+n,g)&&(g.getNod(x,y+n)!=null)) {
                newX = x;
                newY = y + n;
                found = true;
            } else if(isInGraph(x,y-n,g)&&(g.getNod(x,y-n)!=null)) {
                newX = x;
                newY = y-n;
                found = true;
            } else {
                n++;
            }
        }
        return g.getNod(newX,newY);

    }

    public static Previous dijkstra(NavGraph g, int x,int y) {
        for (NavNod n : g.getTableau()){
            n.initialSuccesors();
        }

        Log.d("In Dijkstra", "Initial Successors created");
        NavNod newR = projection(x,y,g);
        Log.d("In dijkstra", "Projection on squeleton : "+newR.getX()+";"+newR.getY());
        return dijkstra(g, newR, new ASet(), new Pi(), new Previous());

    }


    private static Previous dijkstra(NavGraph g, NavNod r, ASet a, Pi pi, Previous previous) {
        Log.d("In Dijkstra", "Started dijkstra execution");
        ArrayList<NavNod> nodsList = g.getTableau();
        int n = nodsList.size();
        a.addNod(r);
        NavNod pivot = r;

        for (NavNod x : nodsList){
            pi.setPi(x,Double.MAX_VALUE);
        }

        pi.setPi(r,0);
        double piPivot = 0;

        for (int i = 1; i<n; i++){
            ArrayList<Successor> successorsOfPivot = pivot.getSuccessors();
            for (Successor y : successorsOfPivot) {
                NavNod yNod = y.getNod(g);
                if(! a.contains(yNod)){
                    double newPi = piPivot + g.getP(pivot, yNod);
                    if(newPi < pi.getPi(yNod)){
                        pi.setPi(yNod,newPi);
                        previous.setPrevious(yNod,pivot);
                    }
                }
            }
            NavNod nextPivot = null;
            double piNextPivot = Double.MAX_VALUE ;

            for (NavNod y : nodsList){
                if(! a.contains(y)){
                    double piY = pi.getPi(y);
                    if (piY < piNextPivot){
                        nextPivot = y;
                        piNextPivot = piY;
                    }
                }
            }

            if (nextPivot == null){
                Log.d("In Dijkstra", "Finished dijkstra execution, pivot is null");
                return previous;
            }

            pivot = nextPivot;
            piPivot = piNextPivot;
            a.addNod(pivot);
        }
        Log.d("In Dijkstra", "Finished dijkstra execution");
        return previous;
    }
}
