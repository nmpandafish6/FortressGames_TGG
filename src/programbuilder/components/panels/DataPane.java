package programbuilder.components.panels;

import programbuilder.components.dataFields.LabeledDataField;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * JPanel with a function to perform with optional options
 * @author Nicolas
 */
public class DataPane extends JPanel{    
    
    private final LabeledDataField[] dataFields;
    
    public DataPane(String titleString, String[] dataLabels){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel(titleString);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(title);
        this.dataFields = new LabeledDataField[dataLabels.length];
        for(int i = 0; i < dataLabels.length; i++){
            this.dataFields[i] = new LabeledDataField(dataLabels[i]);
            this.dataFields[i].setMaximumSize(this.dataFields[i].getPreferredSize());
            this.dataFields[i].setAlignmentX(0);
            this.add(leftJustify(this.dataFields[i]));
        }
        this.updateData(new double[dataLabels.length]);
    }
    
    public void updateData(double[] data){
        for(int i = 0; i < data.length; i++){
            this.dataFields[i].put("" + data[i]);
        }
    }
    
    private Component leftJustify( JComponent panel )  {
        Box  b = Box.createHorizontalBox();
        b.add( panel );
        b.add( Box.createHorizontalGlue() );
        return b;
    }
}
