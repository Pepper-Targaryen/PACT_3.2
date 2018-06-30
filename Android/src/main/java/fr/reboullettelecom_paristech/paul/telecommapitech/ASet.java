package fr.reboullettelecom_paristech.paul.telecommapitech;
import java.util.HashSet;
/**
 * Created by pierr on 05/03/2018.
 */

public class ASet {
    private final HashSet<NavNod> a;

    public ASet() {
        a = new HashSet<NavNod>();
    }

    public void addNod(NavNod n) {
        a.add(n);
    }

    public boolean contains(NavNod n) {
        return a.contains(n) ;
    }
}