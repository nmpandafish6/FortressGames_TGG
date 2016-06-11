package programbuilder.components.panels;

import programbuilder.components.dataFields.LabeledDataField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;
import programbuilder.components.dataFields.LabeledTextField;
import programbuilder.resources.Constants;

/**
 * JPanel with a function to perform with optional options
 * @author Nicolas
 */
public class DataPane extends JPanel{    
    
    private boolean collapsed = false;
    private final JButton collapseButton;
    private final JPanel masterPanel;
    private final LabeledDataField[] dataFields;
    
    public DataPane(String titleString, String[] dataLabels){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel(titleString);
        collapseButton = new JButton("^");
        collapseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collapsed = !collapsed;
                if(collapsed){
                    remove(masterPanel);
                    collapseButton.setText("v");
                }else{
                    add(masterPanel);
                    collapseButton.setText("^");
                }
            }
        });
        collapseButton.setMargin(new Insets(0,6,0,6));
        masterPanel = new JPanel();
        masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(titlePanel);
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.add(collapseButton, BorderLayout.EAST);
        titlePanel.setMaximumSize(new Dimension(titlePanel.getMaximumSize().width, titlePanel.getPreferredSize().height));
        this.dataFields = new LabeledDataField[dataLabels.length];
        for(int i = 0; i < dataLabels.length; i++){
            this.dataFields[i] = new LabeledDataField(dataLabels[i]);
            this.dataFields[i].setMaximumSize(this.dataFields[i].getPreferredSize());
            this.dataFields[i].setAlignmentX(0);
            masterPanel.add(leftJustify(this.dataFields[i]));
        }
        this.add(masterPanel);
        masterPanel.setPreferredSize(new Dimension(Constants.FUNCTION_PANE_WIDTH, 
                (int) (masterPanel.getPreferredSize().height+10)));
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
