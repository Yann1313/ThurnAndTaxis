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
        createDeck();
    }

    /**
     * Methode, welche ein Deck mit 52 Karten erstellt.
     */
    private void createDeck() {
        //TODO
    }

    private void mischen() {
        Collections.shuffle(this);
    }
}
