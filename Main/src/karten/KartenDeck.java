package karten;

import java.util.Collections;
import java.util.Stack;

/**
 * Klasse, welche ein Kartendeck implementiert.
 *
 * @author TPE_UIB_01
 */
public class KartenDeck extends Stack<Karte> {
    /**
     * Konstruktor des Kartendecks.
     */
    public KartenDeck() {
        createDeck(2);
    }

    public KartenDeck(boolean unique) {
      createDeck(0);
    }


    private void createDeck(int kartenDuplexität) {
        for (Stadt stadt : Stadt.values()) {
            for (int i = 0; i <= kartenDuplexität; i++) {
                this.add(new Karte(stadt));
            }
        }
        mischen();
    }

    public void mischen() {
        Collections.shuffle(this);
    }
}
