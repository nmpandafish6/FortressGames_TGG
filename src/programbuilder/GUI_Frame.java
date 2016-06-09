package programbuilder;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import terraingenerator.DiamondSquareFractal;
import terraingenerator.TerrainGenMaster;
import util.ArrayUtil;
import util.StatsUtil;
import util.StatsUtil.Stats;

/**
 * Main Frame for the GUI
 * @author Nicolas
 */
public class GUI_Frame extends JFrame{
    
    private final JLabel picLabel;
    private final JScrollPane functionScrollPane;
    private final JPanel functionPanel;
    private double[][] undoList;
    private double[][] dataArray_List;
    private DataPane stats;
    public GUI_Frame(){
        dataArray_List = new double[129][129];
        picLabel = new JLabel();
        functionScrollPane = new JScrollPane();
        functionPanel = new JPanel();
        generateScrollPane();
        updateImage(null);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                           functionScrollPane, picLabel);
        this.addFunctions(functionPanel);
        this.add(splitPane);
    }
    
    private void generateScrollPane(){
        JViewport functionPanelViewport = new JViewport();
        functionPanelViewport.add(functionPanel);
        functionScrollPane.setViewport(functionPanelViewport);
        functionPanel.setPreferredSize(new Dimension(350, 4000));
        functionPanelViewport.setPreferredSize(new Dimension(350, 650));
        functionScrollPane.getVerticalScrollBar().setUnitIncrement(30);
    }
    
    private void addFunctions(JComponent component){
        FunctionPane diamondSquare = new FunctionPane("<html><h3>Diamond Square()</h3></html>", new String[]{
            "Size", "Random Divider", "Upper Left", "Bottom Left", "Upper Right", "Bottom Right",
            "Random Scalar"}) {
            @Override
            public void function() {
                undoList = ArrayUtil.copyArray(dataArray_List);
                dataArray_List = DiamondSquareFractal.diamondSquareGenerate(this.getAllOptions());
                updateImage(dataArray_List);
                updateStats(dataArray_List);
            }
        };
        component.add(diamondSquare);
        
        FunctionPane flood = new FunctionPane("<html><h3>Flood()</h3></html>", new String[]{
            "Height"}) {
            @Override
            public void function() {
                undoList = ArrayUtil.copyArray(dataArray_List);
                TerrainGenMaster.flood(dataArray_List, this.getOption(0));
                updateImage(dataArray_List);
                updateStats(dataArray_List);
            }
        };
        component.add(flood);
        
        FunctionPane addGaussianRandommness = new FunctionPane("<html><h3>addGaussianRandommness()</h3></html>", new String[]{
            "Min", "Max"}) {
            @Override
            public void function() {
                undoList = ArrayUtil.copyArray(dataArray_List);
                TerrainGenMaster.addGaussianRandomness(dataArray_List, this.getAllOptions());
                updateImage(dataArray_List);
                updateStats(dataArray_List);
            }
        };
        component.add(addGaussianRandommness);
        
        FunctionPane addRandommness = new FunctionPane("<html><h3>addRandommness()</h3></html>", new String[]{
            "Min", "Max"}) {
            @Override
            public void function() {
                undoList = ArrayUtil.copyArray(dataArray_List);
                TerrainGenMaster.addRandomness(dataArray_List, this.getAllOptions());
                updateImage(dataArray_List);
                updateStats(dataArray_List);
            }
        };
        component.add(addRandommness);
        
        FunctionPane laplacian = new FunctionPane("<html><h3>Laplacian()</h3></html>", new String[]{
            "Repeat"}) {
            @Override
            public void function() {
                undoList = ArrayUtil.copyArray(dataArray_List);
                int repeatTimes = (int) this.getOption(0);
                for(int i = 0; i < repeatTimes; i++){
                    TerrainGenMaster.laplacianSmooth(dataArray_List, dataArray_List);
                }
                updateImage(dataArray_List);
                updateStats(dataArray_List);
            }
        };
        component.add(laplacian);
        
        FunctionPane shape = new FunctionPane("<html><h3>MakeFlatShape()</h3></html>", new String[]{
            "Number of Sides of Shape", "Center X", "Center Y", "Radius", "Height"}) {
            @Override
            public void function() {
                undoList = ArrayUtil.copyArray(dataArray_List);
                TerrainGenMaster.makeFlatShape(dataArray_List, this.getAllOptions());
                updateImage(dataArray_List);
                updateStats(dataArray_List);
            }
        };
        component.add(shape);  
        
        FunctionPane keepShape = new FunctionPane("<html><h3>keepShape()</h3></html>", new String[]{
            "Number of Sides of Shape", "Center X", "Center Y", "Radius"}) {
            @Override
            public void function() {
                undoList = ArrayUtil.copyArray(dataArray_List);
                TerrainGenMaster.keepShape(dataArray_List, this.getAllOptions());
                updateImage(dataArray_List);
                updateStats(dataArray_List);
            }
        };
        component.add(keepShape);        
        
        FunctionPane undo = new FunctionPane("<html><h3>Undo()</h3></html>", new String[]{
            }) {
            @Override
            public void function() {
                dataArray_List = undoList;
                updateImage(dataArray_List);
                updateStats(dataArray_List);
            }
        };
        component.add(undo);
        
        stats = new DataPane("<html><h3>Stats</h3></html>", new String[]{
            "Mean", "Max ", "Min "});
        component.add(stats);
        
        FunctionPane mapScale = new FunctionPane("<html><h3>mapScale()</h3></html>", new String[]{
            "Source Low", "Source High"}) {
            @Override
            public void function() {
                TerrainGenMaster.mapScale(this.getAllOptions());
                updateImage(dataArray_List);
                updateStats(dataArray_List);
            }
        };
        component.add(mapScale);
        
        FunctionPane read = new FunctionPane("<html><h3>Read()</h3></html>", new String[]{
        }) {
            @Override
            public void function() {
                dataArray_List = TerrainGenMaster.readBinary();
                updateImage(dataArray_List);
                updateStats(dataArray_List);
            }
        };
        component.add(read);
        
        FunctionPane write = new FunctionPane("<html><h3>Write()</h3></html>", new String[]{
            "File Number"}) {
            @Override
            public void function() {
                TerrainGenMaster.writeImage(dataArray_List, this.getAllOptions());
                TerrainGenMaster.writeBinary(dataArray_List, this.getAllOptions());
            }
        };
        component.add(write);
    }
    
    public void updateImage(double[][] dataArray){
        Image image = TerrainGenMaster.getUpdatedImage(dataArray).getScaledInstance(650, 650, Image.SCALE_SMOOTH);
        picLabel.setIcon(new ImageIcon(image));
    }
    
    public void updateStats(double[][] dataArray){
        Stats stats = StatsUtil.getStats(dataArray);
        double[] data = new double[]{stats.mean, stats.max, stats.min};
        this.stats.updateData(data);
    }
}
