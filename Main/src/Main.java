
import com.sun.java.swing.plaf.windows.WindowsBorders;
import karten.Karte;
import karten.Land;
import karten.Stadt;
import spiel.*;
import swing.ImagePanel;
import swing.StadtLocator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.text.DefaultCaret;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
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
    private JTextArea informationen;
    private JButton streckeWertenButton;
    private JPanel auslageAction;
    private JRadioButton imLandSetzenRadioButton;
    private JRadioButton proLand1HausRadioButton;
    private JComboBox landAuswahl;
    private JTextPane landAuswahlTextPane;
    private JButton houseSetButton;
    private JScrollPane consoleTextPane;
    private JPanel topWindow;
    private JPanel winnerPanel;
    private JButton soundSwitchButton;
    private ArrayList<Spieler> spielerListe;
    private Spiel spiel;
    private boolean soundSchalter = true;
    File spieler1 = readAudioFromPath("Main/src/res/spieler1_wechsel.wav");
    File spieler2 = readAudioFromPath("Main/src/res/spieler2_wechsel.wav");
    File spieler3 = readAudioFromPath("Main/src/res/spieler3_wechsel.wav");
    File spieler4 = readAudioFromPath("Main/src/res/spieler4_wechsel.wav");
    File proLand1Haus = readAudioFromPath("Main/src/res/proLand1haus.wav");
    File imLand = readAudioFromPath("Main/src/res/ImLand.wav");
    File main = readAudioFromPath("Main/src/res/Main.wav");
    File amtmann = readAudioFromPath("Main/src/res/Amtmann_genutzt.wav");
    File karteZiehen = readAudioFromPath("Main/src/res/karte_ziehen.wav");
    File spielzuEnde = readAudioFromPath("Main/src/res/spielZuEnde.wav");
    //Icon speaker = new ImageIcon(readImageFromPath("Main/src/res/speaker-icon-jpg"));
    AudioClip sound = null;

    public Main() {
        spieleSound(main);
        spielerListe = spielerErzeugen();
        spiel = spielVorbereitung();
        verknüpfeKartenMitAuslage(generiereAuslageKnöpfe());
        sound.stop();
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
                    aktualisiereSpielerInformationen();
                    Spiel.revalidate();
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
                    aktualisiereSpielerInformationen();
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
                if (!spiel.getAktuellerSpieler().getAmtmann()) {
                    consoleText.append("Uuund hier kommt der  ");
                    if (chosenAmtmann.equals("Postillion") && spiel.getAktuellerSpieler().getHand().size() > 0
                            ) {
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
                    if (!spiel.getAktuellerSpieler().isErsterZug()) {
                        spieleSound(amtmann);
                    }
                    aktualisiereSpielerInformationen();
                } else {
                    consoleText.append("Noch viel zu lernen du hast, mein junger Padawan\n");
                    consoleText.append("(Schreib es dir endlich hinter die Ohren,\n das geht nur einmal pro Runde!)\n\n");

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
                if (spiel.getAktuellerSpieler().getGezogenCount() == 0 && spiel.getAktuellerSpieler().getGelegtCount() == 0) {
                    int answer = JOptionPane.showConfirmDialog(new JPanel(), "Möchtest du deinen Zug wirklich abgeben?", "Zug beenden", 0);
                    switch (answer) {
                        case 0:
                            consoleText.append("OK OK ich mach ja schon immer mit der Ruhe ;)!\n\n");
                            spiel.spielzugBeenden();
                            initialisiereSpieler();
                            aktualisiereAblageStapel();
                            beginneZug();
                            System.out.println(spiel.spielZuEnde());
                            if (spiel.spielZuEnde()) {
                                String ergebnis = "";
                                int count = 1;
                                Map<Spieler, Integer> gewinner = spiel.ermittleGewinner();
                                for (Map.Entry<Spieler, Integer> entry : gewinner.entrySet()) {
                                    ergebnis += count + " Platz mit " + entry.getKey().getPunkte() + " Punkten geht an " + entry.getKey() + "\n";
                                    count++;
                                }
                                ergebnis += "\n\n Das Spiel wird mit einem Klick auf OK beendet.\nVielen Dank fürs Spielen";
                                JPanel gewinnerPanel = new JPanel();
                                gewinnerPanel.setName("Ergebnis");
                                gewinnerPanel.setVisible(true);
                                spieleSound(spielzuEnde);
                                JOptionPane.showMessageDialog(gewinnerPanel, ergebnis);
                                System.exit(0);
                            }
                            Spiel.revalidate();
                            Spiel.repaint();
                            break;
                        default:
                            consoleText.append("Ich weis von nichts und habe dich auch nicht gesehen. \n" +
                                    "Lass mir dass nächste mal aber bitte ein paar Groschen da OK?\n\n");
                            break;
                    }
                } else {
                    consoleText.append("Oh je oh je. Das geht erst wenn du eine Karte \n" +
                            "ausgespielt und eine Karte gezogen hast\n\n");
                }
            }
        });
        streckeWertenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (spiel.getAktuellerSpieler().getAuslage().size() >= 3) {
                    int answer = JOptionPane.showConfirmDialog(new JPanel(), "Möchtest du die Strecke wirklich werten?", "Strecke werten", 0);
                    switch (answer) {
                        case 0:
                            consoleText.append("Ob das klug war? Naja ich weiß nicht ;)!\n\n");
                            initialisiereWertenFeld();
                            Spiel.revalidate();
                            Spiel.repaint();
                            break;
                        default:
                            consoleText.append("Hin und her und hin und her! Dauernd muss ich eure Arbeit machen\n" +
                                    "Ich will endlich Lihn dafür! :D\n\n");
                            break;
                    }
                } else {
                    consoleText.append("So langsam habe ich mich daran gewöhnt, das ihr die Regeln\n" +
                            "nicht kennt! Du darfst deine Strecke erst werten lassen bei einer Größe\n" +
                            "von 3");
                }
            }
        });
        houseSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enabledButton = null;
                for (Component comp : auslageAction.getComponents()) {
                    if (comp instanceof JRadioButton) {
                        if (((JRadioButton) comp).isSelected()) {
                            enabledButton = ((JRadioButton) comp).getText().replaceAll("\"", "");
                        }
                    }
                }
                wähleSetztenSoundAus(enabledButton);
                spiel.streckeWeten(enabledButton, (Land) landAuswahl.getSelectedItem());
                zeichneHäuserAufDasSpielfeld(spiel.getAktuellerSpieler().getHäuser());
                radioAndComboRemove();
                löscheAuslage();
            }

            private void wähleSetztenSoundAus(String enabledButton) {
                if (enabledButton.equals("Im Land setzen")) {
                    spieleSound(imLand);
                } else {
                    spieleSound(proLand1Haus);
                }
            }
        });
        soundSwitchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (soundSchalter) {
                    soundSchalter = false;
                } else {
                    soundSchalter = true;
                }
            }
        });
    }

    private void spieleSound(File main) {
        if (soundSchalter) {
            try {
                sound = Applet.newAudioClip(main.toURL());
            } catch (Exception e) {
                e.printStackTrace();
            }
            sound.play();
        }
    }


    private void radioAndComboRemove() {
        houseSetButton.setVisible(false);
        for (Component comp : auslageAction.getComponents()) {
            if (comp instanceof JRadioButton) {
                comp.setEnabled(false);
                comp.setVisible(false);
            } else if (comp instanceof JComboBox) {
                ((JComboBox) comp).removeAllItems();
                comp.setVisible(false);
            } else if (comp instanceof JTextPane) {
                comp.setVisible(false);
            }
        }
        aktualisereSpielfeld();
    }

    private void löscheAuslage() {
        spiel.getAktuellerSpieler().getAuslage().clear();
        auslegekarten.removeAll();
    }

    private void zeichneHäuserAufDasSpielfeld(Map<Integer, Karte> häuser) {
        for (Map.Entry<Integer, Karte> haus : häuser.entrySet()) {
            if (haus.getValue() != null) {
                for (Component comp : Spielfeld.getComponents()) {
                    JButton stadt = (JButton) comp;
                    if (haus.getValue().getStadt().toString().equals((stadt.getActionCommand()))) {
                        stadt.setBorder(new LineBorder(spiel.getAktuellerSpieler().getFarbe().getCode(), 3));
                    }
                }
            }
        }
        aktualisereSpielfeld();
    }

    private void
    initialisiereWertenFeld() {
        radioAndComboReset();
        LinkedList<Land> länder = spiel.länderWertung();
        for (Land land : länder) {
            landAuswahl.addItem(land);
        }
        imLandSetzenRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                landAuswahl.setVisible(true);
                landAuswahlTextPane.setVisible(true);
                imLandSetzenRadioButton.setEnabled(false);
                proLand1HausRadioButton.setSelected(false);
                proLand1HausRadioButton.setEnabled(true);
                aktualisereSpielfeld();
            }
        });
        proLand1HausRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                landAuswahl.setVisible(false);
                landAuswahlTextPane.setVisible(false);
                proLand1HausRadioButton.setEnabled(false);
                imLandSetzenRadioButton.setSelected(false);
                imLandSetzenRadioButton.setEnabled(true);
                aktualisereSpielfeld();
            }
        });
    }

    private void radioAndComboReset() {
        houseSetButton.setVisible(true);
        for (Component comp : auslageAction.getComponents()) {
            if (comp instanceof JRadioButton) {
                comp.setEnabled(true);
                comp.setVisible(true);
                ((JRadioButton) comp).setSelected(false);
            } else if (comp instanceof JComboBox) {
                ((JComboBox) comp).removeAllItems();
                comp.setVisible(false);
            } else if (comp instanceof JTextPane) {
                comp.setVisible(false);
            }

        }
    }

    private void aktualisereSpielfeld() {
        Spielfeld.revalidate();
        Spielfeld.repaint();
        Spiel.revalidate();
        Spiel.repaint();
    }

    private void beginneZug() {
        spiel.getAktuellerSpieler().resetteFelder();
        getSpielerSound();
        consoleText.append("------------------------------\nZug beginnt.");
        consoleText.append("Der Spieler sollte nun: \nEine Karte ausspielen \nEine Karte ziehen \n" +
                "Oder einen Postmeister in Anspruch nehmen\n\n");
        aktualisiereSpielerInformationen();
    }

    private void getSpielerSound() {
        switch (spiel.getAktuellerSpielerIndex()) {
            case 0:
                spieleSound(spieler1);
                break;
            case 1:
                spieleSound(spieler2);
                break;
            case 2:
                spieleSound(spieler3);
                break;
            case 3:
                spieleSound(spieler4);
                break;
        }
    }

    private void aktualisiereSpielerInformationen() {
        Spieler aktuellerSpieler = spiel.getAktuellerSpieler();
        informationen.setText(null);
        informationen.append("Momentaner Spieler ist: " + aktuellerSpieler.getName() + "\n");
        informationen.append("Derzeitige Punktzahl ist: " + aktuellerSpieler.getPunkte() + "\n");
        informationen.append("Verbleibende Häuser: " + aktuellerSpieler.getHäuserAnzahl() + "\n\n\n");
        if (!aktuellerSpieler.getAmtmann()) {
            informationen.append("Es kann noch ein Amtmann in Anspruch genommen werden\n");
        } else {
            informationen.append("Es kann kein Amtmann mehr in Anspruch genommen werden\n");
        }
        informationen.append("\nEs kann/können noch " + aktuellerSpieler.getGezogenCount() + " Karte(n) gezogen werden\n");
        informationen.append("Es kann/können noch " + aktuellerSpieler.getGelegtCount() + " Karte(n) gelegt werden\n");
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
        Spieler as = spiel.getAktuellerSpieler();
        löscheAlteHandKnöpfe();
        radioAndComboRemove();
        entferneHäuser();
        zeichneHäuserAufDasSpielfeld(as.getHäuser());
        for (Karte karte : as.getHand()) {
            handkartenAreal.add(generiereNeuenHandKartenKnopf(karte));
        }
        verknüpfeSpielerAuslageMitSpiel();
        aktualisiereSpielerInformationen();
        aktualisereSpielfeld();
    }

    private void entferneHäuser() {
        for (Component component : Spielfeld.getComponents()) {
            ((JButton) component).setBorder(null);
            ((JButton) component).setOpaque(false);
        }
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
                Spieler aktuellerSpieler = spiel.getAktuellerSpieler();
                if (!pruefeZyklus(e.getActionCommand())) {
                    if (aktuellerSpieler.getGelegtCount() > 0) {
                        int answer = JOptionPane.showConfirmDialog(new JPanel(), "Möchtest du diese Karte wirklich auspielen?"
                                , "Karte auspielen", 0);
                        switch (answer) {
                            case 0:
                                for (Karte karte : aktuellerSpieler.getHand()) {
                                    if (karte.getStadt().toString().equals(e.getActionCommand())) {
                                        spiel.karteAuspielen(karte);
                                        aktuellerSpieler.getHand().remove(karte);
                                        aktualisiereSpielerInformationen();
                                        verknüpfeSpielerAuslageMitSpiel();
                                        entferneHandKartenKnopf(karte);
                                    }
                                }
                                break;
                        }
                    } else {
                        consoleText.append("Noch viel zu lernen du hast, mein junger Padawan\n");
                        consoleText.append("(Schreib es dir endlich hinter die Ohren,\n das geht nur einmal pro Runde!)\n\n");
                    }
                } else {
                    consoleText.append("Warte eben ich hole dir schnell noch mehr Bier! Entweder läufst du im Kreis,\n" +
                            "oder dein Kopf dreht sich wie verrückt!!\n");
                    consoleText.append("(Du kannst während du eine Route baust,\n jede Stadt nur einmal besuchen!)\n\n");
                }
            }

            private boolean pruefeZyklus(String actionCommand) {
                boolean zyklus = false;
                for (Karte karte : spiel.getAktuellerSpieler().getAuslage()) {
                    if (karte.getStadt().toString().equals(actionCommand)) {
                        zyklus = true;
                    }
                }
                return zyklus;
            }

            private void entferneHandKartenKnopf(Karte karte) {
                Component buttonToRemove = null;
                for (Component comp : handkartenAreal.getComponents()) {
                    if (((JButton) comp).getText().equals(karte.getStadt().toString())) {
                        buttonToRemove = comp;
                    }
                }
                handkartenAreal.remove(buttonToRemove);
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
        soundSwitchButton = new JButton();
        //soundSwitchButton.setIcon(speaker);
        consoleTextPane = new JScrollPane();
        DefaultCaret caret = (DefaultCaret) consoleText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        BufferedImage buttonIcon = readImageFromPath("Main/src/res/TuTSR.PNG");
        kartenstapelButton = new JButton(new ImageIcon(buttonIcon));
        kartenstapelButton.setBorderPainted(false);
        informationen = new JTextArea();
        auslageAction = new JPanel();
        imLandSetzenRadioButton = new JRadioButton();
        proLand1HausRadioButton = new JRadioButton();
        landAuswahl = new JComboBox<Land>();
        landAuswahlTextPane = new JTextPane();
        landAuswahlTextPane.setOpaque(false);
        houseSetButton = new JButton();
        topWindow = new JPanel();
        winnerPanel = new JPanel();
        winnerPanel.setName("WinP");
        auslageAction.setLayout(new BoxLayout(auslageAction, BoxLayout.PAGE_AXIS));
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

    private File readAudioFromPath(String s) {
        try {
            return new File(s);
        } catch (Exception e) {
            return null;
        }

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
        return new Spiel(spielerListe, beginner);
    }

    private Spieler ermittleBeginner() {
        Map<Spieler, Integer> würfelErgebnisse = new HashMap<Spieler, Integer>();
        for (Spieler spieler : spielerListe) {
            würfelErgebnisse.put(spieler, würfeln(spieler));
        }
        Map<Spieler, Integer> sortedTreeSet = new TreeMap<Spieler, Integer>(new SpielerSortierer(würfelErgebnisse));
        sortedTreeSet.putAll(würfelErgebnisse);
        Spieler startenderSpieler = sortedTreeSet.entrySet().iterator().next().getKey();
        JOptionPane.showMessageDialog(new JPanel(), "Spieler " + startenderSpieler + " beginnt");
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
