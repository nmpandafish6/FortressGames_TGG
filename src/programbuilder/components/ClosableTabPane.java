/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package programbuilder.components;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Nicolas
 */
public class ClosableTabPane extends JTabbedPane{
    
    public ClosableTabPane(){
        super();
    }
    
    public void addClosableTab(String title, JComponent tabBody){
        this.addTab(title, tabBody);
        int index = this.indexOfComponent(tabBody);
        JPanel pnlTab = new JPanel();
        pnlTab.setOpaque(false);
        JLabel lblTitle = new JLabel(title);
        JButton btnClose = new JButton("x");
        btnClose.setMargin(new Insets(0,0,0,0));
        GridBagConstraints gbc = new GridBagConstraints();
   
        pnlTab.add(lblTitle);

        gbc.gridx++;
        gbc.weightx = 0;
        pnlTab.add(btnClose);

        this.setTabComponentAt(index, pnlTab);

    }
}
