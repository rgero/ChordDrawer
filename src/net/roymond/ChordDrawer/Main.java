package net.roymond.ChordDrawer;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("ChordDrawer");
        frame.setTitle("Roy's Chord Drawer");
        frame.setContentPane(new ChordDrawer().DrawerWindow);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

    }
}
