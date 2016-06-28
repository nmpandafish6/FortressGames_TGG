package terraingenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import util.MathUtil;
/**
 * Diamond Square Fractal Algorithm
 * @author Nicolas
 */
public class CircularDiamondSquareFractal {

    private static double randomDivider = 4d;
    private static double randomScalar  = 1d;
    private static Queue<TerrainGenData> queue = new LinkedList<>();
    private static int calls = 0;
    
    
    /**
     * Struct declaring diamond or square sub operations
     */
    private static class TerrainGenData{
        public TerrainGenData(Coordinate coord, int msize, boolean type){
            this.coord = coord;
            this.size = msize;
            this.type = type;
        }
        public Coordinate coord;
        public int size;
        public boolean type;// true for diamond
    }
    
    /**
     * Performs the diamond square operation
     * @param options size, randomDivider, upLeft, dwLeft, upRight, dwRight, and randomScalar (in that order)
     * @return array of diamond square data
     */
    public static double[][] diamondSquareGenerate(double[] options){
        calls = 0;
        int size = (int) options[0];
        double[][] buffer = new double[size][size];
        randomDivider = options[1];
        double upLeft = options[2];
        double dwLeft = options[3];
        double upRight = options[4];
        double dwRight = options[5];
        randomScalar   = options[6];
        buffer[0][0]           = (upLeft * Math.random());
        buffer[size-1][0]      = (dwLeft * Math.random());
        buffer[0][size-1]      = (upRight * Math.random());
        buffer[size-1][size-1] = (dwRight * Math.random());
        diamond(buffer, new Coordinate(buffer.length/2,buffer.length/2), buffer.length/2);
        while(!queue.isEmpty()){
            TerrainGenData data = queue.remove();
            if(data.type){
                diamond(buffer, data.coord, data.size);
            }else{
                square(buffer, data.coord, data.size);
            }
        }
        return buffer;
    }
    
    /**
     * Performs a square operation in target
     * @param target array to modify
     * @param center center of square
     * @param size size of square (left to center)
     */
    public static void square(double[][] target, Coordinate center,int size){
        calls++;
        int x = center.x;
        int y = center.y;
        if(target[y][x] != 0) return;
        //           x,y2
        //   x1,y    x,y   x3,y
        //           x,y3
        int y2 = y-size;
        int y3 = y+size;
        int x1 = x-size;
        int x3 = x+size;
        ArrayList<Double> values = new ArrayList<>();
        //if(x1 >= 0 && y >= 0 && x1 < target.length && y < target.length)
            values.add(target[(y+target.length) % target.length][(x1+target[0].length) % target[0].length]);
        //if(x >= 0 && y2 >= 0 && x < target.length && y2 < target.length)
            values.add(target[(y2+target.length) % target.length][(x+target[0].length) % target[0].length]);
        //if(x >= 0 && y3 >= 0 && x < target.length && y3 < target.length)
            values.add(target[(y3+target.length) % target.length][(x+target[0].length) % target[0].length]);
        //if(x3 >= 0 && y >= 0 && x3 < target.length && y < target.length)
            values.add(target[(y+target.length) % target.length][(x3+target[0].length) % target[0].length]);
        if(values.isEmpty()) return;
        double average = MathUtil.mean(values);
        double random  = Math.random() * randomScalar / Math.log(4*calls+2) / randomDivider;
        target[(y) % target.length][(x) % target[0].length] = (average + random);
        if (size/2 > 0 && x+size/2 >= 0 && x+size/2 < target.length){
            if (y-size/2 >= 0 && y-size/2 < target.length && target[y-size/2][x+size/2] == 0)
                queue.add(new TerrainGenData(new Coordinate(x+size/2,y-size/2), size/2, true));
            if (y+size/2 >= 0 && y+size/2 < target.length && target[y+size/2][x+size/2] == 0)
                queue.add(new TerrainGenData(new Coordinate(x+size/2,y+size/2), size/2, true));               
        }
    }
    
    /**
     * Performs a square operation in target
     * @param target array to modify
     * @param center center of square
     * @param size size of square (left to center)
     */
    public static void diamond(double[][] target, Coordinate center,int size){
        calls++;
        int x = center.x;
        int y = center.y;
        double average = MathUtil.mean(new double[]{
            target[y-size][x-size],
            target[y-size][x+size],
            target[y+size][x-size],
            target[y+size][x+size]
        });
        double random  = Math.random() * randomScalar / Math.log(4*calls+2) / randomDivider;
        target[y][x] = (average + random);
        if(target[y][x+size] == 0)  
            queue.add(new TerrainGenData(new Coordinate(x+size,y), size, false));
        if(target[y][x-size] == 0)
            queue.add(new TerrainGenData(new Coordinate(x-size,y), size, false));
        if(target[y+size][x] == 0)
            queue.add(new TerrainGenData(new Coordinate(x,y+size), size, false));
        if(target[y-size][x] == 0)
            queue.add(new TerrainGenData(new Coordinate(x,y-size), size, false));
    }
}
