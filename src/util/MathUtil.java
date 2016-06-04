/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.ArrayList;

/**
 *
 * @author Nicolas
 */
public class MathUtil {
    
    public static double mean(double[] values){
        double average = 0;
        int i = 0;
        for(; i < values.length; i++){
            average += values[i];
        }
        return average / i;
    }
    
    public static double mean(ArrayList<Double> values){
        double average = 0;
        int i = 0;
        for(; i < values.size(); i++){
            average += values.get(i);
        }
        return average / i;
    }
    
    
    public static short meanShorts(short[] values){
        short average = 0;
        int i = 0;
        for(; i < values.length; i++){
            average += values[i];
        }
        return (short) (average / i);
    }
    
    public static short meanShorts(ArrayList<Short> values){
        short average = 0;
        int i = 0;
        for(; i < values.size(); i++){
            average += values.get(i);
        }
        return (short) (average / i);
    }
    
    public static double map(double inputValue, double lowStart, double highStart, double lowEnd, double highEnd){
        double startRange = highStart - lowStart;
        double endRange = highEnd - lowEnd;
        double scalar = endRange / startRange;
        return (inputValue - lowStart) * scalar + lowEnd;
    }
}
