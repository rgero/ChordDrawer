package net.roymond.ChordDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * The Chord Drawer base class.
 * Created by gero on 2/8/2017.
 */
public class ChordDrawer {
    protected JPanel DrawerWindow;
    private JLabel panelTitle;
    private JLabel chordImage;
    private JPanel imgPanel;

    private int width;
    private int height;

    ChordDrawer(){

        width = 340;
        height = 416;
        chordImage.setText("");


        BufferedImage chordImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D chordGraphic = chordImg.createGraphics();
        chordGraphic.setColor(Color.black);
        chordGraphic.fillRect(0,0,width, height);

        Color clickColor = Color.white;

        chordImage.setIcon(new ImageIcon(chordImg));

        chordImage.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if ( e.getButton() == 1) {

                    chordImg.setRGB(e.getX(), e.getY(), clickColor.getRGB());

                    //Note: It appears that these coordinates are relative to the object the listener is on.
                    System.out.println(String.format("Mouse Pressed:\tx: %d\ty: %d", e.getX(), e.getY()));


                    //Note: we have to update the icon every time we change the image.
                    chordImage.setIcon(new ImageIcon(chordImg));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

}
