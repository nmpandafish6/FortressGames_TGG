/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package terraingenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import util.MathUtil;
/**
 *
 * @author Nicolas
 */
public class DiamondSquareFractal {

    private static double randomDivider = 4d;
    /**
     * @param args the command line arguments
     */
    static int calls = 0;
    static int diamonds = 0;
    static int squares = 0;
    public static double[][] a;
    
    static Queue<TerrainGenData> stack = new LinkedList<>();
    public static void diamondSquareGenerate(double[] values){
        Random random = new Random();
        int size = (int) values[0];
        a = new double[size][size];
        randomDivider = values[1];
        double upLeft = values[2];
        double dwLeft = values[3];
        double upRight = values[4];
        double dwRight = values[5];
        a[0][0]           = (upLeft * Math.random());
        a[size-1][0]      = (dwLeft * Math.random());
        a[0][size-1]      = (upRight * Math.random());
        a[size-1][size-1] = (dwRight * Math.random());
        diamond(a, new Coordinate(a.length/2,a.length/2), a.length/2);
        while(stack.isEmpty() == false){
            TerrainGenData data = stack.remove();
            if(data.type){
                diamond(a, data.coord, data.size);
            }else{
                square(a, data.coord, data.size);
            }
        }
    }
    
    public static void square(double[][] matrix,Coordinate center,int size){
        int x = center.x;
        int y = center.y;
        if(matrix[y][x] != 0) return;
        calls++;
        squares++;
        //System.out.println(x + " , " + y);
        //           x2,y2
        //   x1,y1   x,y   x3,y1
        //           x2,y3
        int y1 = y;
        int y2 = y-size;
        int y3 = y+size;
        int x1 = x-size;
        int x2 = x;
        int x3 = x+size;
        ArrayList<Double> values = new ArrayList<>();
        if(x1 >= 0 && y1 >= 0 && x1 < matrix.length && y1 < matrix.length) {
            double val1 = matrix[y1][x1];
            values.add(val1);
        }
        if(x2 >= 0 && y2 >= 0 && x2 < matrix.length && y2 < matrix.length) {
            double val2 = matrix[y2][x2];
            values.add(val2);
        }
        if(x2 >= 0 && y3 >= 0 && x2 < matrix.length && y3 < matrix.length) {
            double val3 = matrix[y3][x2];
            values.add(val3);
        }
        if(x3 >= 0 && y1 >= 0 && x3 < matrix.length && y1 < matrix.length) {
            double val4 = matrix[y1][x3];
            values.add(val4);
        }
        if(values.isEmpty()) return;
        
        double average = MathUtil.mean(values);
        double random  = Math.random() * average / randomDivider;
        matrix[y][x] = (average + random);
        if(size/2 > 0){
            
//            if ((x >= 0 && y >= 0 && x + size/2 < matrix.length && y + size/2 < matrix.length)) 
//                diamond(matrix, new Coordinate(x,y), size/2);
//            if ((x+size/2 >= 0 && y >= 0 && x + size < matrix.length && y + size / 2 < matrix.length))
//                diamond(matrix, new Coordinate(x+size/2,y), size/2);
//            if ((x >= 0 && y+size/2 >= 0 && x + size / 2 < matrix.length && y + size < matrix.length))
//                diamond(matrix, new Coordinate(x,y+size/2), size/2);
//            if ((x+size/2 >= 0 && y+size/2 >= 0 && x + size < matrix.length && y + size < matrix.length))
//                diamond(matrix, new Coordinate(x+size/2,y+size/2), size/2);
            
            if ((x+size/2 >= 0 && y-size/2 >= 0 && x+size/2 < matrix.length && y-size/2 < matrix.length))
                if(matrix[y-size/2][x+size/2] == 0)
                    stack.add(new TerrainGenData(new Coordinate(x+size/2,y-size/2), size/2, true));
            if ((x+size/2 >= 0 && y+size/2 >= 0 && x+size/2 < matrix.length && y+size/2 < matrix.length))
                if(matrix[y+size/2][x+size/2] == 0)
                    stack.add(new TerrainGenData(new Coordinate(x+size/2,y+size/2), size/2, true));
        }
    }
    
    
    public static void diamond(double[][] matrix,Coordinate center,int size){
        calls++;
        diamonds++;
        //if(size == 1) return;
        int x = center.x;
        int y = center.y;
        
        double val1 = matrix[y-size][x-size];
        double val2 = matrix[y-size][x+size];
        double val3 = matrix[y+size][x-size];
        double val4 = matrix[y+size][x+size];

        double average = MathUtil.mean(new double[]{val1,val2,val3,val4});
        double random  = Math.random() * average / randomDivider;
        matrix[y][x] = (average + random);
        
        
        if(matrix[y][x+size] == 0)  
            stack.add(new TerrainGenData(new Coordinate(x+size,y), size, false));
        if(matrix[y][x-size] == 0)
            stack.add(new TerrainGenData(new Coordinate(x-size,y), size, false));
        if(matrix[y+size][x] == 0)
            stack.add(new TerrainGenData(new Coordinate(x,y+size), size, false));
        if(matrix[y-size][x] == 0)
            stack.add(new TerrainGenData(new Coordinate(x,y-size), size, false));
    }
    
    public static int sqr(int val){
        return val*val;
    }
}
