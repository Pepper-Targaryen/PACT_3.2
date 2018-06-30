
import java.util.ArrayList;
/**
 * Created by pierr on 05/03/2018.
 */



public class Dijkstra {

    public static Previous dijkstra(String file, NavNod r) {
        NavGraph g = new NavGraph();
        g.initFromSkeleton(file);
        for (NavNod n : g.getTableau()){
            n.initialSuccesors();
        }
        return dijkstra(g, r, new ASet(), new Pi(), new Previous());
    }


    private static Previous dijkstra(NavGraph g, NavNod r, ASet a, Pi pi, Previous previous) {
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
                return previous;
            }

            pivot = nextPivot;
            piPivot = piNextPivot;
            a.addNod(pivot);
        }
        return previous;
    }
}
