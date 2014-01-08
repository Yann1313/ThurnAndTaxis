package karten;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasse, welche //TODO: Beschreibung der Klasse
 *
 * @author TPE_UIB_01
 */
public enum Stadt {
    MANNHEIM(Land.BADEN),
    FREIBURG(Land.BADEN),
    STUTTGART(Land.WÜRTTEMBERG),
    CARLSRUHE(Land.BADEN),
    BASEL(Land.SCHWEIZ),
    ZÜRICH(Land.SCHWEIZ),
    INNSBRUCK(Land.TYROL),
    SIGMARINGEN(Land.HOHENZOLLERN),
    ULM(Land.WÜRTTEMBERG),
    BUDWEIS(Land.BÖHMEN),
    PILSEN(Land.BÖHMEN),
    LINZ(Land.SALZBURG),
    SALZBURG(Land.SALZBURG),
    AUGSBURG(Land.BAIERN),
    INGOLSTADT(Land.BAIERN),
    KEMPTEN(Land.BAIERN),
    MÜNCHEN(Land.BAIERN),
    NÜRNBERG(Land.BAIERN),
    PASSAU(Land.BAIERN),
    REGENSBURG(Land.BAIERN),
    WÜRZBURG(Land.BAIERN),
    LODZ(Land.POLEN);


    private Land land;
    private List<Stadt> benachbarteOrte = new LinkedList<Stadt>();

    Stadt(Land land, Stadt... orte) {
        this.land = land;
        Collections.addAll(this.benachbarteOrte, orte);
    }

    public Land getLand() {
        return land;
    }


    public static Collection<Stadt> getAllCitysOfLand(Land land) {
        Collection<Stadt> städte = new LinkedList<Stadt>();
        KartenDeck deck = new KartenDeck(true);
        for (Karte karte : deck) {
            if (karte.getStadt().getLand().equals(land)) {
                städte.add(karte.getStadt());
            }
        }
        return städte;
    }
}
