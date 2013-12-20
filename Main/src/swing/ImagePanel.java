package swing;

/**
 * Klasse, welche //TODO: Beschreibung der Klasse
 *
 * @author TPE_UIB_01
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel(String path) throws IOException {
        System.out.println(Paths.get("Main/src/res/thurnlan.jpg").toAbsolutePath().toString());
        image = ImageIO.read(new File(path));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

}