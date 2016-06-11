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
    

    private boolean collapsed = false;
    private final JButton collapseButton;
    private final JPanel masterPanel;
    private final LabeledFileTextField[] fileOptions;
    
    public BinaryFunctionPane(String titleString, int options){
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
        this.fileOptions = new LabeledFileTextField[options];
        for(int i = 0; i < options; i++){
            this.fileOptions[i] = new LabeledFileTextField("Matrix " + i);
            masterPanel.add(box(this.fileOptions[i]));
        }
        masterPanel.add((button));
        this.add(masterPanel);
        masterPanel.setPreferredSize(new Dimension(Constants.FUNCTION_PANE_WIDTH, 
                (int) (masterPanel.getPreferredSize().height+10)));
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
