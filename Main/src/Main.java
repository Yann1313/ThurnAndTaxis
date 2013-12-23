
import amtsmaenner.*;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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


    public Main() {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        karte2Button.addActionListener(listener);
        karte1Button.addActionListener(listener);
        karte4Button.addActionListener(listener);
        karte3Button.addActionListener(listener);
        karte6Button.addActionListener(listener);
        karte5Button.addActionListener(listener);
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

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(Paths.get("Main/src/res/thurnplan.jpg").toAbsolutePath().toString()));
        } catch (IOException e) {
        }
        try {
            Spielfeld = new ImagePanel(Paths.get("Main/src/res/thurnplan.jpg").toAbsolutePath().toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Spielfeld.setLayout(null);
        karte1Button = new JButton();
        karte2Button = new JButton();
        karte3Button = new JButton();
        karte4Button = new JButton();
        karte5Button = new JButton();
        karte6Button = new JButton();
        System.out.println(karte3Button.getText());

        LinkedList<Spieler> spielerListe = spielerErzeugen();
        Spiel spiel = spielVorbereitung(spielerListe);


        System.out.println("Button" + karte1Button.getText() + " " + karte1Button.getName());
        System.out.println("Button" + karte2Button.getText() + " " + karte2Button.getName());
        System.out.println("Button" + karte3Button.getText() + " " + karte3Button.getName());
        System.out.println("Button" + karte4Button.getText() + " " + karte4Button.getName());
        System.out.println("Button" + karte5Button.getText() + " " + karte5Button.getName());
        System.out.println("Button" + karte6Button.getText() + " " + karte6Button.getName());




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
        karte1Button.repaint();
        karte1Button.revalidate();
    }


    private Spiel spielVorbereitung(LinkedList<Spieler> spielerListe) {

        ArrayList<JButton> auslageKnöpfe = new ArrayList<JButton>();
        auslageKnöpfe.add(karte1Button);
        auslageKnöpfe.add(karte2Button);
        auslageKnöpfe.add(karte3Button);
        auslageKnöpfe.add(karte4Button);
        auslageKnöpfe.add(karte5Button);
        auslageKnöpfe.add(karte6Button);

        return new Spiel(spielerListe, auslageKnöpfe);
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
        for (Spieler spieler : spielerListe) {
            System.out.println(spieler);
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
