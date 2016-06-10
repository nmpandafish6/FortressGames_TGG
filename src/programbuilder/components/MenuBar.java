/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package programbuilder.components;

import programbuilder.resources.Resources;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import static programbuilder.components.GUI_Frame.updateImage;
import static programbuilder.components.GUI_Frame.updateStats;
import terraingenerator.TGG_FileOperations;

/**
 *
 * @author Nicolas
 */
public class MenuBar extends JMenuBar{
    
    public MenuBar(){
        JMenu fileMenu = new JMenu("File");
        this.add(fileMenu);
            JMenuItem newTerrainItem = new JMenuItem("New Terrain");
                newTerrainItem.setEnabled(false);
                fileMenu.add(newTerrainItem);
            JMenuItem importRaw = new JMenuItem("Import Raw");
                importRaw.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Resources.dataArrayList.set(0, TGG_FileOperations.readBinary());
                        GUI_Frame.updateImage(Resources.dataArrayList.get(0));
                        GUI_Frame.updateStats(Resources.dataArrayList.get(0));
                    }
                });
                fileMenu.add(importRaw);
            JMenuItem importImage = new JMenuItem("Import Image");
                importImage.setEnabled(false);
                fileMenu.add(importImage);
            JMenuItem exportRaw = new JMenuItem("Export Raw");
                exportRaw.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TGG_FileOperations.writeBinary(Resources.dataArrayList.get(Resources.activeArrayElement));
                    }
                });
                fileMenu.add(exportRaw);
            JMenuItem exportImage = new JMenuItem("Export Image");
                exportImage.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TGG_FileOperations.writeImage(Resources.dataArrayList.get(Resources.activeArrayElement));
                    }
                });
                fileMenu.add(exportImage);
            JMenuItem exportAll = new JMenuItem("Export All");
                exportAll.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TGG_FileOperations.writeBinary(Resources.dataArrayList.get(Resources.activeArrayElement));
                        TGG_FileOperations.writeImage(Resources.dataArrayList.get(Resources.activeArrayElement));
                    }
                });
                fileMenu.add(exportAll);
        JMenu editMenu = new JMenu("Edit");
            JMenuItem undo = new JMenuItem("Undo");
                undo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            Resources.dataArrayList.set(0, Resources.undoArrayList.get(Resources.activeArrayElement));
                            updateImage(Resources.dataArrayList.get(0));
                            updateStats(Resources.dataArrayList.get(0));
                        }
                    });
            editMenu.add(undo);
        this.add(editMenu);
        JMenu settingsMenu = new JMenu("Settings");
            JMenuItem temp1 = new JMenuItem("Coming Soon...");
            temp1.setEnabled(false);
            settingsMenu.add(temp1);
        this.add(settingsMenu);
        JMenu helpMenu = new JMenu("Help");
            JMenuItem temp2 = new JMenuItem("Coming Soon...");
            temp2.setEnabled(false);
            helpMenu.add(temp2);
        this.add(helpMenu);
    }
}
