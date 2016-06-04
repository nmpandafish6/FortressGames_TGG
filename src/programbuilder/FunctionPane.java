/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package programbuilder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author Nicolas
 */
public abstract class FunctionPane extends JPanel{    
    
    JLabel title;
    JButton button;
    Border blackline = BorderFactory.createLineBorder(Color.black);
    public LabeledTextField[] options;
    
    public FunctionPane(String title, String[] options){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.title = new JLabel(title);
        this.button = new JButton("Apply");
        this.button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                function();
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        this.setPreferredSize(new Dimension(300, 250));
        this.setBorder(blackline);
        this.add(this.title);
        this.options = new LabeledTextField[options.length];
        for(int i = 0; i < options.length; i++){
            
            this.options[i] = new LabeledTextField(options[i]);
            
            this.options[i].setMaximumSize(this.options[i].getPreferredSize());
            this.options[i].setAlignmentX(0);
            this.add(leftJustify(this.options[i]));
        }
        this.add((this.button));
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
    
    private Component leftJustify( JComponent panel )  {
    Box  b = Box.createHorizontalBox();
    b.add( panel );
    b.add( Box.createHorizontalGlue() );
    // (Note that you could throw a lot more components
    // and struts and glue in here.)
    return b;
}
}
