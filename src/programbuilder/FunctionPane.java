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
        this.blackline = BorderFactory.createLineBorder(Color.black);
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
        this.setPreferredSize(new Dimension(300, 300));
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
        return b;
    }
}
