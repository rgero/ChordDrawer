package net.roymond.ChordDrawer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Main {

    public static void main(String[] args) {



        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(ClassLoader.getSystemResource("net/roymond/Resources/Icon.png"));


        JFrame frame = new JFrame("ChordDrawer");
        frame.setTitle("Roy's Chord Drawer");
        frame.setContentPane(new ChordDrawer().DrawerWindow);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setIconImage(img);

    }
}
