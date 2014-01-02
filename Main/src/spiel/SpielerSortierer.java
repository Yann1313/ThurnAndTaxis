package spiel;

import java.util.Comparator;
import java.util.Map;

/**
 * Klasse, welche //TODO: Beschreibung der Klasse
 *
 * @author TPE_UIB_01
 */
public class SpielerSortierer implements Comparator<Spieler> {

    Map<Spieler, Integer> map;

    public SpielerSortierer(Map<Spieler, Integer> map) {
        this.map = map;
    }

    @Override
    public int compare(Spieler o1, Spieler o2) {
        if (this.map.get(o1) >= this.map.get(o2)) {
            return -1;
        } else {
            return 1;
        }
    }
}
