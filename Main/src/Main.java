
import karten.Karte;
import karten.Stadt;
import spiel.*;
import swing.ImagePanel;
import swing.StadtLocator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * Klasse, welche //TODO: Beschreibung der Klasse
 *
 * @author TPE_UIB_01
 */
public class Main extends JFrame {
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
    private JPanel Spielfeld;
    private JPanel handkartenAreal;
    private JButton spielzugBeendenButton;
    private JTextArea consoleText;
    private JPanel auslegekarten;
    private ArrayList<Spieler> spielerListe;
    private Spiel spiel;

    public Main() {
        spielerListe = spielerErzeugen();
        spiel = spielVorbereitung();
        verknüpfeKartenMitAuslage(generiereAuslageKnöpfe());
        aktualisiereAblageStapel();
        beginneZug();
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Karte neueKarte = spiel.karteZiehen(e.getActionCommand());
                if (neueKarte != null) {
                    consoleText.append("Bewege deinen Hintern selbst dorthin." +
                            " Sie liegt direkt vor dir, greife zu! \nWas meinst du damit das ist ein Computer? \n" +
                            "Neue Karte ist " + neueKarte.getStadt() + "\n\n");
                    handkartenAreal.add(generiereNeuenHandKartenKnopf(neueKarte));
                    verknüpfeKartenMitAuslage(generiereAuslageKnöpfe());

                }
            }
        };
        karte2Button.addActionListener(listener);
        karte1Button.addActionListener(listener);
        karte4Button.addActionListener(listener);
        karte3Button.addActionListener(listener);
        karte6Button.addActionListener(listener);
        karte5Button.addActionListener(listener);

        kartenstapelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Karte neueKarte = spiel.karteZiehen(e.getActionCommand());
                if (neueKarte != null) {
                    consoleText.append("Eine Karte vom Kartenstapel ...so so und was bekomme" +
                            " ich dafür? \n" +
                            "Ja ich weiß, nichts! Deine neue Karte ist auf jedenfall " + neueKarte.getStadt() + "\n\n");
                    handkartenAreal.add(generiereNeuenHandKartenKnopf(neueKarte));
                    aktualisiereAblageStapel();
                    Spiel.revalidate();
                } else {
                    consoleText.append("Leider" +
                            " darfst du pro Runde nur eine Karte ziehen\n" +
                            "Ich kenne da jemanden der dir helfen könnte.\n\n");
                }
            }

        });
        ActionListener listener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosenAmtmann = e.getActionCommand();
                System.out.println("amtmann?: " + spiel.getAktuellerSpieler().getAmtmann());
                if (!spiel.getAktuellerSpieler().getAmtmann()) {
                    consoleText.append("Uuund hier kommt der  ");
                    if (chosenAmtmann.equals("Postillion")) {
                        spiel.weitereKarteAuspielen();
                        consoleText.append("Postillion\n");
                        consoleText.append("(Mhmm ja die Karten müssen wirklich dreckig sein\n" +
                                "Oder wieso willst du sie so schnell loswerden?)");
                    } else if (chosenAmtmann.equals("Postmeister")) {
                        spiel.neuZiehen();
                        consoleText.append("Postmeister\n");
                        consoleText.append("(Bist du dir sicher das du diesen dreckigen Stapel weiterhin\n" +
                                "anfassen möchtest? - OK gut!)");
                    } else if (chosenAmtmann.equals("Amtmann")) {
                        consoleText.append("Amtmann\n");
                        consoleText.append("(Harte Arbeit hier! Wo bleibt mein Lohn?\n" +
                                "Auslage getauscht!)");
                        spiel.tauschen();
                        verknüpfeKartenMitAuslage(generiereAuslageKnöpfe());
                        Spiel.revalidate();
                    }
                    spiel.getAktuellerSpieler().setAmtmann(true);
                } else {
                    consoleText.append("Noch viel zu lernen du hast, mein junger Padawan\n");
                    consoleText.append("(Schreib es dir endlich hinter die Ohren,\n das geht nur einmal pro Runde!)");

                }
                consoleText.append("\n\n");
                aktualisiereAblageStapel();
            }
        };
        amtmannButton.addActionListener(listener1);
        postmeisterButton.addActionListener(listener1);
        postillionButton.addActionListener(listener1);
        spielzugBeendenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Davor:" + spiel.getAktuellerSpieler());
                int answer = JOptionPane.showConfirmDialog(new JPanel(), "Möchtest du deinen Zug wirklich abgeben?", "Zug beenden", 0);
                switch (answer) {
                    case 0:
                        consoleText.append("OK OK ich mach ja schon immer mit der Ruhe ;)!\n\n" +
                                "");
                        spiel.spielzugBeenden();
                        initialisiereSpieler();
                        aktualisiereAblageStapel();
                        Spiel.revalidate();
                        Spiel.repaint();
                        break;
                    default:
                        consoleText.append("Ich weis von nichts und habe dich auch nicht gesehen. \n" +
                                "Lass mir dass nächste mal aber bitte ein paar Groschen da OK?\n\n");
                        System.out.println("Danach " + spiel.getAktuellerSpieler());
                        break;
                }
            }
        });
    }

    private void beginneZug() {
        spiel.getAktuellerSpieler().resetteFelder();
        consoleText.append("------------------------------\nZug beginnt.");
        consoleText.append("Der Spieler sollte nun: \nEine Karte ausspielen \nEine Karte ziehen \n" +
                "Oder einen Postmeister in Anspruch nehmen\n\n");
    }

    private void aktualisiereAblageStapel() {
        if (spiel.getAblageStapel().size() > 0) {
            Karte ersteKarte = spiel.getAblageStapel().peek();
            ablagestapelButton.setText(ersteKarte.getStadt().toString());
            ablagestapelButton.setBackground(ersteKarte.getStadt().getLand().getFarbe());
        } else {
            ablagestapelButton.setText("Leer");
            ablagestapelButton.setBackground(new Color(163, 163, 163));
        }
    }

    private void initialisiereSpieler() {
        löscheAlteHandKnöpfe();
        Spiel.revalidate();
        for (Karte karte : spiel.getAktuellerSpieler().getHand()) {
            handkartenAreal.add(generiereNeuenHandKartenKnopf(karte));
        }
        verknüpfeSpielerAuslageMitSpiel();
        Spiel.revalidate();
    }

    private void löscheAlteHandKnöpfe() {
        for (Component comp : handkartenAreal.getComponents()) {
            handkartenAreal.remove(comp);
        }
    }

    private JButton generiereNeuenHandKartenKnopf(Karte neueKarte) {
        JButton handkarte = new JButton(neueKarte.getStadt().toString());
        handkarte.setBackground(neueKarte.getStadt().getLand().getFarbe());
        handkarte.setForeground(new Color(255, 255, 255));
        handkarte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                int answer = JOptionPane.showConfirmDialog(new JPanel(), "Möchtest du diese Karte wirklich auspielen?"
                        , "Karte auspielen", 0);
                switch (answer) {
                    case 0:
                        for (Karte karte : spiel.getAktuellerSpieler().getHand()) {
                            if (karte.getStadt().toString().equals(e.getActionCommand())) {
                                spiel.karteAuspielen(karte);
                                verknüpfeSpielerAuslageMitSpiel();
                                entferneHandKartenKnopf(karte);
                            }
                        }
                        break;
                }
            }

            private void entferneHandKartenKnopf(Karte karte) {
                Component buttonToRemove = null;
                for (Component comp : handkartenAreal.getComponents()) {
                    if (((JButton) comp).getText().equals(karte.getStadt().toString())) {
                        buttonToRemove = comp;
                    }
                }
                handkartenAreal.remove(buttonToRemove);
                for (Component comp : handkartenAreal.getComponents()) {
                    System.out.println(comp);
                }
                handkartenAreal.revalidate();
                handkartenAreal.repaint();
            }

        });
        return handkarte;
    }

    private void verknüpfeSpielerAuslageMitSpiel() {
        auslegekarten.removeAll();
        for (Karte karte : spiel.getAktuellerSpieler().getAuslage()) {
            auslegekarten.add(initialisiereStadtKnopf(karte));
        }
        auslegekarten.revalidate();
        auslegekarten.repaint();
    }

    private JButton initialisiereStadtKnopf(Karte karte) {
        JButton button = new JButton();
        button.setBackground(karte.getStadt().getLand().getFarbe());
        button.setText(karte.getStadt().toString());
        button.setName(karte.getStadt().toString());
        return button;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().Spiel);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        ladeSpielfeld();
        ladeButtons();
    }

    private void ladeSpielfeld() {
        try {
            Spielfeld = new ImagePanel(Paths.get("Main/src/res/thurnplan.jpg").toAbsolutePath().toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Spielfeld.setLayout(null);
    }

    private void ladeButtons() {
        karte1Button = new JButton();
        karte2Button = new JButton();
        karte3Button = new JButton();
        karte4Button = new JButton();
        karte5Button = new JButton();
        karte6Button = new JButton();
        postillionButton = new JButton();
        postmeisterButton = new JButton();
        amtmannButton = new JButton();
        handkartenAreal = new JPanel();
        spielzugBeendenButton = new JButton();
        auslegekarten = new JPanel();
        consoleText = new JTextArea();
        BufferedImage buttonIcon = readImageFromPath("Main/src/res/TuTSR.PNG");
        kartenstapelButton = new JButton(new ImageIcon(buttonIcon));
        kartenstapelButton.setBorderPainted(false);
        JButton mannheimButton = new StadtLocator(Stadt.MANNHEIM, 14, 15, Spielfeld);
        JButton carlsruheButton = new StadtLocator(Stadt.CARLSRUHE, 5, 39, Spielfeld);
        JButton freiburgButton = new StadtLocator(Stadt.FREIBURG, 2, 65, Spielfeld);
        JButton stuttgartButton = new StadtLocator(Stadt.STUTTGART, 22, 31, Spielfeld);
        JButton ulmButton = new StadtLocator(Stadt.ULM, 27, 52, Spielfeld);
        JButton sigmaringenButton = new StadtLocator(Stadt.SIGMARINGEN, 15, 56, Spielfeld);
        JButton baselButton = new StadtLocator(Stadt.BASEL, 1, 81, Spielfeld);
        JButton zurichButton = new StadtLocator(Stadt.ZÜRICH, 12, 88, Spielfeld);
        JButton kemptenButton = new StadtLocator(Stadt.KEMPTEN, 31, 76, Spielfeld);
        JButton augsburgButton = new StadtLocator(Stadt.AUGSBURG, 40, 58, Spielfeld);
        JButton ingolstadtButton = new StadtLocator(Stadt.INGOLSTADT, 47, 43, Spielfeld);
        JButton nuernbergButton = new StadtLocator(Stadt.NÜRNBERG, 48, 22, Spielfeld);
        JButton wuerzburgButton = new StadtLocator(Stadt.WÜRZBURG, 34, 10, Spielfeld);
        JButton regensburgButton = new StadtLocator(Stadt.REGENSBURG, 62, 35, Spielfeld);
        JButton passauButton = new StadtLocator(Stadt.PASSAU, 70, 59, Spielfeld);
        JButton muenchenButton = new StadtLocator(Stadt.MÜNCHEN, 55, 67, Spielfeld);
        JButton pilsenButton = new StadtLocator(Stadt.PILSEN, 66, 13, Spielfeld);
        JButton budweisButton = new StadtLocator(Stadt.BUDWEIS, 83, 33, Spielfeld);
        JButton lodzButton = new StadtLocator(Stadt.LODZ, 83, 10, Spielfeld);
        JButton linzButton = new StadtLocator(Stadt.LINZ, 83, 60, Spielfeld);
        JButton salzburgButton = new StadtLocator(Stadt.SALZBURG, 81, 86, Spielfeld);
        JButton innsbruckButton = new StadtLocator(Stadt.INNSBRUCK, 43, 88, Spielfeld);
    }

    private BufferedImage readImageFromPath(String path) {
        try {
            return ImageIO.read(new File(Paths.get(path).toAbsolutePath().toString()));
        } catch (IOException e) {
            return null;
        }
    }

    private Spiel spielVorbereitung() {
        Spieler beginner = ermittleBeginner();
        consoleText.append("##### Willkommen bei Thurn und Taxis ######\n");
        consoleText.append("Spieler " + beginner.getName() + " beginnt.\n\n");
        return new Spiel(spielerListe);
    }

    private Spieler ermittleBeginner() {
        Map<Spieler, Integer> würfelErgebnisse = new HashMap<Spieler, Integer>();
        for (Spieler spieler : spielerListe) {
            würfelErgebnisse.put(spieler, würfeln(spieler));
        }
        Map<Spieler, Integer> sortedTreeSet = new TreeMap<Spieler, Integer>(new SpielerSortierer(würfelErgebnisse));
        sortedTreeSet.putAll(würfelErgebnisse);
        JOptionPane.showMessageDialog(new JPanel(), "Spieler " + sortedTreeSet.entrySet().iterator().next().getKey() + " beginnt");
        return sortedTreeSet.entrySet().iterator().next().getKey();
    }

    private int würfeln(Spieler spieler) {
        JPanel würfelPanel = new JPanel();
        würfelPanel.setName("Würfeln");
        würfelPanel.setVisible(true);
        int würfelErgebnis = würfelErgebnis();
        JOptionPane.showMessageDialog(würfelPanel, "Spieler " + spieler.getName() + " würfelte eine " + würfelErgebnis);
        return würfelErgebnis;
    }

    private int würfelErgebnis() {
        return (int) (Math.random() * 20 + 1);
    }

    private ArrayList<JButton> generiereAuslageKnöpfe() {
        ArrayList<JButton> tmp = new ArrayList<JButton>();
        tmp.add(karte1Button);
        tmp.add(karte2Button);
        tmp.add(karte3Button);
        tmp.add(karte4Button);
        tmp.add(karte5Button);
        tmp.add(karte6Button);
        return tmp;
    }

    private void verknüpfeKartenMitAuslage(ArrayList<JButton> auslage) {
        int index = 0;
        JButton button;

        for (Karte karte : spiel.getAuslageKarten()) {
            button = auslage.get(index);
            button.setBackground(karte.getStadt().getLand().getFarbe());
            button.setText(karte.getStadt().toString());
            button.setActionCommand("Auslage_" + karte.getStadt().toString());
            button.setName(karte.getStadt().toString());
            index++;

        }

    }

    private ArrayList<Spieler> spielerErzeugen() {
        int anzahl = ermittleSpielerAnzahl();
        return legeSpielerAn(anzahl);
    }

    private ArrayList<Spieler> legeSpielerAn(int anzahl) {
        ArrayList<Spieler> spielerListe = new ArrayList<Spieler>();

        JComboBox farbBox = farbBoxAnlegen();
        for (int i = 0; i < anzahl; i++) {
            String name = spielerNamenEingeben();
            Farbe farbe = farbeAuswaehlen(farbBox);
            farbBox.removeItem(farbe);
            spielerListe.add(new Spieler(name, farbe));
        }
        return spielerListe;
    }

    private JComboBox farbBoxAnlegen() {
        DefaultComboBoxModel farbModel = new DefaultComboBoxModel();
        for (Enum<Farbe> farbe : Farbe.values()) {
            farbModel.addElement(farbe);
        }
        return new JComboBox(farbModel);
    }

    private Farbe farbeAuswaehlen(JComboBox comboBoxFarbe) {
        JPanel farbPanel = new JPanel();
        farbPanel.add(new JLabel("Bitte Farbe auswählen:"));
        farbPanel.add(comboBoxFarbe);
        int result = 1;

        while (result != 0) {
            result = JOptionPane.showConfirmDialog(null, farbPanel, "Farbe auswählen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        }
        return (Farbe) (comboBoxFarbe.getSelectedItem());
    }

    private String spielerNamenEingeben() {
        JPanel panel = new JPanel();
        String name = null;
        while (name == null) {
            name = JOptionPane.showInputDialog(panel, "Bitte Namen eingeben", null);
        }
        return name;
    }

    private int ermittleSpielerAnzahl() {

        JPanel panel = new JPanel();
        panel.add(new JLabel("Bitte Spieleranzahl auswählen:"));
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("2");
        model.addElement("3");
        model.addElement("4");
        JComboBox comboBox = new JComboBox(model);
        panel.add(comboBox);

        int result = 1;

        while (result != 0) {
            result = JOptionPane.showConfirmDialog(null, panel, "Spieleranzahl", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        }
        return Integer.parseInt(comboBox.getSelectedItem().toString());
    }
}
