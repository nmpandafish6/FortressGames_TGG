package programbuilder.components;

import programbuilder.resources.Resources;
import static programbuilder.components.GUI_Frame.updateImage;
import static programbuilder.components.GUI_Frame.updateStats;
import terraingenerator.DiamondSquareFractal;
import terraingenerator.TGG_Master;
import util.ArrayUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nicolas
 */
public class FunctionPanes {
    
    /**
     * Diamond Square Function
     */
    public static final FunctionPane diamondSquare = new FunctionPane("<html><h3>Diamond Square()</h3></html>", new String[]{
        "Size", "Random Divider", "Upper Left", "Bottom Left", "Upper Right", "Bottom Right",
        "Random Scalar"}) {
        @Override
        public void function() {
            Resources.undoArrayList.set(Resources.activeArrayElement, ArrayUtil.copyArray(Resources.dataArrayList.get(0)));
            Resources.dataArrayList.set(0, DiamondSquareFractal.diamondSquareGenerate(this.getAllOptions()));
            updateImage(Resources.dataArrayList.get(0));
            updateStats(Resources.dataArrayList.get(0));
        }
    };
            
    /**
     * Flood Function
     */                    
    public static final FunctionPane flood = new FunctionPane("<html><h3>Flood()</h3></html>", new String[]{
        "Height"}) {
        @Override
        public void function() {
            Resources.undoArrayList.set(Resources.activeArrayElement, ArrayUtil.copyArray(Resources.dataArrayList.get(0)));
            TGG_Master.flood(Resources.dataArrayList.get(0), this.getOption(0));
            updateImage(Resources.dataArrayList.get(0));
            updateStats(Resources.dataArrayList.get(0));
        }
    };

    /**
     * Add Gaussian Randomness Function
     */    
    public static final FunctionPane addGaussianRandommness = new FunctionPane("<html><h3>addGaussianRandommness()</h3></html>", new String[]{
        "Min", "Max"}) {
        @Override
        public void function() {
            Resources.undoArrayList.set(Resources.activeArrayElement, ArrayUtil.copyArray(Resources.dataArrayList.get(0)));
            TGG_Master.addGaussianRandomness(Resources.dataArrayList.get(0), this.getAllOptions());
            updateImage(Resources.dataArrayList.get(0));
            updateStats(Resources.dataArrayList.get(0));
        }
    };

    /**
     * Add Randomness Function
     */    
    public static final FunctionPane addRandommness = new FunctionPane("<html><h3>addRandommness()</h3></html>", new String[]{
        "Min", "Max"}) {
        @Override
        public void function() {
            Resources.undoArrayList.set(Resources.activeArrayElement, ArrayUtil.copyArray(Resources.dataArrayList.get(0)));
            TGG_Master.addRandomness(Resources.dataArrayList.get(0), this.getAllOptions());
            updateImage(Resources.dataArrayList.get(0));
            updateStats(Resources.dataArrayList.get(0));
        }
    };
 
    /**
     * Laplacian Smooth Function
     */    
    public static final FunctionPane laplacian = new FunctionPane("<html><h3>Laplacian()</h3></html>", new String[]{
        "Repeat"}) {
        @Override
        public void function() {
            Resources.undoArrayList.set(Resources.activeArrayElement, ArrayUtil.copyArray(Resources.dataArrayList.get(0)));
            int repeatTimes = (int) this.getOption(0);
            for(int i = 0; i < repeatTimes; i++)
                TGG_Master.laplacianSmooth(Resources.dataArrayList.get(0), Resources.dataArrayList.get(0));
            updateImage(Resources.dataArrayList.get(0));
            updateStats(Resources.dataArrayList.get(0));
        }
    };
    
    /**
     * Make Flat Shape Function
     */
    public static final FunctionPane shape = new FunctionPane("<html><h3>MakeFlatShape()</h3></html>", new String[]{
        "Number of Sides of Shape", "Center X", "Center Y", "Radius", "Height"}) {
        @Override
        public void function() {
            Resources.undoArrayList.set(Resources.activeArrayElement, ArrayUtil.copyArray(Resources.dataArrayList.get(0)));
            TGG_Master.makeFlatShape(Resources.dataArrayList.get(0), this.getAllOptions());
            updateImage(Resources.dataArrayList.get(0));
            updateStats(Resources.dataArrayList.get(0));
        }
    };
                            
    /**
     * Keep Shape Function
     */  
    public static final FunctionPane keepShape = new FunctionPane("<html><h3>keepShape()</h3></html>", new String[]{
        "Number of Sides of Shape", "Center X", "Center Y", "Radius"}) {
        @Override
        public void function() {
            Resources.undoArrayList.set(Resources.activeArrayElement, ArrayUtil.copyArray(Resources.dataArrayList.get(0)));
            TGG_Master.keepShape(Resources.dataArrayList.get(0), this.getAllOptions());
            updateImage(Resources.dataArrayList.get(0));
            updateStats(Resources.dataArrayList.get(0));
        }
    };
          
    /**
     * Map Scale Function
     */
    public static final FunctionPane mapScale = new FunctionPane("<html><h3>mapScale()</h3></html>", new String[]{
        "Source Low", "Source High"}) {
        @Override
        public void function() {
            TGG_Master.mapScale(this.getAllOptions());
            updateImage(Resources.dataArrayList.get(0));
            updateStats(Resources.dataArrayList.get(0));
        }
    };
}
