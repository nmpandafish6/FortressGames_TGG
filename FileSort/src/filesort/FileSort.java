/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filesort;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Nicolas
 */
public class FileSort {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame masterFrame = new JFrame("File Sorter");
        MasterGUI masterPanel = new MasterGUI();
        masterFrame.add(masterPanel);
        masterFrame.setVisible(true);
        masterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //masterFrame.setMinimumSize(new Dimension(500, 300));
        masterFrame.pack();
        
        
        FileChooser fc = new FileChooser();
        fc.performAction(0);
    }
    
}
