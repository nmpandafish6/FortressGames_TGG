package programbuilder.components.panels;

import programbuilder.components.dataFields.*;
import programbuilder.resources.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * JPanel with a function to perform with optional options
 * @author Nicolas
 */
public abstract class BinaryFunctionPane extends JPanel{    
    
    private final LabeledFileTextField[] fileOptions;
    
    public BinaryFunctionPane(String titleString, int options){
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
        this.fileOptions = new LabeledFileTextField[options];
        for(int i = 0; i < options; i++){
            this.fileOptions[i] = new LabeledFileTextField("Matrix " + i);
            this.add(box(this.fileOptions[i]));
        }
        this.add((button));
        this.setPreferredSize(new Dimension(Constants.FUNCTION_PANE_WIDTH, 
                (int) (this.getPreferredSize().height+10)));
        for(int i = 0; i < options; i++)
            this.fileOptions[i].validate();
    }

    public abstract void function();
    
    public String getOption(int i){
        return this.fileOptions[i].get();
    }
    
    public String[] getAllOptions(){
        String[] values = new String[this.fileOptions.length];
        for(int i = 0; i < this.fileOptions.length; i++){
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
