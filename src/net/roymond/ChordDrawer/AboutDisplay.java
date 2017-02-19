package net.roymond.ChordDrawer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The projects Help > About
 * Created by Roymond on 2/14/2017.
 */
public class AboutDisplay {
    JPanel About;
    private JEditorPane editPane;
    private JButton sourceCodeButton;

    AboutDisplay(){
        try {
            editPane.setContentType("text/html");
            editPane.setPage(ClassLoader.getSystemResource("net/roymond/Resources/about.html"));
        } catch (IOException e) {
            editPane.setContentType("text/html");
            editPane.setText("<html>Could not load about document.</html>");
        }

        sourceCodeButton.addActionListener(e -> {
            try {
                URL webPage = new URL("https://github.com/rgero/ChordDrawer");
                Desktop.getDesktop().browse(webPage.toURI());
            } catch (URISyntaxException | IOException e1) {
                e1.printStackTrace();
            }


        });



    }
}
