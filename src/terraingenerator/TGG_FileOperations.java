package terraingenerator;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import programbuilder.resources.Resources;
import util.ArrayUtil;

/**
 *
 * @author Nicolas
 */
public class TGG_FileOperations {
    
    public static void writeImage(double[][] target){
        BufferedImage image = (BufferedImage) TGG_ImageUtil.getUpdatedImage(target);
        BufferedImage saveImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        saveImage.setRGB(0, 0, saveImage.getWidth(), saveImage.getHeight(), 
            image.getRGB(0, 0, saveImage.getWidth(), saveImage.getHeight(), null, 0, saveImage.getWidth()*saveImage.getHeight()), 0, saveImage.getWidth()*saveImage.getHeight());
        try {
                File file = chooseSaveFile("Save Image");
                String fileName = file.getPath();
                String extension = getExtension(fileName);
                boolean validExtension = false;
                for(String format : ImageIO.getReaderFormatNames())
                    if(extension.equals(format)){
                        validExtension = true;
                        break;
                    }
                if(!validExtension) {
                    extension = "png";
                    fileName = fileName + ".png";
                }
                ImageIO.write(saveImage, extension, new File(fileName));
        } catch (Exception ex) {}
    }
    
    public static void write8BitBinary(double[][] target){
        try {
            File file = chooseSaveFile("Save 8-Bit Binary");  
            String fileName = file.getPath();
            String extension = getExtension(fileName);
            if(!extension.equals("raw")) fileName += ".raw";
            DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));
            int[] array = ArrayUtil.doubleToIntArray((ArrayUtil.scaledOneDimensionalArray_8t(target, Resources.sourceLow, Resources.sourceHigh)));
            for(int i = 0; i < array.length; i++)
                out.write(array[i]);
            out.close();
        } catch (Exception ex) {}
    }
    
    public static void write16BitBinary(double[][] target){
        try {
            File file = chooseSaveFile("Save 16-Bit Binary");  
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
        double[][] target = null;
        try {
            File file = chooseOpenFile("Open Raw");
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int available = in.available();
            int size = 1;
            boolean isByteArray = true;
            System.out.println(Math.sqrt(available/2) % 1);
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
                    target[i / size][i % size] = ((int) (in.read()) | (in.read() << 8)) & 0xffff;
                }
                  
            }        
        } catch (Exception ex) {
            target = new double[1][1];
        }
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
