package programbuilder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * JPanel with a function to perform with optional options
 * @author Nicolas
 */
public class DataPane extends JPanel{    
    
    private final JLabel title;
    private final Border blackline;
    private final LabeledDataField[] dataFields;
    
    public DataPane(String title, String[] dataLabels){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.title = new JLabel(title);
        this.blackline = BorderFactory.createLineBorder(Color.black);
        this.setPreferredSize(new Dimension(300, 250));
        this.setBorder(blackline);
        this.add(this.title);
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
