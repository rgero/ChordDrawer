package net.roymond.ChordDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

/**
 * The Chord Drawer base class.
 * Created by gero on 2/8/2017.
 */
public class ChordDrawer {
    protected JPanel DrawerWindow;
    private JLabel panelTitle;
    private JLabel chordImage;
    private JPanel imgPanel;

    private int width;                  //This is the image width
    private int height;                 //This is the image height.
    private BufferedImage chordImg;
    private Graphics2D chordGraphic;

    private int numberOfStrings;
    private int numberOfFrets;
    private int horizontalPadding;
    private int verticalPadding;

    private List<Point> intersectionPoints;

    private void createBaseImage(){
        chordImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        chordGraphic = chordImg.createGraphics();
        chordGraphic.setColor(Color.black);
        chordGraphic.fillRect(0,0,width, height);

        int workingWidth = width-2*horizontalPadding;
        int workingHeight = height - 2 * verticalPadding;

        //Here would be where we draw the guitar.
        for(int string = 0; string < numberOfStrings; string++){
            for(int fret = 0; fret < numberOfFrets; fret++){
                int xPos = horizontalPadding + string * (workingWidth/(numberOfStrings-1));
                int yPos = verticalPadding + fret * (workingHeight/(numberOfFrets-1));
                System.out.println(String.format("x: %d\ty: %d", xPos, yPos));
                intersectionPoints.add( new Point(xPos,yPos) );
                chordImg.setRGB(xPos,yPos, Color.red.getRGB());
            }
        }


    }

    ChordDrawer(){

        numberOfStrings = 6;
        numberOfFrets = 5;

        width = 340;
        height = 416;
        horizontalPadding = 20;
        verticalPadding = 8;

        chordImage.setText("");
        intersectionPoints = new ArrayList<>();

        createBaseImage();

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
