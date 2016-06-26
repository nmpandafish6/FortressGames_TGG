package programbuilder.components.dataFields;

import java.awt.Dimension;
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
        this.setMaximumSize(new Dimension(this.getMaximumSize().width, this.getPreferredSize().height));
    }
    
    /**
     * Puts data into the data field
     * @param data data
     */
    public void put(String data){
        this.dataField.setText(data);
    }
}
