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
import java.util.HashMap;

public class ImageBackgroundJPanel extends JPanel {

    protected BufferedImage image;
    public final static Color TRANSPARENT = new Color(0,0,0,0);
		private final static Random rand = new Random();

		public static class ImageBackgroundView extends ImageBackgroundJPanel implements SingleView {

				private class Cloud {
						
						private BufferedImage image;
						private int x, y, speed;
						private final static int max = 350, maxSpeed = 4;

						public Cloud (String path) {
								try {
										image = ImageIO.read(new File(path));
								} catch (IOException e) {
										e.printStackTrace();
								}

                x = rand.nextInt(250) + 100;
                y = - image.getHeight() - rand.nextInt(300);
                speed = rand.nextInt(Cloud.maxSpeed - 1) + 1;
						}

            public void initPosition () {
                x = rand.nextInt(getWidth() - 250);
                y = - image.getHeight() - rand.nextInt(300);
                speed = rand.nextInt(Cloud.maxSpeed - 1) + 3;
            }

						public void draw (Graphics graphics) {
								graphics.drawImage(image, x, y, ImageBackgroundView.this);
						}

				}

				protected ArrayList<Cloud> clouds;
				protected Thread cloudManager, bkgManager; 
        private int offset = 0;

				public ImageBackgroundView (LayoutManager layout) {
						super(layout);
						clouds = new ArrayList<>();
						clouds.add(new Cloud("./assets/meteor_groupe.png"));
						clouds.add(new Cloud("./assets/meteor_seul.png"));
						clouds.add(new Cloud("./assets/meteor_seul.png"));

						cloudManager = new Thread(() -> {
								while (true) {
										for (Cloud cloud : clouds) {
												cloud.x += cloud.speed;
                        cloud.y += cloud.speed;
												if (cloud.x >= getWidth() || cloud.y >= getHeight())
                            cloud.initPosition();

										}

										try {
												Thread.sleep(15);
										} catch (InterruptedException e) {
												e.printStackTrace();
										}

										repaint();
								}
						});

            bkgManager = new Thread(() -> {
                while(true) {
                    offset = (offset + 1) % getWidth();

                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

				public ImageBackgroundView () {
						this(new GridBagLayout());
				}

				@Override
				public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            if (image != null) {
                this.drawSafeResize(graphics, image, offset, 0);
                this.drawSafeResize(graphics, image, offset - getWidth(), 0);
            }
						for (Cloud cloud: clouds)
							cloud.draw(graphics);
				}
				
				public void onOpen (HashMap<String, Object> map) {
						if (cloudManager.getState() == Thread.State.NEW)
							cloudManager.start();

						if (bkgManager.getState() == Thread.State.NEW)
							bkgManager.start();
				}

				public void onClose () {
				}

		}

    public ImageBackgroundJPanel(LayoutManager layout) {
        super(layout);
        setBackground(TRANSPARENT);
				
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
        if (image != null) drawSafeResize(graphics, image, 0, 0);
    }

    protected void drawSafeResize(Graphics graphics, BufferedImage img, int x, int y) {
        int w = img.getWidth(), h = img.getHeight();
        if (getWidth() > w) {
            w = getWidth();
            h *= getWidth() / w;
        }

        if (getHeight() > h) {
            h = getHeight();
            w *= getHeight() / h;
        }

        graphics.drawImage(img, x, y, w, h, this);
    }
}
