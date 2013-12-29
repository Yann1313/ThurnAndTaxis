
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
import java.util.ArrayList;
import java.util.LinkedList;

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
    private JTextPane consoleTextPane;
    private JPanel Spielfeld;
    private JPanel handkartenAreal;
    private JButton spielzugBeendenButton;
    private LinkedList<Spieler> spielerListe;
    private Spiel spiel;

    public Main() {
        spielerListe = spielerErzeugen();
        spiel = spielVorbereitung(spielerListe);
        verknüpfeKartenMitAuslage(generiereAuslageKnöpfe());

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Karte neueKarte = spiel.karteZiehen(e.getActionCommand());
                if (neueKarte != null) {
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
                System.out.println("Karte vom Kartenstapel");
                Karte neueKarte = spiel.karteZiehen(e.getActionCommand());
                if (neueKarte != null) {
                    System.out.println("Neue Karte ist: " + neueKarte.getStadt());
                    handkartenAreal.add(generiereNeuenHandKartenKnopf(neueKarte));
                    Spiel.revalidate();
                }
            }

        });
        ActionListener listener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosenAmtmann = e.getActionCommand();
                System.out.println(spiel.getSpieler().get(spiel.getAktuellerSpielerIndex()).hashCode());
                if (!spiel.getAktuellerSpieler().getAmtmann()) {
                    if (chosenAmtmann.equals("Postillion")) {
                        spiel.karteAuspielen();
                    } else if (chosenAmtmann.equals("Postmeister")) {
                        spiel.neuZiehen();
                    } else if (chosenAmtmann.equals("Amtmann")) {
                        spiel.tauschen();
                        verknüpfeKartenMitAuslage(generiereAuslageKnöpfe());
                        Spiel.revalidate();
                    }
                }
            }
        };
        amtmannButton.addActionListener(listener1);
        postmeisterButton.addActionListener(listener1);
        postillionButton.addActionListener(listener1);
        spielzugBeendenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spiel.spielzugBeenden();
                initialisiereSpieler();
                System.out.println(spiel.getAktuellerSpieler().getGezogen());
                handkartenAreal.revalidate();
                handkartenAreal.repaint();
            }
        });
    }

    private void initialisiereSpieler() {
        löscheAlteKnöpfe();
        Spiel.revalidate();
        for (Karte karte : spiel.getAktuellerSpieler().getHand()) {
            handkartenAreal.add(generiereNeuenHandKartenKnopf(karte));
        }
        Spiel.revalidate();
    }

    private void löscheAlteKnöpfe() {
        for (int i = 0; i <= handkartenAreal.getComponents().length; i++) {
            System.out.println("GRÖSSE DAVOR: " + handkartenAreal.getComponentCount());
            handkartenAreal.remove(i);
            System.out.println("GRÖSSE DANACH: " + handkartenAreal.getComponentCount());
        }
    }

    private JButton generiereNeuenHandKartenKnopf(Karte neueKarte) {
        JButton handkarte = new JButton(neueKarte.getStadt().toString());
        handkarte.setBackground(neueKarte.getStadt().getLand().getFarbe());
        handkarte.setForeground(new Color(255, 255, 255));
        return handkarte;
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
        kartenstapelButton = new JButton();
        spielzugBeendenButton = new JButton();
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


    private Spiel spielVorbereitung(LinkedList<Spieler> spielerListe) {
        return new Spiel(spielerListe);
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
        button = null;
    }

    private LinkedList<Spieler> spielerErzeugen() {
        int anzahl = ermittleSpielerAnzahl();
        return legeSpielerAn(anzahl);
    }

    private LinkedList<Spieler> legeSpielerAn(int anzahl) {
        LinkedList<Spieler> spielerListe = new LinkedList<Spieler>();

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
            result = JOptionPane.showConfirmDialog(null, panel, "Flavor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        }
        return Integer.parseInt(comboBox.getSelectedItem().toString());
    }
}
