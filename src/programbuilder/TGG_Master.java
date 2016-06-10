package programbuilder;

import programbuilder.components.GUI_Frame;
import javax.swing.JFrame;

/**
 * Terrain Gen Gui Master File
 * Creates the Graphical Interface for the main program
 * @author Nicolas
 */
public class TGG_Master {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GUI_Frame frame = GUI_Frame.getInstance();
        frame.setTitle("Fortress Games TGG");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }
    
}
