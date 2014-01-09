package test;

import org.junit.Test;
import spiel.Farbe;
import spiel.Spiel;
import spiel.Spieler;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Klasse, welche //TODO: Beschreibung der Klasse
 *
 * @author TPE_UIB_01
 */
public class ThurnAndTaxisTest {


    @Test
    public void testeGewinnernachPunkte() {
        ArrayList<Spieler> spielerListe = new ArrayList<Spieler>();
        spielerListe.add(new Spieler("Hallo", Farbe.BLAU));
        spielerListe.add(new Spieler("Hallo3", Farbe.SCHWARZ));
        spielerListe.add(new Spieler("Hallo4", Farbe.WEIS));
        Spiel spiel = new Spiel(spielerListe, spielerListe.get(0));
        int count = 3;
        for (Spieler spieler : spielerListe) {
            spieler.setPunkte(count);
            count++;
        }
        Map<Spieler, Integer> gewinner = spiel.ermittleGewinner();
        assertEquals(-15, gewinner.entrySet().iterator().next().getValue().intValue());

    }

}
