package fr.reboullettelecom_paristech.paul.telecommapitech;
import java.util.ArrayList;
/**
 * Created by pierr on 05/03/2018.
 */

public class NavNod {
    private int x;
    private int y;
    private String category;
    private ArrayList<Successor> successors;
    private NavGraph graph;

    public NavNod(int x, int y, String category, ArrayList<Successor> successors,NavGraph graph) {
        this.x = x;
        this.y = y;
        this.category = category;
        this.successors = successors;
        this.graph = graph;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<Successor> getSuccessors() {
        return successors;
    }

    public void initialSuccesors() {
        // We need to verify 8 neighbours
        if (this!=null) {
            if (x > 0 && graph.getNod(x - 1, y) != null)
                successors.add(new Successor(x-1,y,1));

            if (x < graph.getxMax() - 1 && graph.getNod(x + 1, y) != null)
                successors.add(new Successor(x+1,y,1));

            if (y > 0 && graph.getNod(x, y - 1) != null)
                successors.add(new Successor(x,y-1,1));

            if (y < graph.getyMax() - 1 && graph.getNod(x, y + 1) != null)
                successors.add(new Successor(x,y+1,1));

            if (x>0 && y>0 && graph.getNod(x - 1, y-1)!=null)
                successors.add(new Successor(x-1,y-1,1.4));

            if (x< graph.getxMax() - 1 && y>0 && graph.getNod(x + 1, y-1)!=null)
                successors.add(new Successor(x+1,y-1,1.4));

            if (x< graph.getxMax() - 1 && y< graph.getyMax() - 1 && graph.getNod(x + 1, y+1)!=null)
                successors.add(new Successor(x+1,y+1,1.4));

            if (x>0 && y< graph.getyMax() - 1 && graph.getNod(x - 1, y+1)!=null)
                successors.add(new Successor(x-1,y+1,1.4));
        }

    }

    public boolean canGo() {
        if (category == "E") {
            if((graph.getyMax()>y)&&(graph.getxMax()>x)&&(y>0)&&(y>0)){
                return true;
            }
        }
        return false;
    }




}
