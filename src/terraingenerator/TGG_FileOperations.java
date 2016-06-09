/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package terraingenerator;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import static terraingenerator.TerrainGenMaster.getUpdatedImage;
import static terraingenerator.TerrainGenMaster.sourceHigh;
import static terraingenerator.TerrainGenMaster.sourceLow;
import util.ArrayUtil;

/**
 *
 * @author Nicolas
 */
public class TGG_FileOperations {
    
    public static void writeImage(double[][] target){
        BufferedImage image = (BufferedImage) getUpdatedImage(target);
        BufferedImage saveImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        saveImage.setRGB(0, 0, saveImage.getWidth(), saveImage.getHeight(), 
                image.getRGB(0, 0, saveImage.getWidth(), saveImage.getHeight(), null, 0, saveImage.getWidth()*saveImage.getHeight()), 0, saveImage.getWidth()*saveImage.getHeight());
        try {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Save Image");
            fc.showSaveDialog(null);
            if(fc.accept(null)){
                File file = fc.getSelectedFile();
                String fileName = file.getPath();
                String extension = getExtension(fileName);
                boolean validExtension = false;
                for(String format : ImageIO.getReaderFormatNames())
                    if(extension.equals(format)){
                        validExtension = true;
                        break;
                    }
                if(!validExtension) fileName = fileName + ".png";
                ImageIO.write(saveImage, extension, new File(fileName));
            }
        } catch (Exception ex) {}
    }
    
    public static void writeBinary(double[][] target){
        try {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Save Raw");
            fc.showSaveDialog(null);
            if(fc.accept(null)){
                File file = fc.getSelectedFile();
                String fileName = file.getPath();
                String extension = getExtension(fileName);
                if(!extension.equals("raw")) fileName += ".raw";
                DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));
                int[] array = ArrayUtil.doubleToIntArray((ArrayUtil.scaledOneDimensionalArray(target, sourceLow, sourceHigh)));
                for(int i = 0; i < array.length; i++)
                    out.write(array[i]);
                out.close();
            }
        } catch (Exception ex) {}
    }
    
    public static double[][] readBinary(){
        double[][] target = null;
        try {
            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(null);
            if(fc.accept(null)){
                File file = fc.getSelectedFile();
                DataInputStream in = new DataInputStream(new FileInputStream(file));
                int size = (int) (Math.sqrt(in.available()));
                target = new double[size][size];
                for(int i = 0; i < size*size; i++)
                    target[i / size][i % size] = ((int) in.readByte()) & 0xff;
            }
        } catch (Exception ex) {
            target = new double[1][1];
        }
        return target;
    }
    
    private static String getExtension(String fileName){
        String extension = "";
        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        if (i > p)
            extension = fileName.substring(i+1);
        return extension;
    }
}
