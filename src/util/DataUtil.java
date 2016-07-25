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
        int numberOfBytes = boolArray.length/8;
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
        int numberOfBytes = boolArray_1D.length/8;
        byte[] bytes = new byte[numberOfBytes];
        for(int b = 0; b < numberOfBytes; b++){
            for(int i = 0; i < Math.min(boolArray_1D.length-b*8-i, 8); i++){
                bytes[b] |= 1 << i;
            }
        }
        return bytes;
    }
}
