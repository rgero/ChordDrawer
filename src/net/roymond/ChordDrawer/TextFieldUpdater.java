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
    private ChordDrawer baseClass;

    TextFieldUpdater(ChordDrawer base, JTextField textField){
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

    private  void updateValue(){
        String newValue = textField.getText();
        String name = textField.getName();
        if ( !newValue.equals("") ) {
            int val = Integer.valueOf(newValue);
            if (name.equals("strings")) {
                baseClass.numberOfStrings = val;
            } else if (name.equals("frets")){
                baseClass.numberOfFrets = val;
            }
            if (val >= 2 & val <= 12){
                baseClass.createBaseImage();
            } else {
                JOptionPane.showMessageDialog(null, String.format("The value you entered, %d, is invalid.\nPlease enter a number between 2 and 12.", val));
            }
        }
    }

}
