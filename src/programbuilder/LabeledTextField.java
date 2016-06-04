/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package programbuilder;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Nicolas
 */
public class LabeledTextField extends JPanel{
    
    JTextField textField;
    JLabel label;
    public LabeledTextField(String title){
        textField = new JTextField(6);
        label = new JLabel(title);
        this.add(label);
        this.add(textField);
    }
    
    public String get(){
        return this.textField.getText();
    }
}
