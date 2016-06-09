package programbuilder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.Box;
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
        this.setLayout(new BorderLayout());
        textField = new JTextField(6);
        label = new JLabel(title);
        this.add(label, BorderLayout.WEST);
        this.add(textField, BorderLayout.EAST);
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
