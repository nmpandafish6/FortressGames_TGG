/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package programbuilder;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import terraingenerator.DiamondSquareFractal;
import terraingenerator.TerrainGenMaster;

/**
 *
 * @author Nicolas
 */
public class GUI_Frame extends JFrame{
    
    JLabel picLabel;
    public GUI_Frame(){
        JPanel imagePanel = new JPanel();
        JScrollPane functionScrollPane = new JScrollPane();
        JViewport functionPanelViewport = new JViewport();
        JPanel masterPanel = new JPanel();
        functionPanelViewport.add(masterPanel);
        functionScrollPane.setViewport(functionPanelViewport);
        masterPanel.setPreferredSize(new Dimension(350, 1550));
        functionPanelViewport.setPreferredSize(new Dimension(350, 650));
        functionScrollPane.getVerticalScrollBar().setUnitIncrement(30);
        
        Image image = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
        image = image.getScaledInstance(650,650,Image.SCALE_SMOOTH);
        picLabel = new JLabel(new ImageIcon(image));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                           functionScrollPane, picLabel);
        FunctionPane diamondSquare = new FunctionPane("<html><h3>Diamond Square()</h3></html>", new String[]{
            "Size", "Random Divider", "Upper Left", "Bottom Left", "Upper Right", "Bottom Right"}) {

            @Override
            public void function() {
                DiamondSquareFractal.diamondSquareGenerate(this.getAllOptions());
                updateImage();
            }
        };
        masterPanel.add(diamondSquare);
        
        FunctionPane flood = new FunctionPane("<html><h3>Flood()</h3></html>", new String[]{
            "Height"}) {

            @Override
            public void function() {
                TerrainGenMaster.flood(DiamondSquareFractal.a, (short) this.getOption(0));
                updateImage();
            }
        };
        masterPanel.add(flood);
        
        FunctionPane laplacian = new FunctionPane("<html><h3>Laplacian()</h3></html>", new String[]{
            "Repeat"}) {

            @Override
            public void function() {
                int repeatTimes = (int) this.getOption(0);
                for(int i = 0; i < repeatTimes; i++){
                    TerrainGenMaster.laplacianSmooth(DiamondSquareFractal.a);
                }
                updateImage();
            }
        };
        masterPanel.add(laplacian);
        
        FunctionPane shape = new FunctionPane("<html><h3>MakeFlatShape()</h3></html>", new String[]{
            "Number of Sides of Shape", "Center X", "Center Y", "Radius", "Height"}) {

            @Override
            public void function() {
                TerrainGenMaster.makeFlatShape(DiamondSquareFractal.a, this.getAllOptions());
                updateImage();
            }
        };
        masterPanel.add(shape);  
        
        FunctionPane keepShape = new FunctionPane("<html><h3>keepShape()</h3></html>", new String[]{
            "Number of Sides of Shape", "Center X", "Center Y", "Radius"}) {

            @Override
            public void function() {
                TerrainGenMaster.keepShape(DiamondSquareFractal.a, this.getAllOptions());
                updateImage();
            }
        };
        masterPanel.add(keepShape);        
        
        
        FunctionPane write = new FunctionPane("<html><h3>Write()</h3></html>", new String[]{
            }) {

            @Override
            public void function() {
                //TerrainGenMaster.write();
                TerrainGenMaster.writeImage();
                TerrainGenMaster.writeBinary();
            }
        };
        masterPanel.add(write);
        
        

        this.add(splitPane);
    }
    
    public void updateImage(){
        Image image = TerrainGenMaster.getUpdatedImage().getScaledInstance(650, 650, Image.SCALE_SMOOTH);
        picLabel.setIcon(new ImageIcon(image));
    }
}
