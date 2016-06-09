package programbuilder;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import terraingenerator.DiamondSquareFractal;
import terraingenerator.TGG_FileOperations;
import terraingenerator.TerrainGenMaster;
import util.ArrayUtil;
import util.StatsUtil;
import util.StatsUtil.Stats;

/**
 * Main Frame for the GUI
 * @author Nicolas
 */
public class GUI_Frame extends JFrame{
    
    private static JLabel picLabel;
    private final JScrollPane functionScrollPane;
    private final JPanel functionPanel;
    private static double[][] undoList;
    private static DataPane stats;
    public GUI_Frame(){
        JPanel masterPanel = new JPanel();
        Resources.dataArrayList.add(new double[129][129]);
        JMenuBar menu = new MenuBar();
        functionScrollPane = new JScrollPane();
        functionPanel = new JPanel();
        picLabel = new JLabel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                           functionScrollPane, picLabel);
        this.setJMenuBar(menu);
        masterPanel.add(splitPane);
        addFunctions(functionPanel);
        this.add(masterPanel);
        generateScrollPane();
        updateImage(null);
        
    }
    
    private void generateScrollPane(){
        functionPanel.setPreferredSize(new Dimension((int) (Constants.FUNCTION_PANE_WIDTH*1.1d),
                functionPanel.getPreferredSize().height));
        JViewport functionPanelViewport = new JViewport();
        functionPanelViewport.add(functionPanel);
        functionScrollPane.setViewport(functionPanelViewport);
        functionPanelViewport.setPreferredSize(new Dimension((int) (Constants.FUNCTION_PANE_WIDTH*1.1d), 650));
        functionScrollPane.getVerticalScrollBar().setUnitIncrement(Constants.SCROLL_SPEED);
    }
    
    private void addFunctions(JComponent component){
        component.setLayout(new BoxLayout(component, BoxLayout.PAGE_AXIS));
        FunctionPane diamondSquare = new FunctionPane("<html><h3>Diamond Square()</h3></html>", new String[]{
            "Size", "Random Divider", "Upper Left", "Bottom Left", "Upper Right", "Bottom Right",
            "Random Scalar"}) {
            @Override
            public void function() {
                undoList = ArrayUtil.copyArray(Resources.dataArrayList.get(0));
                Resources.dataArrayList.set(0, DiamondSquareFractal.diamondSquareGenerate(this.getAllOptions()));
                updateImage(Resources.dataArrayList.get(0));
                updateStats(Resources.dataArrayList.get(0));
            }
        };
        component.add(diamondSquare);
        
        FunctionPane flood = new FunctionPane("<html><h3>Flood()</h3></html>", new String[]{
            "Height"}) {
            @Override
            public void function() {
                undoList = ArrayUtil.copyArray(Resources.dataArrayList.get(0));
                TerrainGenMaster.flood(Resources.dataArrayList.get(0), this.getOption(0));
                updateImage(Resources.dataArrayList.get(0));
                updateStats(Resources.dataArrayList.get(0));
            }
        };
        component.add(flood);
        
        FunctionPane addGaussianRandommness = new FunctionPane("<html><h3>addGaussianRandommness()</h3></html>", new String[]{
            "Min", "Max"}) {
            @Override
            public void function() {
                undoList = ArrayUtil.copyArray(Resources.dataArrayList.get(0));
                TerrainGenMaster.addGaussianRandomness(Resources.dataArrayList.get(0), this.getAllOptions());
                updateImage(Resources.dataArrayList.get(0));
                updateStats(Resources.dataArrayList.get(0));
            }
        };
        component.add(addGaussianRandommness);
        
        FunctionPane addRandommness = new FunctionPane("<html><h3>addRandommness()</h3></html>", new String[]{
            "Min", "Max"}) {
            @Override
            public void function() {
                undoList = ArrayUtil.copyArray(Resources.dataArrayList.get(0));
                TerrainGenMaster.addRandomness(Resources.dataArrayList.get(0), this.getAllOptions());
                updateImage(Resources.dataArrayList.get(0));
                updateStats(Resources.dataArrayList.get(0));
            }
        };
        component.add(addRandommness);
        
        FunctionPane laplacian = new FunctionPane("<html><h3>Laplacian()</h3></html>", new String[]{
            "Repeat"}) {
            @Override
            public void function() {
                undoList = ArrayUtil.copyArray(Resources.dataArrayList.get(0));
                int repeatTimes = (int) this.getOption(0);
                for(int i = 0; i < repeatTimes; i++){
                    TerrainGenMaster.laplacianSmooth(Resources.dataArrayList.get(0), Resources.dataArrayList.get(0));
                }
                updateImage(Resources.dataArrayList.get(0));
                updateStats(Resources.dataArrayList.get(0));
            }
        };
        component.add(laplacian);
        
        FunctionPane shape = new FunctionPane("<html><h3>MakeFlatShape()</h3></html>", new String[]{
            "Number of Sides of Shape", "Center X", "Center Y", "Radius", "Height"}) {
            @Override
            public void function() {
                undoList = ArrayUtil.copyArray(Resources.dataArrayList.get(0));
                TerrainGenMaster.makeFlatShape(Resources.dataArrayList.get(0), this.getAllOptions());
                updateImage(Resources.dataArrayList.get(0));
                updateStats(Resources.dataArrayList.get(0));
            }
        };
        component.add(shape);  
        
        FunctionPane keepShape = new FunctionPane("<html><h3>keepShape()</h3></html>", new String[]{
            "Number of Sides of Shape", "Center X", "Center Y", "Radius"}) {
            @Override
            public void function() {
                undoList = ArrayUtil.copyArray(Resources.dataArrayList.get(0));
                TerrainGenMaster.keepShape(Resources.dataArrayList.get(0), this.getAllOptions());
                updateImage(Resources.dataArrayList.get(0));
                updateStats(Resources.dataArrayList.get(0));
            }
        };
        component.add(keepShape);        
        
        FunctionPane undo = new FunctionPane("<html><h3>Undo()</h3></html>", new String[]{
            }) {
            @Override
            public void function() {
                Resources.dataArrayList.set(0, undoList);
                updateImage(Resources.dataArrayList.get(0));
                updateStats(Resources.dataArrayList.get(0));
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
                updateImage(Resources.dataArrayList.get(0));
                updateStats(Resources.dataArrayList.get(0));
            }
        };
        component.add(mapScale);
    }
    
    public static void updateImage(double[][] dataArray){
        Image image = TerrainGenMaster.getUpdatedImage(dataArray).getScaledInstance(650, 650, Image.SCALE_SMOOTH);
        picLabel.setIcon(new ImageIcon(image));
    }
    
    public static void updateStats(double[][] dataArray){
        Stats statistics = StatsUtil.getStats(dataArray);
        double[] data = new double[]{statistics.mean, statistics.max, statistics.min};
        stats.updateData(data);
    }
    
    public static double[][] getActiveDataArray(){
        return Resources.dataArrayList.get(0);
    }
}
