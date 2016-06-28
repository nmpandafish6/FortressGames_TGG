package programbuilder.components;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import programbuilder.components.menu.MenuBar;
import programbuilder.components.panels.*;
import programbuilder.resources.*;
import terraingenerator.*;
import util.StatsUtil;
import util.StatsUtil.Stats;

/**
 * Main Frame for the GUI
 * @author Nicolas
 */
public class GUI_Frame extends JFrame{
    
    private static ArrayList<JLabel> picLabels;
    private static DataPane stats;
    private static GUI_Frame instance;
    
    /**
     * Singleton
     * @return GUI_Frame
     */
    public static GUI_Frame getInstance(){
        if(instance == null) instance = new GUI_Frame();
        return instance;
    }
    
    /**
     * Constructor
     */
    private GUI_Frame(){
        JPanel masterPanel               = new JPanel();
        JMenuBar menu                    = new MenuBar();
        JScrollPane functionScrollPane   = new JScrollPane();
        JPanel functionPanel             = new JPanel();
        ClosableTabPane mapTabPanel      = new ClosableTabPane();
        JSplitPane splitPane             = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            functionScrollPane, mapTabPanel);
        picLabels = new ArrayList<>();
        picLabels.add(new JLabel());
        
        
        GUI_Frame.addFunctions(functionPanel);
        GUI_Frame.generateScrollPane(functionPanel, functionScrollPane);
        mapTabPanel.addClosableTab("default", picLabels.get(0));
        masterPanel.add(splitPane);
        this.setJMenuBar(menu);
        this.add(masterPanel);
        updateImage(null);
        
        mapTabPanel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
            }
        });
    }
    
    /**
     * Generates scroll panel with functions
     * @param functionPanel target panel
     * @param functionScrollPane target scroll pane
     */
    private static void generateScrollPane(JPanel functionPanel, JScrollPane functionScrollPane){
        functionPanel.setPreferredSize(new Dimension((int) (Constants.FUNCTION_PANE_WIDTH*1.1d),
            functionPanel.getPreferredSize().height));
        JViewport functionPanelViewport = new JViewport();
        functionPanelViewport.add(functionPanel);
        functionScrollPane.setViewport(functionPanelViewport);
        functionPanelViewport.setPreferredSize(new Dimension((int) (Constants.FUNCTION_PANE_WIDTH*1.1d), Constants.IMAGE_HEIGHT));
        functionScrollPane.getVerticalScrollBar().setUnitIncrement(Constants.SCROLL_SPEED);
    }
    
    /**
     * Adds functions to target component
     * @param component target component to add functions to.
     */
    private static void addFunctions(JComponent component){
        component.setLayout(new BoxLayout(component, BoxLayout.PAGE_AXIS));

        component.add(FunctionPanes.diamondSquare);
        component.add(FunctionPanes.magicSquare);
        component.add(FunctionPanes.flood);
        component.add(FunctionPanes.thresholdUp);
        component.add(FunctionPanes.thresholdBinary);
        component.add(FunctionPanes.lnFunction);
        component.add(FunctionPanes.addGaussianRandommness);
        component.add(FunctionPanes.addRandommness);
        component.add(FunctionPanes.laplacian);
        component.add(FunctionPanes.shape);  
        component.add(FunctionPanes.keepShape);  
        
        stats = new DataPane("<html><h3>Stats</h3></html>", new String[]{
            "Mean", "Max ", "Min "});
        component.add(stats);
        
        component.add(FunctionPanes.mapScale);
        component.add(FunctionPanes.resize);
        
        component.add(FunctionPanes.binaryAnd);
        component.add(FunctionPanes.binaryOr);
        component.add(FunctionPanes.binaryXOr);
        component.add(FunctionPanes.binaryNot);
        
        component.add(FunctionPanes.automate);  
    }
    
    /**
     * Updates the image according to the data in dataArray
     * @param dataArray heightmap data
     */
    public static void updateImage(double[][] dataArray){
        Image image = TGG_ImageUtil.getUpdatedImage(dataArray).getScaledInstance(Constants.IMAGE_HEIGHT, Constants.IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        picLabels.get(Resources.activeArrayElement).setIcon(new ImageIcon(image));
    }
    
    /**
     * Updates the statistics panels
     * @param dataArray heightmap data
     */
    public static void updateStats(double[][] dataArray){
        Stats statistics = StatsUtil.getStats(dataArray);
        double[] data = new double[]{statistics.mean, statistics.max, statistics.min};
        stats.updateData(data);
    }
}
