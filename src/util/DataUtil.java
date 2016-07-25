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
public class DataUtil {
    
    public static byte[] boolArray2byteArray(boolean[] boolArray){
        int numberOfBytes = (int) Math.ceil(boolArray.length/8d);
        byte[] bytes = new byte[numberOfBytes];
        
        for(int b = 0; b < numberOfBytes; b++){
            for(int i = 0; i < Math.min(boolArray.length-b*8-i, 8); i++){
                bytes[b] |= 1 << i;
            }
        }
        return bytes;
    }
    
    public static byte[] boolArray2D_2byteArray(boolean[][] boolArray){
        boolean[] boolArray_1D = ArrayUtil.oneDimensionalArray(boolArray);
        return boolArray2byteArray(boolArray_1D);
    }
}
