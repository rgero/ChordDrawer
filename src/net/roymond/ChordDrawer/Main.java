package net.roymond.ChordDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
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

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("The About lives here");

        Action aboutAction = new AbstractAction("About Menu") {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame aboutDisplay = new JFrame("AboutDisplay");
                aboutDisplay.setTitle("About Roy's Chord Drawer");
                aboutDisplay.setContentPane(new AboutDisplay().About);
                aboutDisplay.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                aboutDisplay.pack();
                aboutDisplay.setResizable(false);
                aboutDisplay.setVisible(true);
            }
        };

        JMenuItem menuItem = new JMenuItem();
        menuItem.setAction(aboutAction);
        menu.add(menuItem);
        menuBar.add(menu);


        frame.setJMenuBar(menuBar);


        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setIconImage(img);

    }
}
