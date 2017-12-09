package fr.ip.view.core;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageBackgroundJPanel extends JPanel {

    private BufferedImage image;

    public ImageBackgroundJPanel(LayoutManager layout) {
        super(layout);
        setBackground(new Color(0,0,0,0));
    }

    public ImageBackgroundJPanel() {
        this (new GridBagLayout());
    }

    public void setImage(String filepath) {
        try {
            image = ImageIO.read(new File(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(image, 0, 0, this);
    }
}
