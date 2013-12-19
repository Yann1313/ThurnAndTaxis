
import amtsmaenner.*;
import karten.Karte;
import karten.KartenDeck;
import spiel.*;

import javax.swing.*;
import java.awt.*;

/**
 * Klasse, welche //TODO: Beschreibung der Klasse
 *
 * @author TPE_UIB_01
 */
public class Main implements Postillion, Amtsmann, Postmeister, Spielzuege {
    private JButton karte1Button;
    private JButton karte2Button;
    private JButton karte3Button;
    private JButton karte4Button;
    private JButton karte5Button;
    private JButton karte6Button;
    private JPanel Spiel;
    private JButton postmeisterButton;
    private JButton amtmannButton;
    private JButton postillionButton;
    private JButton kartenstapelButton;
    private JButton ablagestapelButton;
    private JTextPane textPane1;
    private JPanel Handkarten;
    private JPanel Spielfeld;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().Spiel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        Handkarten = new JPanel();
        Handkarten.setLayout(null);

        Spielfeld = new JPanel();
        Spielfeld.setLayout(null);

        JButton mannheimButton = new JButton("Mannheim");
        JButton carlsruheButton = new JButton("Carlsruhe");
        JButton freiburgButton = new JButton("Freiburg");

        JButton stuttgartButton = new JButton("Stuttgart");
        JButton ulmButton = new JButton("Ulm");

        JButton sigmaringenButton = new JButton("Sigmaringen");

        JButton baselButton = new JButton("Basel");
        JButton zurichButton = new JButton("Zurich");

        JButton kemptenButton = new JButton("Kempten");
        JButton augsburg = new JButton("Augsburg");
        JButton ingolstadtButton = new JButton("Ingolstadt");
        JButton nuernbergButton = new JButton("Nürnberg");
        JButton wuerzburgButton = new JButton("Würzburg");
        JButton regensburgButton = new JButton("Regensburg");
        JButton passauButton = new JButton("Passau");
        JButton muenchenButton = new JButton("München");

        JButton pilsenButton = new JButton("Pilsen");
        JButton budweisButton = new JButton("Budweis");

        JButton lodzButton = new JButton("Lodz");

        JButton linzButton = new JButton("Linz");
        JButton salzburgButton = new JButton("Salzburg");

        JButton innsbruck = new JButton("Innsbruck");

        positionCity(mannheimButton, 7, 4);
        positionCity(carlsruheButton, 3, 20);
        positionCity(freiburgButton,2 , 30);
        positionCity(baselButton, 2, 40);
        positionCity(zurichButton,7, 45);
        positionCity(stuttgartButton, 20, 15);
        positionCity(ulmButton, 24, 25);


    }

    private void positionCity(JButton button, int x, int y) {
        Spielfeld.add(button);

        //Insets insets = mapPanel.getInsets();
        Dimension size = button.getPreferredSize();

        x = map(x, 0, 100, 0, 1000);
        y = map(y, 0, 50, 0, 500);

        button.setBounds(x, y, size.width, size.height);
    }

    private int map(int x, int in_min, int in_max, int out_min, int out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    @Override
    public void tauschen() {
        //TODO
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
}
