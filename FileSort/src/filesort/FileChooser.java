/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filesort;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 *
 * @author Nicolas
 */
public class FileChooser extends JPanel{

    public final JFileChooser fileChooser;
    public int action = -1;
    public int returnVal = -1;
    
    public FileChooser(){
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setMultiSelectionEnabled(true);
    }
    
    public File[] performAction(int actionCode){
        action = actionCode;
        if(action == 0){
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.setMultiSelectionEnabled(true);
            returnVal = fileChooser.showOpenDialog(FileChooser.this);
        }else if(action == 1){
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setMultiSelectionEnabled(false);
            returnVal = fileChooser.showSaveDialog(FileChooser.this);
        }
        File[] files = null;
        if (action == 0) {
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                files = fileChooser.getSelectedFiles();
                //This is where a real application would open the file.
                System.out.println("Opening: " + Arrays.toString(files) + ".");
            } else {
                System.out.println("Open command cancelled by user.");
            }
        } else if (action == 1) {
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                files = new File[]{fileChooser.getSelectedFile()};
                //This is where a real application would save the file.
                System.out.println("Saving: " + Arrays.toString(files) + ".");
            } else {
                System.out.println("Save command cancelled by user.");
            }
        }
        return files;
    }
    
}
