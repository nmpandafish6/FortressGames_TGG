/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package terraingenerator;

/**
 *
 * @author Nicolas
 */
public class TGG_BinaryOperations {
    
    /**
     * Performs a binary and between each value
     * @param data1 data1
     * @param data2 data2
     * @return  data1 & data2
     */
    public static int[][] binaryAnd(int[][] data1, int[][] data2){
        int width = Math.min(data1[0].length, data2[0].length);
        int height = Math.min(data1.length, data2.length);
        int[][] target = new int[height][width];
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                target[row][col] = data1[row][col] & data2[row][col];
            }
        }
        return target;
    }
    
    /**
     * Performs a binary or between each value
     * @param data1 data1
     * @param data2 data2
     * @return  data1 | data2
     */
    public static int[][] binaryOr(int[][] data1, int[][] data2){
        int width = Math.min(data1[0].length, data2[0].length);
        int height = Math.min(data1.length, data2.length);
        int[][] target = new int[height][width];
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                target[row][col] = data1[row][col] | data2[row][col];
            }
        }
        return target;
    }
      
    /**
     * Performs a binary xor between each value
     * @param data1 data1
     * @param data2 data2
     * @return  data1 ^ data2
     */
    public static int[][] binaryXOr(int[][] data1, int[][] data2){
        int width = Math.min(data1[0].length, data2[0].length);
        int height = Math.min(data1.length, data2.length);
        int[][] target = new int[height][width];
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                target[row][col] = data1[row][col] ^ data2[row][col];
            }
        }
        return target;
    }
    
    /**
     * Performs a binary not on each value
     * @param data1 data1
     * @return ~data1
     */
    public static int[][] binaryNot(int[][] data1){
        int width = data1[0].length;
        int height = data1.length;
        int[][] target = new int[height][width];
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                target[row][col] = ~data1[row][col];
            }
        }
        return target;
    }
    
}
