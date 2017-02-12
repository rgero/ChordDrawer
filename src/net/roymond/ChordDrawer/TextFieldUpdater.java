package net.roymond.ChordDrawer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A custom document listener that is for the two text fields on the ChordDrawer. This should not be used anywhere else.
 * Created by Roymond on 2/11/2017.
 */
class TextFieldUpdater implements DocumentListener {

    private JTextField textField;
    private BoardSetup baseClass;

    TextFieldUpdater(BoardSetup base, JTextField textField){
        this.textField = textField;
        this.baseClass = base;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateValue();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateValue();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateValue();
    }

    private boolean checkValue(int val, int low, int high){
        if (val >= low & val <= high){
            return true;
        } else {
            JOptionPane.showMessageDialog(null, String.format("The value you entered, %d, is invalid.\nPlease enter a number between %d and %d.", val, low, high));
            return false;
        }
    }

    private  void updateValue(){
        String newValue = textField.getText();
        String name = textField.getName();
        if ( !newValue.equals("") ) {
            int val = Integer.valueOf(newValue);
            switch (name) {
                case "strings":
                    if( checkValue(val, 2, 12)) {
                        baseClass.numberOfStrings = val + 1;
                    }
                    break;
                case "frets":
                    if( checkValue(val, 2, 12)) {
                        baseClass.numberOfFrets = val + 1;
                    }
                    break;
                case "root":
                    if( checkValue(val, 0, 20)) {
                        baseClass.rootNote = val;
                    }
                    break;
            }
        }
    }

}
