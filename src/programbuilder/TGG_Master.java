package programbuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import programbuilder.components.GUI_Frame;
import programbuilder.resources.Resources;
import terraingenerator.TGG_FileOperations;

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
        System.out.println("HEY");
        if(args.length > 0){
            System.out.println(args[0]);
                        Resources.dataArrayList.set(0, TGG_FileOperations.readBinary(args[0]));
                        GUI_Frame.updateImage(Resources.dataArrayList.get(0));
                        GUI_Frame.updateStats(Resources.dataArrayList.get(0));
        }
    }
    
}
