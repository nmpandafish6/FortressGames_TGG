package terraingenerator;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import programbuilder.resources.*;
import util.*;

/**
 *
 * @author Nicolas
 */
public class TGG_ImageUtil {
   
    public static BufferedImage getUpdatedImage(double[][] dataArray){
        BufferedImage image = null;
        if(dataArray == null) {
            image = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
        }else{
            image = new BufferedImage(dataArray.length, dataArray[0].length, BufferedImage.TYPE_BYTE_GRAY);
            image.setRGB(0, 0, dataArray.length, dataArray[0].length, 
                ArrayUtil.doubleToIntArray(ArrayUtil.scaledOneDimensionalArray_8t(dataArray, Resources.sourceLow, Resources.sourceHigh)), 0, dataArray.length);
        }
        return image;
    }
    
    public static double[][] readDataFromImage(BufferedImage image){
        double[][] dataArray;
        
        if(image == null) {
            dataArray = new double[1][1];
        }else{
            int[][] intArray = new int[image.getHeight()][image.getWidth()];
            for(int y = 0; y < intArray.length; y++){
                for(int x = 0; x < intArray[0].length; x++){
                    intArray[y][x] = image.getRGB(x, y);
                }
            }
            intArray = ArrayUtil.byteToIntArray(intArray);
            dataArray = ArrayUtil.intToDoubleArray(ArrayUtil.byteToIntArray(intArray));
        }
        
        return dataArray;
    }
    
    public static BufferedImage getImageFromFile(String path){
        try {
            return ImageIO.read(new File(path));
        } catch (IOException ex) {
            return null;
        }
    }
}
