package programbuilder.components;

import programbuilder.resources.Constants;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public abstract class FunctionPane extends JPanel{    
    
    private final JLabel title;
    private final JButton button;
    private final Border blackline;
    private final LabeledTextField[] options;
    
    public FunctionPane(String title, String[] options){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.title = new JLabel(title);
        this.button = new JButton("Apply");
        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                function();
            }
        });
        this.blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);
        this.add(this.title);
        this.options = new LabeledTextField[options.length];
        for(int i = 0; i < options.length; i++){
            this.options[i] = new LabeledTextField(options[i]);
            this.add(box(this.options[i]));
        }
        this.add((this.button));
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
