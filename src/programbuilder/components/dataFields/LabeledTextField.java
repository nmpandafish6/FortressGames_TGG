package programbuilder.components.dataFields;

import java.awt.*;
import javax.swing.*;

/**
 * Creates a generic text field with a label to the left of it
 * @author Nicolas
 */
public class LabeledTextField extends JPanel{
    
    private final JTextField textField;
    private final JLabel label;
    /**
     * Constructor
     * @param title label to text field
     */
    public LabeledTextField(String title){
        this.setLayout(new BorderLayout());
        textField = new JTextField(6);
        label = new JLabel(title);
        this.add(label, BorderLayout.WEST);
        this.add(textField, BorderLayout.EAST);
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
    
    /**
     * Sets text. Used for default values
     * @param value value
     */
    public void setTextField(double value){
        this.textField.setText("" + value);
    }
}
