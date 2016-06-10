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
    
    
    private final LabeledTextField[] options;
    
    public FunctionPane(String titleString, String[] options){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel(titleString);
        JButton button = new JButton("Apply");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                function();
            }
        });
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(title);
        this.options = new LabeledTextField[options.length];
        for(int i = 0; i < options.length; i++){
            this.options[i] = new LabeledTextField(options[i]);
            this.add(box(this.options[i]));
        }
        this.add((button));
        this.setPreferredSize(new Dimension(Constants.FUNCTION_PANE_WIDTH, 
                (int) (this.getPreferredSize().height+10)));
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
