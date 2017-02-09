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

    private Point[][] intersectionPoints;

    private void createBaseImage(){
        chordImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        chordGraphic = chordImg.createGraphics();
        chordGraphic.setColor(Color.gray);
        chordGraphic.fillRect(0,0,width, height);

        int workingWidth = width- 2 * horizontalPadding;
        int workingHeight = height - 2 * verticalPadding;

        //Calculating Intersection Points
        for(int string = 0; string < numberOfStrings; string++){
            for(int fret = 0; fret < numberOfFrets; fret++){
                int xPos = horizontalPadding + string * (workingWidth/(numberOfStrings-1));
                int yPos = verticalPadding + fret * (workingHeight/(numberOfFrets-1));
                System.out.println(String.format("x: %d\ty: %d", xPos, yPos));
                intersectionPoints[string][fret] = new Point(xPos,yPos);
                chordImg.setRGB(xPos,yPos, Color.red.getRGB());
            }
        }

        //Drawing the Lines
        chordGraphic.setColor(Color.blue);
        Point previousPoint = null;
        Point currentPoint;
        for(int i=0; i < numberOfStrings; i++){
            for(int j=0; j < numberOfFrets; j++){
                if( previousPoint != null ){
                    currentPoint = intersectionPoints[i][j];
                    chordGraphic.drawLine( previousPoint.x, previousPoint.y, currentPoint.x, currentPoint.y );
                    previousPoint = currentPoint;
                } else {
                    previousPoint = intersectionPoints[i][j];
                }
            }
            previousPoint = null;
        }
        for(int j=0; j < numberOfFrets; j++){
            for(int i=0; i < numberOfStrings; i++){
                if( previousPoint != null ){
                    currentPoint = intersectionPoints[i][j];
                    chordGraphic.drawLine( previousPoint.x, previousPoint.y, currentPoint.x, currentPoint.y );
                    previousPoint = currentPoint;
                } else {
                    previousPoint = intersectionPoints[i][j];
                }
            }
            previousPoint = null;
        }


    }

    ChordDrawer(){

        numberOfStrings = 6;
        numberOfFrets = 5;

        width = 340;
        height = 416;
        horizontalPadding = 40;
        verticalPadding = 20;

        chordImage.setText("");
        intersectionPoints = new Point[numberOfStrings][numberOfFrets];

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

                    //Calculate Closest Point
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
