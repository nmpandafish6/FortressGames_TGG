/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package programbuilder.resources;

import java.util.ArrayList;

/**
 *
 * @author Nicolas
 */
public class Resources {
    
    public static int activeArrayElement = 0;
    public static double sourceLow  = 0;
    public static double sourceHigh = 255;
    public static ArrayList<double[][]> dataArrayList = new ArrayList<>();
    public static ArrayList<double[][]> undoArrayList = new ArrayList<>();
    
    static {
        dataArrayList.add(new double[129][129]);
        undoArrayList.add(new double[129][129]);
    }
}
