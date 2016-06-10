package programbuilder.components.dataFields;

import javax.swing.*;

/**
 * Creates a generic text field with a label to the left of it
 * @author Nicolas
 */
public class LabeledDataField extends JPanel{
    
    private final JLabel dataField;
    /**
     * Constructor
     * @param title label to data field
     */
    public LabeledDataField(String title){
        dataField = new JLabel("0.0");
        this.add(new JLabel(title));
        this.add(dataField);
    }
    
    public void put(String data){
        this.dataField.setText(data);
    }
}
