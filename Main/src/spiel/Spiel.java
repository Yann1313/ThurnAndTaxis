package spiel;

import amtsmaenner.*;
import karten.Karte;
import karten.KartenDeck;
import karten.Land;
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
    private Stack<Karte> ablageStapel = new Stack<Karte>();
    private int aktuellerSpielerIndex;

    public int getLengthOfLastRoute() {
        return lengthOfLastRoute;
    }

    public void setLengthOfLastRoute(int lengthOfLastRoute) {
        this.lengthOfLastRoute = lengthOfLastRoute;
    }

    private int lengthOfLastRoute = 0;
    private Map<String, Bonus> boni = new HashMap<String, Bonus>();


    public Spiel(List<Spieler> spieler) {
        this.spieler = spieler;
        fügeBoniEin();
        trageWegeEin();
        tauschen();
        aktuellerSpielerIndex = 0;
    }

    private void fügeBoniEin() {
        this.boni.put("Baden", new Bonus(3));
        this.boni.put("Baiern", new Bonus(5));
        this.boni.put("BöhmSalz", new Bonus(4));
        this.boni.put("TyrolSchweiz", new Bonus(3));
        this.boni.put("WürttHohen", new Bonus(3));
        this.boni.put("5-Länge", new Bonus(2));
        this.boni.put("6-Länge", new Bonus(3));
        this.boni.put("7-Länge", new Bonus(4));
        this.boni.put("AlleLänder", new Bonus(6));
        this.boni.put("Spielende", new Bonus(1));
    }

    public Stack<Karte> getAblageStapel() {
        return ablageStapel;
    }

    public void setAblageStapel(Stack<Karte> ablageStapel) {
        this.ablageStapel = ablageStapel;
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


    public List<Spieler> getSpieler() {
        return this.spieler;
    }


    public Spieler ermittleGewinner() {
        return null; //TODO
    }

    private void trageWegeEin() {
        for (Stadt stadt : Stadt.values()) {
            wege.put(stadt, ermittleNachbarn(stadt));
        }
    }

    @Override
    public void tauschen() {
        for (Karte karte : this.auslageKarten) {
        }
        this.spieler.get(this.getAktuellerSpielerIndex()).setAmtmann(true);
        kartenAblegen(this.auslageKarten);
        this.auslageKarten.clear();
        kartenNeuLegen();
    }

    public void karteAuspielen(Karte karte) {
        Spieler aktuellerSpieler = this.getAktuellerSpieler();
        ArrayList<Karte> spielerAuslage = aktuellerSpieler.getAuslage();
        if (spielerAuslage.size() < 1) {
            spielerAuslage.add(karte);
            aktuellerSpieler.entferneHandKarte(karte);
        } else {
            boolean check = prüfeAuspielen(karte);
            if (!check) {
                kartenAblegen(spielerAuslage);
                spielerAuslage.clear();
                spielerAuslage.add(karte);
            }
        }
        aktuellerSpieler.setGelegt(aktuellerSpieler.getGelegtCount() - 1);
    }

    private boolean prüfeAuspielen(Karte karte) {
        Spieler aktuellerSpieler = this.getAktuellerSpieler();
        Karte letzteAuslageKarte = aktuellerSpieler.getAuslage().get(aktuellerSpieler.getAuslage().size() - 1);
        Stadt letzteStadt = Stadt.valueOf(letzteAuslageKarte.getStadt().toString());
        Stadt neueStadt = Stadt.valueOf(karte.getStadt().toString());
        if (wege.get(letzteStadt).contains(neueStadt)) {
            aktuellerSpieler.getAuslage().add(karte);
            return true;
        } else {
            return false;
        }
    }

    private void kartenNeuLegen() {
        if (this.kartenDeck.size() < 8) {
            fülleDeckAuf();
        }
        for (int i = 0; i < 6; i++) {
            this.auslageKarten.add(this.kartenDeck.pop());
        }
    }

    private void kartenAblegen(List<Karte> karten) {
        for (Karte karte : karten) {
            this.ablageStapel.add(karte);
        }
    }

    @Override
    public void weitereKarteAuspielen() {
        this.getAktuellerSpieler().setGelegt(this.getAktuellerSpieler().getGelegtCount() + 1);
    }

    @Override
    public void neuZiehen() {
        this.getAktuellerSpieler().setGezogenCount(this.getAktuellerSpieler().getGezogenCount() + 1);
    }

    @Override
    public Karte ziehen() {
        Spieler aktuellerSpieler = spieler.get(this.getAktuellerSpielerIndex());
        if (this.kartenDeck.size() < 2) {
            fülleDeckAuf();
        }
        Karte karte = kartenDeck.pop();
        aktuellerSpieler.getHand().add(karte);
        aktuellerSpieler.setGezogenCount(aktuellerSpieler.getGezogenCount() - 1);
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
    public int streckeWeten(String enabledButton, Land auswahl) {
        if (enabledButton.equals("Im Land setzen")) {
            this.setzteHaeuserImLand(auswahl);
        } else {
            this.setzteHaeuserProLand();
        }
        ermittleBoni();
        return 0;
    }

    private void ermittleBoni() {
        ermittleLänderBonus();
        ermittleStreckenBonus();
    }

    private void ermittleStreckenBonus() {
        Spieler aktuellerSpieler = this.getAktuellerSpieler();
        switch (this.getLengthOfLastRoute()) {
            case 5:
                aktuellerSpieler.setPunkte(boni.get("5-Länge").pop());
                break;
            case 6:
                aktuellerSpieler.setPunkte(boni.get("6-Länge").pop());
                break;
            case 7:
                aktuellerSpieler.setPunkte(boni.get("7-Länge").pop());
                break;
        }
    }

    private void ermittleLänderBonus() {
        Spieler aktuellerSpieler = this.getAktuellerSpieler();
        Map<Integer, Karte> häuser = aktuellerSpieler.getHäuser();
        for (Map.Entry<String, Bonus> entry : boni.entrySet()) {
            if (entry.equals("Baden")) {
                if (prüfeGleichheit(häuser, Land.BADEN)) {
                    aktuellerSpieler.setPunkte(entry.getValue().pop());
                }
            }
            if (entry.equals("Baiern")) {
                if (prüfeGleichheit(häuser, Land.BAIERN)) {
                    aktuellerSpieler.setPunkte(entry.getValue().pop());
                }
            }
            if (entry.equals("BöhmSalz")) {
                if (prüfeGleichheit(häuser, Land.BÖHMEN) && prüfeGleichheit(häuser, Land.SALZBURG)) {
                    aktuellerSpieler.setPunkte(entry.getValue().pop());
                }
            }
            if (entry.equals("TyrolSchweiz")) {
                if (prüfeGleichheit(häuser, Land.TYROL) && prüfeGleichheit(häuser, Land.SCHWEIZ)) {
                    aktuellerSpieler.setPunkte(entry.getValue().pop());
                }
            }
            if (entry.equals("WürttHohen")) {
                if (prüfeGleichheit(häuser, Land.WÜRTTEMBERG) && prüfeGleichheit(häuser, Land.HOHENZOLLERN)) {
                    aktuellerSpieler.setPunkte(entry.getValue().pop());
                }
            }
            if (entry.equals("AlleLänder")) {
                if (alleLänder(häuser)) {
                    aktuellerSpieler.setPunkte(entry.getValue().pop());
                }
            }
        }
    }

    private boolean prüfeGleichheit(Map<Integer, Karte> häuser, Land land) {
        return häuser.values().containsAll(Stadt.getAllCitysOfLand(land));
    }

    private boolean alleLänder(Map<Integer, Karte> häuser) {
        boolean flag = false;
        for (Land land : Land.values()) {
            flag = prüfeGleichheit(häuser, land);
        }
        return flag;
    }

    @Override
    public void spielzugBeenden() {
        if (this.getAktuellerSpieler().isErsterZug()) {
            this.getAktuellerSpieler().setErsterZug(false);
        }
        this.nächsterSpieler();
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
        if (aktuellerSpieler.getGezogenCount() > 0) {
            if (actionCommand.equals("Kartenstapel")) {
                karte = this.ziehen();

            } else if (actionCommand.contains("Auslage_")) {
                boolean added = false;
                for (Karte tmp : this.auslageKarten) {
                    if (tmp.getStadt().toString().equals(actionCommand.substring(8)) && !added) {
                        karte = tmp;
                        aktuellerSpieler.getHand().add(tmp);
                        added = true;
                        aktuellerSpieler.setGezogenCount(aktuellerSpieler.getGezogenCount() - 1);
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

    public LinkedList<Land> länderWertung() {
        LinkedList<Land> länder = new LinkedList<Land>();
        for (Karte karte : this.getAktuellerSpieler().getAuslage()) {
            if (!länder.contains(karte.getStadt().getLand()))
                System.out.println("Ich sollte eigentlich einzigartig sein: " + karte.getStadt().getLand());
            länder.add(karte.getStadt().getLand());
        }
        return länder;
    }

    public Karte getStadtFromLand(Land land) {
        Karte tmp = null;
        Spieler as = this.getAktuellerSpieler();
        for (Karte karte : as.getAuslage()) {
            if (karte.getStadt().getLand().equals(land) &&
                    !(as.getHäuser().values().contains(karte.getStadt()))) {
                tmp = karte;
            }
        }
        return tmp;
    }

    public void setzteHaeuserImLand(Land land) {
        LinkedList<Karte> städte = new LinkedList<Karte>();
        Collections.addAll(städte, getCitysByRouteofLand(land));
        this.setLengthOfLastRoute(städte.size());
        this.getAktuellerSpieler().setHaus(städte);
    }

    public void setzteHaeuserProLand() {
        LinkedList<Land> länder = this.länderWertung();
        LinkedList<Karte> städte = new LinkedList<Karte>();
        Collections.addAll(städte, getOneOfEachLand(länder));
        this.setLengthOfLastRoute(städte.size());
        this.getAktuellerSpieler().setHaus(städte);
    }

    private Karte[] getCitysByRouteofLand(Land land) {
        int groesse = 0;
        for (Karte karte : this.getAktuellerSpieler().getAuslage()) {
            if (karte.getStadt().getLand().equals(land)) {
                groesse++;
            }
        }
        int counter = 0;
        Karte[] array = new Karte[groesse];
        for (Karte karte : this.getAktuellerSpieler().getAuslage()) {
            if (karte.getStadt().getLand().equals(land)) {
                array[counter] = karte;
                counter++;
            }
        }
        return array;
    }

    private Karte[] getOneOfEachLand(LinkedList<Land> länder) {
        Karte[] array = new Karte[länder.size()];
        int counter = 0;
        for (Land land : länder) {
            array[counter] = this.getStadtFromLand(land);
            counter++;
        }
        return array;
    }
}
