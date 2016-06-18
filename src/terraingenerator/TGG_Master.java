package terraingenerator;

import java.io.File;
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
    
    public static void lnFunction(double[][] target){
        for(int row = 0; row < target.length; row++){
            for(int col = 0; col < target[row].length; col++){
                target[row][col] = Math.log(target[row][col] + 1);
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
    
    public static double[][] magicSquare(int width){
        double[][] target = new double[width][width];
        int x = width - 1;
        int y = width / 2;
        for(int i = 1; i < width*width + 1; i++){
            while(target[y][x] != 0){
                x -= 2;
                y++;
                y = (y + width) % width;
                x = (x + width) % width;
                if(y == -1 && x == width){
                    y = 0;
                    x = width - 2;
                }
                y = (y + width) % width;
                x = (x + width) % width;
            }
            
            y = (y + width) % width;
            x = (x + width) % width;
            target[y][x] = i;
            y--;
            x++;
            y = (y + width) % width;
            x = (x + width) % width;
            if(y == -1 && x == width){
                    y = 0;
                    x = width - 2;
            }
            y = (y + width) % width;
            x = (x + width) % width;
        }
        System.out.println(Arrays.deepToString(target));
        return target;
    }
    
    public static double[][] scaleArray(double[][] source, int width, int height){
        double[][] target = new double[height][width];
        double xStepSize = ((double)(source[0].length - 1)) / (width - 1);
        double yStepSize = ((double)(source.length - 1)) / (height - 1);
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                double deltaY = yStepSize * row;
                double deltaX = xStepSize * col;
                System.out.println(yStepSize + " , " + xStepSize);
                int x1 = (int) Math.floor(deltaX);
                int y1 = (int) Math.floor(deltaY);
                int x2 = (int) Math.ceil(deltaX);
                int y2 = (int) Math.ceil(deltaY);
            
                double val1 = source[y1][x1];
                double val2 = source[y1][x2];
                double val3 = source[y2][x1];
                double val4 = source[y2][y2];
                
                double weightedAverage = (((val2 - val1) * (deltaX % 1) + val1) +
                                          ((val4 - val2) * (deltaY % 1) + val2) +
                                          ((val4 - val3) * (deltaX % 1) + val3) +
                                          ((val3 - val1) * (deltaY % 1) + val1)) / 4d;
                System.out.println(val1 + " , " + val2 + " , " + val3 + " , " + val4);
                target[row][col] = weightedAverage;
            }
        }
        return target;
    }
    
    public static double[][] automate(double[] options){
        int size = (int) options[0];
        int min   = (int) options[1];
        int max   = (int) options[2];
        double[][] result = null;
        File file = TGG_FileOperations.chooseSaveFile("Save 16 Bit File (Generic Name)");
        String name = file.getAbsolutePath();
        for(int i = min; i < max; i++){
            double[][] source = DiamondSquareFractal.diamondSquareGenerate(new double[]{size, 1, 100, 100, 100, 100, 100});
            
            TGG_FileOperations.write16BitBinary(source, name + i);
            if(i == max - 1) result = source;
        }
        return result;
    }
}
