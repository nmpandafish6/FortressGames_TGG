/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package terraingenerator;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import util.ArrayUtil;
import util.MathUtil;

/**
 *
 * @author Nicolas
 */
public class TerrainGenMaster {
    
    public static void write(){
        double[][] a = DiamondSquareFractal.a;
        String out = Arrays.deepToString(a).replace("],", "]\n").replace("[", "").replace("]", "").replace(",", "");
        //System.out.println(" " + out);
        try{
            FileWriter fw = new FileWriter("C:\\Users\\Nicolas\\Documents\\MATLAB\\2D Array.txt");
            fw.write(out);
            fw.close();
        }catch(Exception e){
        }
    }
    
    public static void writeImage(){
        double[][] a = DiamondSquareFractal.a;
        BufferedImage colorImage = new BufferedImage(a.length, a[0].length, BufferedImage.TYPE_BYTE_GRAY);
        colorImage.setRGB(0, 0, a.length, a[0].length, 
                ArrayUtil.doubleToIntArray(ArrayUtil.scaledOneDimensionalArray(a)), 0, a.length);
        
        BufferedImage image = new BufferedImage(a.length, a[0].length, BufferedImage.TYPE_USHORT_GRAY);
        Graphics graphics = image.getGraphics();
        graphics.drawImage(colorImage, 0, 0, null);
        graphics.dispose();
        try {
            ImageIO.write(image, "png", new File("Image.png"));
        } catch (IOException ex) {
            Logger.getLogger(TerrainGenMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Image getUpdatedImage(){
        double[][] a = DiamondSquareFractal.a;
        BufferedImage image = new BufferedImage(a.length, a[0].length, BufferedImage.TYPE_BYTE_GRAY);
        image.setRGB(0, 0, a.length, a[0].length, 
                ArrayUtil.doubleToIntArray(ArrayUtil.scaledOneDimensionalArray(a)), 0, a.length);
        
        try {
            ImageIO.write(image, "png", new File("Image.png"));
        } catch (IOException ex) {
            Logger.getLogger(TerrainGenMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }
    
    public static void writeBinary(){
        DataOutputStream out = null;
        try {
            double[][] a = DiamondSquareFractal.a;
            out = new DataOutputStream(new FileOutputStream("Binary.raw"));
            int[] array = ArrayUtil.doubleToIntArray((ArrayUtil.scaledOneDimensionalArray(a)));
            for(int i = 0; i < array.length; i++){
                out.write(array[i]);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TerrainGenMaster.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TerrainGenMaster.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(TerrainGenMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void laplacianSmooth(double[][] array) {
        for(int row = 0; row < array.length; row++){
            for(int col = 0; col < array[row].length; col++){
                ArrayList<Double> neighborValues = new ArrayList<>();
                
                if(row+1 >= 0 && col >= 0 && row+1 < array.length && col < array.length) {
                    neighborValues.add(array[row+1][col]);
                }
                if(row-1 >= 0 && col >= 0 && row-1 < array.length && col < array.length) {
                    neighborValues.add(array[row-1][col]);
                }
                if(row >= 0 && col+1 >= 0 && row < array.length && col+1 < array.length) {
                    neighborValues.add(array[row][col+1]);
                }
                if(row >= 0 && col-1 >= 0 && row < array.length && col-1 < array.length) {
                    neighborValues.add(array[row][col-1]);
                }
                double average = MathUtil.mean(neighborValues);
                array[row][col] = average;
            }
        }
    }
    
    public static void flood(double[][] array, double floodVal){
        for(int row = 0; row < array.length; row++){
            for(int col = 0; col < array[row].length; col++){
                if(array[row][col] < floodVal){
                    array[row][col] = floodVal;
                }
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
                double angleRad = Math.atan2(col - centerX,centerY - row);
                double angleDeg = (Math.toDegrees(angleRad) + 360) % 360;
                double nearestSide = Math.floor(angleDeg / deltaAngle);
                double lowerAngleRad  = Math.toRadians(nearestSide * deltaAngle);
                double upperAngleRad  = Math.toRadians((nearestSide+1) * deltaAngle);
                double p1x = centerX + (Math.sin(lowerAngleRad))*radius;
                double p1y = height-centerY + (Math.cos(lowerAngleRad))*radius;
                double p2x = centerX + (Math.sin(upperAngleRad))*radius;
                double p2y = height-centerY + (Math.cos(upperAngleRad))*radius;
                boolean centerTruth = height-Math.round(centerY) >= Math.round(((p1y - p2y) / (p1x - p2x)) * (centerX - p1x) + p1y);
                
                boolean pointTruth = height-row >= ((p1y - p2y) / (p1x - p2x)) * (col - p1x) + p1y;
                double rightSide = ((p1y - p2y) / (p1x - p2x)) * (col - p1x) + p1y;
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
                double angleRad = Math.atan2(col - centerX,centerY - row);
                double angleDeg = (Math.toDegrees(angleRad) + 360) % 360;
                double nearestSide = Math.floor(angleDeg / deltaAngle);
                double lowerAngleRad  = Math.toRadians(nearestSide * deltaAngle);
                double upperAngleRad  = Math.toRadians((nearestSide+1) * deltaAngle);
                double p1x = centerX + (Math.sin(lowerAngleRad))*radius;
                double p1y = height-centerY + (Math.cos(lowerAngleRad))*radius;
                double p2x = centerX + (Math.sin(upperAngleRad))*radius;
                double p2y = height-centerY + (Math.cos(upperAngleRad))*radius;
                boolean centerTruth = height-Math.round(centerY) >= Math.round(((p1y - p2y) / (p1x - p2x)) * (centerX - p1x) + p1y);
                
                boolean pointTruth = height-row >= ((p1y - p2y) / (p1x - p2x)) * (col - p1x) + p1y;
                double rightSide = ((p1y - p2y) / (p1x - p2x)) * (col - p1x) + p1y;
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
