

import java.util.Hashtable;

/**
 * Created by pierr on 05/03/2018.
 */


public class Pi {
    private final Hashtable<NavNod,Double> table;

    public Pi() {
        // TODO Auto-generated constructor stub
        table =  new Hashtable<NavNod,Double>();
    }

    public void setPi(NavNod n, double p) {
        // TODO Auto-generated method stub
        table.put(n, new Double(p));
    }

    public double getPi(NavNod n) {
        // TODO Auto-generated method stub
        return table.get(n).doubleValue();
    }
}
