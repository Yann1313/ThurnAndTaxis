package swing;

import karten.Stadt;

import javax.swing.*;
import java.awt.*;

/**
 * Klasse, welche //TODO: Beschreibung der Klasse
 *
 * @author TPE_UIB_01
 */
public class StadtLocator extends JButton {

    Stadt stadt;

    public StadtLocator(Stadt stadt, int x, int y, JPanel map) {

        this.stadt = stadt;
        this.setPreferredSize(new Dimension(75, 20));
        this.setContentAreaFilled(false);
        this.setOpaque(false);
        map.add(this);

        Dimension size = this.getPreferredSize();

        x = map(x, 0, 150, 0, 1000);
        y = map(y, 0, 100, 0, 500);


        this.setBounds(x, y, size.width, size.height);
        this.setForeground(this.stadt.getLand().getFarbe());
        this.setToolTipText(this.stadt.getLand().toString());

    }

    private int map(int x, int in_min, int in_max, int out_min, int out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
