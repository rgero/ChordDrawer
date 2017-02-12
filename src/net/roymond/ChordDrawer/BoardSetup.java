package net.roymond.ChordDrawer;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.event.*;
import java.util.HashMap;

public class BoardSetup extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField stringsTextField;
    private JTextField fretsTextField;
    private JTextField rootTextField;

    int numberOfStrings;
    int numberOfFrets;
    int rootNote;
    private HashMap<String, Integer> results = new HashMap<>();

    BoardSetup(int numberOfStrings, int numberOfFrets, int rootNote) {
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        getRootPane().setDefaultButton(buttonOK);

        this.numberOfFrets = numberOfFrets;
        this.numberOfStrings = numberOfStrings;
        this.rootNote = rootNote;

        stringsTextField.setText( String.valueOf(numberOfStrings) );
        fretsTextField.setText( String.valueOf( numberOfFrets - 1));
        rootTextField.setText( String.valueOf( rootNote ));


        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        PlainDocument stringsField = (PlainDocument) stringsTextField.getDocument();
        stringsField.setDocumentFilter(new IntFilter());
        stringsField.addDocumentListener( new TextFieldUpdater(this, stringsTextField) );


        PlainDocument fretsField = (PlainDocument)  fretsTextField.getDocument();
        fretsField.setDocumentFilter(new IntFilter());
        fretsField.addDocumentListener( new TextFieldUpdater(this, fretsTextField));

        PlainDocument rootField = (PlainDocument) rootTextField.getDocument();
        rootField.setDocumentFilter(new IntFilter());
        rootField.addDocumentListener( new TextFieldUpdater(this, rootTextField));

    }

    private void onOK() {
        results.put("strings", numberOfStrings);
        results.put("frets", numberOfFrets);
        results.put("root", rootNote);
        dispose();
    }

    private void onCancel() {
        results.clear();
        dispose();
    }

    HashMap<String, Integer> getResults(){
        return results;
    }
}
