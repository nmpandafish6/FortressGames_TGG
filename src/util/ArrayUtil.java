/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

/**
 *
 * @author Nicolas
 */
public class ArrayUtil {
    public static double[] scaledOneDimensionalArray(double[][] arr){
        double[] scaledArray = oneDimensionalArray(arr);
        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;
        for(int i = 0; i < scaledArray.length; i ++){
            if(scaledArray[i] > max) max = scaledArray[i];
            if(scaledArray[i] < min) min = scaledArray[i];
        }
        for(int i = 0; i < scaledArray.length; i ++){
            scaledArray[i] = MathUtil.map(scaledArray[i], min, max, 0, 255);
        }
        return scaledArray;
    }
        
    public static double[] oneDimensionalArray(double[][] arr){
        double[] oneDArray = new double[arr.length * arr.length];
        for(int i = 0; i < arr.length; i ++){
          for(int s = 0; s < arr.length; s ++){
            oneDArray[(i * arr.length) + s] = arr[i][s];
          }
        }
        return oneDArray;
    }
    
    public static int[] doubleToIntArray(double[] arr){
        int[] shortArray = new int[arr.length];
        for(int i = 0; i < arr.length; i++){
            double value = arr[i];
            shortArray[i] = (int) value;
        }
        return shortArray;
    }
}
