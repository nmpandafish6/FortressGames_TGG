/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filesort;

import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nicolas
 */
public class MasterGUI extends JPanel {
    
    public MasterGUI(){
        super(new GridLayout(1,1));
        JTabbedPane tabbedPane = new JTabbedPane();
        
        JComponent panel1 = makeTextPanel("Hey");
        JComponent panel2 = makeTextPanel("Hey");
        JComponent panel3 = makeTextPanel("Hey");
        JComponent panel4 = makeTextPanel("Hey");
        JComponent panel5 = makeTextPanel("Hey");
        tabbedPane.addTab("Sort Files", panel1);
        tabbedPane.addTab("Rename Files", panel2);
        tabbedPane.addTab("Delete Files", panel3);
        tabbedPane.addTab("Keep Files", panel4);
        tabbedPane.addTab("Change Permissions", panel5);
        
        this.add(tabbedPane);
    }
    
    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
}
