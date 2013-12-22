
import amtsmaenner.*;
import karten.Karte;
import spiel.*;
import swing.ImagePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Klasse, welche //TODO: Beschreibung der Klasse
 *
 * @author TPE_UIB_01
 */
public class Main extends JFrame implements Postillion, Amtsmann, Postmeister, Spielzuege {
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
    private JPanel Handkarten;
    private JPanel Spielfeld;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().Spiel);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        g.drawLine(50, 50, 200, 200);
    }

    private void createUIComponents() {
        Handkarten = new JPanel();
        Handkarten.setLayout(null);
        Map<String, Color> colorMap = setColorMap();
        LinkedList<Spieler> spielerListe = spielerErzeugen();

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
        Spielfeld.setBackground(colorMap.get("background"));
        Spielfeld.setLayout(null);


        JButton mannheimButton = new JButton();
        positionCity(mannheimButton, 14, 15);

        JButton carlsruheButton = new JButton();
        positionCity(carlsruheButton, 5, 39);

        JButton freiburgButton = new JButton();
        positionCity(freiburgButton, 2, 65);

        JButton stuttgartButton = new JButton();
        positionCity(stuttgartButton, 22, 31);

        JButton ulmButton = new JButton();
        positionCity(ulmButton, 27, 52);

        JButton sigmaringenButton = new JButton();
        positionCity(sigmaringenButton, 15, 56);

        JButton baselButton = new JButton();
        positionCity(baselButton, 1, 81);

        JButton zurichButton = new JButton();
        positionCity(zurichButton, 12, 88);

        JButton kemptenButton = new JButton();
        positionCity(kemptenButton, 31, 76);

        JButton augsburgButton = new JButton();
        positionCity(augsburgButton, 40, 58);

        JButton ingolstadtButton = new JButton();
        positionCity(ingolstadtButton, 47, 43);


        JButton nuernbergButton = new JButton();
        positionCity(nuernbergButton, 48, 22);

        JButton wuerzburgButton = new JButton();
        positionCity(wuerzburgButton, 34, 10);

        JButton regensburgButton = new JButton();
        positionCity(regensburgButton, 62, 35);

        JButton passauButton = new JButton();
        positionCity(passauButton, 70, 59);

        JButton muenchenButton = new JButton();
        positionCity(muenchenButton, 55, 67);

        JButton pilsenButton = new JButton();
        positionCity(pilsenButton, 66, 13);

        JButton budweisButton = new JButton();
        positionCity(budweisButton, 83, 33);

        JButton lodzButton = new JButton();
        positionCity(lodzButton, 83, 10);

        JButton linzButton = new JButton();
        positionCity(linzButton, 83, 60);

        JButton salzburgButton = new JButton();
        positionCity(salzburgButton, 81, 86);

        JButton innsbruckButton = new JButton();
        positionCity(innsbruckButton, 43, 88);

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


    private Map<String, Color> setColorMap() {
        Map<String, Color> temp = new HashMap<String, Color>();
        temp.put("purple", new Color(167, 114, 186));
        temp.put("lightblue", new Color(150, 206, 217));
        temp.put("grey", new Color(204, 204, 204));
        temp.put("green", new Color(31, 122, 44));
        temp.put("lightgreen", new Color(36, 209, 62));
        temp.put("orange", new Color(250, 167, 22));
        temp.put("red", new Color(180, 0, 0));
        temp.put("blue", new Color(0, 0, 180));
        temp.put("darkgrey", new Color(75, 75, 75));
        temp.put("white", new Color(255, 255, 255));
        temp.put("background", new Color(222, 181, 4));
        return temp;
    }

    private void positionCity(JButton button, int x, int y) {
        button.setPreferredSize(new Dimension(75, 20));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        Spielfeld.add(button);

        //Insets insets = mapPanel.getInsets();
        Dimension size = button.getPreferredSize();

        x = map(x, 0, 150, 0, 1000);
        y = map(y, 0, 100, 0, 500);

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
