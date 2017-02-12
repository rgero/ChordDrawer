package net.roymond.ChordDrawer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Created by Roymond on 2/11/2017.
 */
class TextFieldUpdater implements DocumentListener {

    JTextField textField;
    ChordDrawer baseClass;

    TextFieldUpdater(ChordDrawer base, JTextField textField){
        this.textField = textField;
        this.baseClass = base;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        System.out.println("called");
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

    public void updateValue(){
        String newValue = textField.getText();
        String name = textField.getName();
        if ( !newValue.equals("") ) {
            if (name.equals("strings")) {
                baseClass.numberOfStrings = Integer.valueOf(newValue);
            } else if (name.equals("frets")){
                baseClass.numberOfFrets = Integer.valueOf(newValue);
            }
            if (Integer.valueOf(newValue) > 2){
                baseClass.createBaseImage();
            }
        }
    }

}
