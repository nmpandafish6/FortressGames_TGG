package programbuilder.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Creates a generic text field with a label to the left of it
 * @author Nicolas
 */
public class LabeledDataField extends JPanel{
    
    private final JLabel dataField;
    private final JLabel label;
    /**
     * Constructor
     * @param title label to data field
     */
    public LabeledDataField(String title){
        dataField = new JLabel("0.0");
        label = new JLabel(title);
        this.add(label);
        this.add(dataField);
    }
    
    public void put(String data){
        this.dataField.setText(data);
    }
}
