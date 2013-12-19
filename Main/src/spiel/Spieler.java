package spiel;
import karten.*;
import java.util.LinkedList;

/**
 * Klasse, welche //TODO: Beschreibung der Klasse
 *
 * @author TPE_UIB_01
 */
public class Spieler {

    private String name;
    private String farbe;
    private int punkte;
    private LinkedList<Karte> hand;

    public Spieler(String name, String farbe) {
        this.name = name;
        this.farbe = farbe;
        this.punkte = 0;
        this.hand = new LinkedList<Karte>();
    }

    public String getName() {
        return name;
    }

    public String getFarbe() {
        return farbe;
    }

    public int getPunkte() {
        return punkte;
    }
}
