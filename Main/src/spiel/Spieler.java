package spiel;

import karten.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Klasse, welche //TODO: Beschreibung der Klasse
 *
 * @author TPE_UIB_01
 */
public class Spieler {

    private String name;
    private Farbe farbe;
    private int punkte;
    private Map<Integer, Karte> häuser = new HashMap<Integer, Karte>();
    private LinkedList<Karte> hand;

    public Spieler(String name, Farbe farbe) {
        this.name = name;
        this.farbe = farbe;
        this.punkte = 0;
        this.hand = new LinkedList<Karte>();
        initialisiereHäuser();
    }

    private void initialisiereHäuser() {
        for (int i = 1; i <= 20; i++) {
            this.häuser.put(i, null);
        }
    }

    public boolean häuserÜbrig() {
        return this.häuser.containsValue(null);
    }

    public String getName() {
        return name;
    }

    public Farbe getFarbe() {
        return farbe;
    }

    public int getPunkte() {
        return punkte;
    }

    public String toString() {
        return this.name + " - " + this.farbe.name();
    }
}
