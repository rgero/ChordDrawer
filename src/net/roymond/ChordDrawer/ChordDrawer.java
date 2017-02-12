package net.roymond.ChordDrawer;

import javafx.scene.effect.Light;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

/**
 * The Chord Drawer base class.
 * Created by gero on 2/8/2017.
 */
public class ChordDrawer {
    protected JPanel DrawerWindow;
    private JLabel panelTitle;
    private JLabel chordImage;
    private JPanel imgPanel;
    private JTextField stringsTextField;
    private JTextField fretsTextField;
    private JButton clearButton;

    private int width;                  //This is the image width
    private int height;                 //This is the image height.
    private BufferedImage chordImg;
    private Graphics2D chordGraphic;

    int numberOfStrings;
    int numberOfFrets;
    private int horizontalPadding;
    private int verticalPadding;
    private int workingWidth;
    private int workingHeight;
    private int shapeWidth;
    private int shapeHeight;

    private List<Point> intersectionPoints;

    private double calculateDistance(Point p1, Point p2){
        return Math.sqrt( Math.pow( (p1.getX() - p2.getX()), 2) + Math.pow( (p1.getY() - p2.getY()), 2) );
    }

    private Point getString(Point p){
        //Calculate Closest Points
        List<Point> closestPoints = calculateClosestPoints(p);
        int highX = 0;
        int lowX = width;
        int highY = 0;
        int lowY = height;
        for(Point i: closestPoints){
            if( i.x < lowX ) lowX = i.x;
            if( i.x > highX) highX = i.x;
            if( i.y < lowY ) lowY = i.y;
            if( i.y > highY) highY = i.y;
        }

        int targetX;
        if (closerTo(lowX, p.getX(), highX)){
            targetX = highX;
        } else {
            targetX = lowX;
        }
        int targetY = lowY + (highY-lowY)/2;

        return new Point(targetX, targetY);
    }

    private List<Point> calculateClosestPoints(Point clickedPoint){
        List<Point> pointList = new ArrayList<>();
        pointList.addAll(intersectionPoints);
        for(int i = pointList.size()-1; i >= 0; i--){
            boolean horizontalCheck = Math.abs( clickedPoint.x - pointList.get(i).x ) > (workingWidth/(numberOfStrings-1));
            boolean verticalCheck =  Math.abs( clickedPoint.y - pointList.get(i).y ) > (workingHeight/numberOfFrets);
            if (horizontalCheck | verticalCheck){
                pointList.remove(i);
            }
        }
        return pointList;
    }

    private boolean closerTo(int low, double target, int high){
        double decidePoint = low + (high - low)/2.0;
        return target > decidePoint;
    }



    void createBaseImage(){
        intersectionPoints.clear();
        chordImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        chordGraphic = chordImg.createGraphics();

        workingWidth = width - 2 * horizontalPadding;
        workingHeight = height - 2 * verticalPadding;
        int startX = horizontalPadding;
        int startY = verticalPadding;

        //Calculating Intersection Points and drawing.
        Point previousPoint = null;
        Point currentPoint;
        int xPos = 0, yPos;
        chordGraphic.setColor(Color.black);
        for(int string = 0; string < numberOfStrings; string++){
            for(int fret = 0; fret <= numberOfFrets; fret++){
                xPos = horizontalPadding + string * (workingWidth/(numberOfStrings-1));
                yPos = verticalPadding + fret * (workingHeight/numberOfFrets);
                currentPoint = new Point(xPos, yPos);
                intersectionPoints.add(currentPoint);
                if( previousPoint != null){
                    chordGraphic.drawLine(previousPoint.x, previousPoint.y, currentPoint.x,currentPoint.y);
                    previousPoint = currentPoint;
                } else {
                    previousPoint = currentPoint;
                }
                chordImg.setRGB(xPos,yPos, Color.red.getRGB());
            }
            previousPoint = null;
        }

        //Setting the circle size
        shapeWidth = workingWidth/numberOfStrings/2;
        shapeHeight = shapeWidth;

        //Drawing the fret lines
        List<Integer> yValues = new ArrayList<>();
        for(Point p : intersectionPoints){
            if (!yValues.contains( p.y )){
                yValues.add(p.y);
                chordGraphic.drawLine(startX, p.y, xPos, p.y);
            }
        }

        //Draw root fret
        chordGraphic.setStroke(new BasicStroke(5));
        chordGraphic.drawLine(startX,startY,xPos,startY);

        chordImage.setIcon(new ImageIcon(chordImg));

    }

    ChordDrawer(){

        numberOfStrings = 6;
        numberOfFrets = 5;

        shapeHeight = 20;
        shapeWidth = 20;

        width = 340;
        height = 416;
        horizontalPadding = 40;
        verticalPadding = 20;

        chordImage.setText("");
        intersectionPoints = new ArrayList<>();

        createBaseImage();

        chordImage.setIcon(new ImageIcon(chordImg));

        chordImage.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if ( e.getButton() == 1) {

                    //Determine the correct position
                    Point target = getString(e.getPoint());

                    chordGraphic.setColor(Color.blue);
                    chordGraphic.fillOval(target.x-shapeWidth/2, target.y - shapeHeight/2, shapeWidth, shapeHeight);
                    chordGraphic.setColor(Color.black);
                    chordGraphic.setStroke(new BasicStroke(1));
                    chordGraphic.drawOval(target.x-shapeWidth/2, target.y - shapeHeight/2, shapeWidth, shapeHeight);

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

        clearButton.addActionListener(e -> createBaseImage());

        PlainDocument stringsField = (PlainDocument) stringsTextField.getDocument();
        stringsField.setDocumentFilter(new MyIntFilter());
        stringsField.addDocumentListener( new TextFieldUpdater(this, stringsTextField) );


        PlainDocument fretsField = (PlainDocument)  fretsTextField.getDocument();
        fretsField.setDocumentFilter(new MyIntFilter());
        fretsField.addDocumentListener( new TextFieldUpdater(this, fretsTextField));
    }

}
