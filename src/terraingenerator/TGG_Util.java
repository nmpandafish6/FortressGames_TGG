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
public class TGG_Util {
    
    public static double findMinimumEdgeHeight(double[][] source){
        int M = source.length-1;
        int N = source[0].length-1;
        double min = Short.MIN_VALUE;
        for(int row = 0, col = 0, i = 0; i < 4;) {
            //min = source[row][col] > min ? (short) source[row][col] : min;
            min = Math.max(min, (short) source[row][col]);
            System.out.println(min);
            switch(i) {
                case 0: 
                    if(++row == N) ++i;
                break;
                case 1: 
                    if(++col == M) ++i; 
                break;
                case 2: 
                    if(--row == 0) ++i;
                break;
                case 3:
                    if(--col == 0) ++i;
                break;
            }
        }
        System.out.println("MIN : " + min);
        return min;
    }
}
