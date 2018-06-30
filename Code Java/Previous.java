
import java.util.Hashtable;
import java.util.ArrayList;
/**
 * Created by pierr on 05/03/2018.
 */

public class Previous {
    private final Hashtable<NavNod,NavNod> table;

    public Previous() {
        // TODO Auto-generated constructor stub
        table = new Hashtable<NavNod,NavNod>();
    }

    public void setPrevious(NavNod n1, NavNod n2){
        table.put(n1,n2);
    }

    public NavNod getPrevious(NavNod n){
        return table.get(n);
    }

    public ArrayList<NavNod> getPath(NavNod d) {
        // TODO Auto-generated method stub
        ArrayList<NavNod> path = new ArrayList<NavNod>();

        while (d != null) {
            path.add(d);
            d = getPrevious(d);
        }

        return path;
    }



}
