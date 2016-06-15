package programbuilder.components.menu;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import javax.swing.*;
import programbuilder.components.GUI_Frame;
import programbuilder.components.HelpPane;
import programbuilder.resources.*;
import terraingenerator.*;

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
                importImage.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        double[][] image = TGG_ImageUtil.readDataFromImage(
                        TGG_ImageUtil.getImageFromFile(TGG_FileOperations.chooseOpenFile("Open Image").getPath()));
                        Resources.dataArrayList.set(0, image);
                        GUI_Frame.updateImage(Resources.dataArrayList.get(0));
                        GUI_Frame.updateStats(Resources.dataArrayList.get(0));
                    }
                });
                fileMenu.add(importImage);
            JMenuItem exportRaw_8t = new JMenuItem("Export Raw (8 bit)");
                exportRaw_8t.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TGG_FileOperations.write8BitBinary(Resources.dataArrayList.get(Resources.activeArrayElement));
                    }
                });
                fileMenu.add(exportRaw_8t);
            JMenuItem exportRaw_16t = new JMenuItem("Export Raw (16 bit)");
                exportRaw_16t.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TGG_FileOperations.write16BitBinary(Resources.dataArrayList.get(Resources.activeArrayElement));
                    }
                });
                fileMenu.add(exportRaw_16t);
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
                        TGG_FileOperations.write8BitBinary(Resources.dataArrayList.get(Resources.activeArrayElement));
                        TGG_FileOperations.write16BitBinary(Resources.dataArrayList.get(Resources.activeArrayElement));
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
                            GUI_Frame.updateImage(Resources.dataArrayList.get(0));
                            GUI_Frame.updateStats(Resources.dataArrayList.get(0));
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
            JMenuItem helpItem = new JMenuItem("How To Guide");
                helpItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            JFrame helpFrame = new JFrame();
                            helpFrame.add(new HelpPane());
                            helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            helpFrame.setVisible(true);
                            helpFrame.pack();
                        }
                    });
            helpMenu.add(helpItem);
        this.add(helpMenu);
    }
}
