
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
    private String nom; //attribut valant null ou B561 ou ...

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
        // We need to verify 4 neighbours
        if (this.canGo()) {
            if (x > 0 && graph.getNod(x - 1, y).canGo())
                successors.add(new Successor(x-1,y,1));

            if (x < graph.getxMax() - 1 && graph.getNod(x + 1, y).canGo())
                successors.add(new Successor(x+1,y,1));

            if (y > 0 && graph.getNod(x, y - 1).canGo())
                successors.add(new Successor(x,y-1,1));

            if (y < graph.getyMax() - 1 && graph.getNod(x, y + 1).canGo())
                successors.add(new Successor(x,y+1,1));

            if (x>0 && y>0 && graph.getNod(x - 1, y-1).canGo())
                successors.add(new Successor(x-1,y-1,1.4));

            if (x< graph.getxMax() - 1 && y>0 && graph.getNod(x + 1, y-1).canGo())
                successors.add(new Successor(x+1,y-1,1.4));

            if (x< graph.getxMax() - 1 && y< graph.getyMax() - 1 && graph.getNod(x + 1, y-1).canGo())
                successors.add(new Successor(x+1,y+1,1.4));

            if (x>0 && y< graph.getyMax() - 1 && graph.getNod(x - 1, y+1).canGo())
                successors.add(new Successor(x-1,y+1,1.4));
        }

    }

    public boolean canGo() {
        if (category == "E")
            return true;

        return false;
    }




}
