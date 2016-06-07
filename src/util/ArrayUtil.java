package util;

/**
 *
 * @author Nicolas
 */
public class ArrayUtil {
    public static double[] scaledOneDimensionalArray(double[][] arr, double lowStart, double highStart){
        double[] scaledArray = oneDimensionalArray(arr);
        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;
        for(int i = 0; i < scaledArray.length; i ++){
            if(scaledArray[i] > max) max = scaledArray[i];
            if(scaledArray[i] < min) min = scaledArray[i];
        }
        for(int i = 0; i < scaledArray.length; i ++){
            scaledArray[i] = MathUtil.map(scaledArray[i], lowStart, highStart, 0, 255);
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
    
    public static double[][] copyArray(double[][] source){
        double[][] target = null;
        if(source != null){
            target = new double[source.length][source[0].length];
            for(int row = 0; row < source.length; row++){
                for(int col = 0; col < source[row].length; col++){
                    target[row][col] = source[row][col];
                }
            }
        }else{
            target = new double[1][1];
            target[0][0] = 0;
        }
        return target;
    }
}
