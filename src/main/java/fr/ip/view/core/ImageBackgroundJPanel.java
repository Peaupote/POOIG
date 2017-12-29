package fr.ip.view.core;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Thread;
import java.util.Random;
import java.util.ArrayList;
import java.lang.InterruptedException;

public class ImageBackgroundJPanel extends JPanel {

    private BufferedImage image;
		private ArrayList<Cloud> clouds;
    public final static Color TRANSPARENT = new Color(0,0,0,0);
		private final static Random rand = new Random();

		private class Cloud {
				
				private BufferedImage image;
				private int x, y, speed;
				private final static int max = 350, maxSpeed = 2;

				public Cloud (String path) {
						try {
								image = ImageIO.read(new File(path));
						} catch (IOException e) {
								e.printStackTrace();
						}

						x = -image.getWidth();
						y = rand.nextInt(max);
						speed = 1;

				}

				public void draw (Graphics graphics) {
						graphics.drawImage(image, x, y, ImageBackgroundJPanel.this);
				}

		}

    public ImageBackgroundJPanel(LayoutManager layout) {
        super(layout);
        setBackground(TRANSPARENT);
				
				clouds = new ArrayList<>();
				clouds.add(new Cloud("./assets/cloud1.png"));
				clouds.add(new Cloud("./assets/cloud2.png"));
				clouds.add(new Cloud("./assets/cloud2.png"));

				new Thread(() -> {
						while (true) {
								for (Cloud cloud : clouds) {
										cloud.x += cloud.speed;
										if (cloud.x >= ImageBackgroundJPanel.this.getWidth()) { 
												cloud.x = -cloud.image.getWidth() - rand.nextInt(100);
 												cloud.y = rand.nextInt(Cloud.max);
												cloud.speed = rand.nextInt(Cloud.maxSpeed - 1) + 1;
										}

								}

								try {
										Thread.sleep(5);
								} catch (InterruptedException e) {
										e.printStackTrace();
								}

								repaint();
						}
				}).start();
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
				int w = image.getWidth(), h = image.getHeight();
				if (getWidth() > w) {
						w = getWidth();
						h *= getWidth() / w;
				}

				if (getHeight() > h) {
						h = getHeight();
						w *= getHeight() / h;
				}

        graphics.drawImage(image, 0, 0, w, h, this);
				for (Cloud cloud: clouds)
					cloud.draw(graphics);
    }
}
