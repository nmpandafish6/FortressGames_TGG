package programbuilder.components.panels;

import programbuilder.components.GUI_Frame;
import programbuilder.resources.*;
import terraingenerator.*;
import util.*;
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
            GUI_Frame.updateImage(Resources.dataArrayList.get(0));
            GUI_Frame.updateStats(Resources.dataArrayList.get(0));
        }
    };
    
    /**
     * Magic Square Function
     */
    public static final FunctionPane magicSquare = new FunctionPane("<html><h3>Magic Square()</h3></html>", new String[]{
        "Size"}) {
        @Override
        public void function() {
            Resources.undoArrayList.set(Resources.activeArrayElement, ArrayUtil.copyArray(Resources.dataArrayList.get(0)));
            Resources.dataArrayList.set(Resources.activeArrayElement, TGG_Master.magicSquare((int) this.getOption(0)));
            GUI_Frame.updateImage(Resources.dataArrayList.get(Resources.activeArrayElement));
            GUI_Frame.updateStats(Resources.dataArrayList.get(Resources.activeArrayElement));
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
            GUI_Frame.updateImage(Resources.dataArrayList.get(0));
            GUI_Frame.updateStats(Resources.dataArrayList.get(0));
        }
    };
    
    /**
     * Log Function
     */                    
    public static final FunctionPane lnFunction = new FunctionPane("<html><h3>Ln Function()</h3></html>", new String[]{}) {
        @Override
        public void function() {
            Resources.undoArrayList.set(Resources.activeArrayElement, ArrayUtil.copyArray(Resources.dataArrayList.get(0)));
            TGG_Master.lnFunction(Resources.dataArrayList.get(0));
            GUI_Frame.updateImage(Resources.dataArrayList.get(0));
            GUI_Frame.updateStats(Resources.dataArrayList.get(0));
        }
    };

    /**
     * Add Gaussian Randomness Function
     */    
    public static final FunctionPane addGaussianRandommness = new FunctionPane("<html><h3>addGaussRandommness()</h3></html>", new String[]{
        "Min", "Max"}) {
        @Override
        public void function() {
            Resources.undoArrayList.set(Resources.activeArrayElement, ArrayUtil.copyArray(Resources.dataArrayList.get(0)));
            TGG_Master.addGaussianRandomness(Resources.dataArrayList.get(0), this.getAllOptions());
            GUI_Frame.updateImage(Resources.dataArrayList.get(0));
            GUI_Frame.updateStats(Resources.dataArrayList.get(0));
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
            GUI_Frame.updateImage(Resources.dataArrayList.get(0));
            GUI_Frame.updateStats(Resources.dataArrayList.get(0));
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
            GUI_Frame.updateImage(Resources.dataArrayList.get(0));
            GUI_Frame.updateStats(Resources.dataArrayList.get(0));
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
            GUI_Frame.updateImage(Resources.dataArrayList.get(0));
            GUI_Frame.updateStats(Resources.dataArrayList.get(0));
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
            GUI_Frame.updateImage(Resources.dataArrayList.get(0));
            GUI_Frame.updateStats(Resources.dataArrayList.get(0));
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
            GUI_Frame.updateImage(Resources.dataArrayList.get(0));
            GUI_Frame.updateStats(Resources.dataArrayList.get(0));
        }
    };
    
    /**
     * Map Resize Function
     */
    public static final FunctionPane resize = new FunctionPane("<html><h3>resize()</h3></html>", new String[]{
        "Width", "Height"}) {
        @Override
        public void function() {
            Resources.undoArrayList.set(Resources.activeArrayElement, ArrayUtil.copyArray(Resources.dataArrayList.get(0)));
            Resources.dataArrayList.set(Resources.activeArrayElement, 
                    TGG_Master.scaleArray(Resources.dataArrayList.get(Resources.activeArrayElement), (int) this.getOption(0), (int) this.getOption(1)));
            GUI_Frame.updateImage(Resources.dataArrayList.get(0));
            GUI_Frame.updateStats(Resources.dataArrayList.get(0));
        }
    };
    
    /**
     * Binary AND Function
     */
    public static final BinaryFunctionPane binaryAnd = new BinaryFunctionPane("<html><h3>binary AND()</h3></html>", 2) {
        @Override
        public void function() {
            Resources.undoArrayList.set(Resources.activeArrayElement, ArrayUtil.copyArray(Resources.dataArrayList.get(0)));
            Resources.dataArrayList.set(Resources.activeArrayElement, 
                ArrayUtil.intToDoubleArray(TGG_BinaryOperations.binaryAnd(
                    ArrayUtil.doubleToIntArray(TGG_FileOperations.readBinary(this.getOption(0))),
                    ArrayUtil.doubleToIntArray(TGG_FileOperations.readBinary(this.getOption(1))))));
            GUI_Frame.updateImage(Resources.dataArrayList.get(0));
            GUI_Frame.updateStats(Resources.dataArrayList.get(0));
        }
    };

    /**
     * 
     * Binary OR Function
     */
    public static final BinaryFunctionPane binaryOr = new BinaryFunctionPane("<html><h3>binary OR()</h3></html>", 2) {
        @Override
        public void function() {
            Resources.undoArrayList.set(Resources.activeArrayElement, ArrayUtil.copyArray(Resources.dataArrayList.get(0)));
            Resources.dataArrayList.set(Resources.activeArrayElement, 
                ArrayUtil.intToDoubleArray(TGG_BinaryOperations.binaryOr(
                    ArrayUtil.doubleToIntArray(TGG_FileOperations.readBinary(this.getOption(0))),
                    ArrayUtil.doubleToIntArray(TGG_FileOperations.readBinary(this.getOption(1))))));
            GUI_Frame.updateImage(Resources.dataArrayList.get(0));
            GUI_Frame.updateStats(Resources.dataArrayList.get(0));
        }
    };
    
    /**
     * Binary AND Function
     */
    public static final BinaryFunctionPane binaryXOr = new BinaryFunctionPane("<html><h3>binary XOR()</h3></html>", 2) {
        @Override
        public void function() {
            Resources.undoArrayList.set(Resources.activeArrayElement, ArrayUtil.copyArray(Resources.dataArrayList.get(0)));
            Resources.dataArrayList.set(Resources.activeArrayElement, 
                ArrayUtil.intToDoubleArray(TGG_BinaryOperations.binaryXOr(
                    ArrayUtil.doubleToIntArray(TGG_FileOperations.readBinary(this.getOption(0))),
                    ArrayUtil.doubleToIntArray(TGG_FileOperations.readBinary(this.getOption(1))))));
            GUI_Frame.updateImage(Resources.dataArrayList.get(0));
            GUI_Frame.updateStats(Resources.dataArrayList.get(0));
        }
    };
    /**
     * Binary AND Function
     */
    public static final BinaryFunctionPane binaryNot = new BinaryFunctionPane("<html><h3>binary NOT()</h3></html>", 1) {
        @Override
        public void function() {
            Resources.undoArrayList.set(Resources.activeArrayElement, ArrayUtil.copyArray(Resources.dataArrayList.get(0)));
            Resources.dataArrayList.set(Resources.activeArrayElement, 
                ArrayUtil.intToDoubleArray(TGG_BinaryOperations.binaryNot(
                    ArrayUtil.doubleToIntArray(TGG_FileOperations.readBinary(this.getOption(0))))));
            GUI_Frame.updateImage(Resources.dataArrayList.get(0));
            GUI_Frame.updateStats(Resources.dataArrayList.get(0));
        }
    };
}
