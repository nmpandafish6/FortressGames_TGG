package programbuilder.components.dataFields;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import terraingenerator.*;

/**
 * Creates a generic text field with a label to the left of it
 * @author Nicolas
 */
public class LabeledFileTextField extends JPanel{
    
    private final JTextField textField;
    /**
     * Constructor
     * @param title label to text field
     */
    public LabeledFileTextField(String title){
        this.setLayout(new BorderLayout());
        textField = new JTextField(6);
        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = TGG_FileOperations.chooseOpenFile("Open Raw");
                textField.setText(file.getPath());
            }
        });
        this.add(new JLabel(title), BorderLayout.WEST);
        this.add(textField, BorderLayout.CENTER);
        this.add(browseButton, BorderLayout.EAST);
        this.setMaximumSize(new Dimension(this.getMaximumSize().width, this.getPreferredSize().height));
    }
    
    @Override
    public void validate(){
        this.setPreferredSize(new Dimension(this.getParent().getParent().getPreferredSize().width, this.getPreferredSize().height));
        super.validate();
    }
    /**
     * Gets the text from the text field
     * @return the text from the text field
     */
    public String get(){
        return this.textField.getText();
    }
}
