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
    private boolean gezogen = false;
    private boolean gelegt = false;
    private boolean ersterZug = true;
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

    public LinkedList<Karte> getHand() {
        return this.hand;
    }

    public String getName() {
        return name;
    }

    public boolean getErsterZug(){
       return this.ersterZug;
    }

    public Farbe getFarbe() {
        return farbe;
    }

    public int getPunkte() {
        return punkte;
    }

    public boolean getGelegt(){
        return this.gelegt;
    }

    public boolean getGezogen(){
        return this.gezogen;
    }

    public void setGezogen(boolean gezogen){
        this.gezogen = gezogen;
    }

    public String toString() {
        return this.name + " - " + this.farbe.name();
    }
}
