package spiel;

import amtsmaenner.*;
import karten.Karte;
import karten.KartenDeck;
import karten.Stadt;

import javax.swing.*;
import java.util.*;

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

    public Spiel(List<Spieler> spieler, ArrayList<JButton> auslage) {
        this.spieler = spieler;
        trageWegeEin();
        tauschen(auslage);

    }

    private void trageWegeEin() {
        for (Stadt stadt : Stadt.values()) {
            wege.put(stadt, ermittleNachbarn(stadt));
        }
    }


    @Override
    public void tauschen(ArrayList<JButton> auslage) {
        kartenAblegen();
        kartenNeuLegen();
        verknüpfeKartenMitAuslage(auslage);
    }

    private void verknüpfeKartenMitAuslage(ArrayList<JButton> auslage) {
        int index = 0;
        JButton button;
        for (Karte karte : this.auslageKarten) {
            button = auslage.get(index);
            button.setBackground(karte.getStadt().getLand().getFarbe());
            button.setText(karte.getStadt().toString());
            button.setName(karte.getStadt().toString());
            index++;

        }
        button = null;
    }

    private void kartenNeuLegen() {
        for (int i = 0; i < 6; i++) {
            this.auslageKarten.add(kartenDeck.pop());
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
        //TODO
    }

    @Override
    public Karte ziehen() {
        //TODO
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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


}
