package karten;

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
    STUTTGART(Land.HOHENZOLLERN),
    CARLSRUHE(Land.BADEN),
    BASEL(Land.SCHWEIZ),
    ZÜRICH(Land.SCHWEIZ),
    INNSBRUCK(Land.TYROL),
    SIGMARINGEN(Land.WÜRTTEMBERG),
    ULM(Land.HOHENZOLLERN),
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


}
