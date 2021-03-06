package terraingenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import opencv_ext.TGG_OpenCV_Util;
import org.opencv.core.Point;
import programbuilder.resources.*;
import util.ArrayUtil;
import util.MathUtil;
import util.RandomUtil;
import util.StatsUtil;

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
    
    public static void thresholdUp(double[][] target, double minimum){
        for(int row = 0; row < target.length; row++){
            for(int col = 0; col < target[row].length; col++){
                if(target[row][col] < minimum){
                    target[row][col] = 0;
                }
            }
        }
    }
    
    /**
     * Convert the array to a binary array where values are either 0 or 0xfe
     * @param target array to modify
     * @param threshold value used to differentiate 0s and 1s
     */
    public static void thresholdBinary(double[][] target, double threshold){
        for(int row = 0; row < target.length; row++){
            for(int col = 0; col < target[row].length; col++){
                if(target[row][col] < threshold){
                    target[row][col] = 0;
                }else{
                    target[row][col] = 0xffff;
                }
            }
        }
    }
    
    /**
     * Translates the array in the coordinate plane
     * @param source array to modify
     * @param options x and y translate values
     * @return translated array
     */
    public static double[][] translate(double[][] source, double[] options){
        int deltaX = (int) options[0];
        int deltaY = (int) options[1];
        double[][] target = new double[source.length][source[0].length];
        for(int row = 0; row < target.length; row++){
            for(int col = 0; col < target[row].length; col++){
                if(row-deltaY > -1 && row-deltaY < target.length && col-deltaX > -1 && col-deltaX < target[0].length){
                    target[row][col] = source[row-deltaY][col-deltaX];
                }else{
                    target[row][col] = 0;
                }
            }
        }
        return target;
    }
    
    /**
     * Translates the array in the coordinate plane and rotates elements to other side when applicable
     * @param source array to modify
     * @param options x and y translate values
     * @return translated array
     */
    public static double[][] circularTranslate(double[][] source, double[] options){
        int deltaX = (int) options[0];
        int deltaY = (int) options[1];
        double[][] target = new double[source.length][source[0].length];
        for(int row = 0; row < target.length; row++){
            for(int col = 0; col < target[row].length; col++){
                int yCheck = ((row-deltaY) + target.length) % target.length;
                int xCheck = ((col-deltaX) + target[0].length) % target[0].length;
                if(yCheck > -1 && yCheck < target.length && xCheck > -1 && xCheck < target[0].length){
                    target[row][col] = source[yCheck][xCheck];
                }else{
                    target[row][col] = 0;
                }
            }
        }
        return target;
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
                target[row][col] = weightedAverage;
            }
        }
        return target;
    }
    
    
    
    public static double[][] growOutward(double[][] source){
        double[][] target = new double[source.length][source[0].length];
        for(int row = 0; row < source.length; row++){
            for(int col = 0; col < source[row].length; col++){
                if(source[row][col] == 0){
                    ArrayList<Double> neighbors = new ArrayList<>();
                    int[] offset = new int[]{-1,0,1};
                    for(int y = 0; y < offset.length; y++){
                        for(int x = 0; x < offset.length; x++){
                            int y0 = offset[y] + row;
                            int x0 = offset[x] + col;
                            if(y0 > 0 && y0 < source.length){
                                if(x0 > 0 && x0 < source[y0].length){
                                neighbors.add(source[y0][x0]);
                                }
                            }
                        }
                    }
                    for(int i = 0; i < neighbors.size(); i++){
                        if(neighbors.get(i) > 0){
                            target[row][col] = -1;
                            break;
                        }
                    }
                }else if(source[row][col] > 0){
                    continue;
                }else{
                    // Something went wrong....?
                    continue;
                }
            }
        }
        for(int row = 0; row < target.length; row++){
            for(int col = 0; col < target[row].length; col++){
                if(target[row][col] == -1){
                    target[row][col] = 0xffff;
                }else{
                    target[row][col] = source[row][col];
                }
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
        
        for(int i = min; i <= max; i++){
            Resources.sourceLow = 0;
            Resources.sourceHigh = 255;
            double[][] source = DiamondSquareFractal.diamondSquareGenerate(new double[]{size, 1, 100, 100, 100, 100, 100});
            
//            int[][] original = ArrayUtil.doubleToIntArray(ArrayUtil.twoDimensionalArray(ArrayUtil.scaledOneDimensionalArray_16t(source, Resources.sourceLow, Resources.sourceHigh)));
//            //TGG_Master.flood(source, 100);
//            
//            double[][] buffer0 = ArrayUtil.intToDoubleArray(original);
//            double[] shapeOptions1 = new double[]{17, size/2, size/2, new RandomUtil().randomGaussian(60, 80)};
//            TGG_Master.keepShape(buffer0, shapeOptions1);
//            int[][] array0 = ArrayUtil.doubleToIntArray(buffer0);
//            
//            double[][] buffer1 = ArrayUtil.intToDoubleArray(original);
//            Coordinate coord1 = new RandomUtil().randomGaussianRangeCoord(new Coordinate(size/2, size/2), 30);
//            double[] shapeOptions2 = new double[]{13, coord1.x, coord1.y, new RandomUtil().randomGaussian(60, 80)};
//            TGG_Master.keepShape(buffer1, shapeOptions2);
//            int[][] array1 = ArrayUtil.doubleToIntArray(buffer1);
//            
//            double[][] buffer2 = ArrayUtil.intToDoubleArray(original);
//            Coordinate coord2 = new RandomUtil().randomGaussianRangeCoord(coord1, 30);
//            double[] shapeOptions3 = new double[]{13, coord2.x, coord2.y, new RandomUtil().randomGaussian(60, 80)};
//            TGG_Master.keepShape(buffer2, shapeOptions3);
//            int[][] array2 = ArrayUtil.doubleToIntArray(buffer2);
//            
//            
//            int[][] result1 = TGG_BinaryOperations.binaryOr(array0, array1);
//            int[][] result2 = TGG_BinaryOperations.binaryOr(result1, array2);
//            
//            double[][] result10 = ArrayUtil.intToDoubleArray(result2);
            
            for(int repeat = 0; repeat < 20; repeat++){
                TGG_Master.laplacianSmooth(source, source);
            }
            TGG_FileOperations.write16BitBinary(source, name + i);
            TGG_FileOperations.writeXRaw(source, name + i);
//            double minHeight = TGG_Util.findMinimumEdgeHeight(source);
//            System.out.println("i : " + i + " " + minHeight);
//            //TGG_Master.thresholdBinary(source, minHeight-1);
//            Resources.sourceHigh = 65536;
//            
//            TGG_FileOperations.writeImage(source, name + i);
//            source = TGG_Master.growOutward(source);
//            //TGG_Master.flood(source, 0xfffe);
//            BufferedImage grayImage = TGG_ImageUtil.getUpdatedImage(source);
//            Point[][] contourPoints = TGG_OpenCV_Util.getExternalConvexHullPoints(grayImage);
//            System.out.println(Arrays.deepToString(contourPoints));
            if(i == max) result = source;
            Resources.sourceHigh = 255;
        }
        return result;
    }
    
    
}
