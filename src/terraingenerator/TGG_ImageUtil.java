/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package terraingenerator;

import java.awt.image.*;
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
                ArrayUtil.doubleToIntArray(ArrayUtil.scaledOneDimensionalArray(dataArray, Resources.sourceLow, Resources.sourceHigh)), 0, dataArray.length);
        }
        return image;
    }
}
