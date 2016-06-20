/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.Random;
import terraingenerator.Coordinate;

/**
 *
 * @author Nicolas
 */
public class RandomUtil {
    
    private Random rand;
    public RandomUtil(){
        rand = new Random();
    }
    
    public Coordinate randomGaussianRangeCoord(Coordinate point, double range){
        double deltaX = rand.nextGaussian() * range;
        double deltaY = rand.nextGaussian() * range;
        Coordinate result = new Coordinate((int) (point.x + deltaX), (int)(point.y + deltaY));
        return result;
    }
    
    public double randomGaussian(double min, double max){
        return rand.nextGaussian() * (max - min) / 2;
    }
}
