package terraingenerator;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import programbuilder.resources.Resources;
import util.ArrayUtil;
import util.MathUtil;

/**
 *
 * @author Nicolas
 */
public class TGG_FileOperations {
    
    public static void writeImage(double[][] target){
        File file = chooseSaveFile("Save Image");
        writeImage(target, file.getPath());
    }
    
    public static void writeImage(double[][] target, String destination){
        BufferedImage image = (BufferedImage) TGG_ImageUtil.getUpdatedImage(target);
        BufferedImage saveImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        saveImage.setRGB(0, 0, saveImage.getWidth(), saveImage.getHeight(), 
            image.getRGB(0, 0, saveImage.getWidth(), saveImage.getHeight(), null, 0, saveImage.getWidth()*saveImage.getHeight()), 0, saveImage.getWidth()*saveImage.getHeight());
        try {
            File file = new File(destination);
            String fileName = file.getPath();
            String extension = getExtension(fileName);
            if(!extension.equals("png")) fileName += ".png";
            extension = getExtension(fileName);
            ImageIO.write(saveImage, extension, new File(fileName));
        } catch (IOException ex) {}
    }
    
    public static void write8BitBinary(double[][] target){
        File file = chooseSaveFile("Save 8-Bit Binary");  
        write8BitBinary(target, file.getPath());
    }
    
    public static void write8BitBinary(double[][] target, String destination){
        try {
            File file = new File(destination); 
            String fileName = file.getPath();
            String extension = getExtension(fileName);
            if(!extension.equals("raw")) fileName += ".raw";
            DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));
            int[] array = ArrayUtil.doubleToIntArray((ArrayUtil.scaledOneDimensionalArray_8t(target, Resources.sourceLow, Resources.sourceHigh)));
            for(int i = 0; i < array.length; i++)
                out.write(array[i]);
            out.close();
        } catch (IOException ex) {}
    }
    
    public static void write16BitBinary(double[][] target){
        File file = chooseSaveFile("Save 16-Bit Binary");  
        write16BitBinary(target, file.getPath());
    }
    
    public static void write16BitBinary(double[][] target, String destination){
        try {
            File file = new File(destination);
            String fileName = file.getPath();
            String extension = getExtension(fileName);
            if(!extension.equals("raw")) fileName += ".raw";
            DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));
            int[] array = ArrayUtil.doubleToIntArray((ArrayUtil.scaledOneDimensionalArray_16t(target, Resources.sourceLow, Resources.sourceHigh)));
            for(int i = 0; i < array.length; i++){
                out.write(array[i]);
                out.write(array[i] >> 8);
            }
            out.close();
        } catch (Exception ex) {}
    }
    
    public static double[][] readBinary(){
        File file = chooseOpenFile("Open Raw");
        double[][] target = readBinary(file.getPath());
        return target;
    }
    
    public static double[][] readBinary(String path){
        double[][] target = null;
        try {
            File file = new File(path);
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int available = in.available();
            int size = 1;
            boolean isByteArray = true;
            if(Math.sqrt(available/2) % 1 == 0){
                isByteArray = false;
                size = (int) (Math.sqrt(available / 2));
            }else{
                isByteArray = true;
                size = (int) (Math.sqrt(available));
            }
            target = new double[size][size];
            for(int i = 0; i < size*size; i++){
                if(isByteArray){
                    target[i / size][i % size] = ((int) in.readByte()) & 0xff;
                }else{
                    target[i / size][i % size] = ((int) (in.read() << 8) | (in.read())) & 0xffff;
                }
                  
            }    
                
        } catch (Exception ex) {
            target = new double[1][1];
        }
        return target;
    }
    
    public static void writeXRaw(double[][] target){
        File file = chooseSaveFile("Save XRaw");  
        writeXRaw(target, file.getPath());
    }
    
    public static void writeXRaw(double[][] target, String destination){
        try {
            File file = new File(destination);
            String fileName = file.getPath();
            String extension = getExtension(fileName);
            if(!extension.equals("xraw")) fileName += ".xraw";
            DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));
            int[] array = ArrayUtil.doubleToIntArray((ArrayUtil.scaledOneDimensionalArray_16t(target, Resources.sourceLow, Resources.sourceHigh)));
            double minEdgeHeightTemp = (1 + TGG_Util.findMinimumEdgeHeight(target));
            int minEdgeHeight = (int) MathUtil.map(minEdgeHeightTemp, Resources.sourceLow, Resources.sourceHigh, 0, 0xffff);
            out.write(minEdgeHeight);
            out.write(minEdgeHeight >> 8);
            for(int i = 0; i < array.length; i++){
                out.write(array[i]);
                out.write(array[i] >> 8);
            }
            out.close();
        } catch (Exception ex) {}
    }
    
    /***************************************
     * Private Functions                   *
     ***************************************/
    
    private static String getExtension(String fileName){
        String extension = "";
        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        if (i > p)
            extension = fileName.substring(i+1);
        return extension;
    }
    
    public static File chooseSaveFile(String title){
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(title);
        fc.showSaveDialog(null);
        return fc.getSelectedFile();
    }
    
    public static File chooseOpenFile(String title){
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(title);
        fc.showOpenDialog(null);
        return fc.getSelectedFile();
    }
}
