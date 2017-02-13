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
    private JButton mutedNote;
    private JButton openNote;
    private JButton regularNote;
    private JPanel NotePanel;
    private JButton launchFretSetup;
    private JPanel clearPanel;
    private JButton exportChord;
    private JButton barreNote;

    private int width;                  //This is the image width
    private int height;                 //This is the image height.
    private BufferedImage chordImg;
    private Graphics2D chordGraphic;

    private int numberOfStrings;
    private int numberOfFrets;
    private int rootNote;
    private int horizontalPadding;
    private int bottomPadding;
    private int workingWidth;
    private int workingHeight;
    private int shapeWidth;
    private int shapeHeight;
    private NotesEnum selectedNote;
    private List<Point> intersectionPoints;
    private Point startPoint;

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
            boolean verticalCheck =  Math.abs( clickedPoint.y - pointList.get(i).y ) > (workingHeight/numberOfFrets+1);
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



    private void createBaseImage(){
        intersectionPoints.clear();
        chordImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        chordGraphic = chordImg.createGraphics();

        workingWidth = width - 2 * horizontalPadding;
        workingHeight = height - bottomPadding;
        int startX = horizontalPadding;
        int startY = 0;

        //Calculating Intersection Points and drawing.
        Point currentPoint;
        int xPos = 0, yPos = 0;
        chordGraphic.setColor(Color.black);
        for(int string = 0; string < numberOfStrings; string++){
            for(int fret = 0; fret <= numberOfFrets; fret++){
                xPos = horizontalPadding + string * (workingWidth/(numberOfStrings-1));
                yPos = fret * (workingHeight/numberOfFrets+1);
                currentPoint = new Point(xPos, yPos);
                intersectionPoints.add(currentPoint);
            }
        }

        //Drawing the fret lines
        List<Integer> yValues = new ArrayList<>();
        List<Integer> xValues = new ArrayList<>();
        for(Point p : intersectionPoints){
            if (!yValues.contains( p.y ) & (p.y != startY)){
                yValues.add(p.y);
                chordGraphic.drawLine(startX, p.y, xPos, p.y);
            }
            if (!xValues.contains( p.x ) & (p.y != startY)){
                xValues.add(p.x);
                chordGraphic.drawLine(p.x, p.y, p.x, yPos);
            }
        }

        //Draw root fret
        chordGraphic.setStroke(new BasicStroke(5));
        chordGraphic.drawLine(startX,yValues.get(0),xPos,yValues.get(0));

        //Draw Root Note
        if (rootNote != 0) {
            int fontSize = 16;
            chordGraphic.setFont(new Font(null, Font.BOLD, fontSize));
            chordGraphic.drawString(String.valueOf(rootNote), xPos + 10, yValues.get(0) + fontSize/3);
        }

        chordImage.setIcon(new ImageIcon(chordImg));

    }

    ChordDrawer(){

        numberOfStrings = 6;
        numberOfFrets = 6; // Root fret counts as one.
        rootNote = 0;

        shapeHeight = 20;
        shapeWidth = 20;
        selectedNote = NotesEnum.None;

        startPoint = null;

        width = 340;
        height = 416;
        horizontalPadding = 40;
        bottomPadding = 30;

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

                    if (selectedNote == NotesEnum.Regular) {
                        chordGraphic.fillOval(target.x - shapeWidth / 2, target.y - shapeHeight / 2, shapeWidth, shapeHeight);
                        chordGraphic.setColor(Color.black);
                        chordGraphic.setStroke(new BasicStroke(1));
                        chordGraphic.drawOval(target.x - shapeWidth / 2, target.y - shapeHeight / 2, shapeWidth, shapeHeight);
                    } else if (selectedNote == NotesEnum.Open){
                        chordGraphic.setStroke(new BasicStroke(2));
                        chordGraphic.drawOval(target.x - shapeWidth / 2, target.y - shapeHeight / 2, shapeWidth, shapeHeight);
                    } else if (selectedNote == NotesEnum.Closed){
                        chordGraphic.setStroke(new BasicStroke(3));
                        chordGraphic.drawLine(target.x - shapeWidth / 2, target.y - shapeHeight / 2, target.x + shapeWidth / 2, target.y + shapeHeight / 2);
                        chordGraphic.drawLine(target.x - shapeWidth / 2, target.y + shapeHeight / 2, target.x + shapeWidth / 2, target.y - shapeHeight / 2);
                    } else if (selectedNote == NotesEnum.Barre) {

                        if (startPoint == null) {
                            startPoint = target;
                            chordGraphic.fillOval(target.x - shapeWidth / 4, target.y - shapeHeight / 4, shapeWidth/2, shapeHeight/2);
                        } else {
                            BasicStroke barreStroke = new BasicStroke( shapeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                            chordGraphic.setStroke(barreStroke);
                            chordGraphic.drawLine(startPoint.x, startPoint.y, target.x, startPoint.y);
                            startPoint = null;
                        }

                    } else if (selectedNote == NotesEnum.None){
                            JOptionPane.showMessageDialog(null, "You must select a type of note");
                    }

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

        openNote.addActionListener(e -> selectedNote = NotesEnum.Open);
        mutedNote.addActionListener(e -> selectedNote = NotesEnum.Closed);
        regularNote.addActionListener(e -> selectedNote = NotesEnum.Regular);
        barreNote.addActionListener(e -> selectedNote = NotesEnum.Barre);

        launchFretSetup.addActionListener(e -> {
            BoardSetup dialog = new BoardSetup(numberOfStrings, numberOfFrets, rootNote);
            dialog.setTitle("Set up the Fretboard");
            Toolkit kit = Toolkit.getDefaultToolkit();
            Image img = kit.createImage(ClassLoader.getSystemResource("net/roymond/Resources/Icon.png"));
            dialog.setIconImage(img);
            dialog.pack();
            dialog.setVisible(true);

            HashMap<String, Integer> results = dialog.getResults();
            if (!results.isEmpty()) {
                numberOfFrets = results.get("frets");
                numberOfStrings = results.get("strings");
                rootNote = results.get("root");
                createBaseImage();
            }
        });

        exportChord.addActionListener(e -> {
            ExportDialog dialog = new ExportDialog(chordGraphic, chordImg);
            dialog.setTitle("Export the Chord!");
            Toolkit kit = Toolkit.getDefaultToolkit();
            Image img = kit.createImage(ClassLoader.getSystemResource("net/roymond/Resources/Icon.png"));
            dialog.setIconImage(img);
            dialog.pack();
            dialog.setVisible(true);
        });
    }

}
