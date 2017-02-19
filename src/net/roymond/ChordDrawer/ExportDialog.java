package net.roymond.ChordDrawer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ExportDialog extends JDialog {
    private JPanel contentPane;
    private JButton exportButton;
    private JButton closeButton;
    private JTextField exportDirectoryTextfield;
    private JButton browseButton;
    private JTextField chordNameTextfield;

    private Graphics2D chord;
    private BufferedImage img;
    private String exportDir;

    ExportDialog(Graphics2D chord, BufferedImage img) {
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        getRootPane().setDefaultButton(exportButton);

        this.chord = chord;
        this.img = img;

        ImageIcon imgIcon = new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("net/roymond/Resources/FolderIcon.png")).getImage().getScaledInstance(16,16,Image.SCALE_DEFAULT));
        browseButton.setIcon(imgIcon);

        exportButton.addActionListener(e -> exportChord());

        closeButton.addActionListener(e -> onCancel());

        browseButton.addActionListener(e -> {
            JFileChooser exportDirChooser = new JFileChooser();
            exportDirChooser.setCurrentDirectory(new File("."));
            exportDirChooser.setDialogTitle("Select an export directory");
            exportDirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            exportDirChooser.setAcceptAllFileFilterUsed(false);
            int returnVal = exportDirChooser.showOpenDialog(contentPane);
            if(returnVal == JFileChooser.APPROVE_OPTION) {

                exportDir = exportDirChooser.getSelectedFile().getAbsolutePath();
                exportDirectoryTextfield.setText(exportDir);

                System.out.println("You chose to open this file: " +
                        exportDirChooser.getSelectedFile().getName());
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void exportChord() {
        String exportDir = exportDirectoryTextfield.getText();
        String chordName = chordNameTextfield.getText();

        if (!Objects.equals(exportDir, "") & !Objects.equals(chordName, "")){
            chordName += ".png";
            File outputFile = new File(exportDir, chordName);
            chord.drawImage(img, null, 0,0);
            try {
                ImageIO.write(img, "PNG", outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
