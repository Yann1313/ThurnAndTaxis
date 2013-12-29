package spiel;

import amtsmaenner.*;
import karten.Karte;
import karten.KartenDeck;
import karten.Stadt;

import java.util.*;
import java.util.List;

/**
 * Klasse, welche //TODO: Beschreibung der Klasse
 *
 * @author TPE_UIB_01
 */
public class Spiel implements Amtsmann, Postmeister, Postillion, Spielzuege {

    private List<Spieler> spieler;
    private KartenDeck kartenDeck = new KartenDeck();
    private Map<Stadt, ArrayList<Stadt>> wege = new HashMap<Stadt, ArrayList<Stadt>>();
    private List<Karte> auslageKarten = new LinkedList<Karte>();
    private List<Karte> ablageStapel = new LinkedList<Karte>();
    private int aktuellerSpielerIndex;
    private Spieler aktuellerSpieler;


    public Spiel(List<Spieler> spieler) {
        this.spieler = spieler;
        trageWegeEin();
        tauschen();
        aktuellerSpielerIndex = 0;
    }

    public int getAktuellerSpielerIndex() {
        return this.aktuellerSpielerIndex;
    }

    public List<Karte> getAuslageKarten() {
        return this.auslageKarten;
    }


    public Map<Stadt, ArrayList<Stadt>> getWege() {
        return this.wege;
    }

    private void führeZügeAus() {
        for (int i = 0; i < this.spieler.size(); ) {

        }
    }

    public List<Spieler> getSpieler() {
        return this.spieler;
    }

    private void führeSpielerZugAus(Spieler spieler, int i) {
        this.aktuellerSpielerIndex = i;
        Spieler aktuellerSpieler = this.spieler.get(this.aktuellerSpielerIndex);
        aktuellerSpieler.resetteFelder();
        System.out.println(aktuellerSpieler.hashCode());
        while (!aktuellerSpieler.isZugBeendet()) {
            System.out.println("ich bin schlaflos");
        }
    }


    public Spieler ermittleGewinner() {
        return null; //TODO
    }

    public boolean hatJemandGewonnen() {
        boolean gewonnen = false;
        for (Spieler spieler : this.spieler) {
            if (!spieler.häuserÜbrig()) {
                gewonnen = true;
            }
        }
        return gewonnen;
    }

    private void trageWegeEin() {
        for (Stadt stadt : Stadt.values()) {
            wege.put(stadt, ermittleNachbarn(stadt));
        }
    }


    @Override
    public void tauschen() {
        this.spieler.get(this.getAktuellerSpielerIndex()).setAmtmann(true);
        kartenAblegen();
        kartenNeuLegen();

    }

    private void kartenNeuLegen() {
        System.out.println(this.kartenDeck.size());
        if (this.kartenDeck.size() < 8) {
            fülleDeckAuf();
        }
        for (int i = 0; i < 6; i++) {
            this.auslageKarten.add(this.kartenDeck.pop());
        }
    }

    private void kartenAblegen() {
        for (Karte karte : this.auslageKarten) {
            this.ablageStapel.add(karte);
        }
        this.auslageKarten.clear();
    }

    @Override
    public void karteAuspielen() {
        //TODO
    }

    @Override
    public void neuZiehen() {

    }

    @Override
    public Karte ziehen() {
        Spieler aktuellerSpieler = spieler.get(this.getAktuellerSpielerIndex());
        if (this.kartenDeck.size() < 2) {
            fülleDeckAuf();
        }
        Karte karte = kartenDeck.pop();
        aktuellerSpieler.getHand().add(karte);
        aktuellerSpieler.setGezogen(true);
        return karte;
    }

    private void fülleDeckAuf() {
        Karte erste = kartenDeck.pop();
        for (Karte karte : this.ablageStapel) {
            this.kartenDeck.add(karte);
        }
        this.ablageStapel.clear();
        this.kartenDeck.mischen();
        this.kartenDeck.push(erste);
    }

    @Override
    public Karte auslegen() {
        //TODO
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int streckeWeten() {
        //TODO
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void spielzugBeenden() {
        this.nächsterSpieler();
        System.out.println("Index" + aktuellerSpielerIndex);
        this.getAktuellerSpieler().resetteFelder();
    }

    private void nächsterSpieler() {
        if (aktuellerSpielerIndex + 1 == spieler.size()) {
            aktuellerSpielerIndex = 0;
        } else {
            aktuellerSpielerIndex++;
        }
    }

    private ArrayList<Stadt> ermittleNachbarn(Stadt stadt) {

        ArrayList<Stadt> nachbarn = new ArrayList<Stadt>();

        switch (stadt) {
            case MANNHEIM:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.WÜRZBURG, Stadt.STUTTGART, Stadt.CARLSRUHE));
                break;
            case FREIBURG:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.CARLSRUHE, Stadt.SIGMARINGEN, Stadt.BASEL));
                break;
            case STUTTGART:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.CARLSRUHE, Stadt.MANNHEIM, Stadt.WÜRZBURG, Stadt.NÜRNBERG, Stadt.INGOLSTADT, Stadt.ULM, Stadt.SIGMARINGEN));
                break;
            case CARLSRUHE:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.MANNHEIM, Stadt.STUTTGART, Stadt.FREIBURG));
                break;
            case BASEL:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.FREIBURG, Stadt.ZÜRICH));
                break;
            case ZÜRICH:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.BASEL, Stadt.KEMPTEN, Stadt.FREIBURG, Stadt.SIGMARINGEN));
                break;
            case INNSBRUCK:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.KEMPTEN, Stadt.AUGSBURG, Stadt.MÜNCHEN, Stadt.SALZBURG));
                break;
            case SIGMARINGEN:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.STUTTGART, Stadt.FREIBURG, Stadt.ULM, Stadt.KEMPTEN, Stadt.ZÜRICH));
                break;
            case ULM:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.SIGMARINGEN, Stadt.KEMPTEN, Stadt.AUGSBURG, Stadt.INGOLSTADT, Stadt.STUTTGART));
                break;
            case BUDWEIS:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.LINZ, Stadt.PILSEN));
                break;
            case PILSEN:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.LODZ, Stadt.BUDWEIS, Stadt.NÜRNBERG, Stadt.REGENSBURG));
                break;
            case LINZ:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.BUDWEIS, Stadt.PASSAU, Stadt.SALZBURG));
                break;
            case SALZBURG:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.INNSBRUCK, Stadt.MÜNCHEN, Stadt.PASSAU, Stadt.LINZ));
                break;
            case AUGSBURG:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.ULM, Stadt.KEMPTEN, Stadt.INNSBRUCK, Stadt.MÜNCHEN, Stadt.INGOLSTADT));
                break;
            case INGOLSTADT:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.NÜRNBERG, Stadt.REGENSBURG, Stadt.MÜNCHEN, Stadt.AUGSBURG, Stadt.ULM, Stadt.STUTTGART));
                break;
            case KEMPTEN:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.ZÜRICH, Stadt.SIGMARINGEN, Stadt.ULM, Stadt.AUGSBURG, Stadt.INNSBRUCK));
                break;
            case MÜNCHEN:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.INNSBRUCK, Stadt.AUGSBURG, Stadt.INGOLSTADT, Stadt.PASSAU, Stadt.SALZBURG, Stadt.REGENSBURG));
                break;
            case NÜRNBERG:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.WÜRZBURG, Stadt.STUTTGART, Stadt.INGOLSTADT, Stadt.REGENSBURG, Stadt.PILSEN));
                break;
            case PASSAU:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.LINZ, Stadt.MÜNCHEN, Stadt.SALZBURG, Stadt.REGENSBURG));
                break;
            case REGENSBURG:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.NÜRNBERG, Stadt.INGOLSTADT, Stadt.MÜNCHEN, Stadt.PASSAU, Stadt.PILSEN));
                break;
            case WÜRZBURG:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.MANNHEIM, Stadt.STUTTGART, Stadt.NÜRNBERG));
                break;
            case LODZ:
                Collections.addAll(nachbarn, konvertiereZuArray(Stadt.PILSEN));
                break;
        }
        return nachbarn;
    }

    private Stadt[] konvertiereZuArray(Stadt... städte) {
        return städte;
    }


    public Karte karteZiehen(String actionCommand) {
        Karte karte = null;
        Spieler aktuellerSpieler = spieler.get(this.getAktuellerSpielerIndex());
        if (!aktuellerSpieler.getGezogen()) {
            if (actionCommand.equals("Kartenstapel")) {
                karte = this.ziehen();

            } else if (actionCommand.contains("Auslage_")) {
                boolean added = false;
                for (Karte tmp : this.auslageKarten) {
                    if (tmp.getStadt().toString().equals(actionCommand.substring(8)) && !added) {
                        karte = tmp;
                        aktuellerSpieler.getHand().add(tmp);
                        added = true;
                        aktuellerSpieler.setGezogen(true);
                    }
                }
                this.auslageKarten.remove(karte);
                neueKarteFürAuslage();
            }
        }
        return karte;
    }

    private void neueKarteFürAuslage() {
        if (this.kartenDeck.size() < 2) {
            fülleDeckAuf();
        }
        this.auslageKarten.add(kartenDeck.pop());
    }

    public Spieler getAktuellerSpieler() {
        return this.getSpieler().get(this.getAktuellerSpielerIndex());
    }
}
