package spiel;

import java.awt.*;

/**
 * Klasse, welche //TODO: Beschreibung der Klasse
 *
 * @author TPE_UIB_01
 */
public enum Farbe {

    ROT(new Color(255, 0, 0)),
    GRUEN(new Color(0, 120, 0)),
    BLAU(new Color(0, 0, 120)),
    GELB(new Color(255,200,0)),
    LILA(new Color(200,0,255)),
    SCHWARZ(new Color(0,0,0)),
    WEIS(new Color(255,255,255));


    private Color code;

    public Color getCode() {
        return code;
    }

    public void setCode(Color code) {
        this.code = code;
    }

    private Farbe(Color code) {
        this.code = code;
    }



}
