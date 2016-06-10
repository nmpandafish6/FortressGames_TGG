package terraingenerator;

import java.util.*;
import programbuilder.resources.*;

/**
 * Master Class for Generic Terrain Gen Algorithms
 * @author Nicolas
 */
public class TGG_Master {
    
    public static void laplacianSmooth(double[][] source, double[][] target) {
        for(int row = 0; row < source.length; row++){
            for(int col = 0; col < source[row].length; col++){
                double sum = 0;
                int count = 0;
                if(row+1 >= 0 && col >= 0 && row+1 < source.length && col < source.length){
                    sum += source[row+1][col];
                    count++;
                }
                if(row-1 >= 0 && col >= 0 && row-1 < source.length && col < source.length){
                    sum += source[row-1][col];
                    count++;
                }
                if(row >= 0 && col+1 >= 0 && row < source.length && col+1 < source.length){
                    sum += source[row][col+1];
                    count++;
                }
                if(row >= 0 && col-1 >= 0 && row < source.length && col-1 < source.length){
                    sum += source[row][col-1];
                    count++;
                }
                target[row][col] = sum / count;
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
    
    public static void mapScale(double[] options){
        Resources.sourceLow = options[0];
        Resources.sourceHigh = options[1];
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
        double centerX = options[1];
        double centerY = options[2];
        double radius  = options[3];
        double height  = array.length;
        double deltaAngle = 360d / sides;
        for(int row = 0; row < array.length; row++){
            for(int col = 0; col < array[row].length; col++){
                int nearestSide = (int) (((Math.toDegrees( Math.atan2(col - centerX,centerY - row) ) + 360) % 360) / deltaAngle);
                double lowerAngleRad  = Math.toRadians(nearestSide * deltaAngle);
                double upperAngleRad  = Math.toRadians((nearestSide+1) * deltaAngle);
                double p1x = centerX + (Math.sin(lowerAngleRad))*radius;
                double p1y = height-centerY + (Math.cos(lowerAngleRad))*radius;
                double p2x = centerX + (Math.sin(upperAngleRad))*radius;
                double p2y = height-centerY + (Math.cos(upperAngleRad))*radius;
                boolean centerTruth = height-Math.round(centerY) >= Math.round(((p1y - p2y) / (p1x - p2x)) * (centerX - p1x) + p1y);
                boolean pointTruth = height-row >= ((p1y - p2y) / (p1x - p2x)) * (col - p1x) + p1y;
                if(!(centerTruth ^ pointTruth))// is in range
                    array[row][col] = options[4];
                else
                    array[row][col] *= 1;
            }
        }
    }
    
    public static void keepShape(double[][] array, double[] options){
        double sides = options[0];
        double centerX = options[1];
        double centerY = options[2];
        double radius  = options[3];
        double deltaAngle = 360d / sides;
        double height  = array.length;
        for(int row = 0; row < array.length; row++){
            for(int col = 0; col < array[row].length; col++){
                int nearestSide = (int) (((Math.toDegrees(  Math.atan2(col - centerX,centerY - row) ) + 360) % 360) / deltaAngle);
                double lowerAngleRad  = Math.toRadians(nearestSide * deltaAngle);
                double upperAngleRad  = Math.toRadians((nearestSide+1) * deltaAngle);
                double p1x = centerX + (Math.sin(lowerAngleRad))*radius;
                double p1y = height-centerY + (Math.cos(lowerAngleRad))*radius;
                double p2x = centerX + (Math.sin(upperAngleRad))*radius;
                double p2y = height-centerY + (Math.cos(upperAngleRad))*radius;
                boolean centerTruth = height-Math.round(centerY) >= Math.round(((p1y - p2y) / (p1x - p2x)) * (centerX - p1x) + p1y);
                boolean pointTruth = height-row >= ((p1y - p2y) / (p1x - p2x)) * (col - p1x) + p1y;
                if(!(centerTruth ^ pointTruth))// is in range
                    array[row][col] *= 1;
                else
                    array[row][col] *= 0;
            }
        }
    }
}
