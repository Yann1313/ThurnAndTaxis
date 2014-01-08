package spiel;

import karten.*;

import java.util.ArrayList;
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
    private boolean amtmann = false;
    private int gezogenCount = 1;
    private int gelegtCount = 1;
    private boolean ersterZug = true;
    private boolean zugBeendet = false;
    private Map<Integer, Karte> häuser = new HashMap<Integer, Karte>();
    private LinkedList<Karte> hand;
    private ArrayList<Karte> auslage;

    public Spieler(String name, Farbe farbe) {
        this.name = name;
        this.farbe = farbe;
        this.punkte = 0;
        this.hand = new LinkedList<Karte>();
        this.auslage = new ArrayList<Karte>();
        initialisiereHäuser();
    }

    public Map<Integer, Karte> getHäuser() {
        return häuser;
    }

    public void setHaus(LinkedList<Karte> karten) {
        for (Karte karte : karten) {
            boolean set = false;
            for (Map.Entry<Integer, Karte> entry : häuser.entrySet()) {
                if (entry.getValue() == null && !set) {
                    entry.setValue(karte);
                    set = true;
                }
            }
        }
    }

    public int getHäuserAnzahl() {
        int count = 0;
        for (Map.Entry<Integer, Karte> entry : häuser.entrySet()) {
            if (entry.getValue() == null) {
                count++;
            }
        }
        return count;
    }

    public ArrayList<Karte> getAuslage() {
        return auslage;
    }

    public void setAuslage(ArrayList<Karte> auslage) {
        this.auslage = auslage;
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

    public boolean isErsterZug() {
        return this.ersterZug;
    }

    public Farbe getFarbe() {
        return farbe;
    }

    public int getPunkte() {
        return punkte;
    }

    public int getGelegtCount() {
        return this.gelegtCount;
    }

    public void setGelegt(int gelegtCount) {
        this.gelegtCount = gelegtCount;
    }

    public int getGezogenCount() {
        return this.gezogenCount;
    }

    public void setGezogenCount(int gezogenCount) {
        this.gezogenCount = gezogenCount;
    }

    public String toString() {
        return this.name + " - " + this.farbe.name();
    }

    public void setAmtmann(boolean amtmann) {
        this.amtmann = amtmann;
    }

    public boolean getAmtmann() {
        return amtmann;
    }

    public boolean isZugBeendet() {
        return zugBeendet;
    }

    public void setZugBeendet(boolean zugBeendet) {
        this.zugBeendet = zugBeendet;
    }

    public void resetteFelder() {
        setGezogenCount(1);
        setAmtmann(false);
        setGelegt(1);
        setZugBeendet(false);
        if (this.ersterZug) {
            setGezogenCount(2);
            setAmtmann(true);
        }
    }

    public void entferneHandKarte(Karte karte) {
        this.hand.remove(karte);
    }

    public void setErsterZug(boolean ersterZug) {
        this.ersterZug = ersterZug;
    }

    public void setPunkte(int punkte) {
        this.punkte += punkte;
    }
}
