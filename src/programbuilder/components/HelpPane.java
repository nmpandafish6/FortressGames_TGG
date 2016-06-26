/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package programbuilder.components;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.text.BadLocationException;
import javax.swing.text.rtf.RTFEditorKit;

/**
 *
 * @author Nicolas
 */
public class HelpPane extends JPanel{
    
    /**
     * Constructor
     */
    public HelpPane(){
        FileInputStream fi = null;
        try {
            
            RTFEditorKit rtf = new RTFEditorKit();
            JEditorPane editor = new JEditorPane();
            editor.setPreferredSize(new Dimension(800, 600));
            editor.setEditorKit(rtf);
            JViewport helpViewport = new JViewport();
            helpViewport.add(editor);
            JScrollPane helpScrollPane = new JScrollPane();
            helpScrollPane.setViewport(helpViewport);
            helpViewport.setPreferredSize(new Dimension(800, 600));
        
            editor.setEditable(false);
            InputStream stream = HelpPane.class.getResourceAsStream("/resources/help.rtf");
            System.out.println();
            //fi = (FileInputStream) stream;
            
            rtf.read(stream, editor.getDocument(), 0);
            this.add(helpScrollPane);
            editor.setCaretPosition(0);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HelpPane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HelpPane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadLocationException ex) {
            Logger.getLogger(HelpPane.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
        }
    }
}
