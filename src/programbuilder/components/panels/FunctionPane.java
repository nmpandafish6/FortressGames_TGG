package programbuilder.components.panels;

import programbuilder.components.dataFields.LabeledTextField;
import programbuilder.resources.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * JPanel with a function to perform with optional options
 * @author Nicolas
 */
public abstract class FunctionPane extends JPanel{    
    
    private boolean collapsed = false;
    private final JButton collapseButton;
    private final JPanel masterPanel;
    private final LabeledTextField[] options;
    
    public FunctionPane(String titleString, String[] options){
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
        JButton button = new JButton("Apply");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                function();
            }
        });
        masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));   
        
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(titlePanel);
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.add(collapseButton, BorderLayout.EAST);
        titlePanel.setMaximumSize(new Dimension(titlePanel.getMaximumSize().width, titlePanel.getPreferredSize().height));
        this.options = new LabeledTextField[options.length];
        for(int i = 0; i < options.length; i++){
            this.options[i] = new LabeledTextField(options[i]);
            masterPanel.add(box(this.options[i]));
        }
        this.add(masterPanel);
        masterPanel.add((button));
        masterPanel.setPreferredSize(new Dimension(Constants.FUNCTION_PANE_WIDTH, 
                (int) (masterPanel.getPreferredSize().height+10)));
        for(int i = 0; i < options.length; i++){
            this.options[i].validate();
        }
        
    }

    public abstract void function();
    
    public double getOption(int i){
        try{
            return Double.parseDouble(this.options[i].get());
        }catch(Exception e){
            return 0;
        }
    }
    
    public double[] getAllOptions(){
        double[] values = new double[this.options.length];
        for(int i = 0; i < this.options.length; i++){
            values[i] = getOption(i);
        }
        return values;
    }
    
    private Component box( JComponent panel )  {
        Box  b = Box.createHorizontalBox();
        b.add( panel );
        return b;
    }
}
