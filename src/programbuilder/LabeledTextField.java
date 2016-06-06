package programbuilder;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
        textField = new JTextField(6);
        label = new JLabel(title);
        this.add(label);
        this.add(textField);
    }
    
    /**
     * Gets the text from the text field
     * @return the text from the text field
     */
    public String get(){
        return this.textField.getText();
    }
}
