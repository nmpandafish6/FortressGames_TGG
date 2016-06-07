package terraingenerator;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import util.ArrayUtil;
import util.MathUtil;

/**
 * Master Class for Generic Terrain Gen Algorithms
 * @author Nicolas
 */
public class TerrainGenMaster {
        
    public static void writeImage(double[][] target, double[] options){
        BufferedImage image = (BufferedImage) getUpdatedImage(target);
        try {
            int fileNumber = (int) options[0];
            ImageIO.write(image, "png", new File("Image_" + fileNumber + ".png"));
        } catch (IOException ex) {
            Logger.getLogger(TerrainGenMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Image getUpdatedImage(double[][] dataArray){
        BufferedImage image = null;
        if(dataArray == null) {
            image = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
        }else{
            image = new BufferedImage(dataArray.length, dataArray[0].length, BufferedImage.TYPE_BYTE_GRAY);
            image.setRGB(0, 0, dataArray.length, dataArray[0].length, 
                ArrayUtil.doubleToIntArray(ArrayUtil.scaledOneDimensionalArray(dataArray)), 0, dataArray.length);
        }
        return image;
    }
    
    public static void writeBinary(double[][] target, double[] options){
        DataOutputStream out = null;
        try {
            int fileNumber = (int) options[0];
            out = new DataOutputStream(new FileOutputStream("Binary_" + fileNumber + ".raw"));
            int[] array = ArrayUtil.doubleToIntArray((ArrayUtil.scaledOneDimensionalArray(target)));
            for(int i = 0; i < array.length; i++)
                out.write(array[i]);
            out.close();
        } catch (Exception ex) {
            Logger.getLogger(TerrainGenMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void laplacianSmooth(double[][] source, double[][] target) {
        for(int row = 0; row < source.length; row++){
            for(int col = 0; col < source[row].length; col++){
                ArrayList<Double> neighborValues = new ArrayList<>();
                if(row+1 >= 0 && col >= 0 && row+1 < source.length && col < source.length)
                    neighborValues.add(source[row+1][col]);
                if(row-1 >= 0 && col >= 0 && row-1 < source.length && col < source.length)
                    neighborValues.add(source[row-1][col]);
                if(row >= 0 && col+1 >= 0 && row < source.length && col+1 < source.length)
                    neighborValues.add(source[row][col+1]);
                if(row >= 0 && col-1 >= 0 && row < source.length && col-1 < source.length)
                    neighborValues.add(source[row][col-1]);
                double average = MathUtil.mean(neighborValues);
                target[row][col] = average;
            }
        }
    }
    
    public static void flood(double[][] target, double floodVal){
        for(int row = 0; row < target.length; row++){
            for(int col = 0; col < target[row].length; col++){
                if(target[row][col] < floodVal){
                    target[row][col] = floodVal;
                }
            }
        }
    }
    
    public static void addGaussianRandomness(double[][] target, double[] options){
        Random random = new Random();
        double min = options[0];
        double max = options[1];
        double range = max - min;
        for(int row = 0; row < target.length; row++){
            for(int col = 0; col < target[row].length; col++){
                target[row][col] += random.nextGaussian()*range + min;
            }
        }
    }
    
    public static void addRandomness(double[][] target, double[] options){
        Random random = new Random();
        double min = options[0];
        double max = options[1];
        double range = max - min;
        for(int row = 0; row < target.length; row++){
            for(int col = 0; col < target[row].length; col++){
                target[row][col] += random.nextDouble()*range + min;
            }
        }
    }
    
    public static void makeFlatShape(double[][] array, double[] options){
        double sides = options[0];
        double deltaAngle = 360d / sides;
        double centerX = options[1];
        double centerY = options[2];
        double radius  = options[3];
        double height  = array.length;
        for(int row = 0; row < array.length; row++){
            for(int col = 0; col < array[row].length; col++){
                double angleDeg = (Math.toDegrees(      Math.atan2(col - centerX,centerY - row)     ) + 360) % 360;
                int nearestSide = (int) (angleDeg / deltaAngle);
                double lowerAngleRad  = Math.toRadians(nearestSide * deltaAngle);
                double upperAngleRad  = Math.toRadians((nearestSide+1) * deltaAngle);
                double p1x = centerX + (Math.sin(lowerAngleRad))*radius;
                double p1y = height-centerY + (Math.cos(lowerAngleRad))*radius;
                double p2x = centerX + (Math.sin(upperAngleRad))*radius;
                double p2y = height-centerY + (Math.cos(upperAngleRad))*radius;
                boolean centerTruth = height-Math.round(centerY) >= Math.round(((p1y - p2y) / (p1x - p2x)) * (centerX - p1x) + p1y);
                boolean pointTruth = height-row >= ((p1y - p2y) / (p1x - p2x)) * (col - p1x) + p1y;
                boolean finalTruth = !(centerTruth ^ pointTruth);
                if(finalTruth){// is in range
                    array[row][col] = options[4];
                }else{
                    array[row][col] *= 1;
                }
            }
        }
    }
    
    public static void keepShape(double[][] array, double[] options){
        double sides = options[0];
        double deltaAngle = 360d / sides;
        double centerX = options[1];
        double centerY = options[2];
        double radius  = options[3];
        double height  = array.length;
        for(int row = 0; row < array.length; row++){
            for(int col = 0; col < array[row].length; col++){
                double angleDeg = (Math.toDegrees(  Math.atan2(col - centerX,centerY - row) ) + 360) % 360;
                int nearestSide = (int) (angleDeg / deltaAngle);
                double lowerAngleRad  = Math.toRadians(nearestSide * deltaAngle);
                double upperAngleRad  = Math.toRadians((nearestSide+1) * deltaAngle);
                double p1x = centerX + (Math.sin(lowerAngleRad))*radius;
                double p1y = height-centerY + (Math.cos(lowerAngleRad))*radius;
                double p2x = centerX + (Math.sin(upperAngleRad))*radius;
                double p2y = height-centerY + (Math.cos(upperAngleRad))*radius;
                boolean centerTruth = height-Math.round(centerY) >= Math.round(((p1y - p2y) / (p1x - p2x)) * (centerX - p1x) + p1y);
                boolean pointTruth = height-row >= ((p1y - p2y) / (p1x - p2x)) * (col - p1x) + p1y;
                boolean finalTruth = !(centerTruth ^ pointTruth);
                if(finalTruth){// is in range
                    array[row][col] *= 1;
                }else{
                    array[row][col] *= 0;
                }
            }
        }
    }
}
